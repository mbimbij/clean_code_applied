package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class CodecastSummariesStepdefs {
    private ObjectMapper objectMapper = new ObjectMapper();
    private CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();

    @Before
    public void setUp() {
        TestSetup.setupContext();
    }


    @Given("no codecasts")
    public void noCodecasts() {
        List<Codecast> codecasts = Context.codecastGateway.findAllCodeCastsSortedByDateAsc();
        for (Codecast codecast : new ArrayList<>(codecasts)) {
            Context.codecastGateway.delete(codecast);
        }
        assertThat(Context.codecastGateway.findAllCodeCastsSortedByDateAsc()).isEmpty();
    }

    @And("user {string}")
    public void addUser(String username) {
        Context.userGateway.save(new User(username));
    }

    @And("user {string} logged in")
    public void userLoggedIn(String username) {
        User user = Context.userGateway.findUserByName(username);
        if (user != null) {
            Context.gateKeeper.setLoggedInUser(user);
        } else {
            fail("test fixture should have created a logged in user");
        }
    }

    @And("there will be no codecasts presented")
    public void thereWillBeNoCodecastsPresented() {
        assertThat(useCase.summarizeCodecasts(Context.gateKeeper.getLoggedInUser())).isEmpty();
    }

    @And("with licence for {string} able to view {string}")
    public void createLicenceForUserOnVideo(String userName, String codecastTitle) {
        User user = Context.userGateway.findUserByName(userName);
        Codecast codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
        License licence = new License(VIEW, user, codecast);
        Context.licenseGateway.save(licence);
        assertThat(useCase.isLicensedFor(VIEW, user, codecast)).isTrue();
    }

    @Then("then the following codecasts will be presented for {string}")
    public void thenTheFollowingCodecastsWillBePresentedFor(String expectedUserName
            , DataTable expectedPresentedCodecastsInOrder
    ) {
        List<CodecaseSummariesResponseModel> actuallyPresentedCodecasts = useCase.summarizeCodecasts(Context.gateKeeper.getLoggedInUser());
        DataTable actuallyPresentedCodecastsDatatable = convertToCucumberDatatable(actuallyPresentedCodecasts);
        expectedPresentedCodecastsInOrder.diff(actuallyPresentedCodecastsDatatable);
    }

    private DataTable convertToCucumberDatatable(List<CodecaseSummariesResponseModel> actuallyPresentedCodecasts) {
        List<List<String>> actuallyPresentedCodecastsAsList = new ArrayList<>();
        actuallyPresentedCodecastsAsList.add(Arrays.asList("title", "publicationDate", "viewable", "downloadable"));

        List<List<String>> collect = actuallyPresentedCodecasts.stream()
                .map(presentableCodecast -> Arrays.asList(
                        presentableCodecast.title,
                        presentableCodecast.publicationDate,
                        String.valueOf(presentableCodecast.isViewable),
                        String.valueOf(presentableCodecast.isDownloadable)
                        )
                )
                .collect(Collectors.toList());
        actuallyPresentedCodecastsAsList.addAll(collect);

        return DataTable.create(actuallyPresentedCodecastsAsList);
    }

    @And("with licence for {string} able to download {string}")
    public void withLicenceForAbleToDownload(String userName, String codecastTitle) {
        User user = Context.userGateway.findUserByName(userName);
        Codecast codecast = Context.codecastGateway.findCodecastByTitle(codecastTitle);
        License licence = new License(DOWNLOAD, user, codecast);
        Context.licenseGateway.save(licence);
        assertThat(useCase.isLicensedFor(DOWNLOAD, user, codecast)).isTrue();
    }

    @DataTableType
    public PresentedCodecastDatatable presentedCodecastDatatable(Map<String, String> entry) {
        return objectMapper.convertValue(entry, PresentedCodecastDatatable.class);
    }

    @ToString
    @Data
    public static class PresentedCodecastDatatable {
        public String title;
        public String picture;
        public String description;
        public boolean viewable;
        public boolean downloadable;
    }

}
