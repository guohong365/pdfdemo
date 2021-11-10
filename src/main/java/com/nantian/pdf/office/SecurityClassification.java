package com.nantian.pdf.office;

import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;

public class SecurityClassification extends AbstractDocumentElement{
  public final String STAR = "\u2605";
  public final String SECRET="秘密";
  public final String CONFIDENTIAL="机密";
  public final String TOP_SECRET="绝密";

  private String classification;
  private String duration;

  public IBlockElement create(){
    return new Paragraph(classification + STAR + duration).setFont(getFont(FONT_NAME_HEI));
  }
}
