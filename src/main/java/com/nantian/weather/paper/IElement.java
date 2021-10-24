package com.nantian.weather.paper;

import com.itextpdf.layout.element.IBlockElement;

import java.util.Map;

public interface IElement {
    IBlockElement create(Map<String, Object> params);
}
