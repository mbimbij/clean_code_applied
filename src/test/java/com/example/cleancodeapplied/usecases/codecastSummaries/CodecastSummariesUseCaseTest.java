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

    @BeforeEach
    void setUp() {
        TestSetup.setupContext();
        user = Context.userGateway.save(new User("user"));
        codecast = Context.codecastGateway.save(new Codecast(title, publicationDate, permalink));
        useCase = new CodecastSummariesUseCase();
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
        List<CodecastSummariesResponseModel> presentableCodecasts = useCase.summarizeCodecasts(user);
        assertThat(presentableCodecasts).isEmpty();
    }

    @Test
    void presentOneCodecast() {
        codecast.setTitle("some title");
        ZonedDateTime publicationDate = ZonedDateTime.now().plusDays(1);
        codecast.setPublicationDate(publicationDate);
        Context.codecastGateway.save(codecast);
        List<CodecastSummariesResponseModel> presentableCodecasts = useCase.summarizeCodecasts(user);
        CodecastSummariesResponseModel presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.title).isEqualTo("some title");
        assertThat(presentableCodecast.publicationDate).isEqualTo(publicationDate.format(Utils.DATE_FORMAT));
        assertThat(presentableCodecast.permalink).isEqualTo(permalink);
        assertThat(presentableCodecasts).hasSize(1);
    }

    @Test
    void presentedCodecastIsNotViewableIfNoLicence() {
        List<CodecastSummariesResponseModel> presentableCodecasts = useCase.summarizeCodecasts(user);
        CodecastSummariesResponseModel presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.isViewable).isFalse();
    }

    @Test
    void presentedCodecastIsViewableIfLicenceExists() {
        Context.licenseGateway.save(new License(VIEW, user, codecast));
        List<CodecastSummariesResponseModel> presentableCodecasts = useCase.summarizeCodecasts(user);
        CodecastSummariesResponseModel presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.isViewable).isTrue();
    }

    @Test
    void presentedCodecastIsDownloadableIfDownloadLicenceExists() {
        License downloadLicence = new License(DOWNLOAD, user, codecast);
        Context.licenseGateway.save(downloadLicence);
        List<CodecastSummariesResponseModel> presentableCodecasts = useCase.summarizeCodecasts(user);
        CodecastSummariesResponseModel presentableCodecast = presentableCodecasts.get(0);
        assertThat(presentableCodecast.isDownloadable).isTrue();
        assertThat(presentableCodecast.isViewable).isFalse();
    }

    @Test
    void usecaseWiring() {
        CodecastSummariesOutputBoundarySpy presenterSpy = new CodecastSummariesOutputBoundarySpy();
        useCase.summarizeCodecasts(user,presenterSpy);
        assertThat(presenterSpy.responseModel).isNotNull();
    }
}