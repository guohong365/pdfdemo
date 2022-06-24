package com.nantian.pdf.weather;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.ObjectFit;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.renderer.IRenderer;
import com.nantian.pdf.config.IPageSetting;
import com.nantian.pdf.config.IPapersConfig;
import com.nantian.pdf.utils.ChineseSplitterCharacters;
import com.nantian.pdf.utils.FontCollection;
import org.springframework.util.StringUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class PaperGeneratorBase implements IPaperGenerator {
    protected final IPapersConfig config;
    protected final IPageSetting pageSetting;
    protected final FontCollection fonts;
    protected PaperGeneratorBase(IPapersConfig config) {
        this.config = config;
        String name = getName();
        this.pageSetting = config.getSettings().get(name);
        fonts = this.config.createFonts();
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

    protected Document createDocument(Map<String, Object> params, OutputStream output) {
        PdfDocument pdfDocument;
        pdfDocument = new PdfDocument(new PdfWriter(output));
        onPdfDocumentCreated(pdfDocument, params);
        Document document = new Document(pdfDocument, PageSize.A4);
        document.setTextAlignment(TextAlignment.JUSTIFIED);
        document.setMargins(
                getPageSetting().getTopMargin(),
                getPageSetting().getRightMargin(),
                getPageSetting().getBottomMargin(),
                getPageSetting().getLeftMargin());
        document.setFontSize(pageSetting.getDefaultFont().getSize());
        document.setFont(fonts.get(pageSetting.getDefaultFont().getName()));
        document.setSplitCharacters(new ChineseSplitterCharacters());
        onDocumentCreated(document, params);
        return document;
    }

    public void generate(Map<String, Object> params, String output) throws Exception {
        OutputStream bodyOutput = new FileOutputStream(output);
        Document document = createDocument(params, bodyOutput);
        Div div = createBody(params);
        document.add(div);
        Rectangle effectiveArea = document.getPageEffectiveArea(PageSize.A4);

        IRenderer renderer = div.createRendererSubTree().setParent(document.getRenderer());
        int pageNumber = 1;
        LayoutResult layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, effectiveArea)));
        if (layoutResult.getStatus() == LayoutResult.PARTIAL) {
            do {
                pageNumber++;
                renderer = layoutResult.getOverflowRenderer();
                layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, effectiveArea)));
            } while (layoutResult.getStatus() == LayoutResult.PARTIAL);
        }
        Rectangle currentSize = layoutResult.getOccupiedArea().getBBox();
        Div footer = createFooter(params);
        if (footer != null) {
            Rectangle footerArea = effectiveArea.clone().setHeight(effectiveArea.getHeight() - currentSize.getHeight());
            renderer = footer.createRendererSubTree().setParent(document.getRenderer());
            layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, footerArea)));
            if (layoutResult.getStatus() != LayoutResult.FULL) { //无剩余空间可以放置
                document.add(new AreaBreak());
                renderer = footer.createRendererSubTree().setParent(document.getRenderer());
                layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, effectiveArea)));
                if(layoutResult.getStatus()!=LayoutResult.FULL) {
                    do {
                        pageNumber++;
                        renderer = layoutResult.getOverflowRenderer();
                        layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, effectiveArea)));
                    } while (layoutResult.getStatus() != LayoutResult.FULL);
                }
                currentSize = layoutResult.getOccupiedArea().getBBox();
                float blankHeight =effectiveArea.getHeight() - currentSize.getHeight()- FONT_SIZE_50_10_5;
                Paragraph blankParagraph = new Paragraph(" ")
                        .setFontSize(FONT_SIZE_50_10_5)
                        .setFixedLeading(0)
                        .setMarginTop(effectiveArea.getHeight() - currentSize.getHeight() - FONT_SIZE_50_10_5);
                Table blank = new Table(1)
                        .setWidth(UnitValue.createPercentValue(100))
                        .setHeight(blankHeight)
                        .setMargins(0,0,0,0)
                        .setPadding(0);
                blank.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(" ")));
                document.add(blankParagraph);
            } else {
                footer.setFixedPosition(effectiveArea.getLeft(), effectiveArea.getBottom(), effectiveArea.getWidth());
            }
            document.add(footer);
        }
        document.close();
    }
    void drawLine(PdfDocument pdfDocument, int pageNum, float x, float y, float width){
        PdfPage pdfPage= pdfDocument.getPage(pageNum);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
        pdfCanvas
                .saveState()
                .setStrokeColor(ColorConstants.MAGENTA)
                .setLineWidth(2)
                .moveTo(x, y)
                .lineTo(x + width, y)
                .stroke()
                .restoreState();
    }
    void drawBox(PdfDocument pdfDocument, int pageNum, Rectangle rect){
        PdfPage pdfPage= pdfDocument.getPage(pageNum);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
        pdfCanvas
                .saveState()
                .setStrokeColor(ColorConstants.MAGENTA)
                .setLineWidth(1)
                .rectangle(rect)
                .stroke()
                .restoreState();
    }
    protected void onPdfDocumentCreated(PdfDocument pdfDocument, Map<String, Object> params) {
    }

    protected void onDocumentCreated(Document document, Map<String, Object> params) {
    }

    protected abstract Div createBody(Map<String, Object> params) throws MalformedURLException;

    protected Div createFooter(Map<String, Object> params) {
        return null;
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
