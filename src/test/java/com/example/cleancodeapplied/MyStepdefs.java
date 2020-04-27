package com.example.cleancodeapplied;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MyStepdefs {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        Context.gateway = new MockGateway();
    }

    @DataTableType
    public Codecast codecast(Map<String, String> entry) {
        return objectMapper.convertValue(entry, Codecast.class);
    }

    @Given("no codecasts")
    public void noCodecasts() {
        List<Codecast> codecasts = Context.gateway.findAllCodeCasts();
        for (Codecast codecast : new ArrayList<>(codecasts)) {
            Context.gateway.delete(codecast);
        }
        assertThat(Context.gateway.findAllCodeCasts()).isEmpty();
    }

    @And("user U logged in")
    public void userULoggedIn() {
        assertThat(true).isFalse();
    }

    @Then("then the following codecasts will be presented for U")
    public void thenTheFollowingCodecastsWillBePresentedForU() {
        assertThat(true).isFalse();
    }

    @And("there will be no codecasts presented")
    public void thereWillBeNoCodecastsPresented() {
        assertThat(true).isFalse();
    }

    @Given("codecasts")
    public void codecasts(List<Codecast> codecasts) {
        for (Codecast codecast : codecasts) {
            Context.gateway.save(codecast);
        }
    }
}
