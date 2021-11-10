package com.nantian.pdf.office;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.nantian.pdf.utils.FontCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class OfficeDocument implements IOfficeDocument {
  PdfDocument pdfDocument;
  Document document;
  FontCollection fonts;
  public OfficeDocument( String output) throws FileNotFoundException {
    File file =new File(output);
    file.getParentFile().mkdirs();
    pdfDocument=new PdfDocument(new PdfWriter(new FileOutputStream(output)));
    document = new Document(pdfDocument, PageSize.A4);
    document.setMargins(TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, LEFT_MARGIN);
  }
}
