package com.example.cleancodeapplied;

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
