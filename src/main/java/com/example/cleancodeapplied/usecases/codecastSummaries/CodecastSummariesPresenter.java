package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Utils;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesViewModel.ViewableCodecastSummary;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;

public class CodecastSummariesPresenter implements CodecastSummariesOutputBoundary {

    private CodecastSummariesViewModel viewModel;

    public static void formatSummaryFields(User loggedInUser, Codecast codecast, CodecastSummariesResponseModel presentableCodecast) {
        presentableCodecast.title = codecast.getTitle();
        presentableCodecast.publicationDate = codecast.getPublicationDate().format(Utils.DATE_FORMAT);
        presentableCodecast.isViewable = CodecastSummariesUseCase.isLicensedFor(VIEW, loggedInUser, codecast);
        presentableCodecast.isDownloadable = CodecastSummariesUseCase.isLicensedFor(DOWNLOAD, loggedInUser, codecast);
        presentableCodecast.permalink = codecast.getPermalink();
    }

    @Override
    public CodecastSummariesViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void present(CodecastSummariesResponseModel responseModel) {
        viewModel = new CodecastSummariesViewModel();
        for (CodecastSummary codecastSummary: responseModel.getCodecastSummaries()) {
            viewModel.addModel(makeViewable(codecastSummary));
        }
    }

    private ViewableCodecastSummary makeViewable(CodecastSummary codecastSummary) {
        ViewableCodecastSummary summary = new ViewableCodecastSummary();
        summary.title = codecastSummary.title;
        summary.permalink = codecastSummary.permalink;
        summary.publicationDate = Utils.DATE_FORMAT.format(codecastSummary.publicationDate);
        summary.isDownloadable = codecastSummary.isDownloadable;
        summary.isViewable = codecastSummary.isViewable;
        return summary;
    }
}
