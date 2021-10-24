package com.nantian.weather.paper;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.weather.config.ElementDescriptor;
import com.nantian.weather.config.PaperSetting;
import com.nantian.weather.config.PapersConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Map;

//@Component
public class WeeklyPaperGenerator extends PaperGeneratorBase implements IWeeklyPaper {
    float TITLE_SIZE = 47;
    float MAIN_TITLE_SIZE = 18;
    float BODY_SIZE = 16;

    public WeeklyPaperGenerator(PapersConfig config) {
        super(config);
    }

    IBlockElement createElement(ElementDescriptor settings, Map<String, Object> params){
        return null;
    }

    @Override
    protected void onGenerate(Document document, Map<String, Object> params) throws MalformedURLException {
        PaperSetting setting=getSetting();
        for(ElementDescriptor elementSettings:setting.getElements()){
            IBlockElement element = createElement(elementSettings, params);
        }
        document.add(new Paragraph("迪 庆 州 天 气 周 报")
                        .setFont(xbs)
                        .setFontSize(TITLE_SIZE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontColor(ColorConstants.RED))
                .add(new Paragraph(MessageFormat.format("{0}年  第{1}期", params.get(KEY_YEAR), params.get(KEY_STAGE)))
                        .setFont(kai)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Table(new float[]{getWidth() / 3, getWidth() / 3, getWidth() / 3})
                        .setBorder(Border.NO_BORDER)
                        .addCell(new Cell()
                                .add(new Paragraph("迪庆州气象台")
                                        .setTextAlignment(TextAlignment.LEFT))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("签发：{0}", params.get(KEY_DATE_TIME)))
                                        .setTextAlignment(TextAlignment.CENTER))
                                .setBorder(Border.NO_BORDER))
                        .addCell(new Cell()
                                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_DATE_TIME)))
                                        .setTextAlignment(TextAlignment.RIGHT))
                                .setBorder(Border.NO_BORDER)))
                .add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                        .setLineThrough().setMarginBottom(1))
                .add(new LineSeparatorEx(new SolidLine(2.5f), ColorConstants.RED)
                        .setLineThrough().setMarginTop(0))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_WEEK_DATE)))
                        .setFont(xbs)
                        .setTextAlignment(TextAlignment.CENTER))
                .add(new Paragraph("一、上周天气概况")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_CAPTION)))
                        .setFirstLineIndent(BODY_SIZE * 2));
        Object param = params.get("form");
        if(param!=null){
            //TODO: output form table
        }
        param = params.get("barChart");
        if(param!=null){
            document
                    .add(new Image(ImageDataFactory.create(new URL(MessageFormat.format("{0}", params.get(KEY_BAR_CHART)))))
                            .setHorizontalAlignment(HorizontalAlignment.CENTER));
        }
        param = params.get("chart");
        if(param!=null){
            document.add(new Image(ImageDataFactory.create(new URL(MessageFormat.format("{0}", params.get(KEY_CHART)))))
                            .setHorizontalAlignment(HorizontalAlignment.CENTER));
        }
        document.add(new Paragraph("二、本周天气展望")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true))
                .add(new Paragraph(MessageFormat.format("{0}", params.get(KEY_WEATHER_WEEK)))
                        .setFirstLineIndent(BODY_SIZE * 2))
                .add(new Paragraph("具体城镇天气预报")
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(hei).setKeepWithNext(true));
        String[] cityForecast= params.get(KEY_WEATHER_FORECAST).toString().split("\n");
        for (String line:cityForecast){
            document.add(new Paragraph(line)
                    .setFirstLineIndent(BODY_SIZE*2));
        }


    }
}
