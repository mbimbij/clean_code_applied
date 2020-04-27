package com.example.cleancodeapplied;

import java.util.List;

public interface Gateway {
    List<Codecast> findAllCodeCasts();

    void delete(Codecast codecast);

    void save(Codecast codecast);

    void save(User user);

    User findUser(String username);
}
