package com.example.cleancodeapplied;

import com.example.cleancodeapplied.doubles.InMemoryCodecastGateway;
import com.example.cleancodeapplied.doubles.InMemoryLicenseGateway;
import com.example.cleancodeapplied.doubles.InMemoryUserGateway;
import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;

import java.time.ZonedDateTime;

import static com.example.cleancodeapplied.entities.License.Type.VIEW;

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

        Codecast episode1 = new Codecast("Episode 1 - The Beginning", ZonedDateTime.now().minusDays(2), "episode-1");
        Codecast episode2 = new Codecast("Episode 2 - The Continuation", ZonedDateTime.now().minusDays(1), "episode-2");
        Codecast episode3 = new Codecast("Episode 3 - The Epilogue", ZonedDateTime.now(), "episode-3");

        License bobEpisode1 = new License(VIEW, bob, episode1);
        License bobEpisode2 = new License(VIEW, bob, episode2);
        License micahEpisode2 = new License(VIEW, micah, episode2);
        License micahEpisode3 = new License(VIEW, micah, episode3);

        Context.userGateway.save(bob);
        Context.userGateway.save(micah);

        Context.codecastGateway.save(episode1);
        Context.codecastGateway.save(episode2);
        Context.codecastGateway.save(episode3);

        Context.licenseGateway.save(bobEpisode1);
        Context.licenseGateway.save(bobEpisode2);
        Context.licenseGateway.save(micahEpisode2);
        Context.licenseGateway.save(micahEpisode3);

        Context.gateKeeper.setLoggedInUser(bob);
    }
}