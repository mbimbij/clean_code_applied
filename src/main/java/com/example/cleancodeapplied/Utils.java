package com.example.cleancodeapplied;

import java.time.format.DateTimeFormatter;

public class Utils {
    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String addHttpSuccessHeader(String htmlContent) {
        return "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html; charset=UTF-8\n\n" +
                htmlContent;
    }
}
