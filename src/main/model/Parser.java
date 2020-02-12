package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

// Represents an API Parser with URL of API
public class Parser {
    private URL clientURL;

    // REQUIRES: well-formed URL
    // EFFECTS: constructs Parser class, outputs stack trace if the url is malformed, however,
    public Parser(String url) throws MalformedURLException {
        clientURL = new URL(url);
    }

    // REQUIRES: active internet connection
    // EFFECTS: opens a connection to API
    public void sendGetRequest() throws IOException {
        System.setProperty("http.agent", "Chrome");
        HttpURLConnection connection = (HttpURLConnection) clientURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
    }

    // REQUIRES: active internet connection
    // EFFECTS: reads and returns the API response
    // Credits: https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
    public String getResponseAsString() throws IOException {
        String response = "";

        Scanner scanner = new Scanner(clientURL.openStream());
        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();

        return response;
    }

}
