package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Signers extends AbstractDocumentElement{
  String prefix="签发人\u302F";
  private final List<String> signers=new ArrayList<>();

  public Signers(Collection<String> signers){
    this.signers.addAll(signers);
  }
  @Override
  public IBlockElement create() {
    //PdfFont fang=getFont(FONT_NAME_FANG);
    PdfFont kai = getFont(FONT_NAME_KAI);
    float width=0;
    int num = 0;
    Cell signersCell=new Cell();
    while (num < signers.size()){
      String line=signers.get(num);
      num ++;
      if(num < signers.size()){
        line=signers.get(num);
      }
      float lineWidth = kai.getWidth(line, FONT_SIZE_30_16);
      if(width<lineWidth) width=lineWidth;
      signersCell.add(new Paragraph(line).setTextAlignment(TextAlignment.JUSTIFIED));
    }
    signersCell.setWidth(width);
    return signersCell;
  }
}
