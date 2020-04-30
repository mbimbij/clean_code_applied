package com.example.cleancodeapplied.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    private final int port;
    private final SocketService service;
    private boolean running;

    public SocketServer(int port, SocketService service) {
        this.port = port;
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public SocketService getService() {
        return service;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket serviceSocket = serverSocket.accept();
        running = true;
    }

    public boolean isReady() {
        return running;
    }

    public void stop() {
        running = false;
    }
}
