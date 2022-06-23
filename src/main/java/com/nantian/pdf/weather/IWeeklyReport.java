package com.nantian.pdf.weather;

public interface IWeeklyReport extends IPaperGenerator {
    String KEY_YEAR="year";
    String KEY_STAGE = "stage";
    String KEY_ISSUE="issue";
    String KEY_DATE_TIME="dateTime";
    String KEY_WEEK_DATE="weekdate";
    String KEY_CAPTION="caption";
    String KEY_FORM="form";
    String KEY_TABLE_NAME="tableName";
    String KEY_BAR_CHART="barChart";
    String KEY_CHART="chart";
    String KEY_WEATHER_WEEK = "wheatherWeek";
    String KEY_PRE_MAP="preMap";
    String KEY_WEATHER_FORECAST="weatherForecast";
    String KEY_FOLLOW = "follow";
    String KEY_SUBMITTED ="submitted";
    String KEY_MAKE="make";
    String KEY_CHECK_NAME="checkName";
    String KEY_PHONE="phone";

    String NAME = "weeklyReport";

    @Override
    default String getName() {
        return NAME;
    }
}
