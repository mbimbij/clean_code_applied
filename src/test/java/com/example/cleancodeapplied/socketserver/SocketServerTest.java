package com.example.cleancodeapplied.socketserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class SocketServerTest {

    private SocketServer server;
    private int port;

    @BeforeEach
    void setUp() throws IOException {
        port = 8042;
        echoService = new EchoSocketService();
        server = new SocketServer(port, echoService);
    }

    private EchoSocketService echoService;

    @AfterEach
    void tearDown() throws IOException, InterruptedException {
        server.stop();
    }

    @Test
    void canEcho() throws IOException, InterruptedException {
        server.start();
        Thread.yield();
        Socket socket = new Socket("localhost", port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("echo\n".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = bufferedReader.lines().collect(Collectors.joining("\n"));

        assertThat(response).isEqualTo("echo");
    }

    public static class EchoSocketService extends TestSocketService {
        @Override
        protected void doService() throws IOException {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//            String message = bufferedReader.lines().collect(Collectors.joining("\n"));
            String message = bufferedReader.readLine();

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes());
            outputStream.flush();
        }
    }
}
