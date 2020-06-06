package com.example.cleancodeapplied.http;

import com.example.cleancodeapplied.Utils;
import io.vavr.control.Try;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Router {
    private Map<String, Controller> routes = new HashMap<>();

    public void addPath(String path, Controller controller) {
        routes.put(path, controller);
    }

    public String route(ParsedRequest request) {
        String[] parts = request.path.split("/");
        String controllerKey = parts.length > 1 ? parts[1] : "";
        return Optional.ofNullable(routes.get(controllerKey))
                .map(controller -> Try.of(() -> controller.handle(request)))
                .map(tried -> tried.map(Utils::addHttpSuccessHeader))
                .map(tried -> tried.getOrElse("http/1.1 503 server error\n\n503 server error"))
                .orElse("HTTP/1.1 404 Not Found\n\n404 Not Found");
    }
}
