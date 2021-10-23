package com.nantian.weather.paper;

public interface IWeeklyPaper extends IPaperGenerator {
    String NAME = "天气周报";

    @Override
    default String getName() {
        return NAME;
    }
}
