package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

public class Annotation extends AbstractDocumentElement{
  String annotation;
  public Annotation(String annotation){
    this.annotation=annotation;
  }

  @Override
  protected Div createCore() {
    Div div= createElement(Div.class)
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setMarginTop((FontSizes.FONT_30.getValue()+ IOfficeDocument.LINE_LEADING));
    return div.add(new Paragraph("（"+annotation+"）"));
  }
}
