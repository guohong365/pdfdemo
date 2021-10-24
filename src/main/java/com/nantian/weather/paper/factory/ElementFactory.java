package com.nantian.weather.paper.factory;

import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.nantian.weather.config.ElementDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementFactory {

    static TextElementFactory text=new TextElementFactory();

    public static IElement create(ElementDescriptor descriptor, Map<String, Object> params) {
        switch(descriptor.getType()){
            case TEXT:
                return text.create(descriptor, params);
            case LIST:
            case PARAGRAPH:
            case TABLE:
            case CELL:
            case LINE:
            case DIV:
            case IMAGE:
        }
        return null;
    }

    public static List<String> parseKeys(String format){
        Pattern pattern=Pattern.compile("(\\{.+?\\})");
        List<String> keys=new ArrayList<>();
        Matcher matcher = pattern.matcher(format);
        while (matcher.find()){
            keys.add(format.substring(matcher.start(), matcher.end()));
        }
        return keys;
    }

}
