package com.nantian.pdf.office;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.utils.Units;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Posters extends AbstractDocumentElement{
  static float TOP_MARGIN = IOfficeDocument.TOP_MARGIN + Units.cm2pt(35);
  static Color DEFAULT_COLOR = ColorConstants.RED;
  static float DEFAULT_FONT_SIZE = FONT_SIZE_00_42;
  List<String> posters=new ArrayList<>();
  String suffix;
  float fontSize;
  Color color;
  public Posters(Collection<String> posters){
    this(posters, null);
  }
  public Posters(Collection<String> posters, String suffix){
    this(posters, suffix, DEFAULT_FONT_SIZE);
  }
  public Posters(Collection<String> posters, String suffix, float fontSize){
    this(posters, suffix, fontSize, DEFAULT_COLOR);
  }
  public Posters(Collection<String> posters, String suffix, float fontSize, Color color){
    this.posters.addAll(posters);
    this.fontSize=fontSize;
    this.suffix = suffix;
    this.color = color;
  }

  @Override
  public IBlockElement create() {

    Cell postersCell=new Cell();
    int maxLength = 0;
    for (String poster:posters) {
      if(maxLength < poster.length()){
        maxLength = poster.length();
      }
      postersCell
              .add(new Paragraph(poster)
                      .setTextAlignment(TextAlignment.JUSTIFIED_ALL));
    }
    Cell suffixCell=null;
    if(StringUtils.hasText(suffix)){
      suffixCell=new Cell().add(new Paragraph(suffix));
    }
    return suffixCell;
  }
}
