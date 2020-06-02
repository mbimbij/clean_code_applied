package com.example.cleancodeapplied.entities;

import com.example.cleancodeapplied.entities.Entity;

public class User extends Entity {
  private String userName;

  public User(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

}
