package dev.typedit.learning.auth.basis;

import java.io.IOException;
import java.net.URISyntaxException;

public class Demo {
    public static void main(String[] args) {
        HttpClientDemo demo = new HttpClientDemo();
        try {
            demo.performRequest();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
