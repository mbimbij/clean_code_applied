package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.view.ViewTemplate;

import java.util.List;

public class CodecastSummariesView {
    String getFrontPage(List<PresentableCodecastSummary> presentableCodecasts) throws Exception {
        ViewTemplate frontpageTemplate = ViewTemplate.fromClasspathResource("html/frontpage.html");
        StringBuilder codecastLines = new StringBuilder();
        for (PresentableCodecastSummary presentableCodecast : presentableCodecasts) {
            ViewTemplate codecastTemplate = ViewTemplate.fromClasspathResource("html/codecast.html");
            codecastTemplate.replace("title", presentableCodecast.title);
            codecastTemplate.replace("publicationDate", presentableCodecast.publicationDate);
            codecastTemplate.replace("thumbnail", "https://cleancoders.com/images/portraits/robert-martin.jpg");
            codecastTemplate.replace("permalink", presentableCodecast.permalink);
            codecastTemplate.replace("author", "Uncle Bob");
            codecastTemplate.replace("duration", "1:00:00");
            codecastTemplate.replace("licenseOptions", "buying options go here");
            codecastTemplate.replace("contentActions", "");
            codecastLines.append(codecastTemplate.getHtml());
        }

        frontpageTemplate.replace("codecasts", codecastLines.toString());
        return frontpageTemplate.getHtml();
    }
}