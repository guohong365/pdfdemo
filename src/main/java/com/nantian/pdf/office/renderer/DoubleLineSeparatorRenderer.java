package com.nantian.pdf.office.renderer;

import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.LineSeparatorRenderer;
import com.nantian.pdf.office.element.DoubleLineSeparatorEx;

public class DoubleLineSeparatorRenderer extends LineSeparatorRenderer {
  public DoubleLineSeparatorRenderer(DoubleLineSeparatorEx lineSeparator) {
    super(lineSeparator);
  }

  @Override
  public IRenderer getNextRenderer() {
    return new DoubleLineSeparatorRenderer((DoubleLineSeparatorEx) modelElement);
  }
}
