package com.nantian.pdf.utils.locators;

import com.itextpdf.kernel.geom.Rectangle;

public class LocationInfo {
  private final String name;
  private final Rectangle bounds;
  public LocationInfo(String name, Rectangle bounds){
    this.name=name;
    this.bounds=bounds;
  }

  public String getName() {
    return name;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  @Override
  public String toString() {
    return name +": {" + bounds.getLeft() + bounds.getBottom() + bounds.getRight() + bounds.getTop() +
            '}';
  }
}
