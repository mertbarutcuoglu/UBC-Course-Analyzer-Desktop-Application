package model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Scanner;

// Represents an data retriever class with the URL of API
public class DataRetriever {
    private static String UBC_BASE_URL = "https://courses.students.ubc.ca/cs/courseschedule?";

    // EFFECTS: constructs DataRetriever class
    public DataRetriever(){
    }

    // EFFECTS: generates and returns the SSC URL for the given course
    private String generateCourseURL(String courseID, String courseNo, String courseSection) {
        String courseURL = UBC_BASE_URL + "pname=subjarea&tname=subj-section&dept=";
        courseURL = courseURL + courseID + "&course=" + courseNo;
        courseURL = courseURL + "&section=" + courseSection;
        return courseURL;
    }

    // REQUIRES: active internet connection
    // EFFECTS: opens a connection to API
    public void sendGetRequest(String url) throws IOException {
        URL clientURL = new URL(url);
        System.setProperty("http.agent", "Chrome");
        HttpURLConnection connection = (HttpURLConnection) clientURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
    }

    // REQUIRES: active internet connection
    // EFFECTS: reads and returns the API response
    // Credits: https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl
    public String getResponseAsStringFromURL(String url) throws IOException {
        sendGetRequest(url);
        URL clientURL = new URL(url);
        String response = "";

        Scanner scanner = new Scanner(clientURL.openStream());
        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();
        return response;
    }

    // REQUIRES: active internet connection
    // MODIFIES: this
    // EFFECTS: retrieves name of the professor for the  course from SSC and returns it
    public HtmlPage retrieveProfName(String courseID, String courseNo, String courseSection)
            throws IndexOutOfBoundsException, IOException {

        String courseURL = generateCourseURL(courseID, courseNo, courseSection);
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = client.getPage(courseURL);

        return page;
    }

}
