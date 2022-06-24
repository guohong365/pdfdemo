package com.nantian.pdf.content;

import com.itextpdf.kernel.geom.Rectangle;

public interface IAbstractPositionLocatingStrategy extends ILocatingStrategy {
    Rectangle getPosition();

    void setPosition();
}
