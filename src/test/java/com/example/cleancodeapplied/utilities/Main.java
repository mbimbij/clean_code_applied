package com.example.cleancodeapplied.utilities;

import com.example.cleancodeapplied.Context;
import com.example.cleancodeapplied.TestSetup;
import com.example.cleancodeapplied.entities.User;
import com.example.cleancodeapplied.http.Controller;
import com.example.cleancodeapplied.http.ParsedRequest;
import com.example.cleancodeapplied.http.RequestParser;
import com.example.cleancodeapplied.http.Router;
import com.example.cleancodeapplied.socketserver.SocketServer;
import com.example.cleancodeapplied.socketserver.SocketService;
import com.example.cleancodeapplied.usecases.codecastSummaries.CodecastSummariesUseCase;
import com.example.cleancodeapplied.usecases.codecastSummaries.PresentableCodecastSummary;
import com.example.cleancodeapplied.view.ViewTemplate;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class Main {
    private SocketServer server;
    private RequestParser requestParser = new RequestParser();
    private static Router router = new Router();

    public static void main(String[] args) throws IOException {
        router.addPath("", new CodecastSummariesController());
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

    static class CodecastSummariesController implements Controller{
        @Override
        public String handle(ParsedRequest request) {
            try {
                String frontPage = getFrontPage();
                return makeResponse(frontPage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static String makeResponse(String content) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=UTF-8\n" +
                String.format("Content-Length: %d\n\n", content.length()) +
                content;
    }

    private static String getFrontPage() throws Exception {

        CodecastSummariesUseCase useCase = new CodecastSummariesUseCase();
        User micah = Context.userGateway.findUserByName("Micah");
        List<PresentableCodecastSummary> presentableCodecasts = useCase.presentCodecasts(micah);

        ViewTemplate frontpageTemplate = ViewTemplate.fromClasspathResource("html/frontpage.html");

        StringBuilder codecastLines = new StringBuilder();
        for (PresentableCodecastSummary presentableCodecast : presentableCodecasts){
            ViewTemplate codecastTemplate = ViewTemplate.fromClasspathResource("html/codecast.html");
            codecastTemplate.replace("title", presentableCodecast.title);
            codecastTemplate.replace("publicationDate", presentableCodecast.publicationDate);
            codecastTemplate.replace("thumbnail", "https://cleancoders.com/images/portraits/robert-martin.jpg");
            codecastTemplate.replace("permalink", presentableCodecast.permalink);
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
