package com.nantian.pdf;

public class Units {
    public static float cm2pt(float cm) {
        return cm / 2.54f * 72;
    }

    public static float pt2cm(float pt) {
        return pt / 72f * 2.54f;
    }
}
