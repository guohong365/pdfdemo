package com.nantian.pdf.office;

import com.itextpdf.layout.element.Paragraph;

public class CopyNumber {
  private static final String NAME = "份号";
  private int number;

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }
  Paragraph create(){
    return new Paragraph(String.format("%06d", number));
  }
}
