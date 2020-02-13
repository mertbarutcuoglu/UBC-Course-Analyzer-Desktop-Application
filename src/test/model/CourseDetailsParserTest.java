package model;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlHtml;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseDetailsParserTest {
    CourseDetailsParser parser;

    @BeforeEach
    public void runBefore() {
        parser = new CourseDetailsParser();
    }

    @Test
    public void testParseAverageNoErrors() {
        File jsonFile = new File("src/test/model/json/2018W_PHIL_220.json");
        String response = "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();
        List<Double> expectedAverages = new ArrayList<>();
        expectedAverages.add(79.65);
        expectedAverages.add(77.87);

        List<Double> averages = new ArrayList<>();
        try {
            averages = parser.parseAverage(response, "ICHIKAWA, JONATHAN");
        } catch (ParseException e) {
            fail("Parsing error!");
        }
        assertEquals(averages, expectedAverages);
    }

    @Test
    public void testParseAverageWithInvalidJSONResponse(){
        File jsonFile = new File("src/test/model/json/invalidJSONResponse.json");
        String response = "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();

        try {
            List<Double> averages = parser.parseAverage(response, "ICHIKAWA, JONATHAN");
            fail("Didn't catch the exception!");
        } catch (ParseException e) {
            System.out.println("Catched the ParseException!");
        }
    }

    @Test
    public void testParseProfNameWithNoError() {
        File htmlPageFile = new File("src/test/model/html/cpsc121_202.html");
        String htmlPageString = null;

        Scanner scanner = null;
        try {
            scanner = new Scanner(htmlPageFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            htmlPageString = htmlPageString + scanner.nextLine();
        }
        scanner.close();

        // String html testing approach is adapted from StackOverFlow. Link below:
        // https://stackoverflow.com/questions/27803064/html-unit-read-from-a-normal-string
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        MockWebConnection connection = new MockWebConnection();
        connection.setDefaultResponse(htmlPageString);

        client.setWebConnection(connection);
        HtmlPage page = null; // url is a placeholder, response will be the file
        try {
            page = client.getPage("http://example.com");
        } catch (IOException e) {
            fail("IOException occured!");
        }
        String profName = parser.parseProfName(page);
        assertEquals(profName, "BELLEVILLE, PATRICE");
    }


}

