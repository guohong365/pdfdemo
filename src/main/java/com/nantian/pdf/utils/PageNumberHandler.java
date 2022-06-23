package com.nantian.pdf.utils;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

public class PageNumberHandler implements IEventHandler {
    final PdfFont font;
    final float fontSize;
    final String format;
    Document document;

    public PageNumberHandler(Document document, PdfFont font, float fontSize, String format) {
        this.font = font;
        this.fontSize = fontSize;
        this.format = format;
        this.document = document;
    }

    @Override
    public void handleEvent(Event event) {
        PdfDocumentEvent pdfDocumentEvent = (PdfDocumentEvent) event;
        PdfDocument pdfDocument = pdfDocumentEvent.getDocument();
        PdfPage page = pdfDocumentEvent.getPage();
        int pageNumber = pdfDocument.getPageNumber(page);
        Rectangle pageSize = page.getPageSize();
        //Document document = new Document(pdfDocument);
        float bottom = document.getBottomMargin();
        float left = document.getLeftMargin();
        float right = document.getRightMargin();
        Rectangle drawRect = new Rectangle(0, 0, pageSize.getWidth(), bottom);
        PdfCanvas pdfCanvas = new PdfCanvas(page);
        String num = String.valueOf(pageNumber);
        pdfCanvas.saveState()
                .moveTo(left, bottom - 1)
                .setColor(ColorConstants.RED, false)
                .setLineWidth(2)
                .lineTo(pageSize.getWidth() - right, bottom - 1)
                .stroke()
                .beginText()
                .moveText(pageSize.getWidth() - right - font.getWidth(num, fontSize) - 4, bottom - fontSize - 4)
                .setFontAndSize(font, fontSize)
                .showText("" + pageNumber)
                .endText()
                .restoreState();
    }
}
