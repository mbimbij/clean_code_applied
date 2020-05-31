package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;

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
            } catch (IOException e) {
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

    private String getFrontPage() {
        return "the front page";
    }

    public SocketServer getServer() {
        return server;
    }
}
