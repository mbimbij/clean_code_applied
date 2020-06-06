package com.example.cleancodeapplied.usecases.codecastSummaries;

import java.util.ArrayList;
import java.util.List;

public class CodecastSummariesResponseModel {
    private final List<CodecastSummary> codecastSummaries;
    public String title;
    public String publicationDate;
    public boolean isViewable;
    public boolean isDownloadable;
    public String permalink;

    public CodecastSummariesResponseModel() {
        codecastSummaries = new ArrayList<CodecastSummary>();
    }

    public List<CodecastSummary> getCodecastSummaries() {
        return codecastSummaries;
    }

    public void addCodecastSummary(CodecastSummary codecastSummary) {
        codecastSummaries.add(codecastSummary);
    }
}
