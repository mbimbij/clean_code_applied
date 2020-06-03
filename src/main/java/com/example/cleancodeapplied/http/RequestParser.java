package com.example.cleancodeapplied.http;

public class RequestParser {
    public ParsedRequest parse(String requestString) {
        ParsedRequest parsedRequest = new ParsedRequest();
        String[] parts = requestString.split("\\s+");
        parsedRequest.method = parts[0];
        parsedRequest.path = parts.length > 1 ? parts[1] : "";
        return parsedRequest;
    }
}
