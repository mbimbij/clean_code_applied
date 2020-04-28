package com.example.cleancodeapplied;

import lombok.Data;

@Data
public class Codecast {
    private String title;
    private String published;

    public boolean isSame(Codecast codecast) {
        return true;
    }
}
