package com.example.cleancodeapplied;

import com.example.cleancodeapplied.entities.Entity;

public class License extends Entity {
  public enum Type {DOWNLOAD, VIEW;}
  private Type type;

  private User user;
  private Codecast codecast;
  public License(Type type, User user, Codecast codecast) {
    this.type = type;
    this.user = user;
    this.codecast = codecast;
  }

  public Type getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  public Codecast getCodecast() {
    return codecast;
  }
}
