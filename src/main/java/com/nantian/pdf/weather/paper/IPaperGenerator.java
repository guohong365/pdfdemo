package com.nantian.pdf.weather.paper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

public interface IPaperGenerator {
    float FONT_SIZE_00_42=42;//初号=42磅=14.82毫米
    float FONT_SIZE_0S_36=36;//小初=36磅=12.70毫米
    float FONT_SIZE_10_26=26;//一号=26磅=9.17毫米
    float FONT_SIZE_1S_24=24;//小一=24磅=8.47毫米
    float FONT_SIZE_20_22=22;//二号=22磅=7.76毫米
    float FONT_SIZE_2S_18=18;//小二=18磅=6.35毫米
    float FONT_SIZE_30_16=16;//三号=16磅=5.64毫米
    float FONT_SIZE_3S_15=15;//小三=15磅=5.29毫米
    float FONT_SIZE_40_14=14;//四号=14磅=4.94毫米
    float FONT_SIZE_4S_12=12;//小四=12磅=4.23毫米
    float FONT_SIZE_50_10_5=10.5f;//五号=10.5磅=3.70毫米
    float FONT_SIZE_5S_9=9;//小五=9磅=3.18毫米
    float FONT_SIZE_60_7_5=7.5f;//六号=7.5磅=2.56毫米
    float FONT_SIZE_6S_6_5=6.5f;//小六=6.5磅=2.29毫米
    float FONT_SIZE_70_5_5=5.5f;//七号=5.5磅=1.94毫米
    float FONT_SIZE_80_5=5;//八号=5磅=1.76毫米
    String getName();
    void generate(Map<String, Object> params, String output) throws IOException;
}
