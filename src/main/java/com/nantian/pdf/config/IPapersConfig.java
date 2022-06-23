package com.nantian.pdf.config;

import com.nantian.pdf.utils.Font;
import com.nantian.pdf.utils.FontCollection;

import java.util.List;
import java.util.Map;

public interface IPapersConfig extends IFontsConfig{
    Map<String, PageSetting> getSettings();
    FontCollection createFonts(String...names);
}
