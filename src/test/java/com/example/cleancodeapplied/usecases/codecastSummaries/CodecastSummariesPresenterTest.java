package com.example.cleancodeapplied.usecases.codecastSummaries;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class CodecastSummariesPresenterTest {
    @Test
    void validateViewModel() {
        CodecastSummariesResponseModel responseModel = new CodecastSummariesResponseModel();
        CodecastSummary summary = new CodecastSummary();
        summary.title = "Title";
        summary.publicationDate = LocalDate.of(2015,11,3).atStartOfDay(ZoneId.systemDefault());
        summary.permalink = "permalink";
        summary.isViewable = true;
        summary.isDownloadable = false;
        responseModel.addCodecastSummary(summary);

        CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        presenter.present(responseModel);

        CodecastSummariesViewModel viewModel = presenter.getViewModel();
        CodecastSummariesViewModel.ViewableCodecastSummary viewableSummary = viewModel.viewableCodecastSummaries.get(0);
        assertThat(viewableSummary.title).isEqualTo(summary.title);
    }
}
