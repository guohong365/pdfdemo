package com.nantian.pdf.weather.config;

import com.nantian.pdf.weather.paper.FontCollection;

import java.util.List;
import java.util.Map;

public interface IPapersConfig {
    String getFontPath();
    List<Font> getFonts();
    String getResourcesPath();
    Map<String, PageSetting> getSettings();
    FontCollection createFonts(String...names);
}
