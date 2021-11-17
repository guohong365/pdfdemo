package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;
import org.springframework.util.StringUtils;

public class Urgency extends AbstractDocumentElement{
   public static final String EXTRA_URGENT="特急";
   public static final String URGENT="急件";
   private final String urgent;
   public Urgency(String urgent){
      this.urgent=urgent;
   }

   @Override
   protected Div createCore() {
      return StringUtils.hasText(urgent) ?
              new Div()
                      .setFont(getFont(Font.FANG))
                      .setFontSize(FontSizes.FONT_30.getValue())
                      .add(new Paragraph(urgent)
                              .setFixedLeading(IOfficeDocument.LINE_LEADING)
                              .setTextAlignment(TextAlignment.LEFT)
                              .setMargin(0)
                              .setSplitCharacters(splitterCharacters))
              : null;
   }
}
