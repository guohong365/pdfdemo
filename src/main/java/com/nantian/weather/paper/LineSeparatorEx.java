package com.nantian.weather.paper;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.element.LineSeparator;

public class LineSeparatorEx extends LineSeparator {
    public LineSeparatorEx(ILineDrawer lineDrawer, Color color) {
        super(lineDrawer);
        lineDrawer.setColor(color);
    }
}
