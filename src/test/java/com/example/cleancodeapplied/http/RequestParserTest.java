package com.example.cleancodeapplied.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestParserTest {
    @Test
    void emptyRequest() {
        RequestParser requestParser = new RequestParser();
        ParsedRequest parsedRequest = requestParser.parse("");
        assertThat(parsedRequest.method).isEqualTo("");
        assertThat(parsedRequest.path).isEqualTo("");
    }

    @Test
    void requestNonEmptyRequest() {
        RequestParser requestParser = new RequestParser();
        ParsedRequest parsedRequest = requestParser.parse("GET /foo/bar HTTP1.1");
        assertThat(parsedRequest.method).isEqualTo("GET");
        assertThat(parsedRequest.path).isEqualTo("/foo/bar");
    }
}
