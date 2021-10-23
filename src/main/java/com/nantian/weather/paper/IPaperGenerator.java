package com.nantian.weather.paper;

import com.nantian.weather.config.PaperSetting;

import java.io.FileNotFoundException;
import java.util.Map;

public interface IPaperGenerator {
    String getName();

    PaperSetting getSetting();

    void generate(Map<String, Object> params, String output) throws FileNotFoundException;
}
