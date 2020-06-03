package com.example.cleancodeapplied.http;

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
                .map(controller -> controller.handle(request))
                .orElse("");
    }
}
