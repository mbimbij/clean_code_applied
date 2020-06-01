package com.example.cleancodeapplied;

public class CodecastDetailsUseCase {
    public PresentableCodecastDetails getCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails codecastDetails = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);

        PresentCodecastsUseCase.doFormatCodecast(loggedInUser, codecast, codecastDetails);
//        codecastDetails.title = codecast.getTitle();
//        codecastDetails.publicationDate = Utils.DATE_FORMAT.format(codecast.getPublicationDate().toLocalDate());
        return codecastDetails;
    }
}
