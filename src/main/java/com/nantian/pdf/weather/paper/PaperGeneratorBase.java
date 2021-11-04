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
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutPosition;
import com.itextpdf.layout.property.*;
import com.nantian.pdf.parse.ElementLocationLitener;
import com.nantian.pdf.parse.IElementLocationLitener;
import com.nantian.pdf.parse.IPdfElementLocation;
import com.nantian.pdf.utils.locators.DivLocator;
import com.nantian.pdf.utils.locators.LocationInfo;
import com.nantian.pdf.weather.config.Font;
import com.nantian.pdf.weather.config.IPageSetting;
import com.nantian.pdf.weather.config.IPapersConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        List<LocationInfo> bodyLocations = new ArrayList<>();
        Div div=createBody(fonts, params);
        DivLocator divLocator=new DivLocator(div, bodyLocations, "body");
        div.setNextRenderer(divLocator);
        document.add(div);
        document.close();
        if (hasFinalFooter()) {  //如果有末尾置底部分
            List<LocationInfo> footerLocation=new ArrayList<>();
            fonts = config.createFonts();
            ByteArrayOutputStream footerOutput = new ByteArrayOutputStream();
            document = createDocument(fonts, params, null, footerOutput);
            Div footer = createFooter(fonts, params);
            divLocator = new DivLocator(footer, footerLocation, "footer");
            footer.setNextRenderer(divLocator);
            document.add(footer);
            document.close();
            assert bodyOutput instanceof ByteArrayOutputStream;
            byte[] bodyBytes = ((ByteArrayOutputStream) bodyOutput).toByteArray();
            InputStream bodyInput = new ByteArrayInputStream(bodyBytes);

            logger.info(bodyLocations.toString());
            logger.info(footerLocation.toString());
            //两者都有内容，才进行
            if (bodyLocations.size() > 0 && footerLocation.size()>0) {
                fonts = config.createFonts();
                OutputStream finalOutput = new FileOutputStream(output);
                bodyInput = new ByteArrayInputStream(bodyBytes);
                document = createDocument(fonts, params, bodyInput, finalOutput);
                float bodyBottom = bodyLocations.get(0).getBounds().getBottom();
                float footerTop = footerLocation.get(footerLocation.size()-1).getBounds().getTop();
                float footerBottom = footerLocation.get(0).getBounds().getBottom();
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

    protected void onPdfDocumentCreated(PdfDocument pdfDocument, Map<String, Object> params) {
    }

    protected void onDocumentCreated(Document document, Map<String, Object> params) {
    }

    protected abstract Div createBody(FontCollection fonts, Map<String, Object> params) throws MalformedURLException;

    protected Div createFooter(FontCollection fonts, Map<String, Object> params) {
        return null;
    }

    protected boolean hasFinalFooter() {
        return true;
    }

    protected void beforeDocumentClosed(Document document, Map<String, Object> params) {
    }
    protected void addFormTable(Div div, Object tableList, Object tableName) {
        if (!(tableList instanceof List)) return ;
        List<String> list = (List<String>) tableList;
        if (list.size() == 0) return ;

        String[] items = list.get(0).split("\\|");
        float[] widths = new float[items.length];
        Arrays.fill(widths, 1);
        Table table = new Table(widths)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setAutoLayout()
                .setFontSize(FONT_SIZE_50_10_5)
                .setTextAlignment(TextAlignment.CENTER)
                .setKeepWithNext(true)
                .setMarginTop(FONT_SIZE_4S_12)
                .setMarginBottom(FONT_SIZE_4S_12)
                .setKeepTogether(true);

        for (String line : list) {
            items = line.split("\\|");
            for (String item : items) {
                table.addCell(item);
            }
        }
        div.add(table);
        if (tableName != null) {
            div.add(new Paragraph(tableName.toString())
                    .setFontSize(FONT_SIZE_50_10_5)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(FONT_SIZE_4S_12));
        }
    }
    protected void addImage(Div div, Object url)  {
        if (url!=null && StringUtils.hasText(url.toString())) {
            Image image= null;
            try {
                image = new Image(ImageDataFactory.create(new URL(url.toString())))
                                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                                .setMargins(FONT_SIZE_40_14, 0, FONT_SIZE_40_14, 0)
                                .setPadding(0)
                                .setObjectFit(ObjectFit.CONTAIN)
                                .scaleToFit(getWidth(),getHeight() * 0.4f);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            div.add(image);
        }
    }

    protected Div createMultiLineTextBlock(String text, float indent, float leading){
        if(!StringUtils.hasText(text)) return null;
        String[] lines= text.split("\n");
        Div div=new Div();
        for(String line :lines){
            Paragraph p=new Paragraph(line).setFirstLineIndent(indent).setFixedLeading(leading);
            div.add(p);
        }
        return div;
    }
}
