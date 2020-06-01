package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.PresentCodecastUseCase;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;
import com.example.cleancodeapplied.view.ViewTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class Main {
    private SocketServer server;

    public static void main(String[] args) throws IOException {
        TestSetup.setupContext();
        Main main = new Main();
    }

    public Main() throws IOException {
        SocketService mainService = socket -> {
            try {
                String frontPage = getFrontPage();
                String response = makeResponse(frontPage);
                socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        server = new SocketServer(8080, mainService);
        server.start();
    }

    private String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                String.format("Content-Length: %d\n\n", content.length()) +
                content;
    }

    private String getFrontPage() throws Exception {

//        PresentCodecastUseCase useCase =

        ViewTemplate frontpageTemplate = ViewTemplate.fromClasspathResource("html/frontpage.html");
        ViewTemplate codecastTemplate = ViewTemplate.fromClasspathResource("html/codecast.html");

        codecastTemplate.replace("title", "Episode 1: The Beginning!");

        frontpageTemplate.replace("codecasts", codecastTemplate.getHtml());
        return frontpageTemplate.getHtml();
    }

    public SocketServer getServer() {
        return server;
    }
}
