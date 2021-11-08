package com.nantian.pdf.utils;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;

public class PageHeaderHandler implements IEventHandler {
  final PdfFont font;
  final float fontSize;
  final Document document;
  final String header;

  public PageHeaderHandler(String header, Document document, PdfFont font, float fontSize) {
    this.font = font;
    this.fontSize = fontSize;
    this.document = document;
    this.header = header;
  }
  @Override
  public void handleEvent(Event event) {
    PdfDocumentEvent pdfDocumentEvent = (PdfDocumentEvent) event;
    PdfDocument pdfDocument = pdfDocumentEvent.getDocument();
    PdfPage page = pdfDocumentEvent.getPage();
    int pageNumber = pdfDocument.getPageNumber(page);
    Rectangle pageSize = page.getPageSize();
    float bottom =pageSize.getHeight() - document.getTopMargin();
    float left = document.getLeftMargin();
    float right = document.getRightMargin();
    PdfCanvas pdfCanvas = new PdfCanvas(page);
    pdfCanvas.saveState()
            .setStrokeColor(ColorConstants.RED)
            .setLineWidth(2)
            .moveTo(left, bottom )
            .lineTo(pageSize.getWidth() - right, bottom)
            .stroke()
            .setLineWidth(1)
            .moveTo(left, bottom + 2)
            .lineTo(pageSize.getWidth() - right, bottom + 2)
            .stroke()
            .beginText()
            .moveText(left, bottom + fontSize/2)
            .setFontAndSize(font, fontSize)
            .showText(header)
            .endText()
            .restoreState();
  }
}


