package com.example.cleancodeapplied.usecases.codecastSummaries;

public class CodecastSummariesOutputBoundarySpy implements CodecastSummariesOutputBoundary {
    public CodecaseSummariesResponseModel responseModel;

    @Override
    public CodecaseSummariesResponseModel getResponseModel() {
        return responseModel;
    }
}
