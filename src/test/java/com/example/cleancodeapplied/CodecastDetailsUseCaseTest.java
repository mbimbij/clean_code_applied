package com.example.cleancodeapplied;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

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
}