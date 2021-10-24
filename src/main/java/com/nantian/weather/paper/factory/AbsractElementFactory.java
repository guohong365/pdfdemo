package com.nantian.weather.paper.factory;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Text;
import com.nantian.weather.config.ElementDescriptor;
import com.nantian.weather.config.PapersConfig;
import org.springframework.util.StringUtils;

import javax.swing.text.AbstractDocument;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class AbsractElementFactory implements IElementFactory{
    protected abstract IElement createInstance(ElementDescriptor descriptor, String content);
    protected PdfFont getFont(ElementDescriptor descriptor){
        return descriptor.getFont()== null || !StringUtils.hasText(descriptor.getFont().getName()) ?
                null:
                PapersConfig.getFont(descriptor.getFont().getName());
    }
    protected Float getFontSize(ElementDescriptor descriptor){
        return descriptor.getFont()==null ? null : descriptor.getFont().getSize();
    }
    protected void setAttributes(IBlockElement element){

    }
    protected void setAttributes(IElement element){
    }
    protected void setAttributes(ILeafElement element){

    }
    @Override
    public IElement create(ElementDescriptor descriptor, Map<String, Object> params) {
        List<Object> args=new ArrayList<>();
        for(String key:descriptor.getKeys()){
            Object item=params.get(key);
            if(item == null){
                if(descriptor.isOptional()){
                    return null;
                }
                throw new IllegalArgumentException("必备参数：["+ key +"]未提供。");
            }
            args.add(item);
        }
        MessageFormat format = new MessageFormat(descriptor.getFormat());
        String content = format.format(args.toArray());

        return createInstance(descriptor, content);
    }
}
