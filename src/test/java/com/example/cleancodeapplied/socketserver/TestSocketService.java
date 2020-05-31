package com.example.cleancodeapplied.socketserver;

import java.io.IOException;
import java.net.Socket;

public abstract class TestSocketService implements SocketService {
    protected Socket socket;
    protected int connections;

    @Override
    public void serve(Socket socket) {
        this.socket=socket;
        try {
            doService();
            synchronized (this) {
                notify();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void doService() throws IOException;
}
