package com.nantian.weather.paper;

public interface IImportantForecast extends IPaperGenerator {
    String NAME = "重要天气预报";

    @Override
    default String getName() {
        return NAME;
    }
}
