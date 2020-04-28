package com.example.cleancodeapplied;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PresentCodecastUseCaseTest {

    private User user;
    private Codecast codecast;
    private PresentCodecastUseCase useCase;

    @BeforeEach
    void setUp() {
        Context.gateway = new MockGateway();
        user = Context.gateway.save(new User("user"));
        codecast = Context.gateway.save(new Codecast());
        useCase = new PresentCodecastUseCase();
    }

    @Test
    void userWithoutViewLicence_cannotViewCodecast() {
        assertThat(useCase.isLicencedToViewCodecast(user, codecast)).isFalse();
    }

    @Test
    void userWithViewLicence_canViewCodecast() {
        Licence viewLicence = new Licence(user, codecast);
        Context.gateway.save(viewLicence);
        assertThat(useCase.isLicencedToViewCodecast(user, codecast)).isTrue();
    }

    @Test
    void userWithoutViewLicence_cannotViewOtherUsersCodecast() {
        User otherUser = new User("otherUser");
        Context.gateway.save(otherUser);

        Licence viewLicence = new Licence(user, codecast);
        Context.gateway.save(viewLicence);
        assertThat(useCase.isLicencedToViewCodecast(otherUser, codecast)).isFalse();
    }

    @Test
    void presentingNoCodecasts() {
        Context.gateway.delete(codecast);
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        assertThat(presentableCodecasts).isEmpty();
    }

    @Test
    void presentOneCodecast() {
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        assertThat(presentableCodecasts).hasSize(1);
    }

    @Test
    void presentedCodecastIsNotViewableIfNoLicence() {
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.viewable).isFalse();
    }

    @Test
    void presentedCodecastIsViewableIfLicenceExists() {
        Context.gateway.save(new Licence(user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.viewable).isTrue();
    }
}