package com.nantian.pdf.parse;

import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;

import java.util.List;

public interface IElementLocationListener extends IEventListener {
    List<IPdfElementLocation> getLocations();
}
