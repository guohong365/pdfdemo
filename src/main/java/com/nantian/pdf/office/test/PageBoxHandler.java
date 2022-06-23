package com.nantian.pdf.office.test;

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
import com.nantian.pdf.office.IOfficeDocument;
import com.nantian.pdf.utils.Units;

public class PageBoxHandler implements IEventHandler{
    Document document;
    public PageBoxHandler(Document document) {
      this.document = document;
    }

    @Override
    public void handleEvent(Event event) {
      PdfDocumentEvent pdfDocumentEvent = (PdfDocumentEvent) event;
      PdfDocument pdfDocument = pdfDocumentEvent.getDocument();
      PdfPage page = pdfDocumentEvent.getPage();
      int pageNumber = pdfDocument.getPageNumber(page);
      Rectangle pageSize = page.getPageSize();
      float bottom = document.getBottomMargin();
      float left = document.getLeftMargin();
      float right = document.getRightMargin();
      Rectangle drawRect = new Rectangle(0, 0, pageSize.getWidth(), bottom);
      PdfCanvas pdfCanvas = new PdfCanvas(page);
      String num = String.valueOf(pageNumber);
      pdfCanvas.saveState()
              .setStrokeColor(ColorConstants.ORANGE)
              .rectangle(left, bottom, IOfficeDocument.TYPE_PAGE_WIDTH, IOfficeDocument.TYPE_PAGE_HEIGHT)
              .stroke()
              .setStrokeColor(ColorConstants.BLUE)
              .moveTo(IOfficeDocument.LEFT_MARGIN, IOfficeDocument.BOTTOM_MARGIN + IOfficeDocument.TYPE_PAGE_HEIGHT - Units.cm2pt(3.5f))
              .lineTo(IOfficeDocument.PAGE_WIDTH - IOfficeDocument.RIGHT_MARGIN,IOfficeDocument.BOTTOM_MARGIN + IOfficeDocument.TYPE_PAGE_HEIGHT - Units.cm2pt(3.5f))
              .stroke()
              .restoreState();
    }
  }

