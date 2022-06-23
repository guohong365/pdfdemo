package com.nantian.pdf.utils.locators;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TableRenderer;

import java.util.List;

public class TableLocator extends TableRenderer {
  private final List<LocationInfo> locations;
  private final String name;
  private final int times;

  public TableLocator(Table modelElement, List<LocationInfo> locations){
    this(modelElement, locations, "", 0);
  }
  public TableLocator(Table modelElement, List<LocationInfo> locations, String name){
    this(modelElement, locations, name, 0);
  }
  public TableLocator(Table modelElement, List<LocationInfo> locations, String name, int times) {
    super(modelElement);
    this.locations = locations;
    this.name=name;
    this.times=times;
  }

  @Override
  public IRenderer getNextRenderer() {
    return new TableLocator((Table) modelElement, locations, name, times + 1);
  }

  @Override
  public void draw(DrawContext drawContext) {
    super.draw(drawContext);
    locations.add(new LocationInfo(name,getBorderAreaBBox(), times));
  }

}
