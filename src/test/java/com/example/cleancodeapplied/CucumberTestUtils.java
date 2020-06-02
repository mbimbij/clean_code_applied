package com.example.cleancodeapplied;

import com.example.cleancodeapplied.entities.Codecast;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

public class CucumberTestUtils {
    private ObjectMapper objectMapper = new ObjectMapper();
    @DataTableType
    public Codecast codecast(Map<String, String> entry) {
        return new Codecast(entry.get("title"),
                LocalDate.parse(
                        entry.get("publicationDate"),
                        Utils.DATE_FORMAT).atStartOfDay(ZoneId.systemDefault()),
                entry.get("permalink"));
    }

    @Given("codecasts")
    public void codecasts(List<Codecast> codecasts) {
        for (Codecast codecast : codecasts) {
            Context.codecastGateway.save(codecast);
        }
    }

}
