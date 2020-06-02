package com.example.cleancodeapplied.doubles;

import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.gateways.CodecastGateway;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InMemoryCodecastGateway extends GatewayUtilities<Codecast> implements CodecastGateway {
    public List<Codecast> findAllCodeCastsSortedByDateAsc() {
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

    @Override
    public Codecast findCodecastByPermalink(String permalink) {
        for (Codecast codecast : getEntities())
            if (codecast.getPermalink().equals(permalink))
                return codecast;
        return null;
    }
}
