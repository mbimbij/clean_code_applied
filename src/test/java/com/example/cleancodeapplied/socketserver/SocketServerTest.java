package com.example.cleancodeapplied.socketserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class SocketServerTest {

    private FakeSocketService service;
    private SocketServer server;
    private int port;

    @BeforeEach
    void setUp() {
        port = 8042;
        service = new FakeSocketService();
        server = new SocketServer(port, service);
    }

    @Test
    void instantiate() {
        assertThat(server.getPort()).isEqualTo(port);
        assertThat(server.getService()).isEqualTo(service);
    }

    @Test
    void canStartAndStopServer() throws IOException {
        server.start();
        assertThat(server.isReady()).isTrue();
        server.stop();
        assertThat(server.isReady()).isFalse();
    }

    @Test
    void acceptAnIncomingConnection() throws IOException {
        server.start();
        Socket socket = new Socket("localhost",port);
        server.stop();
        assertThat(service.connections).isEqualTo(1);
    }

    public static class FakeSocketService implements SocketService {
        public int connections;
    }
}
