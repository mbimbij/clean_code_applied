package com.example.cleancodeapplied.usecases.codecastSummaries;

public interface CodecastSummariesOutputBoundary {
    CodecastSummariesViewModel getViewModel();

    void present(CodecastSummariesResponseModel responseModel);
}
