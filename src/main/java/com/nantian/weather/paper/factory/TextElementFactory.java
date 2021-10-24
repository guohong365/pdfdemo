package com.nantian.weather.paper.factory;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.weather.config.ElementDescriptor;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Map;

public class TextElementFactory extends AbsractElementFactory implements IElementFactory{


    @Override
    protected IElement createInstance(ElementDescriptor descriptor, String content) {
        Text text=new Text(content);
        PdfFont font = getFont(descriptor);
        Float size = getFontSize(descriptor);
        Color color = descriptor.getColor();
        if(font!=null){
            text.setFont(font);
        }
        if(size!=null){
            text.setFontSize(size);
        }
        if(color!=null){
            text.setFontColor(color);
        }
        switch (descriptor.getAlignment()){
            case LeftTop:
                text.setTextAlignment(TextAlignment.LEFT);
            case CenterTop:
            case RightTop:
            case LeftCenter:
            case CenterCenter:
            case RightCenter:
            case LeftBottom:
            case CenterBottom:
            case RightBottom:
            case Justified:
            case JustifiedFull:
            default:
        }
        return text;
    }
}
