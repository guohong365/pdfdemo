package com.nantian.pdf.weather.paper;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.nantian.pdf.weather.config.IPapersConfig;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.text.MessageFormat;
import java.util.Map;

public class ImportantForecastService extends PaperGeneratorBase implements IImportantForecastService{
    float TITLE_SIZE=48;
    float SUBTITLE_SIZE=18;
    float MSG_SIZE=14;
    protected ImportantForecastService(IPapersConfig config) {
        super(config);
    }

    @Override
    protected void createBody(Document document, FontCollection fonts, Map<String, Object> params) throws MalformedURLException {
        document.add(new Paragraph("重要天气预报")
                        .setFontColor(ColorConstants.RED)
                        .setFont(fonts.xbs)
                        .setFontSize(TITLE_SIZE)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFixedLeading(26))
                .add(new Paragraph(MessageFormat.format("{0} 第{1}期", params.get(KEY_YEAR), params.get(KEY_STAGE)))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFont(fonts.kai)
                        .setFixedLeading(31))
                .add(new Table(new float[]{getWidth()/3, getWidth()/3, getWidth()/3})
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
                .add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED).setMarginBottom(0.5f))
                .add(new LineSeparatorEx(new SolidLine(3), ColorConstants.RED));
        Object param = params.get(KEY_PARAGRAPH_TITLE_1);
        if(param!=null && StringUtils.hasText(param.toString())) {
            document.add(new Paragraph(param.toString())
                    .setFont(fonts.xbs)
                    .setFontSize(SUBTITLE_SIZE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(18));
        }
        param = params.get(KEY_PARAGRAPH_TITLE_2);
        if(param!=null && StringUtils.hasText(param.toString())) {
            document.add(new Paragraph(param.toString())
                    .setFont(fonts.xbs)
                    .setFontSize(SUBTITLE_SIZE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedLeading(18));
        }
        document.add(new Paragraph(params.get(KEY_PARAGRAPH_MESSAGE).toString())
                        .setFont(fonts.kai)
                        .setFontSize(MSG_SIZE)
                        .setFixedLeading(32)
                        .setFirstLineIndent(MSG_SIZE * 2));
        addImage(document, params, KEY_CHART);
        document
                .add(new Paragraph("过程预报")
                        .setFirstLineIndent(18 * 2)
                        .setFont(fonts.hei)
                        .setFontSize(18));

    }
}
