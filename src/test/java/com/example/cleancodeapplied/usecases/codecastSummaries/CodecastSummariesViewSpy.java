package com.example.cleancodeapplied.usecases.codecastSummaries;

public class CodecastSummariesViewSpy implements CodecastSummariesView {
    public boolean generateViewWasCalled;
    public CodecastSummariesViewModel viewModel;

    @Override
    public String generateView(CodecastSummariesViewModel viewModel) {
        this.viewModel = viewModel;
        generateViewWasCalled = true;
        return null;
    }
}
