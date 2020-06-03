package com.example.cleancodeapplied.http;

import java.util.Objects;

public class ParsedRequest {
    public String method = "";
    public String path = "";

    public ParsedRequest() {
    }

    public ParsedRequest(String method, String path) {
        this.method = method;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedRequest request = (ParsedRequest) o;
        return Objects.equals(method, request.method) &&
                Objects.equals(path, request.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
