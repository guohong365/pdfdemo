package com.nantian.weather.paper;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.Units;
import com.nantian.weather.config.Font;
import com.nantian.weather.config.PaperSetting;
import com.nantian.weather.config.PapersConfig;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.Map;

public abstract class PaperGeneratorBase implements IPaperGenerator {
    protected PdfFont hei;
    protected PdfFont kai;
    protected PdfFont fang;
    protected PdfFont li;
    protected PdfFont xbs;
    PaperSetting setting;
    PapersConfig config;

    protected PaperGeneratorBase(PapersConfig config) {
        this.config = config;
        this.config.getSetting(getName());
        this.hei = PapersConfig.getFont("hei");
        this.kai = PapersConfig.getFont("kai");
        this.fang = PapersConfig.getFont("fang");
        this.li = PapersConfig.getFont("li");
        this.xbs = PapersConfig.getFont("xbs");
    }

    public PaperSetting getSetting() {
        return setting;
    }
    protected float getWidth() {
        return getSetting().getPageSetting().getPage().getWidth()
                - getSetting().getPageSetting().getMargin().getLeft()
                - getSetting().getPageSetting().getMargin().getRight();
    }

    protected float getHeight() {
        return getSetting().getPageSetting().getPage().getHeight()
                - getSetting().getPageSetting().getMargin().getTop()
                - getSetting().getPageSetting().getMargin().getBottom();
    }

    protected float getBottom() {
        return getSetting().getPageSetting().getPage().getHeight()
                - getSetting().getPageSetting().getMargin().getBottom();
    }

    protected float getTop() {
        return getSetting().getPageSetting().getMargin().getTop();
    }

    protected float getLeft() {
        return getSetting().getPageSetting().getMargin().getLeft();
    }

    protected float getRight() {
        return getSetting().getPageSetting().getPage().getWidth()
                - getSetting().getPageSetting().getMargin().getRight();
    }
    protected Document createDocument(Map<String, Object> params, String outputFile) throws FileNotFoundException {
        File output = new File(outputFile);
        output.getParentFile().mkdirs();
        PdfWriter writer = new PdfWriter(new File(outputFile));
        com.itextpdf.kernel.pdf.PdfDocument PdfDocument = new PdfDocument(writer);
        PdfDocumentInfo info = PdfDocument.getDocumentInfo();
        Object param = params.get("make");
        if (param != null && StringUtils.hasText(param.toString())) {
            info.setAuthor(param.toString());
        }
        info.setTitle(new File(outputFile).getName());

        Document document = new Document(PdfDocument, PageSize.A4);
        document.setTextAlignment(TextAlignment.JUSTIFIED);
        document.setMargins(
                Units.cm2pt(this.setting.getPageSetting().getMargin().getTop()),
                Units.cm2pt(this.setting.getPageSetting().getMargin().getRight()),
                Units.cm2pt(this.setting.getPageSetting().getMargin().getBottom()),
                Units.cm2pt(this.setting.getPageSetting().getMargin().getLeft()));
        Font font = this.setting.getPageSetting().getDefaultFont();
        document.setFontSize(font.getSize());
        document.setFont(PapersConfig.getFont(font.getName()));
        onDocumentCreated(document, params);
        return document;
    }

    public PapersConfig getConfig() {
        return config;
    }

    public void setConfig(PapersConfig config) {
        this.config = config;
    }

    public void generate(Map<String, Object> params, String output) throws FileNotFoundException, MalformedURLException {
        Document document = createDocument(params, output);
        onGenerate(document, params);
        onBeforeDocumentClosed(document, params);
        document.close();
    }

    protected void onDocumentCreated(Document document, Map<String, Object> params) {
    }

    protected abstract void onGenerate(Document document, Map<String, Object> params) throws MalformedURLException;

    protected void onBeforeDocumentClosed(Document document, Map<String, Object> params) {
    }
}
