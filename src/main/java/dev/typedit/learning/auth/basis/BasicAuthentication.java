package dev.typedit.learning.auth.basis;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

@WebServlet("/login")
public class BasicAuthentication extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(BasicAuthentication.class);

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        PrintWriter printWriter = res.getWriter();
        HttpResponse<String> response;

        try {
            response = performRequest(username, password);
        } catch (InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        res.setContentType("text/plain;charset=UTF-8");
        printWriter.print("Response: \n");
        printWriter.print("Body: " + response.body());
        printWriter.print("\n");
        printWriter.print("Status code: " + response.statusCode());
        printWriter.flush();
    }

    public HttpResponse<String> performRequest(String username, String password) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI("https://postman-echo.com/basic-auth"))
                .header("Authorization", getBasicAuthenticationHeader(username, password))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Status {}", response.statusCode());
        log.info("Body {}", response.body());

        return response;
    }

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }
}
