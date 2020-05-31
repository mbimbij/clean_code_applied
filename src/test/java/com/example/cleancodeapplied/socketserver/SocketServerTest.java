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

    private ClosingSocketService service;
    private SocketServer server;
    private int port;

    @BeforeEach
    void setUp() {
        port = 8042;
    }

    @Nested
    class TestsWithClosingSocketService {
        @BeforeEach
        void setUp() throws IOException {
            service = new ClosingSocketService();
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
            new Socket("localhost", port);
            synchronized (service) {
                service.wait();
            }
            server.stop();
            assertThat(service.connections).isEqualTo(1);
        }

        @Test
        void acceptMultipleIncomingConnections() throws IOException, InterruptedException {
            server.start();
            new Socket("localhost", port);
            synchronized (service) {
                service.wait();
            }
            new Socket("localhost", port);
            synchronized (service) {
                service.wait();
            }
            server.stop();
            assertThat(service.connections).isEqualTo(2);
        }

    }

    @Nested
    class TestsWithReadingSocketService {

        private ReadingSocketService readingService;

        @BeforeEach
        void setUp() throws IOException {
            readingService = new ReadingSocketService();
            server = new SocketServer(port, readingService);
        }

        @Test
        void canSendAndReceiveData() throws IOException, InterruptedException {
            server.start();
            Socket socket = new Socket("localhost", port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello".getBytes());
            outputStream.flush();
            outputStream.close();
            synchronized (readingService) {
                readingService.wait();
            }
            server.stop();
            assertThat(readingService.message).isEqualTo("hello");
        }
    }

    @Nested
    class TestsWithEchoSocketService {

        private EchoSocketService echoService;

        @BeforeEach
        void setUp() throws IOException {
            echoService = new EchoSocketService();
            server = new SocketServer(port, echoService);
        }

        @AfterEach
        void tearDown() throws IOException, InterruptedException {
            server.stop();
        }

        @Test
        void canEcho() throws IOException, InterruptedException {
            server.start();
            Socket socket = new Socket("localhost", port);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("echo\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
//            synchronized (echoService){
//                echoService.wait();
//            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = bufferedReader.lines().collect(Collectors.joining("\n"));

            assertThat(response).isEqualTo("echo");
        }
    }

    public static class ClosingSocketService extends TestSocketService {
        @Override
        protected void doService() {
            connections++;
        }
    }

    public static class ReadingSocketService extends TestSocketService {
        private String message;

        @Override
        protected void doService() throws IOException {
            connections++;
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            message = bufferedReader.lines().collect(Collectors.joining("\n"));
        }
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
