package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.renderer.IRenderer;
import com.nantian.pdf.utils.ChineseSplitterCharacters;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDocumentElement implements IDocumentElement{
  static ChineseSplitterCharacters splitterCharacters=new ChineseSplitterCharacters();
  IOfficeDocument document;
  List<IDocumentElement> items=new ArrayList<>();

  public PdfFont getFont(String name){
    return getDocument()==null? null:getDocument().getFont(name);
  }

  @Override
  public IOfficeDocument getDocument() {
    return document;
  }

  @Override
  public void setDocument(IOfficeDocument document)
  {
    this.document = document;
    for(IDocumentElement item:items){
      item.setDocument(document);
    }
  }

  @Override
  public IDocumentElement add(IDocumentElement element) {
    this.items.add(element);
    return this;
  }

  @Override
  public List<IDocumentElement> children() {
    return this.items;
  }

  protected Div createCore(){
    return null;
  }
  protected void setDefaultValues(IElement element){
    element.setProperty(Property.TEXT_RISE,0);
    element.setProperty(Property.SPLIT_CHARACTERS, splitterCharacters);
  }
  protected Table createTable(float[] columnWidths){
    Table table= new Table(columnWidths);
    setDefaultValues(table);
    return table;
  }
  protected <T extends IBlockElement> T createElement(Class<T> clazz){
    try {
      T element= clazz.newInstance();
      element.setProperty(Property.TEXT_RISE,0);
      element.setProperty(Property.SPLIT_CHARACTERS, splitterCharacters);
      return element;
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
  @Override
  public Div create() {
    Div block=createCore();
    if(block!=null) {
      block.setProperty(Property.SPACING_RATIO, 0);
      block.setProperty(Property.TEXT_RISE, 0);
      block.setProperty(Property.SPLIT_CHARACTERS, splitterCharacters);
    }
    return block;
  }

  public static Rectangle calculateLeftArea(IElement element, Rectangle area){
    IRenderer renderer= element.createRendererSubTree();
    LayoutResult layoutResult=renderer.layout(new LayoutContext(new LayoutArea(0, area)));
    if(layoutResult.getStatus()==LayoutResult.FULL){
      return new Rectangle(area.getLeft(), area.getBottom(), area.getWidth(), area.getHeight() - layoutResult.getOccupiedArea().getBBox().getHeight());
    }
    return null;
  }
  public static float calculateElementHeight(IElement element, Rectangle area){
    IRenderer renderer=element.createRendererSubTree();
    int pageNumber = 0;
    float height=0;
    LayoutResult layoutResult=renderer.layout(new LayoutContext(new LayoutArea(pageNumber, area)));
    if(layoutResult.getStatus()==LayoutResult.PARTIAL){
      while (layoutResult.getStatus()==LayoutResult.PARTIAL) {
        pageNumber++;
        height += layoutResult.getOccupiedArea().getBBox().getHeight();
        renderer = layoutResult.getOverflowRenderer();
        layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, area)));
      }
    }
    if(layoutResult.getStatus()==LayoutResult.FULL){
      height += layoutResult.getOccupiedArea().getBBox().getHeight();
    } else {
      height = 0;
    }
    return height;
  }
}
