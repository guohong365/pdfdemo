package com.nantian.pdf.weather;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.nantian.pdf.utils.CellSlashRenderer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SpecialWeatherService extends PaperGeneratorBase implements ISpecialWeatherService {
    public SpecialWeatherService(IPapersConfig config) {
        super(config);
    }

    @Override
    protected Div createBody(Map<String, Object> params) {
        Div div=new Div();
        IBlockElement block = new Paragraph(params.get(KEY_SPECIAL).toString())
                .setFont(fonts.li)
                .setFontSize(FONT_SIZE_0S_36)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.RED)
                .setMultipliedLeading(1.5f);
        div.add(block);
        block = new Paragraph("第" + params.get(KEY_STAGE) + "期")
                .setFont(fonts.kai)
                .setTextAlignment(TextAlignment.CENTER)
                .setMultipliedLeading(1.5f);
        div.add(block);
        Table table = new Table(new float[]{1, 1, 1})
                .setFont(fonts.kai)
                .setMarginLeft(12)
                .setMarginRight(12)
                .setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .add(new Paragraph("迪庆州气象台"));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .add(new Paragraph("签发：" + params.get(KEY_ISSUE)));
        table.addCell(cell);
        cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT)
                .add(new Paragraph(params.get(KEY_DATE_TIME).toString()));
        table.addCell(cell);
        div.add(table);
        LineSeparator line = new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginBottom(0.8f);
        div.add(line);
        line = new LineSeparatorEx(new SolidLine(3), ColorConstants.RED)
                .setMarginTop(0);
        div.add(line);

        block = new Paragraph(params.get(KEY_TITLE).toString())
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fonts.xbs)
                .setBold()
                .setFontSize(FONT_SIZE_20_22);
        div.add(block);
        block = new Paragraph("摘要：" + params.get(KEY_REMARK))
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setBold()
                .setKeepTogether(true);
        div.add(block);
        block = new Paragraph("一、前期天气概况")
                .setFirstLineIndent(FONT_SIZE_3S_15 * 2)
                .setFont(fonts.hei)
                .setFontSize(FONT_SIZE_3S_15)
                .setFixedLeading(25)
                .setKeepWithNext(true);
        div.add(block);
        block = new Paragraph(params.get(KEY_CAPTION).toString())
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFixedLeading(25)
                .setKeepWithNext(true);
        div.add(block);
        addFormTable(div, params.get(KEY_FORM), "");
        addImage(div, params.get(KEY_CHART));
        block = new Paragraph("二、全州天气预报")
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFont(fonts.hei)
                .setFixedLeading(26)
                .setKeepWithNext(true);
        div.add(block);
        block = new Paragraph(params.get(KEY_WEATHER_FORECAST).toString())
                .setFirstLineIndent(FONT_SIZE_30_16*2)
                .setFixedLeading(26);
        div.add(block);
        addImage(div, params.get(KEY_PRE_MAP));
        block = new Paragraph("具体预报如下:")
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFixedLeading(26)
                .setKeepWithNext(true);
        div.add(block);

        addCityForecastTable(div, fonts.fang, params.get(KEY_CITY_FORECAST));

        block = new Paragraph("三、影响分析和建议")
                .setFont(fonts.hei)
                .setFixedLeading(26)
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setKeepWithNext(true);
        div.add(block);
        Div textDiv =createMultiLineTextBlock(params.get(KEY_FOLLOW).toString(), FONT_SIZE_30_16 *2, 28);
        div.add(textDiv);
        return div;
    }

    private void addCityForecastTable(Div div, PdfFont font, Object tableList) {
        if(!(tableList instanceof List)) return;
        List<String> list = (List<String>)tableList;
        Table table=new Table(new float[]{3,1,1,1,1,1,1})
                .setFontSize(FONT_SIZE_50_10_5)
                .setAutoLayout()
                .setTextAlignment(TextAlignment.CENTER)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setMarginBottom(FONT_SIZE_40_14)
                .setMarginTop(FONT_SIZE_40_14);
        Cell cell=new Cell(2,1);
        cell.setNextRenderer(new CellSlashRenderer(cell, font,  "日期", "城市"));
        table.addCell(cell);
        cell = new Cell(1,2).add(new Paragraph("香格里拉"));
        table.addCell(cell);
        cell = new Cell(1,2).add(new Paragraph("德钦"));
        table.addCell(cell);
        cell = new Cell(1,2).add(new Paragraph("维西"));
        table.addCell(cell);
        for (int i=0; i< 3; i++) {
            cell = new Cell().add(new Paragraph("天气现象"));
            table.addCell(cell);
            cell = new Cell().add(new Paragraph("气温℃"));
            table.addCell(cell);
        }
        for (String line:list) {
            String[] items = line.split("\\|");
            for (String item : items) {
                cell = new Cell().add(new Paragraph(item));
                table.addCell(cell);
            }
        }
        div.add(table);
    }

    @Override
    protected Div createFooter(Map<String, Object> params) {
        Div div = new Div()
                .setFont(fonts.fang)
                .setFontSize(FONT_SIZE_4S_12);
        //上部横线
        LineSeparator line = new LineSeparatorEx(new SolidLine(1), ColorConstants.RED);
        div.add(line);
        //抄送单位
        Paragraph p = new Paragraph()
                .setFirstLineIndent(FONT_SIZE_4S_12 * 2)
                .setFixedLeading(18)
                .add(new Text("报送：").setBold())
                .add(new Text(params.get(KEY_SUBMITTED).toString()));
        div.add(p);
        //下部横线
        line = new LineSeparatorEx(new SolidLine(1), ColorConstants.RED);
        div.add(line);
        Table table = new Table(new float[]{1, 1, 1})
                .setMarginLeft(FONT_SIZE_4S_12 * 2)
                .setMarginRight(FONT_SIZE_4S_12 * 2)
                .setMarginBottom(0)
                .setAutoLayout()
                .setWidth(UnitValue.createPercentValue(100));
        Cell cell = new Cell()
                .add(new Paragraph("制作：" + params.get(KEY_MAKE))
                        .setFixedLeading(24))
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER);
        table.addCell(cell);
        cell = new Cell()
                .add(new Paragraph("校对：" + params.get(KEY_CHECK_NAME)).setFixedLeading(24))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER);
        table.addCell(cell);
        cell = new Cell()
                .add(new Paragraph("联系电话：" + params.get(KEY_PHONE)).setFixedLeading(24))
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER);
        table.addCell(cell);
        div.add(table);
        return div;
    }
}
