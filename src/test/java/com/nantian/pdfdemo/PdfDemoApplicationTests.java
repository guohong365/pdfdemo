package com.nantian.pdfdemo;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.nantian.pdf.parse.ElementLocationListener;
import com.nantian.pdf.parse.IElementLocationListener;
import com.nantian.pdf.parse.IPdfElementLocation;
import com.nantian.pdf.utils.CellSlashRenderer;
import com.nantian.pdf.utils.ChineseSymbolFactory;
import com.nantian.pdf.utils.Units;
import com.nantian.pdf.utils.locators.LocationInfo;
import com.nantian.pdf.utils.locators.ParagraphLocator;
import com.nantian.pdf.utils.locators.TableLocator;
import com.nantian.pdf.weather.paper.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.*;

import static com.itextpdf.layout.property.BackgroundRepeat.BackgroundRepeatValue;

@SpringBootTest
class PdfDemoApplicationTests {
    static final String FONT_PATH = "src/main/resources/fonts/";
    static final String FONT_XBS = FONT_PATH + "FZXBSJW.TTF";
    static final String FONT_KAI = FONT_PATH + "simkai.ttf";
    static final String FONT_HEI = FONT_PATH + "simhei.ttf";
    static final String FONT_FANG = FONT_PATH + "simfang.ttf";
    static final String FONT_SONG = FONT_PATH + "simsun.ttc";
    static float TOP_MARGIN = Units.cm2pt(3.7f);
    static float BOTTOM_MARGIN = Units.cm2pt(3.5f);
    static float LEFT_MARGIN = Units.cm2pt(2.8f);
    static float RIGHT_MARGIN = Units.cm2pt(2.6f);
    static float HEIGHT = Units.cm2pt(22.5f);
    static float WIDTH = Units.cm2pt(15.6f);
    static float TITLE_SIZE = 47f;
    static float BODY_SIZE = 16f;
    static final String RESULT_DIR= "target/results/";
    static final String EXAMPLE_IMG="file:///F:/PdfDemo/map600x800.png";

    @Autowired
    private IWeeklyReport weeklyReport;
    @Autowired
    private ISpecialWeatherService specialWeatherService;

    @Autowired
    private IImportantForecastService importantForecastService;

    @BeforeAll
    static void beforeAll(){
        new File(RESULT_DIR).mkdirs();
    }

    public static FontCollection createFonts() throws IOException {
        FontCollection collection = new FontCollection();
        collection.kai = PdfFontFactory.createFont(FONT_KAI, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        collection.hei = PdfFontFactory.createFont(FONT_HEI, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        collection.fang = PdfFontFactory.createFont(FONT_FANG, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        collection.xbs = PdfFontFactory.createFont(FONT_XBS, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        collection.song = PdfFontFactory.createFont(FONT_XBS, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        return collection;
    }


    //@Test
    void PdfParser() {

        IElementLocationListener listener = new ElementLocationListener();
        try {
            PdfReader reader = new PdfReader(new File(RESULT_DIR +"1.pdf"));
            PdfDocument pdfDocument = new PdfDocument(reader);
            PdfDocumentContentParser parser = new PdfDocumentContentParser(pdfDocument);
            parser.processContent(pdfDocument.getNumberOfPages(), listener);
            reader.close();
            List<IPdfElementLocation> locationList = listener.getLocations();
            for (IPdfElementLocation location : locationList) {
                System.out.println(location.toString());
            }
            System.out.println("======================================");
            System.out.println(locationList.get(locationList.size() - 1).getData());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    Div createDiv(FontCollection fonts) {
        Div div = new Div()
                .setFont(fonts.fang)
                .setFontSize(14);
        div.add(new LineSeparator(new SolidLine(1)));
        div.add(new Paragraph()
                .setFirstLineIndent(28)
                .add(new Text("报送：").setBold())
                .add(new Text("州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。")));
        div.add(new LineSeparator(new SolidLine(1)).setLineThrough());
        div.add(new Paragraph()
                .setMarginLeft(28)
                .setMarginRight(28)
                .setTextAlignment(TextAlignment.JUSTIFIED_ALL)
                .add(new Text("制作：admin"))
                .add(new Tab())
                .add(new Text("校对：admin"))
                .add(new Tab())
                .add(new Text("联系电话：0887-8223034")));
        div.setFixedPosition(LEFT_MARGIN, BOTTOM_MARGIN, WIDTH);
        return div;
    }

    Map<String, Object> weeklyReportPreparing() {
        Map<String, Object> params = new HashMap<>();
        params.put(IWeeklyReport.KEY_YEAR, "2021");
        params.put(IWeeklyReport.KEY_STAGE, "44");
        params.put(IWeeklyReport.KEY_ISSUE, "张三");
        params.put(IWeeklyReport.KEY_DATE_TIME, "2021年10月22日");
        params.put(IWeeklyReport.KEY_WEEK_DATE, "2021年10月22日～28日天气周报");
        params.put(IWeeklyReport.KEY_CAPTION, "过去一周：10月15日、10月16日西南部、西北部、东南部、中部、东部、南部、西部、北部出现小雨，其它地区晴；10月17日西北部、西部出现中雨，其它地区出现小雨；10月18日西北部、东南部、东部、南部、西部出现小雨，其它地区晴；10月19日西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月20日西南部、西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月21日西北部、东南部出现中雨，其它地区出现小雨。上周全州50～99.9毫米的有13站（占总站数9.6%，最大降雨量为德钦县巴东68.8毫米），25～49.9毫米的有38站（占总站数27.9%），10～24.9毫米的有34站（占总站数25.0%），0.1～9.9毫米的有19站（占总站数14.0%）。上周全州最高温31.1℃，出现在香格里拉市东旺站；最低温2℃，出现在德钦县雪山丫口站。上周全州极大风速21.5米/秒（9级烈风），出现在德钦县佛山站。");
        String[] data = {
                "50～99.9毫米|25～49.9毫米|10～24.9毫米|0.1～9.9毫米",
                "5|30|44|26"
        };
        List<String> tableData = new ArrayList<>(Arrays.asList(data));
        params.put(IWeeklyReport.KEY_FORM, tableData);
        params.put(IWeeklyReport.KEY_TABLE_NAME, "表1-降水量区间统计");
        params.put(IWeeklyReport.KEY_BAR_CHART, EXAMPLE_IMG);
        params.put(IWeeklyReport.KEY_CHART, EXAMPLE_IMG);
        params.put(IWeeklyReport.KEY_PRE_MAP, EXAMPLE_IMG);
        params.put(IWeeklyReport.KEY_WEATHER_WEEK, "根据最新气象资料分析，预计10月22日全州有中雨；其他地区有小雨。10月23日全州有小雨。10月24日全州大面积晴转多云。10月25日全州大面积晴转多云。10月26日全州有小雨。10月27日全州有小雨。10月28日全州有小雨转晴。");
        params.put(IWeeklyReport.KEY_WEATHER_FORECAST, "10月18日：香格里拉市、维西县、德钦县阵雨。\n" +
                "10月19～20日:香格里拉市、维西县、德钦县多云。\n" +
                "10月21日：香格里拉市、德钦县小雨；维西县中到大雨。\n" +
                "10月22日：香格里拉市、德钦县中到大雨；维西县大雨。\n" +
                "10月23～24日:香格里拉市、维西县、德钦县多云。");
        params.put(IWeeklyReport.KEY_FOLLOW, "（一）预计未来一周，强对流天气多发，需注意防范大风、雷电等强对流天气对各行业造成的不利影响。\n" +
                "（二）进入雨季，单点性强降水天气频繁，地质灾害气象风险等级较高，请加强地质灾害易发区、库塘堤坝、在建工程、中小学校、交通干道、景区景点、城镇防涝的安全巡察，继续做好山洪、泥石流和山体崩塌等灾害的防范工作。\n" +
                "（三）降雨天气过程增加了土壤墒情，对夏种较为有利，各地宜结合当地农事时节抓紧开展农业生产，但连续的降雨天气，需注意防范对夏收农作物晾晒工作的不利影响。\n" +
                "（四）请及时关注迪庆州气象台发布的各类气象灾害预报。预警信息。");
        params.put(IWeeklyReport.KEY_SUBMITTED, "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。");
        params.put(IWeeklyReport.KEY_MAKE, "李四");
        params.put(IWeeklyReport.KEY_CHECK_NAME, "王二麻子");
        params.put(IWeeklyReport.KEY_PHONE, "0887-8223034");
        return params;
    }

    @Test
    void weeklyReportTest() throws Exception {
        Map<String, Object> params = weeklyReportPreparing();
        weeklyReport.generate(params, RESULT_DIR + "3.pdf");

    }

    Map<String, Object> importantForecastPreparing() {
        Map<String, Object> params = new HashMap<>();
        params.put(IImportantForecastService.KEY_YEAR, "2020");
        params.put(IImportantForecastService.KEY_STAGE, "11");
        params.put(IImportantForecastService.KEY_ISSUE, "江  初");
        params.put(IImportantForecastService.KEY_DATE_TIME, "2020年7月15日");
        params.put(IImportantForecastService.KEY_PARAGRAPH_TITLE_1, "2020年7月16～20日我州将出现");
        params.put(IImportantForecastService.KEY_PARAGRAPH_TITLE_2, "明显降雨天气过程   谨防地质灾害");
        params.put(IImportantForecastService.KEY_PARAGRAPH_MESSAGE, "预计2020年7月16～20日我州自东向西将出现一次明显降雨天气过程：最强降雨时段7月16日午后至7月19日夜间，香格里拉市、德钦县、维西县中雨，局部大雨；其他时段小雨，局部中到大雨。累计雨量：维西县，香格里拉市中南部和德钦县南部地区30～60mm,其他地区10～30mm，最大小时雨强10～25mm。");
        params.put(IImportantForecastService.KEY_CHART, EXAMPLE_IMG);
        params.put(IImportantForecastService.KEY_SPECIFIC_FORECAST, "过程预报：\n" +
                "7月16日全州小雨；17日香格里拉市中东部、维西县澜沧江沿线中到大雨，其他地区小到中雨；7月18～19日香格里拉市中南部、维西县澜沧江沿线、德钦县南部中到大雨，其他地区中雨；7月20日全州转为小雨。\n" +
                "具体城镇预报：\n" +
                "7月16日：香格里拉市、维西县、德钦县小雨。\n" +
                "7月17日：香格里拉市、维西县中雨，德钦县小雨。\n" +
                "7月18～19日：香格里拉市、维西县、德钦县中雨。\n" +
                "7月20日：香格里拉市、维西县、德钦县小雨。");
        params.put(IImportantForecastService.KEY_FOLLOW, "（一）预计2020年7月16～20日我州将出现一次明显降雨天气过程。持续降雨，路面湿滑，能见度低，地质灾害气象风险升高，部分路段易出现道路塌方，请提前防范降雨天气对各行业造成的不利影响；加强地质灾害易发区、库塘堤坝、在建工程、中小学校、交通干道、景区景点、城镇防涝的安全巡察，做好山洪、泥石流和山体崩塌等灾害的防范工作。\n" +
                "（二）夏季多雷电、大风、冰雹等强对流灾害性天气，需加强高层建筑和户外广告等的防雷、防风设施工作。\n" +
                "（三）请及时关注迪庆州气象台发布的各类气象灾害预报预警信息。");
        params.put(IImportantForecastService.KEY_REPORTING, "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。");
        params.put(IImportantForecastService.KEY_REPORT, "省局应急减灾处、省局科技预报处、省台决策科，州局领导，州局各、直属单位、内设机构，各县（市）气象局。 ");
        params.put(IImportantForecastService.KEY_FORECASTER, "李永千");
        params.put(IImportantForecastService.KEY_MAKER, "段志方");
        params.put(IImportantForecastService.KEY_CHECKER, "和丽云");
        params.put(IImportantForecastService.KEY_CONTACT_NUMBER, "0887-8223034");
        return params;
    }

    @Test
    void importantForecastServiceTest() throws Exception {
        Map<String, Object> params = importantForecastPreparing();
        importantForecastService.generate(params, RESULT_DIR +"5.pdf");
    }

    Map<String, Object> specialWeatherServicePreparing() {
        Map<String, Object> params = new HashMap<>();
        params.put(ISpecialWeatherService.KEY_SPECIAL, "2020年春节专题气象服务");
        params.put(ISpecialWeatherService.KEY_STAGE, "01");
        params.put(ISpecialWeatherService.KEY_ISSUE, "多吉次仁");
        params.put(ISpecialWeatherService.KEY_DATE_TIME, "2020年01月22日");
        params.put(ISpecialWeatherService.KEY_TITLE, "2020年春节假日专题气象服务");
        params.put(ISpecialWeatherService.KEY_REMARK, "预计春节期间，受高原槽影响，24～25日海拔2500米以上地区阴有小到中雪，其他地区阴有小雨；26～27日全州多云；28～30日全州出现阵雨或阵雪天气。受雨雪天气影响，相关部门需防范降雨（雪）造成的道路积雪、道路结冰现象对交通出行的不利影响。");
        params.put(ISpecialWeatherService.KEY_CAPTION, "受高原槽影响，2020年1月19日开始，我州出现大范围雨雪天气过程，德钦县、香格里拉市大部出现小到中雪，白马雪山出现暴雪，澜沧江沿线出现中雨。全州大部地区的最低气温在0℃以下，州境内高海拔及背阴路段出现积雪和道路结冰。");
        params.put(ISpecialWeatherService.KEY_FORM, "form");
        params.put(ISpecialWeatherService.KEY_CHART, EXAMPLE_IMG);
        params.put(ISpecialWeatherService.KEY_WEATHER_FORECAST, "预计春节期间24～25日海拔2500米以上地区阴有小到中雪，其他地区阴有小雨；26～27日全州多云；28～30日全州出现阵雨或阵雪天气。");
        params.put(ISpecialWeatherService.KEY_PRE_MAP, EXAMPLE_IMG);
        params.put(ISpecialWeatherService.KEY_CITY_FORECAST, "cityForecast");
        params.put(ISpecialWeatherService.KEY_FOLLOW, "（一）预计春节假日期间，我州有一次降雨（雪）天气过程，迪庆州境内高海拔及背阴路段有积雪和道路结冰，白马雪山路段与小中甸-虎跳峡路段易出现低能见度现象，有关部门需加强道路安全巡查，做好警示、限行等工作，确保春运出行道路交通安全。\n" +
                "（二）正值干季风干物燥，加之午后多大风天气，城乡及森林火险气象风险等级仍较高，城乡燃放烟花炮竹等节日活动和上坟将增加城乡火灾的发生几率，各地要切实做好森林防火和城乡防火工作。\n" +
                "（三）早晚气温低，请注意及时添加衣服，预防感冒；高原紫外线较强，请注意防护。\n" +
                "（四）请及时关注迪庆州气象台发布的各类气象灾害预报预警信息。");
        params.put(ISpecialWeatherService.KEY_SUBMITTED,
                "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、" +
                "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                "防汛抗旱办、护林防火办等相关单位。" +
                "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                        "州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。州长、主管气象副州长、州委办公室、州政府办公室，" +
                        "州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、州水务局、州林草局、" +
                        "防汛抗旱办、护林防火办等相关单位。" +
                        "州长、主管气象副州长、州委办公室、州政府办公室，州人大、州政协、州应急局、州农业农村局、州自然资源和规划局、州交通运输局、州公安局交警支队、州消防支队、州民政局、州文化和旅游局、州自然资源公安局、州生态环境局、州住房城乡建设局、" +
                "州水务局、州林草局、防汛抗旱办、护林防火办等相关单位。");
        params.put(ISpecialWeatherService.KEY_MAKE, "孙娅蕾");
        params.put(ISpecialWeatherService.KEY_CHECK_NAME, "王仔刚");
        params.put(ISpecialWeatherService.KEY_PHONE, "0887-8223034");
        String[] dataForecast = {
                "24～25日（除夕至初一）|小雪|-7～2|小雪|-6～1|小雨|0～10",
                "26～27日（初二至初三）|多云|-11～5|多云|-9～4|多云|-2～13",
                "28～30日（初四至初六）|阵雪|-8～3|阵雪|-7～2|阵雨|-1～12"
        };
        List<String> list = new ArrayList<>(Arrays.asList(dataForecast));
        params.put(ISpecialWeatherService.KEY_CITY_FORECAST, list);
        String[] dataForm = {
                "50～99.9毫米|25～49.9毫米|10～24.9毫米|0.1～9.9毫米",
                "5|30|44|26"
        };
        List<String> tableData = new ArrayList<>(Arrays.asList(dataForm));
        params.put(IWeeklyReport.KEY_FORM, tableData);
        return params;
    }

    @Test
    void specialWeatherService() throws Exception {
        Map<String, Object> params = specialWeatherServicePreparing();
        specialWeatherService.generate(params, RESULT_DIR + "4.pdf");
    }

    //@Test
    void contextLoads() throws IOException {
        try {
            FontCollection fonts = createFonts();
            OutputStream first_output = new FileOutputStream(RESULT_DIR+"1-1.pdf"); // new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(first_output);
            PdfDocument pdfDocument1 = new PdfDocument(writer);
            Document document = new Document(pdfDocument1, PageSize.A4);
            document.setTextAlignment(TextAlignment.JUSTIFIED);
            document.setTopMargin(TOP_MARGIN);
            document.setBottomMargin(BOTTOM_MARGIN);
            document.setLeftMargin(LEFT_MARGIN);
            document.setRightMargin(RIGHT_MARGIN);
            document.setFontSize(BODY_SIZE);
            document.setFont(fonts.fang);

            String[] strings = {"迪庆州气象台", "签发：admin", "2021年10月22日"};
            document.add(new Paragraph("迪 庆 州 天 气 周 报")
                            .setFont(fonts.xbs)
                            .setFontSize(TITLE_SIZE)
                            .setTextAlignment(TextAlignment.CENTER)
                            .setFontColor(ColorConstants.RED))
                    .add(new Paragraph("2021年  第44期")
                            .setFont(fonts.kai)
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
                            .setFont(fonts.xbs)
                            .setTextAlignment(TextAlignment.CENTER))
                    .add(new Paragraph("一、上周天气概况")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(fonts.hei).setKeepWithNext(true))
                    .add(new Paragraph("过去一周：10月15日、10月16日西南部、西北部、东南部、中部、东部、南部、西部、北部出现小雨，其它地区晴；10月17日西北部、西部出现中雨，其它地区出现小雨；10月18日西北部、东南部、东部、南部、西部出现小雨，其它地区晴；10月19日西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月20日西南部、西北部、东南部、中部、东部、南部、西部出现小雨，其它地区晴；10月21日西北部、东南部出现中雨，其它地区出现小雨。上周全州50～99.9毫米的有13站（占总站数9.6%，最大降雨量为德钦县巴东68.8毫米），25～49.9毫米的有38站（占总站数27.9%），10～24.9毫米的有34站（占总站数25.0%），0.1～9.9毫米的有19站（占总站数14.0%）。上周全州最高温31.1℃，出现在香格里拉市东旺站；最低温2℃，出现在德钦县雪山丫口站。上周全州极大风速21.5米/秒（9级烈风），出现在德钦县佛山站。")
                            .setFirstLineIndent(BODY_SIZE * 2))
                    .add(new Image(ImageDataFactory.create("map600x800.png"))
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .add(new Paragraph("二、本周天气展望")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(fonts.hei).setKeepWithNext(true))
                    .add(new Paragraph("根据最新气象资料分析，预计10月22日全州有中雨；其他地区有小雨。10月23日全州有小雨。10月24日全州大面积晴转多云。10月25日全州大面积晴转多云。10月26日全州有小雨。10月27日全州有小雨。10月28日全州有小雨转晴。")
                            .setFirstLineIndent(BODY_SIZE * 2))
                    .add(new Paragraph("具体城镇天气预报")
                            .setFirstLineIndent(BODY_SIZE * 2)
                            .setFont(fonts.hei).setKeepWithNext(true));
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
                paragraph.setFont(fonts.fang);
                document.add(paragraph);
            }
            document.add(new Paragraph("三、关注与建议")
                    .setFirstLineIndent(BODY_SIZE * 2)
                    .setFont(fonts.hei).setKeepWithNext(true));
            sections = new String[]{
                    "（一）预计未来一周，强对流天气多发，需注意防范大风、雷电等强对流天气对各行业造成的不利影响。 ",
                    "（二）进入雨季，单点性强降水天气频繁，地质灾害气象风险等级较高，请加强地质灾害易发区、库塘堤坝、在建工程、中小学校、交通干道、景区景点、城镇防涝的安全巡察，继续做好山洪、泥石流和山体崩塌等灾害的防范工作。",
                    "（三）降雨天气过程增加了土壤墒情，对夏种较为有利，各地宜结合当地农事时节抓紧开展农业生产，但连续的降雨天气，需注意防范对夏收农作物晾晒工作的不利影响。",
                    "（四）请及时关注迪庆州气象台发布的各类气象灾害预报。预警信息。"
            };
            for (String text : sections) {
                Paragraph paragraph = new Paragraph(text)
                        .setFirstLineIndent(BODY_SIZE * 2)
                        .setFont(fonts.fang);
                document.add(paragraph);
            }
            document.close();

            //byte[] onePart = first_output.toByteArray();
            InputStream in = new FileInputStream(RESULT_DIR + "1-1.pdf");
            PdfDocument pdfDocument2 = new PdfDocument(new PdfReader(in));
            PdfDocumentContentParser parser = new PdfDocumentContentParser(pdfDocument2);
            IElementLocationListener endPositionStrategy = new ElementLocationListener();
            parser.processContent(pdfDocument2.getNumberOfPages(), endPositionStrategy);
            List<IPdfElementLocation> lastLocations = endPositionStrategy.getLocations();
            IPdfElementLocation lastLocation = lastLocations.get(0);
            System.out.println(lastLocation);
            pdfDocument2.close();

            OutputStream second_output = new FileOutputStream(RESULT_DIR + "1-2.pdf"); //new ByteArrayOutputStream();
            PdfDocument pdfDocument3 = new PdfDocument(new PdfWriter(second_output));
            Document document2 = new Document(pdfDocument3, PageSize.A4);
            document2.add(createDiv(createFonts()));
            document2.close();
            //byte[] twoPart = second_output.toByteArray();
            in = new FileInputStream("1-2.pdf");
            PdfDocument pdfDocument4 = new PdfDocument(new PdfReader(in));
            IElementLocationListener secondStrategy = new ElementLocationListener();
            parser = new PdfDocumentContentParser(pdfDocument4);
            parser.processContent(pdfDocument4.getNumberOfPages(), secondStrategy);
            pdfDocument4.close();
            lastLocations = secondStrategy.getLocations();
            IPdfElementLocation ccTop = lastLocations.get(0);
            IPdfElementLocation ccBottom = lastLocations.get(lastLocations.size() - 1);
            System.out.println("-------------------------------");
            System.out.println(ccTop);
            System.out.println(ccBottom);
            System.out.println("-------------------------------");
            boolean newPage = false;
            Rectangle bounds = Rectangle.getCommonRectangle(ccTop.getRectangle(), ccBottom.getRectangle());
            System.out.println("top:" + bounds.getTop() + " left:" + bounds.getLeft() + " right:" + bounds.getRight() + " bottom:" + bounds.getBottom());
            System.out.println("width :" + bounds.getWidth());
            System.out.println("height: " + bounds.getHeight());
            System.out.println("BOTTOM MARGIN:" + BOTTOM_MARGIN);
            if (lastLocation.getRectangle().getBottom() < bounds.getTop()) {
                newPage = true;
            }
            in = new FileInputStream(RESULT_DIR +"1-1.pdf");
            OutputStream out = new FileOutputStream(RESULT_DIR+"2.pdf");
            PdfDocument pdfDocument5 = new PdfDocument(new PdfReader(in), new PdfWriter(out));

            if (newPage) {
                pdfDocument5.addNewPage();
            }
            PdfPage page = pdfDocument5.getLastPage();

            PdfCanvas pdfCanvas = new PdfCanvas(page);
            Canvas canvas = new Canvas(pdfCanvas, new Rectangle(LEFT_MARGIN, BOTTOM_MARGIN, WIDTH, HEIGHT));
            canvas.add(createDiv(createFonts()));
            canvas.close();
            pdfDocument5.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    static class MyCellRenderer extends CellRenderer{

        public MyCellRenderer(Cell modelElement) {
            super(modelElement);
        }

        @Override
        public void drawBackground(DrawContext drawContext) {
            super.drawBackground(drawContext);
            Rectangle innerArea = getInnerAreaBBox();
            Rectangle border = getBorderAreaBBox();
            Rectangle occupiedAreaBBox = getOccupiedAreaBBox();
            drawContext.getCanvas()
                    .setColor(ColorConstants.RED, false)
                    .moveTo(innerArea.getLeft(), innerArea.getBottom())
                    .lineTo(innerArea.getRight(), innerArea.getTop())
                    .closePathStroke()
                    .moveTo(border.getLeft(),border.getBottom() + border.getHeight()/2)
                    .lineTo(border.getRight(), border.getBottom() + border.getHeight()/2)
                    .closePathStroke()
                    .moveTo(occupiedAreaBBox.getLeft(), occupiedAreaBBox.getTop())
                    .lineTo(occupiedAreaBBox.getRight(), occupiedAreaBBox.getBottom())
                    .closePathStroke();
        }
    }

    @Test
    void slashTest() throws IOException {
        FontCollection fonts=createFonts();
        String DEST = RESULT_DIR + "slash.pdf";

        PdfDocument pdfDocument= new PdfDocument(new PdfWriter(new FileOutputStream(DEST)));

        Document document=new Document(pdfDocument, PageSize.A4);

        PdfFormXObject formXObject = new PdfFormXObject(new Rectangle(130, 30));
        PdfCanvas pdfCanvas=new PdfCanvas(formXObject, pdfDocument);

        pdfCanvas
                .setColor(ColorConstants.RED, false)
                .setLineWidth(1)
                .moveTo(0,formXObject.getHeight())
                .lineTo(formXObject.getWidth(), 0)
                .closePathStroke();

        BackgroundSize backgroundSize=new BackgroundSize();
        backgroundSize.setBackgroundSizeToCover();
        BackgroundImage backgroundImage= new BackgroundImage.Builder()
                .setImage(formXObject)
                .setBackgroundRepeat(new BackgroundRepeat(BackgroundRepeatValue.NO_REPEAT))
                .setBackgroundSize(backgroundSize)
                .build();
        List<LocationInfo> rectangles=new ArrayList<>();
        Paragraph p=new Paragraph("迪庆州气象局")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFont(fonts.xbs)
                .setFontSize(48);
        p.setNextRenderer(new ParagraphLocator(p, rectangles, "first"));
        document.add(p);
        document.add(new Paragraph("dsafs dsaf;s dsaf;ls dsafl;sdjf sdf"));

        Table table=new Table(new UnitValue[]{UnitValue.createPercentValue(20), UnitValue.createPercentValue(80)})
                        .setFont(fonts.fang)
                        .setAutoLayout();

        String[] lines= {
                "（一）预计春节假日期间，我州有一次降雨（雪）天气过程，迪庆州境内高海拔及背阴路段有积雪和道路结冰，白马雪山路段与小中甸-虎跳峡路段易出现低能见度现象，有关部门需加强道路安全巡查，做好警示、限行等工作，确保春运出行道路交通安全。",
                "（二）正值干季风干物燥，加之午后多大风天气，城乡及森林火险气象风险等级仍较高，城乡燃放烟花炮竹等节日活动和上坟将增加城乡火灾的发生几率，各地要切实做好森林防火和城乡防火工作。",
                "（三）早晚气温低，请注意及时添加衣服，预防感冒；高原紫外线较强，请注意防护。",
                "（四）请及时关注迪庆州气象台发布的各类气象灾害预报预警信息。"
        };
        boolean first=true;

        for(String line:lines) {
            Cell cell = new Cell();
            if(first){
                cell.setPadding(0);
                cell.setNextRenderer(new CellSlashRenderer(cell));
                first=false;
            }
            p = new Paragraph(line);
            p.setNextRenderer(new ParagraphLocator(p, rectangles, "line"));
            table.addCell(cell)
                    .addCell(new Cell().add(p));
        }
        TableLocator tableLocator=new TableLocator(table, rectangles, "table");
        table.setNextRenderer(tableLocator);
        document.add(table);
        document.close();
        rectangles.sort((o1, o2) -> {
            return Float.compare(o1.getBounds().getBottom(), o2.getBounds().getBottom());
        });
        System.out.println(rectangles.size());
        for(LocationInfo rectangle:rectangles){
            System.out.println(MessageFormat.format("{4}: ({0},{1})({2},{3})",
                    rectangle.getBounds().getLeft(), rectangle.getBounds().getBottom(),
                    rectangle.getBounds().getRight(), rectangle.getBounds().getTop(), rectangle.getName()));
        }
    }

    @Test
    void listTest() throws IOException {
        FontCollection fonts = createFonts();
        String DEST= RESULT_DIR + "list_test.pdf";
        PdfDocument pdfDocument=new PdfDocument(new PdfWriter(DEST));
        Style style = new Style().setFont(fonts.hei).setBold();
        IListSymbolFactory symbolFactory=new ChineseSymbolFactory(style,"、");
        IListSymbolFactory subFactory=new ChineseSymbolFactory(new Style().setBold().setFont(fonts.kai).setFontColor(ColorConstants.BLUE), "(", ")");
        Document document=new Document(pdfDocument);
        document.setFont(fonts.fang);
        com.itextpdf.layout.element.List list=new com.itextpdf.layout.element.List();
        list.setProperty(Property.LIST_SYMBOL, symbolFactory);
        for(int i=0; i<50; i++){
            ListItem item=new ListItem("item:" + i);
            item.add(new Paragraph("iText中文列表段落"));
            com.itextpdf.layout.element.List subList=new com.itextpdf.layout.element.List();
            subList.setProperty(Property.LIST_SYMBOL, subFactory);
            for(int j=0; j<5; j++){
                ListItem subItem = (ListItem) new ListItem("中文列表：" +(i+1) +"." +(j+1)).setFont(fonts.kai).setFontColor(ColorConstants.BLUE);
                subItem.add(new Paragraph("iText中文列表段落").setFont(fonts.hei).setFontColor(ColorConstants.GREEN));
                subList.add(subItem);
            };
            item.add(subList);
            list.add(item);
        }
        document.add(list);
        document.close();
    }

}
