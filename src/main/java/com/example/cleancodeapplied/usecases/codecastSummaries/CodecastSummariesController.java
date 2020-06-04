package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.http.Controller;
import com.example.cleancodeapplied.http.ParsedRequest;

public class CodecastSummariesController implements Controller {
    @Override
    public String handle(ParsedRequest request) {
        try {
            CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
            User micah = Context.userGateway.findUserByName("Micah");
            CodecastSummariesView codecastSummariesView = new CodecastSummariesView();
            String frontpageHtmlContent = codecastSummariesView.toHtml(useCase.presentCodecasts(micah));
            return Controller.makeResponse(frontpageHtmlContent);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
