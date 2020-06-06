package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.http.ParsedRequest;
import com.example.cleancodeapplied.http.RequestParser;
import com.example.cleancodeapplied.http.Router;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesController;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesPresenter;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesUseCase;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesViewImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


public class Main {
    private SocketServer server;
    private RequestParser requestParser = new RequestParser();
    private static Router router = new Router();

    public static void main(String[] args) throws IOException {
        CodecastSummariesUseCase usecase = new CodecastSummariesUseCase();
        CodecastSummariesPresenter presenter = new CodecastSummariesPresenter();
        CodecastSummariesViewImpl view = new CodecastSummariesViewImpl();
        CodecastSummariesController controller = new CodecastSummariesController(usecase, presenter, view);
        router.addPath("", controller);
//        router.addPath("episode", new CodecastDetailsController());

        TestSetup.setupSampleData();
        Main main = new Main();
    }

    public Main() throws IOException {
        SocketService mainService = socket -> {
            try {
                String browserRequest = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
                System.out.println("received : "+browserRequest);
                ParsedRequest parsedRequest = requestParser.parse(browserRequest);
                String response = router.route(parsedRequest);
                socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        server = new SocketServer(8080, mainService);
        server.start();
    }

    public SocketServer getServer() {
        return server;
    }
}
