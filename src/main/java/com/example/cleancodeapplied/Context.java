package com.example.cleancodeapplied;

import com.example.cleancodeapplied.gateways.CodecastGateway;
import com.example.cleancodeapplied.gateways.LicenseGateway;
import com.example.cleancodeapplied.gateways.UserGateway;

public class Context {
  public static UserGateway userGateway;
  public static CodecastGateway codecastGateway;
  public static LicenseGateway licenseGateway;
  public static GateKeeper gateKeeper;
}
