package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Signature extends AbstractDocumentElement {
  List<String> texts=new ArrayList<>();
  public Signature(String text){
    String[] lines= text.split("\\n");
    texts.addAll(Arrays.asList(lines));
  }
  public Signature(Collection<String> paragraphs){
    texts.addAll(paragraphs);
  }
  public Signature(String[] paragraphs){
    this(Arrays.asList(paragraphs));
  }

  @Override
  protected Div createCore() {
    Div div = createElement(Div.class)
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setMarginTop(FontSizes.FONT_30.getValue())
            .setMarginRight(FontSizes.FONT_30.getValue() * 2);

    for(String p:texts){
      div.add(new Paragraph(p)
              .setTextAlignment(TextAlignment.RIGHT)
              .setFixedLeading(IOfficeDocument.LINE_LEADING));
    }
    return div;
  }
}
