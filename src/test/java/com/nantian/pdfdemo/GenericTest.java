package com.nantian.pdfdemo;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.ObjectFit;
import com.itextpdf.layout.property.Property;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.renderer.IRenderer;
import com.nantian.pdf.utils.ChineseNumberConverter;
import com.nantian.pdf.utils.ChineseSplitterCharacters;
import com.nantian.pdf.utils.PageHeaderHandler;
import com.nantian.pdf.utils.PageNumberHandler;
import com.nantian.pdf.weather.paper.FontCollection;
import com.nantian.pdf.weather.paper.IPaperGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class GenericTest {
    static final String DEST_DIR = "target/test/";
    static FontCollection fonts;

    @BeforeAll
    static void beforeAll() {
        new File(DEST_DIR).mkdirs();
    }

    @Test
    void listTest() throws IOException {
        String dest = "list_test.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST_DIR + dest));
        Document document = new Document(pdfDocument);
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageNumberHandler(document, PdfFontFactory.createFont(), 16, ""));


        for (ListNumberingType listNumberingType : ListNumberingType.values()) {
            document.add(new Paragraph("" + listNumberingType))
                    .add(new List(listNumberingType)
                            .add(new ListItem("aaaaaa"))
                            .add(new ListItem("bbbbbb"))
                            .add(new ListItem("cccccc")));
        }
        document.close();
    }

    @Test
    void numberSplitter() {
        String result = ChineseNumberConverter.convert(1234567890);
        System.out.println(result);

        result = ChineseNumberConverter.convert(10);
        System.out.println(result);
    }

    static final String TITLE="国密算法概述 SM1、SM2、SM3、SM4、SM7、SM9、ZUC";
    static final String[] LIST_TITLE = {"SM1对称密码", "SM2椭圆曲线公钥密码算法", "SM3杂凑算法", "SM4对称算法", "SM7对称密码", "SM9标识密码算法", "ZUC祖冲之算法"};
    static final String[] PARAGRAPHS = {
            "众所周知，为了保障商用密码的安全性，国家商用密码管理办公室制定了一系列密码标准，包括SM1（SCB2）、SM2、SM3、SM4、SM7、SM9、祖冲之密码算法（ZUC)那等等。" +
                    "其中SM1、SM4、SM7、祖冲之密码（ZUC）是对称算法；SM2、SM9是非对称算法；SM3是哈希算法。目前，这些算法已广泛应用于各个领域中，期待有一天会有采用国密算法的" +
                    "区块链应用出现。",
            "其中SM1、SM7算法不公开，调用该算法时，需要通过加密芯片的接口进行调用；比较少人了解这些算法，在这里对这些国密算法做简单的科普：",
            "SM1 算法是分组密码算法，分组长度为128位，密钥长度都为 128 比特，算法安全保密强度及相关软硬件实现性能与 AES 相当，算法不公开，仅以IP核的形式存在于芯片中。" +
                    "采用该算法已经研制了系列芯片、智能IC卡、智能密码钥匙、加密卡、加密机等安全产品，广泛应用于电子政务、电子商务及国民经济的各个应用领域（包括国家政务通、警务通" +
                    "等重要领域）。",
            "SM2算法就是ECC椭圆曲线密码机制，但在签名、密钥交换方面不同于ECDSA、ECDH等国际标准，而是采取了更为安全的机制。另外，SM2推荐了一条256位的曲线作为标准曲线。" +
                    "SM2标准包括总则，数字签名算法，密钥交换协议，公钥加密算法四个部分，并在每个部分的附录详细说明了实现的相关细节及示例。SM2算法主要考虑素域Fp和F2m上的椭圆曲线，" +
                    "分别介绍了这两类域的表示，运算，以及域上的椭圆曲线的点的表示，运算和多倍点计算算法。然后介绍了编程语言中的数据转换，包括整数和字节串，字节串和比特串，域元素和" +
                    "比特串，域元素和整数，点和字节串之间的数据转换规则。详细说明了有限域上椭圆曲线的参数生成以及验证，椭圆曲线的参数包括有限域的选取，椭圆曲线方程参数，椭圆曲线群" +
                    "基点的选取等，并给出了选取的标准以便于验证。最后给椭圆曲线上密钥对的生成以及公钥的验证，用户的密钥对为（s，sP），其中s为用户的私钥，sP为用户的公钥，由于离散" +
                    "对数问题从sP难以得到s，并针对素域和二元扩域给出了密钥对生成细节和验证方式。总则中的知识也适用于SM9算法。在总则的基础上给出了数字签名算法（包括数字签名生成算" +
                    "法和验证算法），密钥交换协议以及公钥加密算法（包括加密算法和解密算法），并在每个部分给出了算法描述，算法流程和相关示例。数字签名算法，密钥交换协议以及公钥加密" +
                    "算法都使用了国家密管理局批准的SM3密码杂凑算法和随机数发生器。数字签名算法，密钥交换协议以及公钥加密算法根据总则来选取有限域和椭圆曲线，并生成密钥对。SM2算法" +
                    "在很多方面都优于RSA算法（RSA发展得早应用普遍，SM2领先也很自然），与RSA安全性对比如下图",
            "SM3密码杂凑（哈希、散列）算法给出了杂凑函数算法的计算方法和计算步骤，并给出了运算示例。此算法适用于商用密码应用中的数字签名和验证，消息认证码的生成与验证以及" +
                    "随机数的生成，可满足多种密码应用的安全需求。在SM2，SM9标准中使用。此算法对输入长度小于2的64次方的比特消息，经过填充和迭代压缩，生成长度为256比特的杂凑值，" +
                    "其中使用了异或，模，模加，移位，与，或，非运算，由填充，迭代过程，消息扩展和压缩函数所构成。具体算法及运算示例见SM3标准。",
            "此算法是一个分组算法，用于无线局域网产品。该算法的分组长度为128比特，密钥长度为128比特。加密算法与密钥扩展算法都采用32轮非线性迭代结构。解密算法与加密算法的" +
                    "结构相同，只是轮密钥的使用顺序相反，解密轮密钥是加密轮密钥的逆序。此算法采用非线性迭代结构，每次迭代由一个轮函数给出，其中轮函数由一个非线性变换和线性变换复合" +
                    "而成，非线性变换由S盒所给出。其中rki为轮密钥，合成置换T组成轮函数。轮密钥的产生与上图流程类似，由加密密钥作为输入生成，轮函数中的线性变换不同，还有些参数的区" +
                    "别。SM4算法的具体描述和示例见SM4标准。",
            "SM7算法，是一种分组密码算法，分组长度为128比特，密钥长度为128比特。SM7适用于非接触式IC卡，应用包括身份识别类应用(门禁卡、工作证、参赛证)，票务类应用(大型赛" +
                    "事门票、展会门票)，支付与通卡类应用（积分消费卡、校园一卡通、企业一卡通等）。",
            "为了降低公开密钥系统中密钥和证书管理的复杂性，以色列科学家、RSA算法发明人之一Adi Shamir在1984年提出了标识密码（Identity-Based Cryptography）的理念。" +
                    "标识密码将用户的标识（如邮件地址、手机号码、QQ号码等）作为公钥，省略了交换数字证书和公钥过程，使得安全系统变得易于部署和管理，非常适合端对端离线安全通讯、云端" +
                    "数据加密、基于属性加密、基于策略加密的各种场合。2008年标识密码算法正式获得国家密码管理局颁发的商密算法型号：SM9(商密九号算法)，为我国标识密码技术的应用奠定了" +
                    "坚实的基础。SM9算法不需要申请数字证书，适用于互联网应用的各种新兴应用的安全保障。如基于云技术的密码服务、电子邮件安全、智能终端保护、物联网安全、云存储安全等等。" +
                    "这些安全应用可采用手机号码或邮件地址作为公钥，实现数据加密、身份认证、通话加密、通道加密等安全应用，并具有使用方便，易于部署的特点，从而开启了普及密码算法的大门。",
            "祖冲之序列密码算法是中国自主研究的流密码算法,是运用于移动通信4G网络中的国际标准密码算法,该算法包括祖冲之算法(ZUC)、加密算法(128-EEA3)和完整性算法(128-EIA3)" +
                    "三个部分。目前已有对ZUC算法的优化实现，有专门针对128-EEA3和128-EIA3的硬件实现与优化。密码算法作为国家战略资源，比历史上任何时候都显得更为关键。在大数据和云计" +
                    "算的时代，关键信息往往通过数据挖掘技术在海量数据中获得，所以每一个人的信息保护都非常重要。"
    };
    static final String IMAGE_DIR = "src/main/resources/images/";
    static final String IMAGE_METHODS="methods.jpg";
    static final String IMAGE_SPEED_COMPARE = "speed.jpg";

    @Test
    void layoutTest() throws IOException {
        fonts = PdfDemoApplicationTests.createFonts();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST_DIR + "layout_test.pdf"));
        Document document = new Document(pdfDocument);
        document.setMargins(90, 72, 90, 72.f);
        document.setFont(fonts.fang).setFontSize(IPaperGenerator.FONT_SIZE_4S_12).setSplitCharacters(new ChineseSplitterCharacters());
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageNumberHandler(document,fonts.song, IPaperGenerator.FONT_SIZE_50_10_5, ""));
        pdfDocument.addEventHandler(PdfDocumentEvent.START_PAGE, new PageHeaderHandler("国密算法概述", document, fonts.kai, IPaperGenerator.FONT_SIZE_50_10_5));
        Paragraph title =new Paragraph(TITLE).setFont(fonts.hei).setFontSize(IPaperGenerator.FONT_SIZE_10_26).setBold().setTextAlignment(TextAlignment.CENTER);
        document.add(title);
        Div firstPart=new Div();
        Paragraph first = new Paragraph(PARAGRAPHS[0]).setFirstLineIndent(IPaperGenerator.FONT_SIZE_4S_12 * 2).setMultipliedLeading(1.5f);
        Image     image = new Image(ImageDataFactory.create(IMAGE_DIR + IMAGE_METHODS)).setAutoScaleWidth(true);
        Paragraph second = new Paragraph(PARAGRAPHS[1]).setFirstLineIndent(IPaperGenerator.FONT_SIZE_4S_12 * 2);
        firstPart.add(first).add(image).add(second);

        Div secondPart=new Div();
        List list = new List().setListSymbol(ListNumberingType.DECIMAL);
        list.setPostSymbolText(". ");
        for (int i = 0; i< LIST_TITLE.length; i++){
            ListItem item = (ListItem) new ListItem(LIST_TITLE[i]).setFont(fonts.hei).setFontSize(IPaperGenerator.FONT_SIZE_40_14).setBold();
            Paragraph list_paragraph = new Paragraph(PARAGRAPHS[i + 2]).setFirstLineIndent(IPaperGenerator.FONT_SIZE_4S_12*2);
            list_paragraph.setFont(fonts.fang).setFontSize(IPaperGenerator.FONT_SIZE_4S_12).setProperty(Property.BOLD_SIMULATION, false);
            item.add(list_paragraph);
            if(i==1){
                Image speedCompare=new Image(ImageDataFactory.create(IMAGE_DIR + IMAGE_SPEED_COMPARE)).setObjectFit(ObjectFit.FILL).setAutoScaleWidth(true);
                item.add(speedCompare);
            }
            list.add(item);
        }
        secondPart.add(list);

        Rectangle effectiveArea = document.getPageEffectiveArea(pdfDocument.getDefaultPageSize());
        IRenderer renderer = title.createRendererSubTree().setParent(document.getRenderer());
        LayoutResult layoutResult = renderer.layout(new LayoutContext(new LayoutArea(1, effectiveArea)));
        Rectangle size = layoutResult.getOccupiedArea().getBBox();
        drawLine(pdfDocument, pdfDocument.getNumberOfPages(), size.getLeft(), size.getBottom(), size.getWidth());
        float y = size.getHeight();
        document.add(firstPart);
        renderer = firstPart.createRendererSubTree().setParent(document.getRenderer());
        layoutResult=renderer.layout(new LayoutContext(new LayoutArea(1, effectiveArea)));
        size = layoutResult.getOccupiedArea().getBBox();
        drawLine(pdfDocument, pdfDocument.getNumberOfPages(), size.getLeft(), size.getBottom() - y, size.getWidth());
        y = y + size.getHeight();
        renderer = secondPart.createRendererSubTree().setParent(document.getRenderer());
        Rectangle leftSize = effectiveArea.clone().setHeight(effectiveArea.getHeight()  - y);
        layoutResult = renderer.layout(new LayoutContext(new LayoutArea(2, leftSize)));
        int pageNumber = 2;
        while (layoutResult.getStatus() == LayoutResult.PARTIAL){
            renderer= layoutResult.getOverflowRenderer();
            layoutResult = renderer.layout(new LayoutContext(new LayoutArea(pageNumber, effectiveArea)));
            pageNumber +=1;
        }
        size = layoutResult.getOccupiedArea().getBBox();
        document.add(secondPart);
        drawLine(pdfDocument, pdfDocument.getNumberOfPages(), size.getX(), size.getY() , size.getWidth());
        document.flush();
        document.close();
    }
    void drawLine(PdfDocument pdfDocument, int pageNum, float x, float y, float width){
        PdfPage pdfPage= pdfDocument.getPage(pageNum);
        PdfCanvas pdfCanvas = new PdfCanvas(pdfPage);
        pdfCanvas
                .saveState()
                .setStrokeColor(ColorConstants.MAGENTA)
                .setLineWidth(2)
                .moveTo(x, y)
                .lineTo(x + width, y)
                .stroke()
                .restoreState();
    }
}
