package com.nantian.weather.config;

import com.itextpdf.kernel.colors.Color;

import java.util.List;

public class ElementSettings {
    public static final ElementType DEFAULT_TYPE = ElementType.PARAGRAPH;
    String name;
    List<String> keys;
    String format;
    float width;
    ElementType type;
    String value;
    Font font;
    float fontSize;
    ElementAlignment alignment;
    Color color;
    List<ElementSettings> children;
    ElementSettings() {
        this.type = ElementType.PARAGRAPH;
    }

    public ElementAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ElementAlignment alignment) {
        this.alignment = alignment;
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

    public List<ElementSettings> getChildren() {
        return children;
    }

    public void setChildren(List<ElementSettings> children) {
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}