package com.example.cleancodeapplied;

import java.util.ArrayList;
import java.util.List;

public class MockGateway implements Gateway {

    private ArrayList<Codecast> codecasts;

    public MockGateway() {
        codecasts = new ArrayList<>();
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
}
