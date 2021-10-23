package com.nantian.weather.config;

public class Font {
    public static final String DEFAULT_NAME = "fang";
    public static final float DEFAULT_SIZE = 16;
    private String name;
    private String file;
    private float size;

    public Font() {
        this.size = DEFAULT_SIZE;
    }

    public Font(String name, String file) {
        this(name, file, DEFAULT_SIZE);
    }

    public Font(String name, String file, float size) {
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

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }
}
