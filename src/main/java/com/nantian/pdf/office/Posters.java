package com.nantian.pdf.office;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.nantian.pdf.FontSizes;
import com.nantian.pdf.utils.Font;
import com.nantian.pdf.utils.Units;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Posters extends AbstractDocumentElement {
  static float TOP_MARGIN = IOfficeDocument.TOP_MARGIN + Units.cm2pt(35);
  static Color DEFAULT_COLOR = ColorConstants.RED;
  static float DEFAULT_FONT_SIZE = FontSizes.FONT_00.getValue();
  List<String> posters = new ArrayList<>();
  String suffix;
  float fontSize;
  Color color;

  public Posters(String[] posters) {
    this(Arrays.asList(posters));
  }
  public Posters(String[] posters, String suffix) {
    this(Arrays.asList(posters), suffix);
  }
  public Posters(Collection<String> posters) {
    this(posters, null);
  }

  public Posters(Collection<String> posters, String suffix) {
    this(posters, suffix, DEFAULT_FONT_SIZE);
  }

  public Posters(Collection<String> posters, String suffix, float fontSize) {
    this(posters, suffix, fontSize, DEFAULT_COLOR);
  }

  public Posters(Collection<String> posters, String suffix, float fontSize, Color color) {
    this.posters.addAll(posters);
    this.fontSize = fontSize;
    this.suffix = suffix;
    this.color = color;
  }
  float adjustFontSize(PdfFont font, float size) {
    float posterWidth = 0;
    int maxLine = 0;
    for (String poster : posters) {
      float width = font.getWidth(poster, size);
      if (posterWidth < width) {
        posterWidth = width;
        maxLine = poster.length();
      }
    }
    float total = posterWidth;
    if (StringUtils.hasText(suffix)) {
      total += font.getWidth(suffix, size);
      maxLine += suffix.length();
    }
    if (total > getDocument().getTypePageWidth() - 3 * size) {
      size =IOfficeDocument.TYPE_PAGE_WIDTH/ (maxLine + 3) -1;
      return adjustFontSize(font, size);
    }
    return size;
  }

  @Override
  public Div createCore() {
    PdfFont fang = this.getFont(Font.FANG);
    float size = adjustFontSize(fang, fontSize);
    Style style=new Style().setFontSize(size).setFont(getFont(Font.XBS)).setFontColor(ColorConstants.RED);
    Div div=new Div().addStyle(style);
    if(posters.size()==1){
      div.add(new Paragraph(posters.get(0) + (StringUtils.hasText(suffix) ? suffix : ""))
              .setTextAlignment(TextAlignment.CENTER)
              .setFixedLeading(size + IOfficeDocument.LEADING_ADD)
      );
      return div;
    }
    Cell postersCell = new Cell();
    for (String poster : posters) {
      postersCell
              .setBorder(Border.NO_BORDER)
              .add(new Paragraph(poster)
                      .setFixedLeading(size + IOfficeDocument.LEADING_ADD)
                      .setTextAlignment(TextAlignment.JUSTIFIED_ALL));
    }
    Cell suffixCell = null;
    if (StringUtils.hasText(suffix)) {
      suffixCell = new Cell()
              .setBorder(Border.NO_BORDER)
              .setVerticalAlignment(VerticalAlignment.MIDDLE)
              .add(new Paragraph(suffix).setFixedLeading(size + IOfficeDocument.LEADING_ADD));
    }

    Table table = new Table(new float[]{1,1});
    table
            .setAutoLayout()
            .setHorizontalAlignment(HorizontalAlignment.CENTER)
            .setFont(this.getFont(Font.XBS))
            .setFontSize(size)
            .setFontColor(ColorConstants.RED)
            .addCell(postersCell);

    if (suffixCell != null) {
      table.addCell(suffixCell);
    }
    div.add(table);
    return div;
  }
}
