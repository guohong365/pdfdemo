package com.nantian.pdf.weather;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.nantian.pdf.config.IPapersConfig;
import com.nantian.pdf.office.element.LineSeparatorEx;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Map;

@Component
public class WeeklyReport extends PaperGeneratorBase implements IWeeklyReport {
    final float TITLE_SIZE = 47;

    public WeeklyReport(IPapersConfig config) {
        super(config);
    }


    @Override
    protected Div createBody(Map<String, Object> params) {
        Div div=new Div();
        div.add(new Paragraph("迪 庆 州 天 气 周 报")
                        .setFont(fonts.xbs)
                        .setFontSize(TITLE_SIZE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.RED))
                .add(new Paragraph(MessageFormat.format("{0}年  第{1}期", params.get(KEY_YEAR), params.get(KEY_STAGE)))
                        .setFont(fonts.kai)
                        .setFixedLeading(31)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Table(new float[]{1, 1, 1})
                        .setMarginLeft(12)
                        .setMarginRight(12)
                        .setWidth(UnitValue.createPercentValue(100))
                        .addCell(new Cell()
                                .add(new Paragraph("迪庆州气象台")
                                        .setTextAlignment(TextAlignment.LEFT)
                                        .setFixedLeading(31))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("签发：{0}", params.get(KEY_ISSUE)))
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setFixedLeading(31))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_DATE_TIME)))
                                        .setTextAlignment(TextAlignment.RIGHT)
                                        .setFixedLeading(31))
                                .setBorder(Border.NO_BORDER)))
                .add(new LineSeparatorEx(new SolidLine(2.f), ColorConstants.RED)
                        .setLineThrough().setMarginTop(0))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_WEEK_DATE)))
                        .setFont(fonts.xbs)
                        .setFontSize(FONT_SIZE_2S_18)
                        .setFixedLeading(32)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("一、上周天气概况")
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                        .setFont(fonts.hei)
                        .setFixedLeading(9)
                        .setKeepWithNext(true))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_CAPTION)))
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2));
        addFormTable(div, params.get(KEY_FORM), params.get(KEY_TABLE_NAME));
        addImage(div, params.get(KEY_BAR_CHART));
        addImage(div, params.get(KEY_CHART));
        div
                .add(new Paragraph("二、本周天气展望")
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                        .setFont(fonts.hei).setKeepWithNext(true)
                        .setFixedLeading(28))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_WEATHER_WEEK)))
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                        .setFixedLeading(28));
        addImage(div, params.get(KEY_PRE_MAP));
        div
                .add(new Paragraph("具体城镇天气预报")
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                        .setFixedLeading(28)
                        .setFont(fonts.hei)
                        .setKeepWithNext(true));
        IBlockElement block=createMultiLineTextBlock(params.get(KEY_WEATHER_FORECAST).toString(), FONT_SIZE_30_16 * 2, 28);
        div.add(block);
        div
                .add(new Paragraph("三、关注与建议")
                        .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                        .setFont(fonts.hei)
                        .setFixedLeading(30)
                        .setKeepWithNext(true));
        block = createMultiLineTextBlock(params.get(KEY_FOLLOW).toString(), FONT_SIZE_30_16*2, 28);
        div.add(block);
        return div;
    }


    @Override
    protected Div createFooter(Map<String, Object> params) {
        Div div = new Div()
                .setFont(fonts.fang)
                .setFontSize(FONT_SIZE_4S_12)
                .setKeepTogether(true);
        //上部横线
        div.add(new LineSeparator(new SolidLine(1)));
        //抄送单位
        div.add(new Paragraph()
                .setFirstLineIndent(FONT_SIZE_4S_12 * 2)
                .setFixedLeading(18)
                .add(new Text("报送：").setBold())
                .add(new Text(params.get(KEY_SUBMITTED).toString())));
        //下部横线
        div.add(new LineSeparator(new SolidLine(1)));
        Table table = new Table(new float[]{1, 1, 1})
                .setMarginLeft(FONT_SIZE_4S_12 * 2)
                .setMarginRight(FONT_SIZE_4S_12 * 2)
                .setWidth(UnitValue.createPercentValue(100))
                .setAutoLayout()
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("制作：" + params.get(KEY_MAKE))
                        .setTextAlignment(TextAlignment.LEFT));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("校对：" + params.get(KEY_CHECK_NAME))
                        .setTextAlignment(TextAlignment.CENTER));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("联系电话：" + params.get(KEY_PHONE))
                        .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(cell);
        div.add(table);
        return div;
    }
}
