package com.nantian.pdf.content;

import com.itextpdf.kernel.pdf.canvas.parser.listener.ILocationExtractionStrategy;

public interface ILocatingStrategy extends ILocationExtractionStrategy {
    ICharacterMatcher getMatcher();
    void setMatcher(ICharacterMatcher matcher);
}


