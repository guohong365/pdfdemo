
package com.nantian.pdf.content;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;

import java.util.Collection;
import java.util.Set;

/**
 * pdf渲染监听，当找到渲染的文本时，得到文本的坐标x,y,w,h
 *
 * @author cunchang
 * @date 2020/9/4 上午10:45
 */
public class PositionRenderListener extends ResultRenderListener {

    /**
     * 出现key的标识，说明下一个值就是替换的目标
     */
    private boolean keyFlag = false;

    /**
     * 目标关键字的，前一个关键字
     */
    private Set<String> preKeySet;

    /**
     * 目标关键字
     */
    private String preKey;

    public PositionRenderListener() {
    }

    public PositionRenderListener(Set<String> preKeySet) {
        this.preKeySet = preKeySet;
    }

    public PositionRenderListener(Set<String> preKeySet, float fontSize) {
        this.preKeySet = preKeySet;
        super.defaultH = fontSize;
    }

    public void renderText(TextRenderInfo textInfo) {
        String text = textInfo.getText();
        System.out.println("listener text:"+text);
        if (keyFlag) {
            // bound 块内容
            Rectangle bound = textInfo.getBaseline().getBoundingRectangle();
            ReplaceRegion region = new ReplaceRegion();
            region.setPreKey(preKey);
            region.setValue(text);
            region.setH(bound.getHeight() == 0 ? this.defaultH : bound.getHeight());
            region.setW(bound.getWidth());
            region.setX(bound.getX());
            region.setY(bound.getY());
            result.add(region);
            keyFlag = false;
        }
        if (preKeySet.contains(text)) {
            keyFlag = true;
            preKey = text;
        }
    }

    @Override
    public Collection<IPdfTextLocation> getResultantLocations() {
        return null;
    }

    @Override
    public void eventOccurred(IEventData iEventData, EventType eventType) {

    }

    @Override
    public Set<EventType> getSupportedEvents() {
        return null;
    }
}