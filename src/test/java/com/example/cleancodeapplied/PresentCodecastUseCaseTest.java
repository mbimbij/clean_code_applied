package com.example.cleancodeapplied;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.cleancodeapplied.License.Type.*;
import static org.assertj.core.api.Assertions.assertThat;

class PresentCodecastUseCaseTest {

    private User user;
    private Codecast codecast;
    private PresentCodecastUseCase useCase;

    @BeforeEach
    void setUp() {
        TestSetup.setupContext();
        user = Context.userGateway.save(new User("user"));
        codecast = Context.codecastGateway.save(new Codecast("codecast",ZonedDateTime.now()));
        useCase = new PresentCodecastUseCase();
    }

    @Test
    void userWithoutViewLicence_cannotViewCodecast() {
        assertThat(useCase.isLicensedFor(VIEW, user, codecast)).isFalse();
    }

    @Test
    void userWithViewLicence_canViewCodecast() {
        License viewLicence = new License(VIEW, user, codecast);
        Context.licenseGateway.save(viewLicence);
        assertThat(useCase.isLicensedFor(VIEW, user, codecast)).isTrue();
    }

    @Test
    void userWithoutViewLicence_cannotViewOtherUsersCodecast() {
        User otherUser = new User("otherUser");
        Context.userGateway.save(otherUser);

        License viewLicence = new License(VIEW, user, codecast);
        Context.licenseGateway.save(viewLicence);
        assertThat(useCase.isLicensedFor(VIEW, otherUser, codecast)).isFalse();
    }

    @Test
    void presentingNoCodecasts() {
        Context.codecastGateway.delete(codecast);
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        assertThat(presentableCodecasts).isEmpty();
    }

    @Test
    void presentOneCodecast() {
        codecast.setTitle("some title");
        ZonedDateTime publicationDate = ZonedDateTime.now().plusDays(1);
        codecast.setPublicationDate(publicationDate);
        Context.codecastGateway.save(codecast);
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
        assertThat(presentableCodecast.isViewable).isFalse();
    }

    @Test
    void presentedCodecastIsViewableIfLicenceExists() {
        Context.licenseGateway.save(new License(VIEW, user, codecast));
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.isViewable).isTrue();
    }

    @Test
    void presentedCodecastIsDownloadableIfDownloadLicenceExists() {
        License downloadLicence = new License(DOWNLOAD, user, codecast);
        Context.licenseGateway.save(downloadLicence);
        List<PresentableCodecast> presentableCodecasts = useCase.presentCodecasts(user);
        PresentableCodecast presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.isDownloadable).isTrue();
        assertThat(presentableCodecast.isViewable).isFalse();
    }
}