package com.nantian.pdf.weather.paper;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.nantian.pdf.weather.config.IPapersConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Component
public class SpecialWeatherService extends PaperGeneratorBase implements ISpecialWeatherService {
    public SpecialWeatherService(IPapersConfig config) {
        super(config);
    }

    @Override
    protected void createBody(Document document, FontCollection fonts, Map<String, Object> params) throws MalformedURLException {
        IBlockElement block = new Paragraph(params.get(KEY_SPECIAL).toString())
                .setFont(fonts.li)
                .setFontSize(FONT_SIZE_0S_36)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.RED)
                .setMultipliedLeading(1.5f);
        document.add(block);
        block = new Paragraph("第" + params.get(KEY_STAGE) + "期")
                .setFont(fonts.kai)
                .setTextAlignment(TextAlignment.CENTER)
                .setMultipliedLeading(1.5f);
        document.add(block);
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
        document.add(table);
        LineSeparator line = new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                .setMarginBottom(0.8f);
        document.add(line);
        line = new LineSeparatorEx(new SolidLine(3), ColorConstants.RED)
                .setMarginTop(0);
        document.add(line);

        block = new Paragraph(params.get(KEY_TITLE).toString())
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(fonts.xbs)
                .setBold()
                .setFontSize(FONT_SIZE_20_22);
        document.add(block);
        block = new Paragraph("摘要：" + params.get(KEY_REMARK))
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setBold()
                .setKeepTogether(true);
        document.add(block);
        block = new Paragraph("一、前期天气概况")
                .setFirstLineIndent(FONT_SIZE_3S_15 * 2)
                .setFont(fonts.hei)
                .setFontSize(FONT_SIZE_3S_15)
                .setFixedLeading(25)
                .setKeepWithNext(true);
        document.add(block);
        block = new Paragraph(params.get(KEY_CAPTION).toString())
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFixedLeading(25)
                .setKeepWithNext(true);
        document.add(block);
        addFormTable(document, params.get(KEY_FORM), "");
        addImage(document, params.get(KEY_CHART));
        block = new Paragraph("二、全州天气预报")
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFont(fonts.hei)
                .setFixedLeading(26)
                .setKeepWithNext(true);
        document.add(block);
        block = new Paragraph(params.get(KEY_WEATHER_FORECAST).toString())
                .setFirstLineIndent(FONT_SIZE_30_16*2)
                .setFixedLeading(26);
        document.add(block);
        addImage(document, params.get(KEY_PRE_MAP));
        block = new Paragraph("具体预报如下:")
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setFixedLeading(26)
                .setKeepWithNext(true);
        document.add(block);

        addCityForcastTable(document, params.get(KEY_CITY_FORECAST));

        block = new Paragraph("三、影响分析和建议")
                .setFont(fonts.hei)
                .setFixedLeading(26)
                .setFirstLineIndent(FONT_SIZE_30_16 * 2)
                .setKeepWithNext(true);
        document.add(block);
        Div div =createMultiLineTextBlock(params.get(KEY_FOLLOW).toString(), FONT_SIZE_30_16 *2, 28);
        document.add(div);
    }

    private void addCityForcastTable(Document document, Object tableList) {
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
        try {
            Image image =new Image(ImageDataFactory.create(config.getResourcesPath() + File.separator + "slash.png"));
            BackgroundImage backgroundImage=
                    new BackgroundImage.Builder()
                            .setImage(image.getXObject())
                            .setBackgroundRepeat(new BackgroundRepeat(BackgroundRepeat.BackgroundRepeatValue.ROUND)).build();
            cell.setBackgroundImage(backgroundImage)
                    .add(new Paragraph("城市")
                            .setTextAlignment(TextAlignment.RIGHT)
                            .setVerticalAlignment(VerticalAlignment.MIDDLE)
                            .setMargins(0, 2, 0, 0))
                    .add(new Paragraph("日期")
                            .setTextAlignment(TextAlignment.CENTER)
                            .setVerticalAlignment(VerticalAlignment.BOTTOM)
                            .setMargins(0, 0,2,0));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
        document.add(table);
    }

    @Override
    protected IBlockElement createFooter(FontCollection fonts, Map<String, Object> params) {
        Div div = new Div()
                .setFont(fonts.fang)
                .setFontSize(FONT_SIZE_4S_12)
                .setKeepTogether(true);
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

        div.setFixedPosition(getLeft(), getBottom(), getWidth());
        return div;
    }
}