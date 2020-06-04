package com.example.cleancodeapplied.socketserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
        String response = new BufferedReader(new InputStreamReader(socket.getInputStream())).lines().collect(Collectors.joining("\n"));

        assertThat(response).isEqualTo("echo");
    }

    @Test
    void multipleEchos() throws IOException {
        server.start();
        Thread.yield();

        Socket socket1 = new Socket("localhost", port);
        Socket socket2 = new Socket("localhost", port);

        socket1.getOutputStream().write("echo1\n".getBytes(StandardCharsets.UTF_8));
        socket2.getOutputStream().write("echo2\n".getBytes(StandardCharsets.UTF_8));

        String response1 = new BufferedReader(new InputStreamReader(socket1.getInputStream())).readLine();
        String response2 = new BufferedReader(new InputStreamReader(socket2.getInputStream())).readLine();

        assertThat(response1).isEqualTo("echo1");
        assertThat(response2).isEqualTo("echo2");
    }

    public static class EchoSocketService extends TestSocketService {
        @Override
        protected void doService(Socket socket) throws IOException {
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
