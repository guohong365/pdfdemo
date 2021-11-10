package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.font.FontProvider;

public interface IDocumentFontProvider {
  PdfFont getFont(String name);
}
