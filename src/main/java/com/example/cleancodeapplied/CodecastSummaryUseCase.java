package com.example.cleancodeapplied;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.License.Type.VIEW;

public class CodecastSummaryUseCase {

    public List<PresentableCodecastSummary> presentCodecasts(User loggedInUser) {
        return Context.codecastGateway.findAllCodeCastsSortedByDateAsc().stream()
                .map(codecast -> formatCodecast(loggedInUser, codecast))
                .collect(Collectors.toList());
    }

    private PresentableCodecastSummary formatCodecast(User loggedInUser, Codecast codecast) {
        PresentableCodecastSummary presentableCodecast = new PresentableCodecastSummary();
        formatSummaryFields(loggedInUser, codecast, presentableCodecast);
        return presentableCodecast;
    }

    public static void formatSummaryFields(User loggedInUser, Codecast codecast, PresentableCodecastSummary presentableCodecast) {
        presentableCodecast.title=codecast.getTitle();
        presentableCodecast.publicationDate=codecast.getPublicationDate().format(Utils.DATE_FORMAT);
        presentableCodecast.isViewable= isLicensedFor(VIEW, loggedInUser, codecast);
        presentableCodecast.isDownloadable= isLicensedFor(DOWNLOAD, loggedInUser, codecast);
    }

    public static boolean isLicensedFor(License.Type licenceType, User user, Codecast codecast) {
        List<License> licences = Context.licenseGateway.findLicensesForUserAndCodecast(user, codecast);
        for (License licence : licences) {
            if (licence.getType().equals(licenceType))
                return true;
        }
        return false;
    }
}
