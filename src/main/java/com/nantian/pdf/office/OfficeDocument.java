package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Div;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;
import com.nantian.pdf.utils.FontCollection;

import java.util.ArrayList;
import java.util.List;

public class OfficeDocument extends Document implements IOfficeDocument {
  boolean debug=true;
  FontCollection fonts;
  List<IDocumentElement> items=new ArrayList<>();
  Style defaultStyle;
  public OfficeDocument(PdfDocument pdfDoc, FontCollection fonts) {
    this(pdfDoc, fonts, true);
  }
  public OfficeDocument(PdfDocument pdfDoc, FontCollection fonts, boolean immediateFlush) {
    super(pdfDoc, PageSize.A4, immediateFlush);
    this.fonts = fonts;
    setMargins(TOP_MARGIN, RIGHT_MARGIN, BOTTOM_MARGIN, LEFT_MARGIN);
    setFont(getFont(Font.FANG));
    setFontSize(FontSizes.FONT_30.getValue());
    defaultStyle=new Style()
            .setFont(getFont(Font.FANG))
            .setFontSize(FontSizes.FONT_30.getValue())
            .setMargin(0)
            .setPadding(0);
  }

  public IOfficeDocument add(IDocumentElement item){
    this.items.add(item);
    item.setDocument(this);
    return this;
  }

  @Override
  public PdfFont getFont(String name) {
    return fonts==null? null : fonts.get(name);
  }

  @Override
  public void generate() {
    for(IDocumentElement element:items){
      Div div=element.create();
      if(div!=null){
        add(div);
      }
    }
    close();
  }
}
