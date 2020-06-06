package com.example.cleancodeapplied.usecases.codecastSummaries;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.Utils;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static com.example.cleancodeapplied.entities.License.Type.DOWNLOAD;
import static com.example.cleancodeapplied.entities.License.Type.VIEW;
import static org.assertj.core.api.Assertions.assertThat;

class CodecastSummariesUseCaseTest {

    private User user;
    private Codecast codecast;
    private CodecastSummariesUseCase useCase;
    private String title = "codecast";
    private String permalink = "permalink";
    private ZonedDateTime publicationDate = ZonedDateTime.now();
    private CodecastSummariesOutputBoundarySpy presenterSpy;

    @BeforeEach
    void setUp() {
        TestSetup.setupContext();
        user = Context.userGateway.save(new User("user"));
        codecast = Context.codecastGateway.save(new Codecast(title, publicationDate, permalink));
        useCase = new CodecastSummariesUseCase();
        presenterSpy = new CodecastSummariesOutputBoundarySpy();
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
        useCase.summarizeCodecasts(user, presenterSpy);
        List<CodecastSummary> codecastSummaries = presenterSpy.responseModel.getCodecastSummaries();
        assertThat(codecastSummaries).isEmpty();
    }

    @Test
    void presentOneCodecast() {
        codecast.setTitle("some title");
        ZonedDateTime publicationDate = ZonedDateTime.now().plusDays(1);
        codecast.setPublicationDate(publicationDate);
        Context.codecastGateway.save(codecast);

        useCase.summarizeCodecasts(user,presenterSpy);
        assertThat(presenterSpy.responseModel.getCodecastSummaries()).hasSize(1);
        CodecastSummary codecastSummary = presenterSpy.responseModel.getCodecastSummaries().get(0);
        assertThat(codecastSummary.title).isEqualTo("some title");
        assertThat(codecastSummary.publicationDate).isEqualTo(publicationDate);
        assertThat(codecastSummary.permalink).isEqualTo(permalink);
    }

    @Test
    void presentedCodecastIsNotViewableIfNoLicence() {
        useCase.summarizeCodecasts(user, presenterSpy);
        CodecastSummary codecastSummary = presenterSpy.responseModel.getCodecastSummaries().get(0);
        assertThat(codecastSummary.isViewable).isFalse();
    }

    @Test
    void presentedCodecastIsViewable_ifLicenceExists() {
        Context.licenseGateway.save(new License(VIEW, user, codecast));
        useCase.summarizeCodecasts(user, presenterSpy);
        CodecastSummary codecastSummary = presenterSpy.responseModel.getCodecastSummaries().get(0);
        assertThat(codecastSummary.isViewable).isTrue();
    }

    @Test
    void presentedCodecastIsDownloadableIfDownloadLicenceExists() {
        License downloadLicence = new License(DOWNLOAD, user, codecast);
        Context.licenseGateway.save(downloadLicence);
        useCase.summarizeCodecasts(user, presenterSpy);
        CodecastSummary codecastSummary = presenterSpy.responseModel.getCodecastSummaries().get(0);
        assertThat(codecastSummary.isDownloadable).isTrue();
        assertThat(codecastSummary.isViewable).isFalse();
    }

    @Test
    void usecaseWiring() {
        useCase.summarizeCodecasts(user, presenterSpy);
        assertThat(presenterSpy.responseModel).isNotNull();
    }
}