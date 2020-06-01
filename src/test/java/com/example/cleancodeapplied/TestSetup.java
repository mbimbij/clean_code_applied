package com.example.cleancodeapplied;

import com.example.cleancodeapplied.doubles.InMemoryCodecastGateway;
import com.example.cleancodeapplied.doubles.InMemoryLicenseGateway;
import com.example.cleancodeapplied.doubles.InMemoryUserGateway;

import java.time.ZonedDateTime;

import static com.example.cleancodeapplied.License.Type.VIEW;

public class TestSetup {

    public static void setupContext() {
        Context.codecastGateway = new InMemoryCodecastGateway();
        Context.licenseGateway = new InMemoryLicenseGateway();
        Context.userGateway = new InMemoryUserGateway();
        Context.gateKeeper = new GateKeeper();
    }

    public static void setupSampleData() {
        setupContext();

        User bob = new User("Bob");
        User micah = new User("Micah");

        Codecast episode1 = new Codecast("Episode 1 - The Beginning", ZonedDateTime.now().minusDays(1));
        Codecast episode2 = new Codecast("Episode 2 - The Continuation", ZonedDateTime.now());

        License bobEpisode1 = new License(VIEW, bob, episode1);
        License bobEpisode2 = new License(VIEW, bob, episode2);

        Context.userGateway.save(bob);
    }
}