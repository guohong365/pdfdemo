package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

public class PublishDate extends AbstractDocumentElement {
  int year;
  int month;
  int day;
  public PublishDate(int year, int month, int day){
    this.year=year;
    this.month=month;
    this.day=day;
  }

  @Override
  protected Div createCore() {
    Div div= createElement(Div.class)
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setMarginRight(FontSizes.FONT_30.getValue() * 4);
    div.add(new Paragraph(String.valueOf(year)+"年" + String.valueOf(month) + "月" + String.valueOf(day)+"日")
            .setTextAlignment(TextAlignment.RIGHT));
    return div;
  }
}
