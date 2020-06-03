package com.example.cleancodeapplied.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RouterTest {
    private ParsedRequest actualRequest;
    private Router router;

    @BeforeEach
    void setUp() {
        router = new Router();
    }

    @Test
    void simplePath() {
        router.addPath("it", new TestController());
        ParsedRequest request = new ParsedRequest("GET", "/it");

        router.route(request);

        assertThat(actualRequest).isEqualToComparingFieldByField(request);
    }

    @Test
    void rootPath() {
        router.addPath("", new TestController());
        ParsedRequest request = new ParsedRequest("GET", "/");

        router.route(request);

        assertThat(actualRequest).isEqualToComparingFieldByField(request);
    }

    @Test
    void pathWithDynamicData() {
        router.addPath("a", new TestController());

        ParsedRequest request = new ParsedRequest("GET", "/a/b/c");
        router.route(request);

        assertThat(actualRequest).isEqualToComparingFieldByField(request);
    }

    class TestController implements Controller{
        @Override
        public String handle(ParsedRequest request) {
            actualRequest = request;
            return null;
        }
    }
}