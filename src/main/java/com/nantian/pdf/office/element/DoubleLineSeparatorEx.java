package com.nantian.pdf.office.element;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.layout.element.LineSeparator;
import com.nantian.pdf.office.drawer.DoubleLine;

public class DoubleLineSeparatorEx extends LineSeparator {
  public DoubleLineSeparatorEx(){
    super(new DoubleLine());
  }
  public DoubleLineSeparatorEx(Color color){
    super(new DoubleLine(color));
  }
  public DoubleLineSeparatorEx(DoubleLine line){
    super(line);
  }
  public DoubleLineSeparatorEx(float firstLine,float separator, float secondLine){
    super(new DoubleLine(firstLine, separator, secondLine));
  }
  public DoubleLineSeparatorEx(float firstLine, float separator, float secondLine, Color color){
    super(new DoubleLine(firstLine, separator, secondLine, color));
  }
}
