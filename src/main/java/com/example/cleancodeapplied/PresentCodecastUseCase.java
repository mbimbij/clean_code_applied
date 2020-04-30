package com.example.cleancodeapplied;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return Context.gateway.findAllCodeCastsSortedByDateAsc().stream()
                .map(codecast -> {
                    PresentableCodecast presentableCodecast = new PresentableCodecast();
                    presentableCodecast.title=codecast.getTitle();
                    presentableCodecast.publicationDate=codecast.getPublicationDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                    presentableCodecast.viewable=isLicencedToViewCodecast(loggedInUser,codecast);
                    presentableCodecast.downloadable=isLicencedToDownloadCodecast(loggedInUser,codecast);
                    return presentableCodecast;
                })
                .collect(Collectors.toList());
    }

    public boolean isLicencedToViewCodecast(User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        return !licences.isEmpty();
    }

    public boolean isLicencedToDownloadCodecast(User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        for (Licence licence : licences) {
            if(licence instanceof DownloadLicence)
                return true;
        }
        return false;
    }
}
