package com.nantian.pdf.office;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.element.Div;
import com.nantian.pdf.utils.Units;

import java.util.List;

public interface IDocumentElement {
  float THICK_LINE_WIDTH= Units.cm2pt(0.035F);
  float THIN_LINE_WIDTH=Units.cm2pt(0.025F);
  String FULL_COLON="\uFF1A";
  String SLIGHT_PAUSE="\u3001";
  String FULL_COMMA="\uFF0C";
  String FULL_JU="\uFF0E";
  String KEY_COPY_NUMBER = "份号";
  String KEY_SECURITY_CLASSIFICATION="密级";
  String KEY_URGENCY = "紧急程度";
  String KEY_POSTERS ="发文单位";
  String KEY_POST_NUMBER="发文字号";
  String KEY_TITLE="标题";
  String KEY_CONTENT="正文";
  String FULL_BLANK ="\u3000";
  IOfficeDocument getDocument();
  void setDocument(IOfficeDocument document);
  PdfFont getFont(String fontName);
  Div create();
  List<IDocumentElement> children();
  IDocumentElement add(IDocumentElement element);
}
