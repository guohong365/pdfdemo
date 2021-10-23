package com.nantian.weather.paper;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.Units;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Properties;

public class Paper {

    public static final float MAIN_TITLE_SIZE = 47;
    public static final float BODY_SIZE = 16;
    public static final float SUB_TITLE_SIZE = 18;
    public static float TOP_MARGIN = Units.cm2pt(3.7f);
    public static float BOTTOM_MARGIN = Units.cm2pt(3.5f);
    public static float LEFT_MARGIN = Units.cm2pt(2.8f);
    public static float RIGHT_MARGIN = Units.cm2pt(2.6f);
    Map<String, PaperElement> elements;
    private Properties properties;
    private Document document;

    private Paper(Document document) {
        this.document = document;
    }

    public static Paper create(String name) throws FileNotFoundException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(new File(name)));
        return new Paper(new Document(pdf));
    }

    public Map<String, PaperElement> getElements() {
        return elements;
    }

    public void setElements(Map<String, PaperElement> elements) {
        this.elements = elements;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Paper title(String title) {
        Paragraph paragraph = new Paragraph(title);
        document.add(paragraph);
        return this;
    }

    public Paper issue(String year, String issue) {
        Paragraph paragraph = new Paragraph(String.format("%s年  第%s期", year, issue));
        document.add(paragraph);
        return this;
    }

    public Paper publisher(String publisher, String signer, String date) {
        return this;
    }

}
