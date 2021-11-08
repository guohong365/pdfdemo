package com.nantian.pdf.parse;

import com.itextpdf.kernel.geom.IShape;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.geom.Subpath;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.PathRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.CharacterRenderInfo;

import java.util.*;

public class ElementLocationListener implements IElementLocationListener {
    private final List<IPdfElementLocation> result=new ArrayList<>();

    public List<IPdfElementLocation> getLocations() {
        result.sort((l1, l2) -> {
            Rectangle o1 = l1.getRectangle();
            Rectangle o2 = l2.getRectangle();
            if (o1.getY() == o2.getY()) {
                return Float.compare(o1.getX(), o2.getX());
            } else {
                return o1.getY() < o2.getY() ? -1 : 1;
            }
        });
        removeDuplicates(result);
        return result;
    }

    private void removeDuplicates(List<IPdfElementLocation> sortedList) {
        IPdfElementLocation lastItem = null;
        int orgSize = sortedList.size();
        for (int i = orgSize - 1; i >= 0; i--) {
            IPdfElementLocation currItem = sortedList.get(i);
            Rectangle currRect = currItem.getRectangle();
            if (lastItem != null && currRect.equalsWithEpsilon(lastItem.getRectangle())) {
                sortedList.remove(currItem);
            }
            lastItem = currItem;
        }
    }

    @Override
    public void eventOccurred(IEventData data, EventType type) {
        Rectangle bounds =null;
        if(data instanceof TextRenderInfo){
            TextRenderInfo info=(TextRenderInfo) data;
            for(Rectangle r:toRectangles(toCRI(info))) {
                bounds = Rectangle.getCommonRectangle(bounds, r);
            }
            result.add(new ElementLocation(0, bounds, info.getText()));
        } else if (data instanceof PathRenderInfo){
            PathRenderInfo info = (PathRenderInfo) data;
            for (Subpath subpath: info.getPath().getSubpaths()) {
                for (IShape shape : subpath.getSegments()) {
                    Rectangle rectangle=Rectangle.calculateBBox(shape.getBasePoints());
                    if(bounds==null){
                        bounds = rectangle;
                    } else {
                        bounds = Rectangle.getCommonRectangle(rectangle, bounds);
                    }
                }
            }
            result.add(new ElementLocation(0, bounds, info.getPath()));
        } else if(data instanceof ImageRenderInfo){

            ImageRenderInfo info = (ImageRenderInfo) data;
            Matrix matrix= info.getImageCtm();
            System.out.println(matrix);
            result.add(new ElementLocation(0, new Rectangle(
                    info.getStartPoint().get(0),
                    info.getStartPoint().get(1),
                    matrix.get(Matrix.I11), matrix.get(Matrix.I22)), info.getImage()));
        }
    }

    @Override
    public Set<EventType> getSupportedEvents() {
        return null;
    }

    protected List<CharacterRenderInfo> toCRI(TextRenderInfo tri) {
        List<CharacterRenderInfo> cris = new ArrayList<>();
        for (TextRenderInfo subTri : tri.getCharacterRenderInfos()) {
            cris.add(new CharacterRenderInfo(subTri));
        }
        return cris;
    }

    protected List<Rectangle> toRectangles(List<CharacterRenderInfo> cris) {
        List<Rectangle> retval = new ArrayList<>();
        if (cris.isEmpty()) {
            return retval;
        }
        int prev = 0;
        int curr = 0;
        while (curr < cris.size()) {
            while (curr < cris.size() ) {
                curr++;
            }
            Rectangle resultRectangle = null;
            for (CharacterRenderInfo cri : cris.subList(prev, curr)) {
                // in case letters are rotated (imagine text being written with an angle of 90 degrees)
                resultRectangle = Rectangle.getCommonRectangle(resultRectangle, cri.getBoundingBox());
            }
            retval.add(resultRectangle);
            prev = curr;
        }

        // return
        return retval;
    }
}
