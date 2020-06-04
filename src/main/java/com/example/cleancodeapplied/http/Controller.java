package com.example.cleancodeapplied.http;

public interface Controller {
    String handle(ParsedRequest request);

    static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                String.format("Content-Length: %d\n\n", content.length()) +
                content;
    }
}
