package com.nantian.pdf.office;

import com.itextpdf.kernel.geom.Rectangle;

public interface IOfficeDocument extends IDocumentFontProvider {
  float PAGE_WIDTH = 595.0F;
  float PAGE_HEIGHT = 842.0F;
  float TYPE_PAGE_WIDTH = 442.2F;
  float TYPE_PAGE_HEIGHT = 637.8F;
  float TOP_MARGIN = 104.88F;
  float LEFT_MARGIN= 79.37F;
  float BOTTOM_MARGIN = 99.32F;
  float RIGHT_MARGIN = 73.43F;

  float LINE_LEADING = 29;
  float LEADING_ADD=13;
  float DEFAULT_FONT_SIZE = 16;
  String DEFAULT_FONT = "fang";

  int LINES = 22;
  int WORDS = 28;


  String KEY_FEN_HAO = "份号";

  default float getPageWidth() {
    return PAGE_WIDTH;
  }
  default float getPageHeight(){
    return PAGE_HEIGHT;
  }
  default float getTypePageWidth(){
    return TYPE_PAGE_WIDTH;
  }
  default float getTypePageHeight(){
    return TYPE_PAGE_HEIGHT;
  }
  float getLeftMargin();
  float getBottomMargin();
  float getRightMargin();
  float getTopMargin();
  default Rectangle getTypePageBox(){
    return new Rectangle(getLeftMargin(), getBottomMargin(), getTypePageWidth(), getTypePageHeight());
  }

  IOfficeDocument add(IDocumentElement element);

  void generate();

}
