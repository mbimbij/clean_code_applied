package com.example.cleancodeapplied.http;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Controller> routes = new HashMap<>();

    public void addPath(String path, Controller controller) {
        routes.put(path, controller);
    }

    public void route(ParsedRequest request) {
        Controller controller = routes.get(request.path);
        controller.handle(request);
    }
}
