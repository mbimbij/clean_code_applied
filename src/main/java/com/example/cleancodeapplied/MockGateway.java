package com.example.cleancodeapplied;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockGateway implements Gateway {

    private List<Codecast> codecasts = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Licence> licences = new ArrayList<>();

    public MockGateway() {
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
    public Codecast save(Codecast codecast) {
        codecasts.add((Codecast) establishId(codecast));
        return codecast;
    }

    @Override
    public User save(User user) {
        users.add((User) establishId(user));
        return user;
    }

    private Entity establishId(Entity user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        return user;
    }

    @Override
    public User findUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName))
                return user;
        }
        return null;
    }

    @Override
    public Codecast findCodecastByTitle(String codecastTitle) {
        return codecasts.stream()
                .filter(codecast -> codecast.getTitle().equals(codecastTitle))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Licence licence) {
        licences.add(licence);
    }

    @Override
    public List<Licence> findLicencesForUserAndCodecast(User user, Codecast codecast) {
        return licences.stream()
                .filter(licence -> licence.getUser().isSame(user) && licence.getCodecast().isSame(codecast))
                .collect(Collectors.toList());
    }
}
