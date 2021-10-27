package com.nantian.pdf.utils.locators;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import org.springframework.lang.NonNull;

import java.util.List;


public class ParagraphLocator extends ParagraphRenderer {
  private final List<LocationInfo> locations;
  private final String name;
  private final int times;

  public ParagraphLocator(Paragraph modelElement, List<LocationInfo> locations){
    this(modelElement, locations, "", 0);
  }
  public ParagraphLocator(Paragraph modelElement, List<LocationInfo> locations, String name){
    this(modelElement, locations, name, 0);
  }
  public ParagraphLocator(Paragraph modelElement, List<LocationInfo> locations, String name, int times) {
    super(modelElement);
    this.locations = locations;
    this.name=name;
    this.times=times;
  }

  @Override
  public IRenderer getNextRenderer() {
    return new ParagraphLocator((Paragraph) modelElement, locations, name, times + 1);
  }

  @Override
  public void draw(DrawContext drawContext) {
    super.draw(drawContext);
    locations.add(new LocationInfo(name,getBorderAreaBBox(), times));
  }
}
