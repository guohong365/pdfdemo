package com.nantian.pdfdemo;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.nantian.pdf.Units;
import com.nantian.weather.paper.LineSeparatorEx;
import com.nantian.weather.paper.WeeklyPaperGenerator;
import com.nantian.weather.paper.factory.ElementFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class PdfDemoApplicationTests {
    static final String FONT_PATH = "d:/PdfDemo/src/main/resources/fonts/";
    static final String FONT_XBS = FONT_PATH + "FZXBSJW.TTF";
    static final String FONT_KAI = FONT_PATH + "simkai.ttf";
    static final String FONT_HEI = FONT_PATH + "simhei.ttf";
    static final String FONT_FANG = FONT_PATH + "simfang.ttf";
    static float TOP_MARGIN = Units.cm2pt(3.7f);
    static float BOTTOM_MARGIN = Units.cm2pt(3.5f);
    static float LEFT_MARGIN = Units.cm2pt(2.8f);
    static float RIGHT_MARGIN = Units.cm2pt(2.6f);
    static float HEIGHT = Units.cm2pt(22.5f);
    static float WIDTH = Units.cm2pt(15.6f);
    static float LINE_SPACE = 14.3f;
    static float FOOT = Units.cm2pt(3f);
    static float TITLE_SIZE = 47f;
    static float BODY_SIZE = 16f;
    static float MAIN_TITLE_SIZE = 18f;
    static PdfFont kai;
    static PdfFont hei;
    static PdfFont fang;
    static PdfFont xbs;
    //@Autowired
    WeeklyPaperGenerator generator;

    @BeforeAll
    static void prepare() throws IOException {
        kai = PdfFontFactory.createFont(FONT_KAI, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        hei = PdfFontFactory.createFont(FONT_HEI, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        fang = PdfFontFactory.createFont(FONT_FANG, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        xbs = PdfFontFactory.createFont(FONT_XBS, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
    }

    @Test
    void genWeekly() {

    }

    @Test
    void keyParser(){
        List<String> keys= ElementFactory.parseKeys("123year{date1}}456{date}");
    }

    @Test
    void contextLoads() throws IOException {
        try {
            PdfWriter writer = new PdfWriter(new File("1.pdf"));
            PdfDocument PdfDocument = new PdfDocument(writer);
            Document document = new Document(PdfDocument, PageSize.A4);
            document.setTextAlignment(TextAlignment.JUSTIFIED);
            document.setTopMargin(TOP_MARGIN);
            document.setBottomMargin(BOTTOM_MARGIN);
            document.setLeftMargin(LEFT_MARGIN);
            document.setRightMargin(RIGHT_MARGIN);
            document.setFontSize(BODY_SIZE);
            document.setFont(fang);
            PdfDocumentInfo documentInfo = PdfDocument.getDocumentInfo();
            String[] strings = {"迪庆州气象台", "签发：admin", "2021年10月22日"};
            document.add(new Paragraph("迪 庆 州 天 气 周 报")
                            .setFont(xbs)
                            .setFontSize(TITLE_SIZE)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontColor(ColorConstants.RED))
                    .add(new Paragraph("2021年  第44期")
                            .setFont(kai)
                            .setTextAlignment(TextAlignment.CENTER))
                    .add(new Table(new float[]{WIDTH / 3, WIDTH / 3, WIDTH / 3})
                            .setBorder(Border.NO_BORDER)
                            .addCell(new Cell()
                                    .add(new Paragraph(strings[0])
                                            .setTextAlignment(TextAlignment.LEFT))
                                    .setBorder(Border.NO_BORDER))
                            .addCell(new Cell().add(new Paragraph(strings[1])
                                            .setTextAlignment(TextAlignment.CENTER))
                                    .setBorder(Border.NO_BORDER))
                            .addCell(new Cell().add(new Paragraph(strings[2])
                                            .setTextAlignment(TextAlignment.RIGHT))
                                    .setBorder(Border.NO_BORDER)))
                    .add(new LineSeparatorEx(new SolidLine(1), ColorConstants.RED)
                            .setLineThrough().setMarginBottom(1))
                    .add(new LineSeparatorEx(new SolidLine(2.5f), ColorConstants.RED)
                            .setLineThrough().setMarginTop(0))
                    .add(new Paragraph("2021年10月22日～28日天气周报")
                            .setFont(xbs)
                            .setTextAlignment(TextAlignment.CENTER))
                    .add(new Paragraph("一、上周天气概况")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(hei).setKeepWithNext(true))
                    .add(new Paragraph("过去一周：10月15日、10月16日西南部、西北部、东南部、中部、东部、南部、西部、北部出现小雨，其它地区晴；10月17日西北部、西部出现中雨，其它地区出现小雨；10月18日西北部、东南部、东部、南部、西部出现小雨，其它地区晴；10月19日西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月20日西南部、西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月21日西北部、东南部出现中雨，其它地区出现小雨。上周全州50～99.9毫米的有13站（占总站数9.6%，最大降雨量为德钦县巴东68.8毫米），25～49.9毫米的有38站（占总站数27.9%），10～24.9毫米的有34站（占总站数25.0%），0.1～9.9毫米的有19站（占总站数14.0%）。上周全州最高温31.1℃，出现在香格里拉市东旺站；最低温2℃，出现在德钦县雪山丫口站。上周全州极大风速21.5米/秒（9级烈风），出现在德钦县佛山站。")
                            .setFirstLineIndent(BODY_SIZE * 2))
                    .add(new Image(ImageDataFactory.create("map600x800.png"))
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .add(new Paragraph("二、本周天气展望")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(hei).setKeepWithNext(true))
                    .add(new Paragraph("根据最新气象资料分析，预计10月22日全州有中雨；其他地区有小雨。10月23日全州有小雨。10月24日全州大面积晴转多云。10月25日全州大面积晴转多云。10月26日全州有小雨。10月27日全州有小雨。10月28日全州有小雨转晴。")
                            .setFirstLineIndent(BODY_SIZE * 2))
                    .add(new Paragraph("具体城镇天气预报")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(hei).setKeepWithNext(true));
            String[] sections = {
                    "10月18日：香格里拉市、维西县、德钦县阵雨。",
                    "10月19～20日:香格里拉市、维西县、德钦县多云。",
                    "10月21日：香格里拉市、德钦县小雨；维西县中到大雨。",
                    "10月22日：香格里拉市、德钦县中到大雨；维西县大雨。",
                    "10月23～24日:香格里拉市、维西县、德钦县多云。"
            };
            for (String text : sections) {
                Paragraph paragraph = new Paragraph(text);
                paragraph.setFirstLineIndent(BODY_SIZE * 2);
                paragraph.setFont(fang);
                document.add(paragraph);
            }
            document.add(new Paragraph("三、关注与建议")
                    .setFirstLineIndent(BODY_SIZE * 2)
                    .setFont(hei).setKeepWithNext(true));
            sections = new String[]{
                    "（一）预计未来一周，强对流天气多发，需注意防范大风、雷电等强对流天气对各行业造成的不利影响。 ",
                    "（二）进入雨季，单点性强降水天气频繁，地质灾害气象风险等级较高，请加强地质灾害易发区、库塘堤坝、在建工程、中小学校、交通干道、景区景点、城镇防涝的安全巡察，继续做好山洪、泥石流和山体崩塌等灾害的防范工作。",
                    "（三）降雨天气过程增加了土壤墒情，对夏种较为有利，各地宜结合当地农事时节抓紧开展农业生产，但连续的降雨天气，需注意防范对夏收农作物晾晒工作的不利影响。",
                    "（四）请及时关注迪庆州气象台发布的各类气象灾害预报。预警信息。"};
            for (String text : sections) {
                Paragraph paragraph = new Paragraph(text)
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(fang);
                document.add(paragraph);
            }
            int pageNumbers = PdfDocument.getNumberOfPages();
            System.out.println(String.format("total pages:[%d]", pageNumbers));

            Div div = new Div()
                    .setKeepTogether(true)
                    .add(new LineSeparator(new SolidLine(1))
                            .setLineThrough()
                            .setVerticalAlignment(VerticalAlignment.BOTTOM))
                    .add(new Paragraph()
                            .setFirstLineIndent(28)
                            .add(new Text("报送：")
                                    .setBold().setFontSize(14))
                            .add(new Text("州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。")
                                    .setFontSize(14)))
                    .add(new LineSeparator(new SolidLine(1)).setLineThrough()
                            .setVerticalAlignment(VerticalAlignment.BOTTOM))
                    .add(new Paragraph()
                            .setFontSize(14)
                            .setMarginLeft(28)
                            .setMarginRight(28)
                            .setTextAlignment(TextAlignment.JUSTIFIED_ALL)
                            .add(new Text("制作：admin"))
                            .add(new Tab())
                            .add(new Text("校对：admin"))
                            .add(new Tab())
                            .add(new Text("联系电话：0887-8223034")));
            div.setFixedPosition(LEFT_MARGIN, BOTTOM_MARGIN, WIDTH);
            document.add(div);
            //UnitValue h=div.getHeight();
            //UnitValue w = div.getWidth();
            PdfPage lastPage = PdfDocument.getLastPage();
            //int pageNumber =PdfDocument.getPageNumber(lastPage);
            //div.setPageNumber(pageNumber);

            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
