package com.example.cleancodeapplied;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jdk.jshell.spi.ExecutionControl;
import org.assertj.core.api.Assertions;

import java.util.List;

public class ShowEpisodeStepdefs {

    private CodecastDetailsUseCase codecastDetailsUseCase = new CodecastDetailsUseCase();

    @When("the user requests for episode {string}")
    public void theUserRequestsForEpisode(String arg0) {
        Assertions.fail("not implemented");
    }

    @Then("then the presented title is")
    public void thenThePresentedTitleIs(List<Codecast> codecasts) {
        Assertions.fail("not implemented");
    }
}
