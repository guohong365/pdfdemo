package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Title extends AbstractDocumentElement{
  List<String> lines=new ArrayList<>();
  public Title(String text){
    String[] l= text.split("\\n");
    lines.addAll(Arrays.asList(l));
  }
  public Title(Collection<String> paragraphs){
    lines.addAll(paragraphs);
  }
  public Title(String[] paragraphs){
    this(Arrays.asList(paragraphs));
  }

  @Override
  protected Div createCore() {
    Div div = new Div()
            .setFont(getFont(Font.XBS))
            .setFontSize(FontSizes.FONT_20.getValue())
            .setMarginTop((FontSizes.FONT_20.getValue() + IOfficeDocument.LEADING_ADD)*2);
    for(String line:lines){
      div.add(new Paragraph(line)
              .setFixedLeading(FontSizes.FONT_20.getValue() + IOfficeDocument.LEADING_ADD)
              .setTextAlignment(TextAlignment.CENTER));
    }
    return div;
  }
}
