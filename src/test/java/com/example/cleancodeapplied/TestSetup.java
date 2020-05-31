package com.example.cleancodeapplied;

import com.example.cleancodeapplied.doubles.InMemoryCodecastGateway;
import com.example.cleancodeapplied.doubles.InMemoryLicenseGateway;
import com.example.cleancodeapplied.doubles.InMemoryUserGateway;

public class TestSetup {

    public static void setupContext() {
        Context.codecastGateway = new InMemoryCodecastGateway();
        Context.licenseGateway = new InMemoryLicenseGateway();
        Context.userGateway = new InMemoryUserGateway();
        Context.gateKeeper = new GateKeeper();
    }
}