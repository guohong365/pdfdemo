package com.nantian.pdf.weather.paper;

import com.itextpdf.kernel.font.PdfFont;

import java.util.*;

public class FontCollection {
    static final String[] NAMES = {"kai", "hei", "fang", "xbs", "li"};
    public PdfFont kai;
    public PdfFont hei;
    public PdfFont fang;
    public PdfFont xbs;
    public PdfFont li;
    public static Collection<String> allName(){
        return Arrays.asList(NAMES);
    }
    public PdfFont get(String name){
        switch (name){
            case "kai":
                return kai;
            case "hei":
                return hei;
            case "fang":
                return fang;
            case "xbs":
                return xbs;
            case "li":
                return li;
            default:
                return null;
        }
    }
    public void assign(String name, PdfFont pdfFont){
        switch (name){
            case "kai":
                kai= pdfFont;
                break;
            case "hei":
                hei= pdfFont;
                break;
            case "fang":
                fang = pdfFont;
                break;
            case "xbs":
                xbs = pdfFont;
                break;
            case "li":
                li=pdfFont;
                break;
        }
    }
}
