package com.example.cleancodeapplied.gateways;

import com.example.cleancodeapplied.entities.Codecast;
import com.example.cleancodeapplied.entities.License;
import com.example.cleancodeapplied.entities.User;

import java.util.List;

public interface LicenseGateway {
  License save(License license);

  List<License> findLicensesForUserAndCodecast(User user, Codecast codecast);
}
