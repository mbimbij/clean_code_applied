package com.example.cleancodeapplied;

import java.util.ArrayList;
import java.util.List;

public class MockGateway implements Gateway {

    private List<Codecast> codecasts;
    private List<User> users;

    public MockGateway() {
        codecasts = new ArrayList<>();
        users = new ArrayList<>();
    }

    @Override
    public List<Codecast> findAllCodeCasts() {
        return codecasts;
    }

    @Override
    public void delete(Codecast codecast) {
        codecasts.remove(codecast);
    }

    @Override
    public void save(Codecast codecast) {
        codecasts.add(codecast);
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User findUser(String username) {
        for (User user : users) {
            if(user.getUserName().equals(username))
                return user;
        }
        return null;
    }
}
