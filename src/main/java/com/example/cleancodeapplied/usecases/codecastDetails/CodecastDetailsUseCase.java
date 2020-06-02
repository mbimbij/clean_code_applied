package com.example.cleancodeapplied.usecases.codecastDetails;

import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesUseCase;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails getCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails codecastDetails = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        if(codecast == null){
            codecastDetails.wasFound = false;
        }else {
            codecastDetails.wasFound = true;
            CodecastSummariesUseCase.formatSummaryFields(loggedInUser, codecast, codecastDetails);
        }
        return codecastDetails;
    }
}
