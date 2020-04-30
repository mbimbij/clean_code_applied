package com.example.cleancodeapplied.socketserver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled
public class SocketServerTest {

    private FakeSocketService service;
    private SocketServer server;
    private int port;

    @BeforeEach
    void setUp() throws IOException {
        port = 8042;
        service = new FakeSocketService();
        server = new SocketServer(port, service);
    }

    @AfterEach
    void tearDown() throws IOException, InterruptedException {
        server.stop();
    }

    @Test
    void instantiate() {
        assertThat(server.getPort()).isEqualTo(port);
        assertThat(server.getService()).isEqualTo(service);
    }

    @Test
    void canStartAndStopServer() throws IOException, InterruptedException {
        server.start();
        assertThat(server.isReady()).isTrue();
        server.stop();
        assertThat(server.isReady()).isFalse();
    }

    @Test
    void acceptAnIncomingConnection() throws IOException, InterruptedException {
        server.start();
        Socket socket = new Socket("localhost", port);
        socket.close();
        server.stop();
        assertThat(service.connections).isEqualTo(1);
    }

    @Test
    void canSendAndReceiveData() throws IOException, InterruptedException {
        server.start();
        Socket socket = new Socket("localhost", port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello".getBytes());
        outputStream.flush();
        outputStream.close();
        service.readMessage();
        server.stop();
        assertThat(service.message).isEqualTo("hello");
    }

    public static class FakeSocketService implements SocketService {
        public int connections;
        private String message;
        Socket socket;

        @Override
        public void serve(Socket socket) {
            this.socket=socket;
            connections++;
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void readMessage() throws IOException {
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            message = bufferedReader.lines().collect(Collectors.joining("\n"));
        }
    }
}
