package com.example.cleancodeapplied;

public class User extends Entity {
  private String userName;

  public User(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

}
