package com.nantian.pdf.weather;

public interface ISpecialWeatherService extends IPaperGenerator {
    String NAME = "specialWeatherService";
    String KEY_SPECIAL = "special";
    String KEY_STAGE = "stage";
    String KEY_ISSUE = "issue";
    String KEY_DATE_TIME = "dateTime";
    String KEY_TITLE = "title";
    String KEY_REMARK = "remark";
    String KEY_CAPTION = "caption";
    String KEY_FORM = "form";
    String KEY_CHART = "chart";
    String KEY_WEATHER_FORECAST = "weatherForecast";
    String KEY_PRE_MAP = "preMap";
    String KEY_CITY_FORECAST = "cityForcast";
    String KEY_FOLLOW = "follow";
    String KEY_SUBMITTED = "submitted";
    String KEY_MAKE = "make";
    String KEY_CHECK_NAME = "checkName";
    String KEY_PHONE = "phone";

    @Override
    default String getName() {
        return NAME;
    }
}
