package com.example.cleancodeapplied.gateways;

import com.example.cleancodeapplied.entities.Codecast;

import java.util.List;

public interface CodecastGateway {
    List<Codecast> findAllCodeCastsSortedByDateAsc();

    void delete(Codecast codecast);

    Codecast save(Codecast codecast);

    Codecast findCodecastByTitle(String codecastTitle);

    Codecast findCodecastByPermalink(String permalink);
}
