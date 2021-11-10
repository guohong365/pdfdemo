package com.nantian.pdf.weather;

public interface IImportantForecastService extends IPaperGenerator {
    String NAME = "importantForecast";
    String KEY_YEAR = "year";
    String KEY_STAGE = "stage";
    String KEY_ISSUE = "issue";
    String KEY_DATE_TIME = "dateTime";
    String KEY_PARAGRAPH_TITLE_1 = "paragraphTitle1";
    String KEY_PARAGRAPH_TITLE_2 = "paragraphTitle2";
    String KEY_PARAGRAPH_MESSAGE = "paragraphMessage";
    String KEY_CHART = "chart";
    String KEY_SPECIFIC_FORECAST = "specificForcast";
    String KEY_FOLLOW = "follow";
    String KEY_REPORTING = "reporting";
    String KEY_REPORT = "report";
    String KEY_FORECASTER = "forecaster";
    String KEY_MAKER = "maker";
    String KEY_CHECKER = "checker";
    String KEY_CONTACT_NUMBER = "contactNumber";

    @Override
    default String getName() {
        return NAME;
    }
}
