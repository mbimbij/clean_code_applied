package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.view.ViewTemplate;

import java.util.List;

public class CodecastSummariesViewImpl implements CodecastSummariesView {
    @Override
    public String generateView(CodecastSummariesViewModel viewModel) throws Exception {
        ViewTemplate frontpageTemplate = ViewTemplate.fromClasspathResource("html/frontpage.html");
        StringBuilder codecastLines = new StringBuilder();
        for (CodecastSummariesViewModel.ViewableCodecastSummary viewableCodecastSummary : viewModel.viewableCodecastSummaries) {
            ViewTemplate codecastTemplate = ViewTemplate.fromClasspathResource("html/codecast.html");
            codecastTemplate.replace("title", viewableCodecastSummary.title);
            codecastTemplate.replace("publicationDate", viewableCodecastSummary.publicationDate);
            codecastTemplate.replace("thumbnail", "https://cleancoders.com/images/portraits/robert-martin.jpg");
            codecastTemplate.replace("permalink", viewableCodecastSummary.permalink);
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