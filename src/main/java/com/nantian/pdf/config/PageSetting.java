package com.nantian.pdf.config;

import com.itextpdf.kernel.geom.PageSize;
import com.nantian.pdf.utils.Font;

public class PageSetting implements IPageSetting{
    PageSize pageSize;
    float leftMargin;
    float rightMargin;
    float topMargin;
    float bottomMargin;

    public PageSetting(){
        pageSize = new PageSize(PageSize.A4);
    }

    @Override
    public PageSize getPageSize() {
        return pageSize;
    }

    @Override
    public float getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(float leftMargin) {
        this.leftMargin = leftMargin;
    }

    @Override
    public float getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(float rightMargin) {
        this.rightMargin = rightMargin;
    }

    @Override
    public float getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(float topMargin) {
        this.topMargin = topMargin;
    }

    @Override
    public float getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(float bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    @Override
    public Font getDefaultFont() {
        return defaultFont;
    }

    public void setDefaultFont(Font defaultFont) {
        this.defaultFont = defaultFont;
    }

    Font defaultFont;
}
