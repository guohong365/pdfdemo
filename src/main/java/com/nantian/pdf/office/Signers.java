package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Signers extends AbstractDocumentElement{
  String prefix="签发人\uFF1A";
  private final List<String> signers=new ArrayList<>();

  public Signers(String[] signers){
    this(Arrays.asList(signers));
  }
  public Signers(Collection<String> signers){
    this.signers.addAll(signers);
  }
  @Override
  protected Div createCore() {
    if(signers.size()==0) return null;
    PdfFont kai = this.getFont(Font.KAI);
    int num = 0;
    Div div=new Div()
            .setFont(kai)
            .setFontSize(FontSizes.FONT_30.getValue());
    Table table=new Table(new float[]{1,1,1})
            .setAutoLayout()
            .setVerticalAlignment(VerticalAlignment.BOTTOM)
            .setHorizontalAlignment(HorizontalAlignment.RIGHT);
    int lineNumber = signers.size() / 2 + signers.size() % 2;
    Cell cell = new Cell(lineNumber, 1)
            .setBorder(Border.NO_BORDER)
            .add(new Paragraph(prefix)
                    .setFixedLeading(IOfficeDocument.LINE_LEADING)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setVerticalAlignment(VerticalAlignment.TOP));
    table.addCell(cell);
    while (num < signers.size()){
      String line=signers.get(num);
      cell = new Cell()
              .setBorder(Border.NO_BORDER)
              .add(new Paragraph(line)
                      .setFixedLeading(IOfficeDocument.LINE_LEADING)
                      .setTextAlignment(TextAlignment.LEFT));
      table.addCell(cell);
      num ++;
      if(num < signers.size()){
        line = signers.get(num);
        cell=new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph(line)
                        .setFixedLeading(IOfficeDocument.LINE_LEADING)
                        .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(cell);
        num ++;
      }
    }
    div.add(table);
    return div;
  }
}
