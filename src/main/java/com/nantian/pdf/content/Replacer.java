package com.nantian.pdf.content;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ILocationExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.kernel.pdf.canvas.parser.listener.RegexBasedLocationExtractionStrategy;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleaner;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Replacer implements IPdfReplacer {
    private static PdfFont font;
    static {
        try {
            font = PdfFontFactory.createRegisteredFont("helvetica");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private final ReplacerStrategy replacerStrategy;
    private FilteredEventListener contentListener;
    private final List<PdfCleanUpLocation> cleanUpLocations=new ArrayList<>();
    private final List<ILocationExtractionStrategy> locationExtractionStrategies=new ArrayList<>();
    private final int pageNumber;
    private boolean used = false;
    public Replacer(int pageNumber, Rectangle[] regions){
        this.pageNumber=pageNumber;
        replacerStrategy= ReplacerStrategy.BY_POSITION;
        for(Rectangle rectangle:regions)
        {
            cleanUpLocations.add(new PdfCleanUpLocation(pageNumber, new Rectangle(rectangle)));
        }
    }
    public Replacer(int pageNumber, String[] values){
        this.pageNumber=pageNumber;
        replacerStrategy=ReplacerStrategy.BY_STRING_VALUE;
        contentListener=new FilteredEventListener();
        for(String value:values){
            ILocationExtractionStrategy strategy=new RegexBasedLocationExtractionStrategy(value);
            locationExtractionStrategies.add(strategy);
            contentListener.attachEventListener(strategy);
        }
    }

    @Override
    public void replace(String sourceFile, String outputFile) throws Exception {
        if(used){
            throw new IllegalStateException("实列不能重复使用");
        }
        used = true;
        InputStream inputStream = new FileInputStream(sourceFile);
        OutputStream outputStream=new FileOutputStream(outputFile);
        replace(inputStream, outputStream);
    }

    @Override
    public void replace(InputStream inputStream, OutputStream outputStream) throws Exception {
        PdfReader reader=new PdfReader(inputStream);
        reader.setUnethicalReading(true);
        PdfDocument document=new PdfDocument(reader, new PdfWriter(outputStream));
        if(replacerStrategy==ReplacerStrategy.BY_STRING_VALUE)
        {
            PdfDocumentContentParser parser=new PdfDocumentContentParser(document);
            parser.processContent(pageNumber, contentListener);
            cleanUpLocations.clear();
            for(ILocationExtractionStrategy strategy:locationExtractionStrategies){
                Collection<IPdfTextLocation> locations=strategy.getResultantLocations();
                for(IPdfTextLocation location:locations){
                    cleanUpLocations.add(new PdfCleanUpLocation(pageNumber, location.getRectangle().moveRight(2)));
                }
            }
        }
        PdfCleaner.cleanUp(document, cleanUpLocations);

        PdfPage page=document.getPage(pageNumber);

        PdfCanvas canvas=new PdfCanvas(
                page.newContentStreamAfter(),
                page.getResources(),
                document);
        canvas.saveState();
        for(PdfCleanUpLocation location:cleanUpLocations) {
            canvas.beginText();
            canvas.setTextMatrix(location.getRegion().getX(), location.getRegion().getY() - 2);
            canvas.setFontAndSize(font, 10);
            canvas.showText("*****");
            canvas.endText();
        }
        document.close();
    }
}
