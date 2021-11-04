package com.nantian.pdf.utils;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;

public class CellSlashRenderer extends CellRenderer {
  public CellSlashRenderer(Cell modelElement) {
    super(modelElement);
  }

  @Override
  public void drawBackground(DrawContext drawContext) {
    super.drawBackground(drawContext);
    Rectangle innerArea = getInnerAreaBBox();
    Rectangle border = getBorderAreaBBox();
    Rectangle occupiedAreaBBox = getOccupiedAreaBBox();
    Color color=getPropertyAsColor(Property.STROKE_COLOR);
    drawContext.getCanvas()
            .setStrokeColor(color==null ? ColorConstants.BLACK: color)
            .moveTo(innerArea.getLeft(), innerArea.getTop())
            .lineTo(innerArea.getRight(), innerArea.getBottom())
            .closePathStroke();
  }
}
