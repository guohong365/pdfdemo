package com.nantian.weather.config;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
//@PropertySource(value = "classpath:papers.yaml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "papers")
public class PapersConfig {
    static Map<String, PdfFont> pdfFontMap = new HashMap<>();
    String fontPath;
    String templatesPath;
    List<Font> fonts;
    List<PaperSetting> papers;

    public static PdfFont getFont(String name) {
        return pdfFontMap.get(name);
    }

    public String getTemplatesPath() {
        return templatesPath;
    }

    public void setTemplatesPath(String templatesPath) {
        this.templatesPath = templatesPath;
    }

    public PaperSetting getSetting(String name) {
        for (PaperSetting setting : this.getPapers()) {
            if (name.equals(setting.getName())) {
                return setting;
            }
        }
        return null;
    }

    public String getFontPath() {
        return fontPath;
    }

    public void setFontPath(String fontPath) {
        this.fontPath = fontPath;
    }

    public List<Font> getFonts() {
        return fonts;
    }

    public void setFonts(List<Font> fonts) {
        this.fonts = fonts;
    }

    public List<PaperSetting> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperSetting> papers) {
        this.papers = papers;
    }

    @Bean
    public void loadPdfFonts(PapersConfig config) {
        for (Font font : fonts) {
            try {
                PdfFont pdfFont = PdfFontFactory.createFont(font.getFile(), PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
                if (pdfFont != null) {
                    pdfFontMap.put(font.getName(), pdfFont);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
