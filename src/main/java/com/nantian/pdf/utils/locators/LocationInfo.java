package com.nantian.pdf.utils.locators;

import com.itextpdf.kernel.geom.Rectangle;
import org.springframework.lang.NonNull;

import java.util.Comparator;
import java.util.List;

public class LocationInfo {
  private final String name;
  private final Rectangle bounds;

  public int getTimes() {
    return times;
  }

  private final int times;
  public LocationInfo(String name, Rectangle bounds, int times){
    this.name=name;
    this.bounds=bounds;
    this.times = times;
  }

  public String getName() {
    return name;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  @Override
  public String toString() {
    return name +"["+ times + "]: {" + bounds.getLeft() +", " + bounds.getBottom() +", "+ bounds.getRight() +", "+ bounds.getTop() +
            '}' + "("+bounds +")";
  }

}
