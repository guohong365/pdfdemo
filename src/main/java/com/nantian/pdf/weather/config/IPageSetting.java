package com.nantian.pdf.weather.config;

import com.itextpdf.kernel.geom.PageSize;

public interface IPageSetting {
    PageSize getPageSize();
    float getLeftMargin();
    float getRightMargin();
    float getTopMargin();
    float getBottomMargin();
    Font getDefaultFont();

}
