package com.nantian.pdf.office;

import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.util.StringUtils;

public class Urgency extends AbstractDocumentElement{
   public static final String EXTRA_URGENT="特急";
   public static final String URGENT="急件";
   private final String urgent;
   public Urgency(String urgent){
      this.urgent=urgent;
   }

   @Override
   public IBlockElement create() {
      return StringUtils.hasText(urgent) ? new Paragraph(urgent)
              .setTextAlignment(TextAlignment.LEFT) : null;
   }
}
