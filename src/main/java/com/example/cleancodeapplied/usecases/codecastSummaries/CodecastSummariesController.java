package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.http.Controller;
import com.example.cleancodeapplied.http.ParsedRequest;

public class CodecastSummariesController implements Controller {
    private CodecastSummariesInputBoundary usecase;
    private CodecastSummariesOutputBoundary presenter;
    private CodecastSummariesView view;

    public CodecastSummariesController(CodecastSummariesInputBoundary usecase,
                                       CodecastSummariesOutputBoundary presenter,
                                       CodecastSummariesView view) {
        this.usecase = usecase;
        this.presenter = presenter;
        this.view = view;
    }

    @Override
    public String handle(ParsedRequest request) {
        usecase.summarizeCodecasts(Context.gateKeeper.getLoggedInUser(), presenter);
        view.generateView(presenter.getViewModel());
        return null;
    }

}
