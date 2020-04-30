package com.example.cleancodeapplied;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.License.Type.*;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return Context.codecastGateway.findAllCodeCastsSortedByDateAsc().stream()
                .map(codecast -> formatCodecast(loggedInUser, codecast))
                .collect(Collectors.toList());
    }

    private PresentableCodecast formatCodecast(User loggedInUser, Codecast codecast) {
        PresentableCodecast presentableCodecast = new PresentableCodecast();
        presentableCodecast.title=codecast.getTitle();
        presentableCodecast.publicationDate=codecast.getPublicationDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        presentableCodecast.isViewable= isLicensedFor(VIEW, loggedInUser, codecast);
        presentableCodecast.isDownloadable= isLicensedFor(DOWNLOAD, loggedInUser, codecast);
        return presentableCodecast;
    }

    public boolean isLicensedFor(License.Type licenceType, User user, Codecast codecast) {
        List<License> licences = Context.licenseGateway.findLicensesForUserAndCodecast(user, codecast);
        for (License licence : licences) {
            if (licence.getType().equals(licenceType))
                return true;
        }
        return false;
    }
}
