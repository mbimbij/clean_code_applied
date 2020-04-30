package com.example.cleancodeapplied.socketserver;

import java.net.Socket;

public interface SocketService {
    void serve(Socket socket);
}
