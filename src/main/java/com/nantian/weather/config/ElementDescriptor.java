package com.nantian.weather.config;

import com.itextpdf.kernel.colors.Color;

import java.awt.geom.FlatteningPathIterator;
import java.util.List;

public class ElementDescriptor {
    public static final ElementType DEFAULT_TYPE = ElementType.PARAGRAPH;
    String name;
    List<String> keys;
    String format;
    float width;
    ElementType type;
    String value;
    Font font;
    float fontSize;

    public ElementAlignment getVertAlignment() {
        return vertAlignment;
    }

    public void setVertAlignment(ElementAlignment vertAlignment) {
        this.vertAlignment = vertAlignment;
    }

    public ElementAlignment getHorzAlignment() {
        return horzAlignment;
    }

    public void setHorzAlignment(ElementAlignment horzAlignment) {
        this.horzAlignment = horzAlignment;
    }

    public ElementAlignment getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(ElementAlignment textAlignment) {
        this.textAlignment = textAlignment;
    }

    ElementAlignment vertAlignment;
    ElementAlignment horzAlignment;
    ElementAlignment textAlignment;
    Color color;

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    boolean optional;
    List<ElementDescriptor> children;
    ElementDescriptor() {
        this.type = ElementType.PARAGRAPH;
        this.vertAlignment=ElementAlignment.None;
        this.horzAlignment=ElementAlignment.None;
        this.textAlignment=ElementAlignment.None;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public List<ElementDescriptor> getChildren() {
        return children;
    }

    public void setChildren(List<ElementDescriptor> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
