package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.entities.User;

public interface CodecastSummariesInputBoundary {
    void summarizeCodecasts(User loggedInUser, CodecastSummariesOutputBoundary presenter);
}
