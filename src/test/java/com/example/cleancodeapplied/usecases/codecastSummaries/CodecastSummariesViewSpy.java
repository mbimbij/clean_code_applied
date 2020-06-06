package com.example.cleancodeapplied.usecases.codecastSummaries;

public class CodecastSummariesViewSpy implements CodecastSummariesView {
    public boolean generateViewWasCalled;
    public CodecaseSummariesResponseModel responseModel;

    @Override
    public String generateView(CodecaseSummariesResponseModel responseModel) {
        this.responseModel = responseModel;
        generateViewWasCalled = true;
        return null;
    }
}
