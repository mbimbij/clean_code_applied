package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.entities.User;

public class CodecastSummaryInputBoundarySpy implements CodecastSummaryInputBoundary {
    public boolean summarizeCodecastsWasCalled = false;
    public User requestedUser;

    @Override
    public void summarizeCodecasts(User loggedInUser) {
        summarizeCodecastsWasCalled = true;
        requestedUser = loggedInUser;
    }
}
