package com.example.cleancodeapplied;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.cleancodeapplied.Licence.Type.DOWNLOADABLE;
import static com.example.cleancodeapplied.Licence.Type.VIEWABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class MyStepdefs {

    private ObjectMapper objectMapper = new ObjectMapper();
    private GateKeeper gateKeeper = new GateKeeper();
    private PresentCodecastUseCase useCase = new PresentCodecastUseCase();

    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
    }

    @DataTableType
    public Codecast codecast(Map<String, String> entry) {
        return new Codecast(entry.get("title"),
                LocalDate.parse(
                        entry.get("publicationDate"),
                        DateTimeFormatter.ofPattern("MM/dd/yyyy")).atStartOfDay(ZoneId.systemDefault()));
    }

    @Given("codecasts")
    public void codecasts(List<Codecast> codecasts) {
        for (Codecast codecast : codecasts) {
            Context.gateway.save(codecast);
        }
    }

    @Given("no codecasts")
    public void noCodecasts() {
        List<Codecast> codecasts = Context.gateway.findAllCodeCastsSortedByDateAsc();
        for (Codecast codecast : new ArrayList<>(codecasts)) {
            Context.gateway.delete(codecast);
        }
        assertThat(Context.gateway.findAllCodeCastsSortedByDateAsc()).isEmpty();
    }

    @And("user {string}")
    public void addUser(String username) {
        Context.gateway.save(new User(username));
    }

    @And("user {string} logged in")
    public void userLoggedIn(String username) {
        User user = Context.gateway.findUser(username);
        if (user != null) {
            gateKeeper.setLoggedInUser(user);
        } else {
            fail("test fixture should have created a logged in user");
        }
    }

    @And("there will be no codecasts presented")
    public void thereWillBeNoCodecastsPresented() {
        assertThat(useCase.presentCodecasts(gateKeeper.getLoggedInUser())).isEmpty();
    }

    @And("with licence for {string} able to view {string}")
    public void createLicenceForUserOnVideo(String userName, String codecastTitle) {
        User user = Context.gateway.findUser(userName);
        Codecast codecast = Context.gateway.findCodecastByTitle(codecastTitle);
        Licence licence = new Licence(VIEWABLE, user, codecast);
        Context.gateway.save(licence);
        assertThat(useCase.isLicencedToViewCodecast(user, codecast)).isTrue();
    }

    @Then("then the following codecasts will be presented for {string}")
    public void thenTheFollowingCodecastsWillBePresentedFor(String expectedUserName
            , DataTable expectedPresentedCodecastsInOrder
    ) {
        List<PresentableCodecast> actuallyPresentedCodecasts = useCase.presentCodecasts(gateKeeper.getLoggedInUser());
        DataTable actuallyPresentedCodecastsDatatable = convertToCucumberDatatable(actuallyPresentedCodecasts);
        expectedPresentedCodecastsInOrder.diff(actuallyPresentedCodecastsDatatable);
    }

    private DataTable convertToCucumberDatatable(List<PresentableCodecast> actuallyPresentedCodecasts) {
        List<List<String>> actuallyPresentedCodecastsAsList = new ArrayList<>();
        actuallyPresentedCodecastsAsList.add(Arrays.asList("title", "publicationDate", "viewable", "downloadable"));

        List<List<String>> collect = actuallyPresentedCodecasts.stream()
                .map(presentableCodecast -> Arrays.asList(
                        presentableCodecast.title,
                        presentableCodecast.publicationDate,
                        String.valueOf(presentableCodecast.viewable),
                        String.valueOf(presentableCodecast.downloadable)
                        )
                )
                .collect(Collectors.toList());
        actuallyPresentedCodecastsAsList.addAll(collect);

        return DataTable.create(actuallyPresentedCodecastsAsList);
    }

    @DataTableType
    public PresentedCodecastDatatable presentedCodecastDatatable(Map<String, String> entry) {
        return objectMapper.convertValue(entry, PresentedCodecastDatatable.class);
    }

    @And("with licence for {string} able to download {string}")
    public void withLicenceForAbleToDownload(String userName, String codecastTitle) {
        User user = Context.gateway.findUser(userName);
        Codecast codecast = Context.gateway.findCodecastByTitle(codecastTitle);
        Licence licence = new Licence(DOWNLOADABLE, user, codecast);
        Context.gateway.save(licence);
        assertThat(useCase.isLicencedToDownloadCodecast(user, codecast)).isTrue();
    }

    @ToString
    @Data
    private static class PresentedCodecastDatatable {
        public String title;
        public String picture;
        public String description;
        public boolean viewable;
        public boolean downloadable;
    }
}
