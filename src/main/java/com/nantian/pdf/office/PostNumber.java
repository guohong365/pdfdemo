package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

public class PostNumber extends AbstractDocumentElement {
  static final String prefix="签发人\u302F";
  static final String left="\u3014";
  static final String right="\u3015";
  String org;
  int year;
  int number;
  boolean up;
  Signers signers;

  public PostNumber(String org, int year){
    this(org, year, 1);
  }
  public PostNumber(String org, int year, int number){
    this(org, year, number, false);
  }
  public PostNumber(String org, int year, int number, boolean up){
    this.org=org;
    this.year=year;
    this.number = number;
    this.up=up;
  }

  public PostNumber addSigners(Signers signers) {
    this.signers = signers;
    add(signers);
    return this;
  }

  public Div createCore(){
    PdfFont fang = this.getFont(Font.FANG);
    Div div=new Div()
            .setFont(fang)
            .setFontSize(FontSizes.FONT_30.getValue());
    Paragraph postNumber=createElement(Paragraph.class).add(org + left + year + right + number + "号")
            .setFixedLeading(IOfficeDocument.LINE_LEADING);
    if(up){
      postNumber.setFixedLeading(FontSizes.FONT_30.getValue())
              .setTextAlignment(TextAlignment.LEFT);
    } else {
      postNumber.setTextAlignment(TextAlignment.CENTER);
    }
    div.add(postNumber);
    return div;
  }

}
