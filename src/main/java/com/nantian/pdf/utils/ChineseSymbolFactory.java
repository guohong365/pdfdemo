package com.nantian.pdf.utils;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.IListSymbolFactory;
import org.springframework.util.StringUtils;

public class ChineseSymbolFactory implements IListSymbolFactory {
    static final String DEFAULT_SUFFIX="．";
    static final String DEFAULT_PREFIX="";
    Style style;
    String prefix;
    String suffix;
    public static Style createStyle(PdfFont font, float fontSize){
        Style style=new Style();
        style.setFont(font).setFontSize(fontSize);
        return style;
    }
    public ChineseSymbolFactory(Style style, String suffix){
        this.style=style;
        this.suffix=suffix;
    }
    public ChineseSymbolFactory(Style style, String prefix, String suffix){
        this.style=style;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public ChineseSymbolFactory(PdfFont font, float fontSize){
        this.style=createStyle(font, fontSize);
    }
    public ChineseSymbolFactory(PdfFont font, float fontSize, String suffix){
        this.style=createStyle(font, fontSize);
        this.suffix = suffix;
    }
    public ChineseSymbolFactory(PdfFont font, float fontSize, String prefix, String suffix){
        this.style=createStyle(font, fontSize);
        this.prefix = prefix;
        this.suffix = suffix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public IElement createSymbol(int i, IPropertyContainer iPropertyContainer, IPropertyContainer iPropertyContainer1) {
        return new Text((StringUtils.hasText(this.prefix) ? this.prefix :DEFAULT_PREFIX) +
                        ChineseNumberConverter.convert(i) +
                        (StringUtils.hasText(this.suffix)?this.suffix :DEFAULT_SUFFIX))
                .addStyle(style);
    }
}
