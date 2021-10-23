package com.nantian.weather.paper;

public interface ISpecialWeatherService extends IPaperGenerator {
    String NAME = "专题气象服务";

    @Override
    default String getName() {
        return NAME;
    }
}
