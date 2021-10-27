package com.nantian.pdf.utils;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;

public class CellSlashRenderer extends CellRenderer {
  private final String textBottomLeft;
  private final String textTopRight;
  private final PdfFont font;

  public CellSlashRenderer(Cell modelElement, PdfFont font, String textBottomLeft, String textTopRight) {
    super(modelElement);
    this.textBottomLeft=textBottomLeft;
    this.textTopRight=textTopRight;
    this.font = font;
  }
  public CellSlashRenderer(Cell modelElement, PdfFont font, String textBottomLeft) {
    this(modelElement, font, textBottomLeft, null);
  }
  public CellSlashRenderer(Cell modelElement) {
    this(modelElement, null, null);
  }

  @Override
  public void drawBorder(DrawContext drawContext) {
    PdfCanvas canvas = drawContext.getCanvas();
    Rectangle rect = getOccupiedAreaBBox();

    canvas
            .saveState()
            .moveTo(rect.getLeft(), rect.getTop())
            .lineTo(rect.getRight(), rect.getBottom())
            .stroke()
            .restoreState();

    new Canvas(canvas, getOccupiedAreaBBox())
            .setFont(font)
            .showTextAligned(textTopRight, rect.getRight() - 2, rect.getTop() - rect.getHeight()/2 - 2,
                    TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0)
            .showTextAligned(textBottomLeft, rect.getLeft() + rect.getWidth()/2 + 2, rect.getBottom() + 2, TextAlignment.RIGHT);
  }

  @Override
  public IRenderer getNextRenderer() {
    return new CellSlashRenderer((Cell) modelElement, font, textBottomLeft, textTopRight);
  }
}
