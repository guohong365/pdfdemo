package com.nantian.pdf.office;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.UnitValue;
import com.nantian.pdf.office.element.LineSeparatorEx;
import com.nantian.pdf.utils.Units;

import java.util.ArrayList;
import java.util.List;

public class Header extends AbstractDocumentElement {
  float POSTERS_POSITION_TOP = IOfficeDocument.PAGE_HEIGHT - IOfficeDocument.TOP_MARGIN - Units.cm2pt(3.5f);
  CopyNumber copyNumber;
  Security security;
  Urgency urgency;
  Posters posters;
  Signers signers;
  PostNumber postNumber;

  public Header addCopyNumber(CopyNumber copyNumber) {
    this.copyNumber = copyNumber;
    return (Header) add(copyNumber);
  }

  public Header addSecurity(Security security) {
    this.security = security;
    add(security);
    return this;
  }

  public Header addUrgency(Urgency urgency) {
    this.urgency = urgency;
    add(urgency);
    return this;
  }

  public Header addPosters(Posters posters) {
    this.posters = posters;
    add(posters);
    return this;
  }

  public Header addPostNumber(PostNumber postNumber) {
    this.postNumber = postNumber;
    add(postNumber);
    return this;
  }

  public Header addSigners(Signers signers) {
    this.signers = signers;
    add(signers);
    return this;
  }

  public Div createCore() {
    Div header = new Div();
    Rectangle typeBox = getDocument().getTypePageBox();
    List<IBlockElement> sections = new ArrayList<>();
    Div part;
    if (copyNumber != null && (part = copyNumber.create()) != null) {
      header.add(part);
      typeBox = calculateLeftArea(part, typeBox);
    }
    if (security != null && (part = security.create()) != null) {
      header.add(part);
      typeBox = calculateLeftArea(part, typeBox);
    }
    if (urgency != null && (part = urgency.create()) != null) {
      header.add(part);
      typeBox = calculateLeftArea(part, typeBox);
    }
    assert typeBox != null;
    float top_margin = typeBox.getTop() - POSTERS_POSITION_TOP;
    part = posters.create();
    part.setProperty(Property.MARGIN_TOP, new UnitValue(UnitValue.POINT, top_margin));
    part.add(new Paragraph(" ")).add(new Paragraph(" "));
    header.add(part);
    typeBox = calculateLeftArea(part, typeBox);

    if (signers != null) {
      part = signers.create();
      header.add(part);
      typeBox = calculateLeftArea(part, typeBox);
    }
    if (postNumber != null) {
      part = postNumber.create();
      part.setFixedPosition(typeBox.getX(), typeBox.getTop(), typeBox.getWidth());
      header.add(part);
    }
    header.add(new LineSeparatorEx(new SolidLine(THICK_LINE_WIDTH),ColorConstants.RED).setMarginTop(Units.cm2pt(0.4F)));
    return header;
  }
}
