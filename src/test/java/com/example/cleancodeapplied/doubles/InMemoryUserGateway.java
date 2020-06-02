package com.example.cleancodeapplied.doubles;

import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.gateways.UserGateway;

public class InMemoryUserGateway extends GatewayUtilities<User> implements UserGateway {
  public User findUserByName(String username) {
    for (User user : getEntities()) {
      if (user.getUserName().equals(username))
        return user;
    }
    return null;
  }
}
