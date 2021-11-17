package com.nantian.pdf.office;

import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LordSent extends AbstractDocumentElement{
  Style style;
  List<String> sent =new ArrayList<>();
  public LordSent(String text){
    String[] l= text.split("\\n");
    sent.addAll(Arrays.asList(l));
  }
  public LordSent(Collection<String> paragraphs){
    sent.addAll(paragraphs);
  }
  public LordSent(String[] paragraphs){
    this(Arrays.asList(paragraphs));
  }

  @Override
  protected Div createCore() {
    style=new Style()
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue());
    Div div =new Div().addStyle(style);
    String text=StringUtils.collectionToDelimitedString(sent, FULL_COMMA)+FULL_COLON;
    div.add(new Paragraph(text)
            .setFixedLeading(IOfficeDocument.LINE_LEADING));
    return div;
  }
}
