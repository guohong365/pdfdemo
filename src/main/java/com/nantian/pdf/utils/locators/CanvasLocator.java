package com.nantian.pdf.utils.locators;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.renderer.CanvasRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;

import java.util.List;

public class CanvasLocator extends CanvasRenderer {
  private final List<LocationInfo> locations;
  private final String name;

  public CanvasLocator(Canvas modelElement, List<LocationInfo> locations){
    this(modelElement, locations, "");
  }
  public CanvasLocator(Canvas modelElement, List<LocationInfo> locations, String name) {
    super(modelElement);
    this.locations = locations;
    this.name=name;
  }

  @Override
  public void draw(DrawContext drawContext) {
    super.draw(drawContext);
    locations.add(new LocationInfo(name,getBorderAreaBBox()));
  }
}
