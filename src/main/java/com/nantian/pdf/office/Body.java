package com.nantian.pdf.office;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Body extends AbstractDocumentElement{
  List<String> texts=new ArrayList<>();
  public Body(String text){
    String[] lines= text.split("\\n");
    texts.addAll(Arrays.asList(lines));
  }
  public Body(Collection<String> paragraphs){
    texts.addAll(paragraphs);
  }
  public Body(String[] paragraphs){
    this(Arrays.asList(paragraphs));
  }

  @Override
  protected Div createCore() {
    Div div = createElement(Div.class)
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue());
    float leading= getDocument().getTypePageHeight() /22;
    for(String p:texts){
      div.add(new Paragraph(p)
              .setMargin(0)
              .setPadding(0)
              .setFirstLineIndent(FontSizes.FONT_30.getValue() * 2)
              .setFixedLeading(leading));
    }
    return div;
  }
}
