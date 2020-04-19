package model;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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
        File jsonFile = new File("./data/json/2018W_PHIL_220.json");
        StringBuilder response = new StringBuilder();

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        List<Double> expectedAverages = new ArrayList<>();
        expectedAverages.add(79.65);
        expectedAverages.add(77.87);

        List<Double> averages = new ArrayList<>();
        try {
            averages = parser.parseAverage(response.toString(), "ICHIKAWA, JONATHAN");
        } catch (ParseException e) {
            fail("Parsing error!");
        }
        assertEquals(expectedAverages, averages);
    }

    @Test
    public void testParseAverageWithInvalidJSONResponse(){
        File jsonFile = new File("./data/json/invalidJSONResponse.json");
        StringBuilder response = new StringBuilder();

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        try {
            List<Double> averages = parser.parseAverage(response.toString(), "ICHIKAWA, JONATHAN");
            fail("Didn't catch the exception!");
        } catch (ParseException e) {
            System.out.println("Catched the ParseException!");
        }
    }

    @Test
    public void testParseProfNameWithNoError() {
        File htmlPageFile = new File("./data/html/cpsc121_202.html");
        StringBuilder htmlPageString = new StringBuilder();

        Scanner scanner = null;
        try {
            scanner = new Scanner(htmlPageFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            htmlPageString.append(scanner.nextLine());
        }
        scanner.close();

        // String html testing approach is adapted from StackOverFlow. Link below:
        // https://stackoverflow.com/questions/27803064/html-unit-read-from-a-normal-string
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        MockWebConnection connection = new MockWebConnection();
        connection.setDefaultResponse(htmlPageString.toString());

        client.setWebConnection(connection);
        HtmlPage page = null; // url is a placeholder, response will be the file
        try {
            page = client.getPage("http://example.com");
        } catch (IOException e) {
            fail("IOException occured!");
        }
        String profName = parser.parseProfName(page);
        assertEquals("BELLEVILLE, PATRICE", profName);
    }

    @Test
    public void testParseGradeDistributionWithNoErrors() {
        File jsonFile = new File("./data/json/2018W_PHIL_220.json");
        StringBuilder response = new StringBuilder();

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();

        Map<String, Integer> gradeDistributions = new LinkedHashMap<>();
        try {
            parser.parseGradeDistribution(response.toString(), "ICHIKAWA, JONATHAN", gradeDistributions);
        } catch (ParseException e) {
            fail("Parsing error!");
        }
        assertEquals(gradeDistributions, getGradeDistributionSample());
    }

    @Test
    public void testParseGradeDistributionWithInvalidJSONResponse(){
        File jsonFile = new File("./data/json/invalidJSONResponse.json");
        StringBuilder response = new StringBuilder();

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response.append(scanner.nextLine());
        }
        scanner.close();
        Map<String, Integer> gradeDistributions = new LinkedHashMap<>();
        try {
            parser.parseGradeDistribution(response.toString(), "ICHIKAWA, JONATHAN", gradeDistributions);
            fail("Didn't catch the exception!");
        } catch (ParseException e) {
            System.out.println("Catched the ParseException!");
        }
    }


    private Map<String, Integer> getGradeDistributionSample() {
        Map<String, Integer> gradeDistributions = new LinkedHashMap<>();
        gradeDistributions.put("<50%", 10);
        gradeDistributions.put("50-54%", 3);
        gradeDistributions.put("55-59%", 11);
        gradeDistributions.put("60-63%", 5);
        gradeDistributions.put("64-67%", 13);
        gradeDistributions.put("68-71%", 12);
        gradeDistributions.put("72-75%", 19);
        gradeDistributions.put("76-79%", 28);
        gradeDistributions.put("80-84%", 42);
        gradeDistributions.put("85-89%", 51);
        gradeDistributions.put("90-100%", 54);
        return gradeDistributions;
    }

}

