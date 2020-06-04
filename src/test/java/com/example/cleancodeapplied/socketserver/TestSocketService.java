package com.example.cleancodeapplied.socketserver;

import java.io.IOException;
import java.net.Socket;

public abstract class TestSocketService implements SocketService {

    @Override
    public void serve(Socket socket) {
        try {
            doService(socket);
            synchronized (this) {
                notify();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract void doService(Socket socket) throws IOException;
}
