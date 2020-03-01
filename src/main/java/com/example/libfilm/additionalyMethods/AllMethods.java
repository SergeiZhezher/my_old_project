package com.example.libfilm.additionalyMethods;

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

public interface AllMethods {

     static String searchHints(String s) {
        return "none";
    }

    static String searchСorrectionsMethod(String s) {
        System.setProperty("javax.net.ssl.trustStore", "C:\\work program\\original_JDK\\jdk-11.0.6\\lib\\security\\cacerts");
        System.setProperty ( "javax.net.ssl.trustStorePassword", "changeit");

//        String searchLink = "https://www.google.com/search?q=" + s + "&rlz=1C1SQJL_enUA880UA880&oq=" + s + "&aqs=chrome..69i57j0l5.278j0j9&sourceid=chrome&ie=UTF-8";
          String searchLink = "https://www.google.com/search?q=" + s;
        searchLink = searchLink.replaceAll("\\s", "+");

        try {

            Document doc = Jsoup.connect(searchLink).timeout(25000).get();
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
