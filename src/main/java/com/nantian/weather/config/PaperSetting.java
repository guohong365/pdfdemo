package com.nantian.weather.config;

import java.util.List;

public class PaperSetting {
    private String name;
    private PageSetting pageSetting;
    private List<ElementDescriptor> elements;

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

    public List<ElementDescriptor> getElements() {
        return elements;
    }

    public void setElements(List<ElementDescriptor> elements) {
        this.elements = elements;
    }
}