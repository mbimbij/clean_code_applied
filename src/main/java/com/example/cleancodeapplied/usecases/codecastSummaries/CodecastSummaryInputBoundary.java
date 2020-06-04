package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.entities.User;

public interface CodecastSummaryInputBoundary {
    void summarizeCodecasts(User loggedInUser);
}
