package com.example.cleancodeapplied;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails getCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails codecastDetails = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        PresentCodecastsUseCase.doFormatCodecast(loggedInUser, codecast, codecastDetails);
        return codecastDetails;
    }
}
