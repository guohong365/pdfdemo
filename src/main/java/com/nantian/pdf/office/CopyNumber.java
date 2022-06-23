package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

public class CopyNumber extends AbstractDocumentElement{
  private static final String NAME = "份号";
  private int number=1;

  public CopyNumber(){
  }
  public CopyNumber(int number){
    this.number=number;
  }

  public Integer getNumber() {
    return number;
  }
  public void setNumber(int number) {
    this.number = number;
  }
  public Div createCore(){
    return new Div().add(new Paragraph(String.format("%06d", number))
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setFixedLeading(IOfficeDocument.LINE_LEADING)
            .setMargin(0));
  }
}
