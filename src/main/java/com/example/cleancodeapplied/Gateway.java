package com.example.cleancodeapplied;

import java.util.List;

public interface Gateway {
    List<Codecast> findAllCodeCasts();

    void delete(Codecast codecast);

    void save(Codecast codecast);
}
