package com.example.cleancodeapplied;

import java.util.List;

public interface Gateway {
    List<Codecast> findAllCodeCasts();

    void delete(Codecast codecast);

    Codecast save(Codecast codecast);

    User save(User user);

    User findUser(String username);

    Codecast findCodecastByTitle(String codecastTitle);

    void save(Licence licence);

    List<Licence> findLicencesForUserAndCodecast(User user, Codecast codecast);
}
