package com.nantian.pdf.utils;

public class Font {
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
