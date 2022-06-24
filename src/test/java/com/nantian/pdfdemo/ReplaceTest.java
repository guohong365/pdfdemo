package com.nantian.pdfdemo;

import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.PdfDocumentContentParser;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IPdfTextLocation;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.RegexBasedLocationExtractionStrategy;
import com.itextpdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.pdfcleanup.PdfCleaner;
import com.nantian.pdf.content.IPdfReplacer;
import com.nantian.pdf.insurance.ReplacerFactory;
import com.nantian.pdf.parse.ElementLocationListener;
import com.nantian.pdf.parse.IPdfElementLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;

@SpringBootTest
public class ReplaceTest {
    private static final String SOURCE_DIR = "src/main/resources/replace/";
    private static final String TARGET_DIR = "target/replaced/";
    private static final String SUN_SHINE_SOURCE = "阳光.pdf";
    private static final String SUN_SHINE_TARGET = "阳光_OK.pdf";
    private static final String CONTINENT_SOURCE = "大地.pdf";
    private static final String CONTINENT_TARGET = "大地_OK.pdf";
    private static final String CHANG_AN_SOURCE = "长安.pdf";
    private static final String CHANG_AN_TARGET = "长安_OK.pdf";
    private static final String nnn = "505254CE547347D5BDFF0D3A2B4521AF.pdf";

    @BeforeAll
    public static void init() {
        new File(TARGET_DIR).mkdirs();
    }

    /*读取文件的字节数组*/
    public static byte[] toByteArray(File file) throws IOException {
        if (Objects.isNull(file)) {
            return null;
        }
        if (!file.exists()) {
            throw new FileNotFoundException("file not exists");
        }
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length())) {
            BufferedInputStream in;
            in = new BufferedInputStream(new FileInputStream(file));
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testElementLocation() throws IOException {
        PdfReader reader = new PdfReader(SOURCE_DIR + CONTINENT_SOURCE);
        reader.setUnethicalReading(true);
        PdfDocument document = new PdfDocument(reader);
        ElementLocationListener listener = new ElementLocationListener();
        PdfDocumentContentParser parser = new PdfDocumentContentParser(document);
        parser.processContent(1, listener);
        List<IPdfElementLocation> locations = listener.getLocations();
        for (IPdfElementLocation location : locations) {
            print(location);
        }
    }

    private void print(IPdfElementLocation location) {
        if (location.getData() instanceof String) {
            Rectangle rectangle = location.getRectangle();
            String text = (String) location.getData();
            System.out.format("(%f, %f, %f, %f) %s\n",
                    rectangle.getX(), rectangle.getY(),
                    rectangle.getWidth(), rectangle.getHeight(),
                    text);
        }
    }

    @Test
    public void testExtractContinent() throws IOException {

        PdfReader reader = new PdfReader(SOURCE_DIR + CONTINENT_SOURCE);
        reader.setUnethicalReading(true);
        PdfDocument document = new PdfDocument(reader, new PdfWriter(TARGET_DIR + CONTINENT_TARGET));
        PdfDocumentContentParser parser = new PdfDocumentContentParser(document);
        FilteredEventListener filteredEventListener = new FilteredEventListener();
        RegexBasedLocationExtractionStrategy customerName = new RegexBasedLocationExtractionStrategy("233fd0c26e407bb42ad05937bcf12b92");
        RegexBasedLocationExtractionStrategy idNumber = new RegexBasedLocationExtractionStrategy("91110105MABM03KH1F");
        RegexBasedLocationExtractionStrategy projectName = new RegexBasedLocationExtractionStrategy("654ece482c3ca4aeb9ea35726080a83b");
        RegexBasedLocationExtractionStrategy tenderFileName = new RegexBasedLocationExtractionStrategy("ZB5241635215451");
        RegexBasedLocationExtractionStrategy name = new RegexBasedLocationExtractionStrategy("办公地址");
        RegexBasedLocationExtractionStrategy org = new RegexBasedLocationExtractionStrategy("组织机构代码");

        filteredEventListener.attachEventListener(customerName);
        filteredEventListener.attachEventListener(idNumber);
        filteredEventListener.attachEventListener(projectName);
        filteredEventListener.attachEventListener(tenderFileName);
        filteredEventListener.attachEventListener(name);
        filteredEventListener.attachEventListener(org);
        parser.processContent(1, filteredEventListener);

        List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<>();
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(183, 615.71f, 184, 10.03f), ColorConstants.RED));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(183, 578.37f, 184, 10.03f), ColorConstants.BLUE));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(443, 615.71f, 108, 10.03f), ColorConstants.GREEN));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(443, 578.37f, 108, 10.03f), ColorConstants.YELLOW));

        PdfCleaner.cleanUp(document, new ArrayList<>(cleanUpLocations));
        print(customerName.getResultantLocations());
        print(idNumber.getResultantLocations());
        print(projectName.getResultantLocations());
        print(tenderFileName.getResultantLocations());
        print(name.getResultantLocations());
        print(org.getResultantLocations());
        document.close();
    }

    @Test
    public void testExtractChangAn() throws IOException {
        PdfDocument document = new PdfDocument(new PdfReader(SOURCE_DIR + File.separator + CHANG_AN_SOURCE),
                new PdfWriter(TARGET_DIR + File.separator + CHANG_AN_TARGET));
        PdfDocumentContentParser parser = new PdfDocumentContentParser(document);
        FilteredEventListener filteredEventListener = new FilteredEventListener();
        RegexBasedLocationExtractionStrategy customerName = new RegexBasedLocationExtractionStrategy("南天信息科技有限公司");
        RegexBasedLocationExtractionStrategy idNumber = new RegexBasedLocationExtractionStrategy("91530000713401509F");
        RegexBasedLocationExtractionStrategy projectName = new RegexBasedLocationExtractionStrategy("长安投标");
        RegexBasedLocationExtractionStrategy tenderFileName = new RegexBasedLocationExtractionStrategy("ZB5241635215451");
        RegexBasedLocationExtractionStrategy name = new RegexBasedLocationExtractionStrategy("办公地址");
        RegexBasedLocationExtractionStrategy org = new RegexBasedLocationExtractionStrategy("组织机构代码");

        filteredEventListener.attachEventListener(customerName);
        filteredEventListener.attachEventListener(idNumber);
        filteredEventListener.attachEventListener(projectName);
        filteredEventListener.attachEventListener(tenderFileName);
        filteredEventListener.attachEventListener(name);
        filteredEventListener.attachEventListener(org);
        parser.processContent(1, filteredEventListener);

        List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<>();
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(183, 615.71f, 184, 10.03f), ColorConstants.RED));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(183, 578.37f, 184, 10.03f), ColorConstants.BLUE));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(443, 615.71f, 108, 10.03f), ColorConstants.GREEN));
        cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(443, 578.37f, 108, 10.03f), ColorConstants.YELLOW));

        PdfCleaner.cleanUp(document, new ArrayList<>(cleanUpLocations));
        print(customerName.getResultantLocations());
        print(idNumber.getResultantLocations());
        print(projectName.getResultantLocations());
        print(tenderFileName.getResultantLocations());
        print(name.getResultantLocations());
        print(org.getResultantLocations());
        document.close();
    }

    private void print(Collection<IPdfTextLocation> locations) {
        for (IPdfTextLocation location : locations) {
            Rectangle rectangle = location.getRectangle();
            System.out.format("%d (%f,%f,%f,%f) %s", location.getPageNumber(), rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(), location.getText());
            System.out.println();
        }

    }

    @Test
    public void testExtractSunShine() throws IOException {
        PdfDocument document = new PdfDocument(new PdfReader(SOURCE_DIR + SUN_SHINE_SOURCE));
        PdfDocumentContentParser parser = new PdfDocumentContentParser(document);
        //TextRegionEventFilter textRegionEventFilter=new TextRegionEventFilter(new Rectangle(174, 591, 123, 10));
        LocationTextExtractionStrategy textExtractionStrategy = new LocationTextExtractionStrategy();
        FilteredEventListener filteredEventListener = new FilteredEventListener();
        filteredEventListener.attachEventListener(textExtractionStrategy, new TextRegionEventFilter(new Rectangle(175, 591, 122, 10)));
        filteredEventListener.attachEventListener(textExtractionStrategy, new TextRegionEventFilter(new Rectangle(380, 591, 122, 10)));
        filteredEventListener.attachEventListener(textExtractionStrategy, new TextRegionEventFilter(new Rectangle(175, 551, 122, 10)));
        filteredEventListener.attachEventListener(textExtractionStrategy, new TextRegionEventFilter(new Rectangle(380, 551, 122, 10)));
        int page = document.getPageNumber(document.getFirstPage());
        parser.processContent(page, filteredEventListener);
        System.out.println(textExtractionStrategy.getResultantText());
        document.close();
    }

    @Test
    public void cleanUpSunShine() throws IOException {
        Set<String> fonts = FontProgramFactory.getRegisteredFonts();
        System.out.println(fonts);
        PdfWriter writer = new PdfWriter(TARGET_DIR + SUN_SHINE_TARGET);
        PdfDocument document = new PdfDocument(
                new PdfReader(SOURCE_DIR + SUN_SHINE_SOURCE),
                writer);
        PdfFont font = PdfFontFactory.createRegisteredFont("helvetica");
        PdfCleanUpLocation location;
        List<PdfCleanUpLocation> locations = new ArrayList<>();
        location = new PdfCleanUpLocation(1, new Rectangle(175, 593, 121, 9));
        locations.add(location);
        location = new PdfCleanUpLocation(1, new Rectangle(381, 593, 121, 9));
        locations.add(location);
        location = new PdfCleanUpLocation(1, new Rectangle(175, 551, 121, 9));
        locations.add(location);
        location = new PdfCleanUpLocation(1, new Rectangle(381, 551, 121, 9));
        locations.add(location);
        PdfCleaner.cleanUp(document, locations);
        PdfPage page = document.getFirstPage();
        PdfCanvas canvas = new PdfCanvas(page);
        //page.newContentStreamAfter(),
        //page.getResources(), document);
        for (PdfCleanUpLocation pos : locations) {
            canvas.beginText();
            canvas.setFontAndSize(font, 10);
            canvas.setTextMatrix(pos.getRegion().getX(), pos.getRegion().getY());
            canvas.showText("******");
            canvas.endText();
        }
        document.close();
    }

    @Test
    public void testContinent() {
        process(ReplacerFactory.TYPE_CONTINENT,
                CONTINENT_SOURCE,
                CONTINENT_TARGET);
    }

    @Test
    public void testSunShine() {
        process(ReplacerFactory.TYPE_SUN_SHINE, SUN_SHINE_SOURCE, SUN_SHINE_TARGET);
    }

    @Test
    void testChangAn() {
        process(ReplacerFactory.TYPE_CHANG_AN, CHANG_AN_SOURCE, CHANG_AN_TARGET);
    }

    @Test
    public void testSunShineByValues() throws Exception {
        processByValues(SUN_SHINE_SOURCE, SUN_SHINE_TARGET, new String[]{
                "招标代理测试一","913502127DDDDDDDD3","（测试）2019房建-施工-综合A","E3502060201114846001"
        });
    }
    @Test
    void testChangAnByValues() throws Exception {
        processByValues(CHANG_AN_SOURCE, CHANG_AN_TARGET, new String[]{
                "南天信息科技有限公司","91530000713401509F","长安投标", "ZB5241635215451"
        });
    }
    @Test
    void testContinentByValues() throws Exception {
        processByValues(CONTINENT_SOURCE, CONTINENT_TARGET, new String[]{
                "233fd0c26e407bb42ad05937bcf12b92",
                "91110105MABM03KH1F",
                "654ece482c3ca4aeb9ea35726080a83b"
        });
    }
    private void processByValues(String input, String output, String[] values) throws Exception {
        IPdfReplacer replacer=ReplacerFactory.create(values);
        replacer.replace(SOURCE_DIR + input,TARGET_DIR + output);
    }
    private void process(String type, String input, String output) {
        // 第一步配置监听器
        IPdfReplacer replacer = ReplacerFactory.create(type);
        if (replacer == null) {
            System.out.println("unknown replacer for [" + type + "]");
            return;
        }
        try {
            // 第二步执行
            replacer.replace(SOURCE_DIR + input, TARGET_DIR  + output);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
