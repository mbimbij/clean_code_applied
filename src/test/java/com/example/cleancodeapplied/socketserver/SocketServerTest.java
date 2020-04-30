package com.example.cleancodeapplied.socketserver;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SocketServerTest {
    @Test
    void instantiate() {
        int port = 8080;
        SocketService service = new FakeSocketService();
        SocketServer server = new SocketServer(port, service);
        assertThat(server.getPort()).isEqualTo(port);
        assertThat(server.getService()).isEqualTo(service);
    }

    @Test
    void canStartAndStopServer() {
        SocketService service = new FakeSocketService();
        SocketServer server = new SocketServer(8080, service);
        server.start();
        assertThat(server.isReady()).isTrue();
        server.stop();
        assertThat(server.isReady()).isFalse();
    }
}
