package com.nantian.weather.paper;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.weather.config.PapersConfig;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;

@Component
public class WeeklyPaperGenerator extends PaperGeneratorBase implements IWeeklyPaper {
    float TITLE_SIZE = 47;
    float MAIN_TITLE_SIZE = 18;
    float BODY_SIZE = 16;

    public WeeklyPaperGenerator(PapersConfig config) {
        super(config);
    }

    float getWidth() {
        return getSetting().getPageSetting().getPage().getWidth()
                - getSetting().getPageSetting().getMargin().getLeft()
                - getSetting().getPageSetting().getMargin().getRight();
    }

    float getHeight() {
        return getSetting().getPageSetting().getPage().getHeight()
                - getSetting().getPageSetting().getMargin().getTop()
                - getSetting().getPageSetting().getMargin().getBottom();
    }

    float getBottom() {
        return getSetting().getPageSetting().getPage().getHeight()
                - getSetting().getPageSetting().getMargin().getBottom();
    }

    float getTop() {
        return getSetting().getPageSetting().getMargin().getTop();
    }

    float getLeft() {
        return getSetting().getPageSetting().getMargin().getLeft();
    }

    float getRight() {
        return getSetting().getPageSetting().getPage().getWidth()
                - getSetting().getPageSetting().getMargin().getRight();
    }

    @Override
    protected void onGenerate(Document document, Map<String, Object> params) throws MalformedURLException {
        document.add(new Paragraph("迪 庆 州 天 气 周 报")
                        .setFont(xbs)
                        .setFontSize(TITLE_SIZE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.RED))
                .add(new Paragraph(MessageFormat.format("{0}年  第{1}期", params.get("year"), params.get("stage")))
                        .setFont(kai)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Table(new float[]{getWidth() / 3, getWidth() / 3, getWidth() / 3})
                        .setBorder(Border.NO_BORDER)
                        .addCell(new Cell()
                                .add(new Paragraph("迪庆州气象台")
                                        .setTextAlignment(TextAlignment.LEFT))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("签发：{0}", params.get("issue")))
                                        .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("{0}", params.get("dateTime")))
                                        .setTextAlignment(TextAlignment.RIGHT))
                                .setBorder(Border.NO_BORDER)))
                .add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                        .setLineThrough().setMarginBottom(1))
                .add(new LineSeparatorEx(new SolidLine(2.5f), ColorConstants.RED)
                        .setLineThrough().setMarginTop(0))
                .add(new Paragraph(MessageFormat.format("{0}", params.get("weekdate")))
                        .setFont(xbs)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("一、上周天气概况")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true))
                .add(new Paragraph(MessageFormat.format("{0}", params.get("caption")))
                        .setFirstLineIndent(BODY_SIZE * 2))

                .add(new Image(ImageDataFactory.create(new URL(MessageFormat.format("{0}", params.get("chart")))))
                        .setHorizontalAlignment(HorizontalAlignment.CENTER))
                .add(new Paragraph("二、本周天气展望")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true))
                .add(new Paragraph(MessageFormat.format("{0}", params.get("weatherWeek")))
                        .setFirstLineIndent(BODY_SIZE * 2))
                .add(new Paragraph("具体城镇天气预报")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true));
    }
}
