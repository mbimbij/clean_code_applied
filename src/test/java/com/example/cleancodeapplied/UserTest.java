package com.example.cleancodeapplied;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {
    @Test
    void twoDifferentUsersAreNotTheSame() {
        User u1 = new User("u1");
        User u2 = new User("u2");

        u1.setId("u1Id");
        u2.setId("u2Id");

        assertThat(u1.isSame(u2)).isFalse();
        assertThat(u2.isSame(u1)).isFalse();
    }

    @Test
    void oneUserIsSameAsItself() {
        User u1 = new User("u1");
        u1.setId("u1Id");

        assertThat(u1.isSame(u1)).isTrue();
    }

    @Test
    void usersWithTheSameIdAreTheSame() {
        User u1 = new User("u1");
        User u2 = new User("u2");

        u1.setId("u1Id");
        u2.setId("u1Id");

        assertThat(u1.isSame(u2)).isTrue();
    }

    @Test
    void userWithNullIdsAreNeverSame() {
        User u1 = new User("u1");
        User u2 = new User("u2");

        assertThat(u1.isSame(u2)).isFalse();
    }
}