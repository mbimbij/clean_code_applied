package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.*;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;

public class CodecastSummariesUseCase {

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
