package com.example.cleancodeapplied;

public class Licence extends Entity {
    private Type type;
    private final User user;
    private final Codecast codecast;

    public Licence(Type type, User user, Codecast codecast) {
        this.type = type;
        this.user = user;
        this.codecast = codecast;
    }

    public User getUser() {
        return user;
    }

    public Codecast getCodecast() {
        return codecast;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        DOWNLOAD, VIEW
    }
}
