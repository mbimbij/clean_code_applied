package com.example.cleancodeapplied.usecases.codecastSummaries;

import java.util.ArrayList;
import java.util.List;

public class CodecastSummariesViewModel {

    public List<ViewableCodecastSummary> viewableCodecastSummaries = new ArrayList<>();

    public void addModel(ViewableCodecastSummary makeViewable) {
        viewableCodecastSummaries.add(makeViewable);
    }

    public static class ViewableCodecastSummary {
        public String title;
        public String publicationDate;
        public String permalink;
        public boolean isViewable;
        public boolean isDownloadable;
    }
}
