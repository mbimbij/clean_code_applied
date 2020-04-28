package com.example.cleancodeapplied;

public class Licence extends Entity {
    private final User user;
    private final Codecast codecast;

    public Licence(User user, Codecast codecast) {

        this.user = user;
        this.codecast = codecast;
    }

    public User getUser() {
        return user;
    }

    public Codecast getCodecast() {
        return codecast;
    }
}
