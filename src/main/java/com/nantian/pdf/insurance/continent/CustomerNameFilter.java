package com.nantian.pdf.insurance.continent;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;

//被保人姓名
public class CustomerNameFilter extends TextRegionEventFilter {

    public CustomerNameFilter(Rectangle filterRect) {
        super(filterRect);
    }
    @Override
    public boolean accept(IEventData data, EventType eventType) {
        if (eventType == EventType.RENDER_TEXT) {
            TextRenderInfo textRenderInfo=(TextRenderInfo) data;
            System.out.println(textRenderInfo.getText());
        }
        return false;
    }
}
