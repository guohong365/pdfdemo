package com.nantian.pdf.office;

import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;
import org.springframework.util.StringUtils;

public class Security extends AbstractDocumentElement {
  private static final String STAR = "\u2605";
  public static final String SECRET = "秘密";
  public static final String CONFIDENTIAL = "机密";
  public static final String TOP_SECRET = "绝密";

  private final String classification;
  private final String duration;

  public Security() {
    this(null);
  }

  public Security(String classification) {
    this(classification, null);
  }

  public Security(String classification, String duration) {
    this.classification = classification;
    this.duration = duration;
  }

  protected Div createCore() {
    if(!StringUtils.hasText(classification)){
      return null;
    }
    return new Div()
            .setFont(this.getFont(Font.HEI))
            .setFontSize(FontSizes.FONT_30.getValue())
            .add(new Paragraph(classification + (StringUtils.hasText(duration) ? (STAR + duration):""))
                    .setFixedLeading(IOfficeDocument.LINE_LEADING));
  }
}
