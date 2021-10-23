package com.nantian.weather.config;

import java.util.List;

public class PaperSetting {
    private String name;
    private PageSetting pageSetting;
    private List<ElementSettings> elements;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageSetting getPageSetting() {
        return pageSetting;
    }

    public void setPageSetting(PageSetting pageSetting) {
        this.pageSetting = pageSetting;
    }

    public List<ElementSettings> getElements() {
        return elements;
    }

    public void setElements(List<ElementSettings> elements) {
        this.elements = elements;
    }
}