package com.nantian.pdf.utils;

import com.itextpdf.kernel.font.PdfFont;

import java.util.*;

public class FontCollection {
    public static class Fonts {
        public static final Font HEI = new Font("hei", "simhei.ttf");  //黑体
        public static final Font FANG = new Font("fang", "simfang.ttf"); //仿宋体
        public static final Font KAI = new Font("kai", "simkai.ttf"); //楷体
        public static final Font XBS = new Font("xbs", "fzxbsjw.ttf");  //方正小标宋
        public static final Font LI = new Font("li", "stliti.ttf");   //华文隶书
        public static final Font[] all ={HEI, FANG, KAI, XBS, LI};
    }
    static final String[] NAMES = {"kai", "hei", "fang", "xbs", "li", "song"};
    public PdfFont kai;
    public PdfFont hei;
    public PdfFont fang;
    public PdfFont xbs;
    public PdfFont li;
    public PdfFont song;
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
            case "song":
                return song;
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
            case "song":
                song=pdfFont;
                break;
        }
    }
}
