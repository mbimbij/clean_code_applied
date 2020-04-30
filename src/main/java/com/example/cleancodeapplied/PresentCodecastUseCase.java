package com.example.cleancodeapplied;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.Licence.Type.*;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return Context.gateway.findAllCodeCastsSortedByDateAsc().stream()
                .map(codecast -> formatCodecast(loggedInUser, codecast))
                .collect(Collectors.toList());
    }

    private PresentableCodecast formatCodecast(User loggedInUser, Codecast codecast) {
        PresentableCodecast presentableCodecast = new PresentableCodecast();
        presentableCodecast.title=codecast.getTitle();
        presentableCodecast.publicationDate=codecast.getPublicationDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        presentableCodecast.viewable= isLicencedFor(VIEW, loggedInUser, codecast);
        presentableCodecast.downloadable= isLicencedFor(DOWNLOAD, loggedInUser, codecast);
        return presentableCodecast;
    }

    public boolean isLicencedFor(Licence.Type licenceType, User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        for (Licence licence : licences) {
            if (licence.getType().equals(licenceType))
                return true;
        }
        return false;
    }
}
