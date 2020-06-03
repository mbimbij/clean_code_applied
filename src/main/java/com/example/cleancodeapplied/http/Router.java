package com.example.cleancodeapplied.http;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Controller> routes = new HashMap<>();

    public void addPath(String path, Controller controller) {
        routes.put(path, controller);
    }

    public void route(ParsedRequest request) {
        String[] parts = request.path.split("/");
        String controllerKey = parts[1];
        Controller controller = routes.get(controllerKey);
        controller.handle(request);
    }
}
