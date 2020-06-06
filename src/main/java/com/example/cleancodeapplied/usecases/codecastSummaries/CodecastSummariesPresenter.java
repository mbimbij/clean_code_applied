package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Utils;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.User;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;

public class CodecastSummariesPresenter {
    public static void formatSummaryFields(User loggedInUser, Codecast codecast, CodecaseSummariesViewModel presentableCodecast) {
        presentableCodecast.title = codecast.getTitle();
        presentableCodecast.publicationDate = codecast.getPublicationDate().format(Utils.DATE_FORMAT);
        presentableCodecast.isViewable = CodecastSummariesUseCase.isLicensedFor(VIEW, loggedInUser, codecast);
        presentableCodecast.isDownloadable = CodecastSummariesUseCase.isLicensedFor(DOWNLOAD, loggedInUser, codecast);
        presentableCodecast.permalink = codecast.getPermalink();
    }

    public static CodecaseSummariesViewModel formatCodecast(User loggedInUser, Codecast codecast) {
        CodecaseSummariesViewModel presentableCodecast = new CodecaseSummariesViewModel();
        formatSummaryFields(loggedInUser, codecast, presentableCodecast);
        return presentableCodecast;
    }
}
