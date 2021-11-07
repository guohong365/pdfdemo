package com.nantian.pdfdemo;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.ListNumberingType;
import com.nantian.pdf.utils.ChineseNumberConverter;
import com.nantian.pdf.utils.PageNumberHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class GenericTest {
    static final String DEST_DIR="target/test/";
    @BeforeAll
    static void beforeAll(){
        new File(DEST_DIR).mkdirs();
    }
    @Test
    void listTest() throws IOException {
        String dest = "list_test.pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(DEST_DIR + dest));
        pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new PageNumberHandler(PdfFontFactory.createFont(), 16,""));
        Document document = new Document(pdfDocument);

        for(ListNumberingType listNumberingType:ListNumberingType.values()) {
            document.add(new Paragraph(""+listNumberingType))
                    .add(new List(listNumberingType)
                    .add(new ListItem("aaaaaa"))
                    .add(new ListItem("bbbbbb"))
                    .add(new ListItem("cccccc")));
        }
        document.close();
    }

    @Test
    void numberSpliter(){
        String result=ChineseNumberConverter.convert(1234567890);
        System.out.println(result);

        result=ChineseNumberConverter.convert(10);
        System.out.println(result);
    }
}
