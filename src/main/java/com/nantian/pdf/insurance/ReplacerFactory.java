package com.nantian.pdf.insurance;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.nantian.pdf.content.IPdfReplacer;
import com.nantian.pdf.content.Replacer;
import com.nantian.pdf.content.ReplacerStrategy;

import java.util.HashMap;
import java.util.Map;

public class ReplacerFactory {
    public static final String TYPE_SUN_SHINE="阳光保险";
    public static final String TYPE_CONTINENT="大地保险";
    public static final String TYPE_CHANG_AN="长安保险";

    //阳光保险
    private static final Rectangle[] sunShineRegions=new Rectangle[]{
            new Rectangle(175, 593, 121, 9),//被保险人名称
            new Rectangle(381, 593, 121, 9), //被保险人证件号
            new Rectangle(174, 551, 121, 9),//项目名称
            new Rectangle(381, 551, 121, 9)//招标编号
    };
    private static final Rectangle[] changAnRegions=new Rectangle[]{
            new Rectangle(183,615.71f, 184, 10.03f),
            new Rectangle(183,578.37f, 184, 10.03f),
            new Rectangle(443,615.71f, 108, 10.03f),
            new Rectangle(443,578.37f, 108, 10.03f),
    };
    private static final Rectangle[] continentRegions=new Rectangle[]{};

    public static IPdfReplacer create(String type){
        switch (type){
            case TYPE_CONTINENT:
                return new Replacer(1, continentRegions);
            case TYPE_SUN_SHINE:
                return new Replacer(1, sunShineRegions);
            case TYPE_CHANG_AN:
                return new Replacer(1, changAnRegions);
        }
        return null;
    }
    public static IPdfReplacer create(String[] values){
        return new Replacer(1, values);
    }

}
