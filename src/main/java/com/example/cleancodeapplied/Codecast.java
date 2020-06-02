package com.example.cleancodeapplied;

import com.example.cleancodeapplied.entities.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Codecast extends Entity {
    private String title;
    private ZonedDateTime publicationDate;
    private String permalink;
}
