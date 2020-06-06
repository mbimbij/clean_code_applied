package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.entities.User;

public class CodecastSummariesInputBoundarySpy implements CodecastSummariesInputBoundary {
    public boolean summarizeCodecastsWasCalled = false;
    public User requestedUser;
    public CodecastSummariesOutputBoundary outputBoundary;

    @Override
    public void summarizeCodecasts(User loggedInUser, CodecastSummariesOutputBoundary presenter) {
        summarizeCodecastsWasCalled = true;
        requestedUser = loggedInUser;
        outputBoundary = presenter;
    }
}
