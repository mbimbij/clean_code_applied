package com.example.cleancodeapplied;

import java.util.List;
import java.util.stream.Collectors;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return Context.gateway.findAllCodeCasts().stream()
                .map(codecast -> new PresentableCodecast())
                .collect(Collectors.toList());
    }

    public boolean isLicencedToViewCodecast(User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        return !licences.isEmpty();
    }
}
