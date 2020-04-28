package com.example.cleancodeapplied;

import java.util.List;
import java.util.stream.Collectors;

public class PresentCodecastUseCase {
    public List<PresentableCodecast> presentCodecasts(User loggedInUser) {
        return Context.gateway.findAllCodeCasts().stream()
                .map(codecast -> {
                    PresentableCodecast presentableCodecast = new PresentableCodecast();
                    presentableCodecast.viewable=isLicencedToViewCodecast(loggedInUser,codecast);
                    return presentableCodecast;
                })
                .collect(Collectors.toList());
    }

    public boolean isLicencedToViewCodecast(User user, Codecast codecast) {
        List<Licence> licences = Context.gateway.findLicencesForUserAndCodecast(user, codecast);
        return !licences.isEmpty();
    }
}
