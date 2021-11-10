package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.font.FontInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDocumentElement implements IDocumentElement{
  IDocumentFontProvider fontProvider;
  List<IDocumentElement> items=new ArrayList<>();
  public PdfFont getFont(String name){
    return fontProvider.getFont(name);
  }

  @Override
  public void add(IDocumentElement element) {
    this.items.add(element);
  }

  @Override
  public List<IDocumentElement> children() {
    return this.items;
  }
}
