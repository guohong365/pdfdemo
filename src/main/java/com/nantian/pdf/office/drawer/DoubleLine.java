package com.nantian.pdf.office.drawer;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;

public class DoubleLine extends SolidLine implements ILineDrawer {
  private float first = 1.0F;
  private float second= 2.0F;
  private float separator=1.50F;
  public DoubleLine(){
  }
  public DoubleLine(Color color){
    setColor(color);
  }
  public DoubleLine(float first, float separator, float second)
  {
    this(first, separator, second, ColorConstants.RED);

  }
  public DoubleLine(float first, float separator, float second,Color color)
  {
    this.first=first;
    this.separator=separator;
    this.second=second;
    setColor(color);
  }

  public DoubleLine flip(){
    float tmp= getFirst();
    setFirst(getSecond());
    setSecond(tmp);
    return this;
  }

  @Override
  public float getLineWidth() {
    return first + second + separator;
  }

  @Override
  public void setLineWidth(float lineWidth) {
    float total= getLineWidth();
    if(total <=0){
      total = 1.0F;
    }
    this.setFirst(lineWidth * this.getFirst() / total);
    this.setSecond(lineWidth * this.getSecond() /total);
    this.setSeparator(lineWidth * this.getSeparator() / total);
  }

  @Override
  public void draw(PdfCanvas canvas, Rectangle drawArea) {
    canvas.saveState()
            .setStrokeColor(this.getColor())
            .setLineWidth(this.getFirst())
            .moveTo(drawArea.getX(), drawArea.getY() + this.getFirst() / 2.0F)
            .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + this.getFirst() / 2.0F)
            .stroke()
            .setLineWidth(getSecond())
            .moveTo(drawArea.getX(), drawArea.getY() + this.getFirst()/2.0F - this.getSeparator() - this.getSecond() / 2.0F)
            .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + this.getFirst()/2.0F - this.getSeparator() - this.getSecond() / 2.0F)
            .stroke()
            .restoreState();
  }

  public float getFirst() {
    return first;
  }

  public void setFirst(float first) {
    this.first = first;
  }

  public float getSecond() {
    return second;
  }

  public void setSecond(float second) {
    this.second = second;
  }

  public float getSeparator() {
    return separator;
  }

  public void setSeparator(float separator) {
    this.separator = separator;
  }

  @Override
  public String toString() {
    return "DoubleLine{" +
            "first=" + first +
            ", second=" + second +
            ", separator=" + separator +
            '}';
  }
}
