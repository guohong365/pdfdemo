package com.nantian.pdf.weather;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.nantian.pdf.config.IPapersConfig;
import java.text.MessageFormat;
import java.util.Map;

@Component
public class ImportantForecastService extends PaperGeneratorBase implements IImportantForecastService {
    protected ImportantForecastService(IPapersConfig config) {
        super(config);
    }

    @Override
    protected Div createBody(Map<String, Object> params) {
        Div div=new Div();
        div.add(new Paragraph("重要天气预报")
                        .setFontColor(ColorConstants.RED)
                        .setFont(fonts.xbs)
                        .setFontSize(FONT_SIZE_00_42)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFixedLeading(26))
                .add(new Paragraph(MessageFormat.format("{0} 第{1}期", params.get(KEY_YEAR), params.get(KEY_STAGE)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(fonts.kai)
                        .setFixedLeading(31))
                .add(new Table(new float[]{1, 1, 1})
                        .setAutoLayout()
                        .setWidth(UnitValue.createPercentValue(100))
                        .setMarginLeft(12)
                        .setMarginRight(12)
                        .addCell(new Cell()
                                .add(new Paragraph("迪庆州气象局")
                                        .setTextAlignment(TextAlignment.LEFT))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("签发：{0}", params.get(KEY_ISSUE)))
                                        .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(params.get(KEY_DATE_TIME).toString())
                                        .setTextAlignment(TextAlignment.RIGHT))
                                .setBorder(Border.NO_BORDER)))
                .add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED).setMarginBottom(0.8f))
                .add(new LineSeparatorEx(new SolidLine(3), ColorConstants.RED));
        Object param = params.get(KEY_PARAGRAPH_TITLE_1);
        if (param != null && StringUtils.hasText(param.toString())) {
            div.add(new Paragraph(param.toString())
                    .setFont(fonts.xbs)
                    .setFontSize(FONT_SIZE_2S_18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(18));
        }
        param = params.get(KEY_PARAGRAPH_TITLE_2);
        if (param != null && StringUtils.hasText(param.toString())) {
            div.add(new Paragraph(param.toString())
                    .setFont(fonts.xbs)
                    .setFontSize(FONT_SIZE_2S_18)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(18));
        }
        div.add(new Paragraph(params.get(KEY_PARAGRAPH_MESSAGE).toString())
                .setFont(fonts.kai)
                .setFontSize(FONT_SIZE_40_14)
                .setFixedLeading(32)
                .setFirstLineIndent(FONT_SIZE_40_14 * 2));
        addImage(div, params.get(KEY_CHART));
        div
                .add(new Paragraph("具体预报")
                        .setFirstLineIndent(18 * 2)
                        .setFont(fonts.hei)
                        .setFontSize(FONT_SIZE_2S_18));
        Div textDiv = createMultiLineTextBlock(params.get(KEY_SPECIFIC_FORECAST).toString(), FONT_SIZE_30_16 * 2, 28);
        div.add(textDiv);
        div.add(new Paragraph("建  议：")
                .setFirstLineIndent(18 * 2)
                .setFont(fonts.hei)
                .setFontSize(FONT_SIZE_2S_18));
        textDiv = createMultiLineTextBlock(params.get(KEY_SPECIFIC_FORECAST).toString(), FONT_SIZE_30_16 * 2, 28);
        div.add(textDiv);
        return div;
    }

    @Override
    protected Div createFooter(Map<String, Object> params) {
        Div div = new Div()
                .setFont(fonts.fang)
                .setFontSize(FONT_SIZE_4S_12)
                .setKeepTogether(true);
        //上部横线
        div.add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginBottom(0.8f));
        div.add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginTop(0));
        //抄送单位
        div.add(new Paragraph()
                .setFirstLineIndent(FONT_SIZE_4S_12 * 2)
                .setFixedLeading(18)
                .add(new Text("报送：").setBold())
                .add(new Text(params.get(KEY_REPORTING).toString())));
        div.add(new Paragraph()
                .setFirstLineIndent(FONT_SIZE_4S_12 * 2)
                .setFixedLeading(18)
                .add(new Text("抄送").setBold())
                .add(new Text(params.get(KEY_REPORT).toString())));
        //下部横线
        div.add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginBottom(0.8f));
        div.add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginTop(0));
        Table table = new Table(new float[]{1, 1, 1, 1})
                .setFont(fonts.fang)
                .setFontSize(FONT_SIZE_50_10_5)
                .setMarginLeft(FONT_SIZE_50_10_5 * 2)
                .setMarginRight(FONT_SIZE_50_10_5 * 2)
                .setWidth(UnitValue.createPercentValue(100))
                .setAutoLayout()
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("预报：" + params.get(KEY_FORECASTER))
                        .setTextAlignment(TextAlignment.LEFT));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("制作：" + params.get(KEY_MAKER))
                        .setTextAlignment(TextAlignment.CENTER));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("校对：" + params.get(KEY_CHECKER))
                        .setTextAlignment(TextAlignment.CENTER));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph("联系电话：" + params.get(KEY_CONTACT_NUMBER))
                        .setTextAlignment(TextAlignment.RIGHT));
        table.addCell(cell);
        div.add(table);
        return div;
    }
}
