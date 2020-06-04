package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.http.ParsedRequest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodecastSummariesControllerTest {
    @Test
    void testInputBoundaryInvocation() {
        TestSetup.setupSampleData();

        CodecastSummaryInputBoundarySpy codecastSummaryInputBoundary = new CodecastSummaryInputBoundarySpy();
        CodecastSummariesController controller = new CodecastSummariesController(codecastSummaryInputBoundary);

        ParsedRequest request = new ParsedRequest("GET","blah");
        controller.handle(request);

        assertThat(codecastSummaryInputBoundary.summarizeCodecastsWasCalled).isTrue();
        User loggedInUser = Context.userGateway.findUserByName("Bob");
        assertThat(codecastSummaryInputBoundary.requestedUser.getId()).isEqualTo(loggedInUser.getId());
        CodecastSummaryOutputBoundary outputBoundary = codecastSummaryInputBoundary.outputBoundary;
        assertThat(outputBoundary).isNotNull();
    }
}