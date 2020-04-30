package com.example.cleancodeapplied;

import java.util.List;

public interface CodecastGateway {
  List<Codecast> findAllCodeCastsSortedByDateAsc();

  void delete(Codecast codecast);

  Codecast save(Codecast codecast);

  Codecast findCodecastByTitle(String codecastTitle);
}
