package com.example.cleancodeapplied.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SocketServer {
    private final int port;
    private final SocketService service;
    private boolean running;
    private ServerSocket serverSocket;
    private ExecutorService executor;

    public SocketServer(int port, SocketService service) throws IOException {
        this.port = port;
        this.service = service;
        serverSocket = new ServerSocket(this.port);
        executor = Executors.newFixedThreadPool(4);
    }

    public int getPort() {
        return port;
    }

    public SocketService getService() {
        return service;
    }

    public void start() throws IOException, InterruptedException {
        Runnable connectionHandler = () -> {
            try {
                while (running){
                    Socket serviceSocket = serverSocket.accept();
                    executor.execute(() -> service.serve(serviceSocket));
                }
            } catch (IOException e) {
                if (running)
                    e.printStackTrace();
            }
        };
        executor.execute(connectionHandler);
        running = true;
    }

    public boolean isReady() {
        return running;
    }

    public void stop() throws IOException, InterruptedException {
        serverSocket.close();
        running = false;
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
    }
}
