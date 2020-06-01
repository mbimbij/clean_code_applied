package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.*;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;
import com.example.cleancodeapplied.view.ViewTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Main {
    private SocketServer server;

    public static void main(String[] args) throws IOException {
        TestSetup.setupSampleData();
        Main main = new Main();
    }

    public Main() throws IOException {
        SocketService mainService = socket -> {
            try {
                String frontPage = getFrontPage();
                String response = makeResponse(frontPage);
                socket.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        server = new SocketServer(8080, mainService);
        server.start();
    }

    private String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                String.format("Content-Length: %d\n\n", content.length()) +
                content;
    }

    private String getFrontPage() throws Exception {

        CodecastSummaryUseCase useCase = new CodecastSummaryUseCase();
        User micah = Context.userGateway.findUserByName("Micah");
        List<PresentableCodecastSummary> presentableCodecasts = useCase.presentCodecasts(micah);

        ViewTemplate frontpageTemplate = ViewTemplate.fromClasspathResource("html/frontpage.html");

        StringBuilder codecastLines = new StringBuilder();
        for (PresentableCodecastSummary presentableCodecast : presentableCodecasts){
            ViewTemplate codecastTemplate = ViewTemplate.fromClasspathResource("html/codecast.html");
            codecastTemplate.replace("title", presentableCodecast.title);
            codecastTemplate.replace("publicationDate", presentableCodecast.publicationDate);
            codecastTemplate.replace("thumbnail", "https://cleancoders.com/images/portraits/robert-martin.jpg");
            codecastTemplate.replace("author", "Uncle Bob");
            codecastTemplate.replace("duration", "1:00:00");
            codecastTemplate.replace("licenseOptions", "buying options go here");
            codecastTemplate.replace("contentActions", "");
            codecastLines.append(codecastTemplate.getHtml());
        }

        frontpageTemplate.replace("codecasts", codecastLines.toString());
        return frontpageTemplate.getHtml();
    }

    public SocketServer getServer() {
        return server;
    }
}
