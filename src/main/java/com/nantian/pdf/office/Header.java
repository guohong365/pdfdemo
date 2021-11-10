package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;

public class Header extends AbstractDocumentElement{
  CopyNumber copyNumber;
  SecurityClassification securityClassification;
  Urgency urgency;
  Posters posters;
  Signers signers;
  PostNumber postNumber;

  public IBlockElement create(){
    Div header =new Div();
    for(IDocumentElement element:children()) {
      IBlockElement part = null;
      if ((part = copyNumber.create()) != null) {
        header.add(part);
      }
    }
    return header;
  }
}
