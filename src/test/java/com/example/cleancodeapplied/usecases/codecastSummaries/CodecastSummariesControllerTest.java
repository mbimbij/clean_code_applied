package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.http.ParsedRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodecastSummariesControllerTest {

    private CodecastSummariesInputBoundarySpy usecaseSpy;
    private CodecastSummariesOutputBoundarySpy presenterSpy;
    private CodecastSummariesViewSpy viewSpy;
    private CodecastSummariesController controller;

    @BeforeEach
    public void setup() {
        TestSetup.setupSampleData();
        usecaseSpy = new CodecastSummariesInputBoundarySpy();
        presenterSpy = new CodecastSummariesOutputBoundarySpy();
        viewSpy = new CodecastSummariesViewSpy();
        controller = new CodecastSummariesController(usecaseSpy, presenterSpy, viewSpy);
    }

    @Test
    void testInputBoundaryInvocation() {
        ParsedRequest request = new ParsedRequest("GET","blah");
        controller.handle(request);

        assertThat(usecaseSpy.summarizeCodecastsWasCalled).isTrue();
        User loggedInUser = Context.userGateway.findUserByName("Bob");
        assertThat(usecaseSpy.requestedUser.getId()).isEqualTo(loggedInUser.getId());
        assertThat(presenterSpy).isEqualTo(usecaseSpy.outputBoundary);
    }

    @Test
    void controllerSendsTheResponseModelToTheView() {
        CodecaseSummariesResponseModel model = new CodecaseSummariesResponseModel();
        presenterSpy.responseModel = model;

        ParsedRequest request = new ParsedRequest("GET","blah");
        controller.handle(request);

        assertThat(viewSpy.generateViewWasCalled).isTrue();
        assertThat(viewSpy.responseModel).isEqualTo(presenterSpy.responseModel);
    }
}