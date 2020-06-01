package com.example.cleancodeapplied;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails getCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails codecastDetails = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        if(codecast == null){
            codecastDetails.wasFound = false;
        }else {
            codecastDetails.wasFound = true;
            CodecastSummaryUseCase.formatSummaryFields(loggedInUser, codecast, codecastDetails);
        }
        return codecastDetails;
    }
}
