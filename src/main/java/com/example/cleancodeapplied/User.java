package com.example.cleancodeapplied;

import java.util.Objects;

public class User {
    private String userName;
    private String id;

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isSame(User user) {
        return id != null && Objects.equals(id, user.getId());
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
