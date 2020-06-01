package com.example.cleancodeapplied;

import java.time.format.DateTimeFormatter;

public class CodecastDetailsUseCase {


    public PresentableCodecastDetails getCodecastDetails(User loggedInUser, String permalink) {
        PresentableCodecastDetails codecastDetails = new PresentableCodecastDetails();
        Codecast codecast = Context.codecastGateway.findCodecastByPermalink(permalink);
        codecastDetails.title = codecast.getTitle();
        codecastDetails.publicationDate = Utils.DATE_FORMAT.format(codecast.getPublicationDate().toLocalDate());
        return codecastDetails;
    }
}
