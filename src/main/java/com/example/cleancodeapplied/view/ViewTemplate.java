package com.example.cleancodeapplied.view;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ViewTemplate {
    private String html;

    private ViewTemplate(String html) {
        this.html = html;
    }

    public static ViewTemplate fromHtmlContent(String htmlContent) {
        return new ViewTemplate(htmlContent);
    }

    public static ViewTemplate fromClasspathResource(String templateClassPathResource) throws IOException {
        File templateFile = new File(ViewTemplate.class.getClassLoader().getResource(templateClassPathResource).getFile());
        return new ViewTemplate(FileUtils.readFileToString(templateFile, StandardCharsets.UTF_8));
    }

    public String getHtml() {
        return html;
    }

    public void replace(String tagName, String replacement) {
        html = html.replace(String.format("${%s}", tagName), replacement);
    }
}
