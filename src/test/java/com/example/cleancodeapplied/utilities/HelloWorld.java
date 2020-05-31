package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

public class HelloWorld implements SocketService {
    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer(8080, new HelloWorld());
        server.start();
    }

    @Override
    public void serve(Socket socket) {
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            String content = "<h1> Hello, World! </h1>";
            String httpResponse =
                    "HTTP/1.1 200 OK\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    String.format("Content-Length: %d\n",content.length()) +
                    "\n" +
                    content;
            outputStream.write(httpResponse.getBytes(StandardCharsets.UTF_8));
//            outputStream.flush();
            System.out.println("called at "+ ZonedDateTime.now());
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
