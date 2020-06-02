package com.example.cleancodeapplied.usecases.codecastDetails;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.usecases.codecastDetails.CodecastDetailsUseCase;
import com.example.cleancodeapplied.usecases.codecastDetails.PresentableCodecastDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.*;

class CodecastDetailsUseCaseTest {

    private User user;

    @BeforeEach
    void setUp() {
        TestSetup.setupContext();
        user = Context.userGateway.save(new User("user"));
    }

    @Test
    void createCodecastDetailsPresentation() {
        String permalink = "permalink-a";
        String title = "codecast";
        String expectedDate = "22/04/2015";
        ZonedDateTime publicationDate = LocalDate.parse("2015-04-22", DateTimeFormatter.ISO_DATE)
                .atStartOfDay(ZoneId.systemDefault());
        Codecast codecast = new Codecast(title, publicationDate, permalink);
        Context.codecastGateway.save(codecast);
        CodecastDetailsUseCase useCase = new CodecastDetailsUseCase();
        PresentableCodecastDetails codecastDetails = useCase.getCodecastDetails(user, permalink);

        assertThat(codecastDetails.title).isEqualTo(title);
        assertThat(codecastDetails.publicationDate).isEqualTo(expectedDate);
    }

    @Test
    void doesntCrashOnMissingCodecast() {
        CodecastDetailsUseCase useCase = new CodecastDetailsUseCase();
//        assertThatThrownBy(() -> useCase.getCodecastDetails(user, "missing")).doesNotThrowAnyException();
        PresentableCodecastDetails codecastDetails = useCase.getCodecastDetails(user, "missing");
        assertThat(codecastDetails.wasFound).isFalse();
    }
}