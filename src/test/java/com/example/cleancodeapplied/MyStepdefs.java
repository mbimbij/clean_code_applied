package com.example.cleancodeapplied;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return objectMapper.convertValue(entry, Codecast.class);
    }

    @Given("codecasts")
    public void codecasts(List<Codecast> codecasts) {
        for (Codecast codecast : codecasts) {
            Context.gateway.save(codecast);
        }
    }

    @Given("no codecasts")
    public void noCodecasts() {
        List<Codecast> codecasts = Context.gateway.findAllCodeCasts();
        for (Codecast codecast : new ArrayList<>(codecasts)) {
            Context.gateway.delete(codecast);
        }
        assertThat(Context.gateway.findAllCodeCasts()).isEmpty();
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

    @Then("then the following codecasts will be presented for {string}")
    public void thenTheFollowingCodecastsWillBePresentedFor(String expectedUserName, List<Map<String,Object>> table) {
        assertThat(gateKeeper.getLoggedInUser().getUserName()).isEqualTo(expectedUserName);
    }

    @And("with licence for {string} able to view {string}")
    public void createLicenceForUserOnVideo(String userName, String codecastTitle) {
        User user = Context.gateway.findUser(userName);
        Codecast codecast = Context.gateway.findCodecastByTitle(codecastTitle);
        Licence licence = new Licence(user, codecast);
        Context.gateway.save(licence);
        assertThat(useCase.isLicencedToViewCodecast(user, codecast)).isTrue() ;
    }
}
