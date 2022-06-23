package com.nantian.pdf.office;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.ListNumberingType;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Attachment extends AbstractDocumentElement {
  List<String> texts=new ArrayList<>();
  public Attachment(String text){
    String[] lines= text.split("\\n");
    texts.addAll(Arrays.asList(lines));
  }
  public Attachment(Collection<String> paragraphs){
    texts.addAll(paragraphs);
  }
  public Attachment(String[] paragraphs){
    this(Arrays.asList(paragraphs));
  }

  @Override
  protected Div createCore() {
    Div div = createElement(Div.class)
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setMarginTop(FontSizes.FONT_30.getValue());
    Table table=createTable(new float[]{1,1})
            .setAutoLayout();
    table.addCell(new Cell()
            .setBorder(Border.NO_BORDER)
            .add(new Paragraph("附件：")));
    Cell cell=new Cell().setBorder(Border.NO_BORDER);
    com.itextpdf.layout.element.List list=new com.itextpdf.layout.element.List()
            .setListSymbol(ListNumberingType.DECIMAL);
    list.setPostSymbolText(FULL_JU);
    for(String p:texts){
      list.add(new ListItem(p));
    }
    cell.add(list);
    table.addCell(cell);
    div.add(table);
    return div;
  }
}
