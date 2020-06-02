package com.example.cleancodeapplied;

import com.example.cleancodeapplied.usecases.codecastDetails.CodecastDetailsUseCase;
import com.example.cleancodeapplied.usecases.codecastDetails.PresentableCodecastDetails;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PresentCodecastDetailsStepdefs {

    private CodecastDetailsUseCase useCase = new CodecastDetailsUseCase();
    private PresentableCodecastDetails actualCodecastDetails;

    @When("the user requests for episode {string}")
    public void theUserRequestsForEpisode(String permalink) {
        actualCodecastDetails = useCase.getCodecastDetails(Context.gateKeeper.getLoggedInUser(), permalink);
    }

    @Then("then the presented title is")
    public void thenThePresentedTitleIs(List<PresentableCodecastDetails> presentableCodecastDetails) {
        assert presentableCodecastDetails.size() == 1;
        PresentableCodecastDetails expectedCodecastDetails = presentableCodecastDetails.get(0);
        assertThat(actualCodecastDetails).isEqualToComparingOnlyGivenFields(
                expectedCodecastDetails,
                "title",
                "publicationDate");

    }

    @DataTableType
    public PresentableCodecastDetails presentableCodecastDetails(Map<String, String> entry) {
        PresentableCodecastDetails presentableCodecastDetails = new PresentableCodecastDetails();
        presentableCodecastDetails.title = entry.get("title");
        presentableCodecastDetails.publicationDate = entry.get("publicationDate");
        return presentableCodecastDetails;
    }

    @And("with option to purchase {string} license")
    public void withOptionToPurchaseViewingLicense(String licenseTypeName) {
        License.Type licenceType = License.Type.valueOf(licenseTypeName);
        assertThat((licenceType.equals(License.Type.VIEW) && !actualCodecastDetails.isViewable) ||
                        (licenceType.equals(License.Type.DOWNLOAD) && !actualCodecastDetails.isDownloadable))
                .isTrue();
    }
}
