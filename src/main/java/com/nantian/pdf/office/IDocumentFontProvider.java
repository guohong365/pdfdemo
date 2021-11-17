package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;

public interface IDocumentFontProvider {
  PdfFont getFont(String name);
}
