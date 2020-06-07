package com.example.cleancodeapplied.http;

public interface Controller {
    String  handle(ParsedRequest request) throws Exception;
}
