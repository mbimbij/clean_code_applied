package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.*;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class CodecastSummariesUseCase implements CodecastSummariesInputBoundary {

    public List<CodecastSummariesResponseModel> summarizeCodecasts(User loggedInUser) {
        return Context.codecastGateway.findAllCodeCastsSortedByDateAsc().stream()
                .map(codecast -> CodecastSummariesPresenter.formatCodecast(loggedInUser, codecast))
                .collect(Collectors.toList());
    }

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
        presenter.present(responseModel);
    }
}

