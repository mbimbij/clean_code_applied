package com.example.cleancodeapplied.gateways;

import com.example.cleancodeapplied.entities.User;

public interface UserGateway {
  User save(User user);

  User findUserByName(String username);
}
