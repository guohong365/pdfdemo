package com.nantian.weather.paper.factory;

import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.nantian.weather.config.ElementDescriptor;

import java.util.Map;

public interface IElementFactory {
    IElement create(ElementDescriptor descriptor, Map<String, Object> params);
}
