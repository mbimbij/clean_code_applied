package com.example.cleancodeapplied.http;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RouterTest {
    private ParsedRequest actualRequest;

    @Test
    void simplePath() {
        Router router = new Router();
        router.addPath("it", new TestController());

        router.route(new ParsedRequest("GET", "it"));

        assertThat(actualRequest).isEqualTo(new ParsedRequest("GET", "it"));
    }

    class TestController implements Controller{
        @Override
        public void handle(ParsedRequest request) {
            actualRequest = request;
        }
    }
}