package com.nantian.pdf.weather.config;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.nantian.pdf.weather.paper.FontCollection;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Configuration
//@PropertySource(value = "classpath:papers.yaml", factory = YamlPropertySourceFactory.class, ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "papers")
public class PapersConfig implements IPapersConfig{
    String fontPath;
    String resourcesPath;
    List<Font> fonts;
    Map<String, PageSetting> settings=new HashMap<>();
    @Override
    public Map<String, PageSetting> getSettings() {
        return settings;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
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

    public FontCollection createFonts(String...names) {

        FontCollection fontCollection = new FontCollection();
        Set<String> nameSet = names==null || names.length==0 ? new HashSet<>(FontCollection.allName()) : new HashSet<>(Arrays.asList(names));
        for (Font font : fonts) {
            if (!nameSet.contains(font.getName())) {
                continue;
            }
            try {
                PdfFont pdfFont = PdfFontFactory.createFont(
                        getFontPath() + File.separator +
                        font.getFile(), PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
                if (pdfFont != null) {
                    fontCollection.assign(font.getName(), pdfFont);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fontCollection;
    }

    @Override
    public String toString() {
        return "PapersConfig{" +
                "fontPath='" + fontPath + '\'' +
                ", templatesPath='" + resourcesPath + '\'' +
                ", fonts=" + fonts +
                ", settings=" + settings +
                '}';
    }
}
