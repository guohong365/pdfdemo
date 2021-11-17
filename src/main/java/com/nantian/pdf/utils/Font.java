package com.nantian.pdf.utils;

public class Font {
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

    public static final String FANG="fang";
    public static final String KAI ="kai";
    public static final String HEI ="hei";
    public static final String XBS ="xbs";
    public static final String LI = "li";
    public static final String SONG = "song";
    public static final String DEFAULT_NAME = "fang";
    public static final float DEFAULT_SIZE = 16;
    private String name;
    private String file;
    private Float size;

    public Font() {
        this.size = DEFAULT_SIZE;
    }

    public Font(String name, String file) {
        this(name, file, DEFAULT_SIZE);
    }

    public Font(String name, String file, Float size) {
        this.name = name;
        this.size = size;
        this.file = file;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Font{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                ", size=" + size +
                '}';
    }
}
