package com.example.cleancodeapplied;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThat(useCase.isLicencedToViewCodecast(user,codecast)).isFalse();
    }

    @Test
    void userWithViewLicence_canViewCodecast() {
        Licence viewLicence = new Licence(user, codecast);
        Context.gateway.save(viewLicence);
        Assertions.assertThat(useCase.isLicencedToViewCodecast(user, codecast)).isTrue();
    }

    @Test
    void userWithoutViewLicence_cannotViewOtherUsersCodecast() {
        User otherUser = new User("otherUser");
        Context.gateway.save(otherUser);

        Licence viewLicence = new Licence(user, codecast);
        Context.gateway.save(viewLicence);
        Assertions.assertThat(useCase.isLicencedToViewCodecast(otherUser, codecast)).isFalse();
    }
}