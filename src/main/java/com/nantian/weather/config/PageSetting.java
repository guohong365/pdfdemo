package com.nantian.weather.config;

import com.itextpdf.kernel.geom.PageSize;

public class PageSetting {
    PageSize page;
    Margin margin;
    Font defaultFont;

    public PageSetting() {
        this.margin = new Margin();
        this.defaultFont = null;
        this.page = PageSize.A4;
    }

    public PageSize getPage() {
        return page;
    }

    public void setPage(PageSize page) {
        this.page = page;
    }

    public Margin getMargin() {
        return margin;
    }

    public void setMargin(Margin margin) {
        this.margin = margin;
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

    public void setDefaultFont(Font defaultFont) {
        this.defaultFont = defaultFont;
    }

    public static class Margin {
        float left;
        float right;
        float top;
        float bottom;

        public Margin() {
            this.left = this.right = this.top = this.bottom = 0;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public float getRight() {
            return right;
        }

        public void setRight(float right) {
            this.right = right;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public float getBottom() {
            return bottom;
        }

        public void setBottom(float bottom) {
            this.bottom = bottom;
        }
    }
}
