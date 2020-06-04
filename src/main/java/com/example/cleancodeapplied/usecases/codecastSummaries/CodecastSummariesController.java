package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.http.Controller;
import com.example.cleancodeapplied.http.ParsedRequest;

public class CodecastSummariesController implements Controller {
    private CodecastSummaryInputBoundary codecastSummaryInputBoundary;

    public CodecastSummariesController(CodecastSummaryInputBoundary codecastSummaryInputBoundary) {
        this.codecastSummaryInputBoundary = codecastSummaryInputBoundary;
    }

    @Override
    public String handle(ParsedRequest request) {
        codecastSummaryInputBoundary.summarizeCodecasts(Context.gateKeeper.getLoggedInUser());
        return null;
//        try {
//            CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
//            User micah = Context.userGateway.findUserByName("Micah");
//            CodecastSummariesView codecastSummariesView = new CodecastSummariesView();
//            String frontpageHtmlContent = codecastSummariesView.toHtml(useCase.summarizeCodecasts(micah));
//            return Controller.makeResponse(frontpageHtmlContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

}
