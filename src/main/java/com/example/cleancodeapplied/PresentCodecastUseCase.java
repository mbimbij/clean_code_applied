package com.example.cleancodeapplied;

import java.util.ArrayList;
import java.util.List;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return new ArrayList<>();
    }

    public boolean isLicencedToViewCodecast(User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        return !licences.isEmpty();
    }
}
