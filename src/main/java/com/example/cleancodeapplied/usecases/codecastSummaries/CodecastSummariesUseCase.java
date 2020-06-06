package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.*;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;

public class CodecastSummariesUseCase implements CodecastSummariesInputBoundary {

    public static boolean isLicensedFor(License.Type licenceType, User user, Codecast codecast) {
        List<License> licences = Context.licenseGateway.findLicensesForUserAndCodecast(user, codecast);
        for (License licence : licences) {
            if (licence.getType().equals(licenceType))
                return true;
        }
        return false;
    }

    @Override
    public void summarizeCodecasts(User loggedInUser, CodecastSummariesOutputBoundary presenter) {
        CodecastSummariesResponseModel responseModel = new CodecastSummariesResponseModel();
        List<Codecast> allCodeCasts = Context.codecastGateway.findAllCodeCastsSortedByDateAsc();

        for (Codecast codecast : allCodeCasts) {
            responseModel.addCodecastSummary(summarizeCodecast(codecast,loggedInUser));
        }

        presenter.present(responseModel);
    }

    private CodecastSummary summarizeCodecast(Codecast codecast, User loggedInUser) {
        CodecastSummary summary = new CodecastSummary();
        summary.title = codecast.getTitle();
        summary.publicationDate = codecast.getPublicationDate();
        summary.permalink = codecast.getPermalink();
        summary.isViewable = isLicensedFor(VIEW, loggedInUser, codecast);
        summary.isDownloadable = isLicensedFor(DOWNLOAD, loggedInUser, codecast);
        return summary;
    }
}

