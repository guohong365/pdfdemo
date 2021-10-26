package com.nantian.pdf.parse;

import com.itextpdf.kernel.geom.Rectangle;

import java.text.MessageFormat;

public class ElementLocation implements IPdfElementLocation {
    Class<?> elementType;
    Rectangle rectangle;
    Object data;

    public ElementLocation(int pageNumber, Rectangle rectangle, Object data) {
        this(pageNumber, rectangle, data, data==null?null:data.getClass());
    }
    public ElementLocation(int pageNumber, Rectangle rectangle, Object data, Class<?> elementType) {
        this.elementType = elementType;
        this.rectangle = rectangle;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ElementLocation{" +
                "elementType=" + (elementType==null?"null":elementType.getName()) +
                ", rectangle=" + MessageFormat.format("[left:{0},top:{1},right:{2},bottom:{3}]",rectangle.getLeft(), rectangle.getTop(),
                rectangle.getRight(), rectangle.getBottom()) +
                ", data=" + data +
                '}';
    }

    @Override
    public Class<?> getElementType() {
        return elementType;
    }

    @Override
    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public Object getData() {
        return data;
    }

}
