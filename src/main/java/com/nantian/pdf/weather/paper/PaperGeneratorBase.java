package com.nantian.pdf.weather.paper;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.property.*;
import com.nantian.pdf.parse.ElementLocationLitener;
import com.nantian.pdf.parse.IElementLocationLitener;
import com.nantian.pdf.parse.IPdfElementLocation;
import com.nantian.pdf.weather.config.Font;
import com.nantian.pdf.weather.config.IPageSetting;
import com.nantian.pdf.weather.config.IPapersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public abstract class PaperGeneratorBase implements IPaperGenerator {
    protected final IPapersConfig config;
    private final IPageSetting pageSetting;
    protected Logger logger= LoggerFactory.getLogger(getClass());

    protected PaperGeneratorBase(IPapersConfig config) {
        this.config = config;
        String name = getName();
        this.pageSetting = config.getSettings().get(name);
    }

    public IPageSetting getPageSetting() {
        return pageSetting;
    }

    protected float getWidth() {
        return getPageSetting().getPageSize().getWidth()
                - getPageSetting().getLeftMargin()
                - getPageSetting().getRightMargin();
    }

    protected float getHeight() {
        return getPageSetting().getPageSize().getHeight()
                - getPageSetting().getTopMargin()
                - getPageSetting().getBottomMargin();
    }

    protected float getTop() {
        return getPageSetting().getPageSize().getHeight()
                - getPageSetting().getBottomMargin();
    }

    protected float getBottom() {
        return getPageSetting().getTopMargin();
    }

    protected float getLeft() {
        return getPageSetting().getLeftMargin();
    }

    protected float getRight() {
        return getPageSetting().getPageSize().getWidth()
                - getPageSetting().getRightMargin();
    }

    protected Document createDocument(FontCollection fonts, Map<String, Object> params, InputStream input, OutputStream output) throws IOException {
        PdfDocument pdfDocument;
        if (input != null) {
            pdfDocument = new PdfDocument(new PdfReader(input), new PdfWriter(output));
        } else {
            pdfDocument = new PdfDocument(new PdfWriter(output));
        }
        onPdfDocumentCreated(pdfDocument, params);
        Document document = new Document(pdfDocument, PageSize.A4);
        document.setTextAlignment(TextAlignment.JUSTIFIED);
        document.setMargins(
                getPageSetting().getTopMargin(),
                getPageSetting().getRightMargin(),
                getPageSetting().getBottomMargin(),
                getPageSetting().getLeftMargin());
        Font font = getPageSetting().getDefaultFont();
        document.setFontSize(font.getSize());
        document.setFont(fonts.get(font.getName()));
        onDocumentCreated(document, params);
        return document;
    }

    public void generate(Map<String, Object> params, String output) throws IOException {
        FontCollection fonts = config.createFonts();
        //输出文档主要部分到内存文件
        OutputStream bodyOutput = hasFinalFooter() ? new ByteArrayOutputStream() : new FileOutputStream(output);
        Document document = createDocument(fonts, params, null, bodyOutput);
        createBody(document, fonts, params);
        document.close();
        if (hasFinalFooter()) {  //如果有末尾置底部分
            fonts = config.createFonts();
            ByteArrayOutputStream footerOutput = new ByteArrayOutputStream();
            document = createDocument(fonts, params, null, footerOutput);
            IBlockElement footer = createFooter(fonts, params);
            if (footer != null) {
                document.add(footer);
            }
            document.close();
            if (footer != null) {
                assert bodyOutput instanceof ByteArrayOutputStream;
                byte[] bodyBytes = ((ByteArrayOutputStream) bodyOutput).toByteArray();
                byte[] footerBytes = footerOutput.toByteArray();
                InputStream bodyInput = new ByteArrayInputStream(bodyBytes);
                InputStream footerInput = new ByteArrayInputStream(footerBytes);
                List<IPdfElementLocation> bodyLocations = calcElementsLocations(bodyInput, -1);
                List<IPdfElementLocation> footerLocations = calcElementsLocations(footerInput, -1);
                logger.info(footerLocations.toString());
                //两者都有内容，才进行
                if (bodyLocations.size() > 0 && footerLocations.size() > 0) {
                    fonts = config.createFonts();
                    OutputStream finalOutput = new FileOutputStream(output);
                    bodyInput = new ByteArrayInputStream(bodyBytes);
                    document = createDocument(fonts, params, bodyInput, finalOutput);
                    float bodyBottom = bodyLocations.get(0).getRectangle().getBottom();
                    float footerTop = footerLocations.get(footerLocations.size() - 1).getRectangle().getTop();
                    float footerBottom = footerLocations.get(0).getRectangle().getBottom();
                    logger.info("body:{}, footer top: {}, footer bottom: {}", bodyBottom, footerTop, footerBottom);

                    if (footerTop - footerBottom + getPageSetting().getBottomMargin() > bodyBottom) {
                        document.getPdfDocument().addNewPage();
                    }
                    PdfPage lastPage = document.getPdfDocument().getLastPage();
                    footer = createFooter(fonts, params);
                    PdfCanvas pdfCanvas = new PdfCanvas(lastPage);
                    Rectangle bounds = new Rectangle(getPageSetting().getLeftMargin(), getPageSetting().getBottomMargin(), getWidth(), footerTop - footerBottom);
                    Canvas canvas = new Canvas(pdfCanvas, bounds);
                    //和 footer.setFixedPosition等效
                    footer.setProperty(Property.POSITION, LayoutPosition.FIXED);
                    footer.setProperty(Property.LEFT, getPageSetting().getLeftMargin());
                    footer.setProperty(Property.BOTTOM, getPageSetting().getBottomMargin());
                    footer.setProperty(Property.WIDTH, UnitValue.createPointValue(getWidth()));
                    canvas.add(footer);
                    canvas.close();
                    beforeDocumentClosed(document, params);
                    document.close();
                }

            }
        }
    }

    protected List<IPdfElementLocation> calcElementsLocations(InputStream input, int page) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfReader(input));
        PdfDocumentContentParser parser = new PdfDocumentContentParser(pdfDocument);
        IElementLocationLitener litener = new ElementLocationLitener();
        parser.processContent(page <= 0 ?
                pdfDocument.getNumberOfPages() :
                (Math.min(page, pdfDocument.getNumberOfPages())), litener);
        pdfDocument.close();
        return litener.getLocations();
    }

    protected void onPdfDocumentCreated(PdfDocument pdfDocument, Map<String, Object> params) {
    }

    protected void onDocumentCreated(Document document, Map<String, Object> params) {
    }

    protected abstract void createBody(Document document, FontCollection fonts, Map<String, Object> params) throws MalformedURLException;

    protected IBlockElement createFooter(FontCollection fonts, Map<String, Object> params) {
        return null;
    }

    protected boolean hasFinalFooter() {
        return true;
    }

    protected void beforeDocumentClosed(Document document, Map<String, Object> params) {
    }
    protected void addImage(Document document, Map<String, Object> params, String keyBarChart) throws MalformedURLException {
        Object param;
        param = params.get(keyBarChart);
        if (param != null) {
            document
                    .add(new Image(ImageDataFactory.create(new URL(MessageFormat.format("{0}", params.get(keyBarChart)))))
                            .setHorizontalAlignment(HorizontalAlignment.CENTER)
                            .setMargins(0, 0, 0, 0)
                            .setPadding(0)
                            .setObjectFit(ObjectFit.CONTAIN)
                            .scaleToFit(getWidth(),getHeight()/2));

        }
    }
}
