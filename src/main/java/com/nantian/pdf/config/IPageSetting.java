package com.nantian.pdf.config;

import com.itextpdf.kernel.geom.PageSize;
import com.nantian.pdf.utils.Font;

public interface IPageSetting {

    PageSize getPageSize();
    float getLeftMargin();
    float getRightMargin();
    float getTopMargin();
    float getBottomMargin();
    Font getDefaultFont();

}
