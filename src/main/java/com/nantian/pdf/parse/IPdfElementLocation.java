package com.nantian.pdf.parse;

import com.itextpdf.kernel.geom.Rectangle;

public interface IPdfElementLocation {
    Class<?> getElementType();
    Rectangle getRectangle();
    Object getData();
}
