package com.example.cleancodeapplied;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        codecast = Context.gateway.save(new Codecast("codecast",ZonedDateTime.now()));
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
        codecast.setTitle("some title");
        ZonedDateTime publicationDate = ZonedDateTime.now().plusDays(1);
        codecast.setPublicationDate(publicationDate);
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.title).isEqualTo("some title");
        assertThat(presentableCodecast.publicationDate).isEqualTo(publicationDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
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

    @Test
    void presentedCodecastIsDownloadableIfDownloadLicenceExists() {
        Licence downloadLicence = new DownloadLicence(user, codecast);
        Context.gateway.save(downloadLicence);
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.downloadable).isTrue();
    }
}