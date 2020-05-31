package com.example.cleancodeapplied.doubles;

import com.example.cleancodeapplied.User;
import com.example.cleancodeapplied.UserGateway;

public class InMemoryUserGateway extends GatewayUtilities<User> implements UserGateway {
  public User findUserByName(String username) {
    for (User user : getEntities()) {
      if (user.getUserName().equals(username))
        return user;
    }
    return null;
  }
}
