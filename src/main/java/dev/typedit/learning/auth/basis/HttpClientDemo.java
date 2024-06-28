package dev.typedit.learning.auth.basis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class HttpClientDemo {
    private static final Logger log = LoggerFactory.getLogger(HttpClientDemo.class);

    public void performRequest() throws IOException, InterruptedException, URISyntaxException {
//        HttpClient client = HttpClient.newBuilder()
//                .authenticator(new Authenticator() {
//                    @Override
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication("postman", "password".toCharArray());
//                    }
//                })
//                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://postman-echo.com/basic-auth"))
                .header("Authorization", getBasicAuthenticationHeader("postman", "password"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Status {}", response.statusCode());
        System.out.println(response.statusCode());
    }

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

}
