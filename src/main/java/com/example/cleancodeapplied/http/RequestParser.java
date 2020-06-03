package com.example.cleancodeapplied.http;

import org.apache.commons.lang3.StringUtils;

public class RequestParser {
    public ParsedRequest parse(String requestString) {
        ParsedRequest parsedRequest = new ParsedRequest();
        if(!StringUtils.isBlank(requestString)){
            String[] parts = requestString.split("\\s+");
            parsedRequest.method = parts[0];
            parsedRequest.path = parts.length > 1 ? parts[1] : "";
        }
        return parsedRequest;
    }
}
