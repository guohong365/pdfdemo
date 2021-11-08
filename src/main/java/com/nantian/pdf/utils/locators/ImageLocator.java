package com.nantian.pdf.utils.locators;

import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ImageRenderer;

import java.util.List;

public class ImageLocator extends ImageRenderer {
  private final List<LocationInfo> locations;
  private final String name;
  private final int times;

  public ImageLocator(Image modelElement, List<LocationInfo> locations){
    this(modelElement, locations, "", 0);
  }
  public ImageLocator(Image modelElement, List<LocationInfo> locations, String name){
    this(modelElement, locations, name, 0);
  }
  public ImageLocator(Image modelElement, List<LocationInfo> locations, String name, int times) {
    super(modelElement);
    this.locations = locations;
    this.name=name;
    this.times=times;
  }

  @Override
  public IRenderer getNextRenderer() {
    return new ImageLocator((Image) modelElement, locations, name, times + 1);
  }

  @Override
  public void draw(DrawContext drawContext) {
    super.draw(drawContext);
    locations.add(new LocationInfo(name,getBorderAreaBBox(), times));
  }

}
