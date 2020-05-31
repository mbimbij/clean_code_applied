package com.example.cleancodeapplied.doubles;

import com.example.cleancodeapplied.Codecast;
import com.example.cleancodeapplied.License;
import com.example.cleancodeapplied.LicenseGateway;
import com.example.cleancodeapplied.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryLicenseGateway extends GatewayUtilities<License> implements LicenseGateway {
  public List<License> findLicensesForUserAndCodecast(User user, Codecast codecast) {
    List<License> results = new ArrayList<License>();
    for (License license : getEntities()) {
      if (license.getUser().isSame(user) && license.getCodecast().isSame(codecast))
        results.add(license);
    }
    return results;
  }
}
