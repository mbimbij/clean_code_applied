package com.example.cleancodeapplied.doubles;

import com.example.cleancodeapplied.Codecast;
import com.example.cleancodeapplied.CodecastGateway;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryCodecastGateway extends GatewayUtilities<Codecast> implements CodecastGateway {
  public List<Codecast> findAllCodeCastsSortedByDateAsc()
  {
    List<Codecast> sortedCodecasts = new ArrayList<Codecast>(getEntities());
    sortedCodecasts.sort(Comparator.comparing(Codecast::getPublicationDate));
    return sortedCodecasts;
  }

  public Codecast findCodecastByTitle(String codecastTitle) {
    for (Codecast codecast : getEntities())
      if (codecast.getTitle().equals(codecastTitle))
        return codecast;
    return null;
  }
}
