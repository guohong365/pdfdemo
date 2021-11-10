package com.nantian.pdf;

import com.nantian.pdf.utils.Font;
import com.nantian.pdf.utils.FontCollection;

import java.util.List;
import java.util.Map;

public interface IPapersConfig {
    String getFontPath();
    List<Font> getFonts();
    Map<String, PageSetting> getSettings();
    FontCollection createFonts(String...names);
}
