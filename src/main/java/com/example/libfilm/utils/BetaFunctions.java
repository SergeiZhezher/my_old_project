package com.example.libfilm.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public interface BetaFunctions {

    static String spellingСorrections(String s) {

        String searchLink = "https://www.google.com/search?q=" + s;
        searchLink = searchLink.replaceAll("\\s", "+");

        try {

            Document doc = Jsoup.connect(searchLink).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4404.110 Safari/537.36").timeout(25000).get();
            Element debug = doc.getElementById("fprsl");
            System.out.println("Google result: (есть ошибки) " + debug.text());
            return debug.text();

        }
        catch (ConnectException e) {
            System.out.println("proxy нада мінять");
        }
        catch (NullPointerException e) {
            System.out.println("Названия не содержит ошибок " + s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}
