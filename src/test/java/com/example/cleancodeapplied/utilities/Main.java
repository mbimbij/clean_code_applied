package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.CodecastGateway;
import com.example.cleancodeapplied.doubles.InMemoryCodecastGateway;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;

import java.io.IOException;


public class Main {
    private SocketService mainService;
    private SocketServer server;
    private CodecastGateway codecastGateway;

    public Main() throws IOException {
        this.server = new SocketServer(8080, mainService);
        this.codecastGateway = new InMemoryCodecastGateway();
    }

    public SocketServer getServer() {
        return server;
    }

    public CodecastGateway getCodecastGateway() {
        return codecastGateway;
    }
}
