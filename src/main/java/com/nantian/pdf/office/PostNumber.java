package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.nantian.pdf.utils.Units;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

public class PostNumber extends AbstractDocumentElement {
  String prefix="签发人\u302F";
  String left="\u3014";
  String right="\u3015";

  List<String> signers;
  String year;
  String number;
  boolean up;
  public IBlockElement create(){

    PdfFont fang = getFont(FONT_NAME_FANG);
    PdfFont kai=getFont(FONT_NAME_KAI);
    float width=fang.getWidth(prefix, FONT_SIZE_30_16);

    Cell prefixCell=new Cell()
            .setWidth(width)
            .add(new Paragraph(prefix)
                    .setVerticalAlignment(VerticalAlignment.TOP)
                    .setTextAlignment(TextAlignment.RIGHT));
    width = 0;
    Cell signerCell=new Cell();
    for(String signer:signers){
      float s = kai.getWidth(signer, FONT_SIZE_30_16);
      if(width < s) width = s;
      signerCell.add(new Paragraph(signer)
              .setTextAlignment(TextAlignment.LEFT)
              .setVerticalAlignment(VerticalAlignment.TOP));
    }
    signerCell.setWidth(width);
    String post = "";
    if(StringUtils.hasText(year)){
      post=left + year + right ;
    }
    if(StringUtils.hasText(number)){
      post +=number + "号";
    }
    width = fang.getWidth(post, FONT_SIZE_30_16);
    Cell postCell =new Cell();
    postCell.add(new Paragraph(post)
            .setTextAlignment(TextAlignment.LEFT)
            .setVerticalAlignment(VerticalAlignment.BOTTOM));
    postCell.setWidth(width);
    Table table=new Table(new float[]{prefixCell.getWidth().getValue(),signerCell.getWidth().getValue(),postCell.getWidth().getValue()});
    table
            .setMargins(Units.cm2pt(3.5f),0,0,0)
            .setPadding(0)
            .addCell(prefixCell).addCell(signerCell).addCell(postCell);
    if(up){
      table.setMarginLeft(FONT_SIZE_30_16);
    } else {
      table.setHorizontalAlignment(HorizontalAlignment.CENTER);
    }
    return table;
  }
}
