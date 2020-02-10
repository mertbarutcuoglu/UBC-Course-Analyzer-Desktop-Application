package model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Parser {
    private URL clientURL;

    public Parser(String url) {
        try {
            clientURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void sendGetRequest() {
        try {
            System.setProperty("http.agent", "Chrome");
            HttpURLConnection connnection = (HttpURLConnection) clientURL.openConnection();
            connnection.setRequestMethod("GET");
            connnection.connect();

        } catch (IOException e) {
            e.printStackTrace();;
        }
    }

    public String getResponseAsString() {
        String response = "";
        try {
            Scanner scanner = new Scanner(clientURL.openStream());
            while (scanner.hasNext()) {
                response = response + scanner.nextLine();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
