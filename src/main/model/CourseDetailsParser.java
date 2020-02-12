package model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents a class with methods for parsing course details
public class CourseDetailsParser {
    private static String API_BASE_URL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests
    private static String UBC_BASE_URL = "https://courses.students.ubc.ca/cs/courseschedule?";

    public CourseDetailsParser(){
    }

    // REQUIRES: active internet connection
    // MODIFIES: this
    // EFFECTS: retrieves name of the professor for the  course from SSC and returns it
    public String retrieveProfName(String courseID, String courseNo, String courseSection)
            throws IndexOutOfBoundsException, IOException {

        String courseURL = generateCourseURL(courseID, courseNo, courseSection);
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        String profName = null;

        HtmlPage page = client.getPage(courseURL);
        HtmlElement tableElement = (HtmlElement) page.getByXPath("//table").get(2);
        HtmlElement profNameTable = (HtmlElement) tableElement.getChildNodes().get(0).getChildNodes().get(0);
        HtmlElement tableData = (HtmlElement) profNameTable.getChildNodes().get(1);
        profName = tableData.getChildNodes().get(0).asText();

        return profName;
    }

    // EFFECTS: generates and returns the SSC URL for the given course
    private String generateCourseURL(String courseID, String courseNo, String courseSection) {
        String courseURL = UBC_BASE_URL + "pname=subjarea&tname=subj-section&dept=";
        courseURL = courseURL + courseID + "&course=" + courseNo;
        courseURL = courseURL + "&section=" + courseSection;
        return courseURL;
    }

    // REQUIRES: responseArray is not empty && response is a valid JSON data && active internet connection
    // MODIFIES: this
    // EFFECTS: retrieves the average for the course for the given year's winter term
    private List<Double> retrieveAverageForYear(String year, String courseID, String courseNumber, String profName)
            throws ParseException, IOException {

        String term = year + "W";
        String apiUrl = API_BASE_URL + term + "/" + courseID + "/" + courseNumber;

        Parser parser = new Parser(apiUrl);
        parser.sendGetRequest();

        String response = parser.getResponseAsString();
        JSONParser jsonParser = new JSONParser();
        JSONArray responseArray = null;

        responseArray = (JSONArray) jsonParser.parse(response);


        List<Double> termAverages = new ArrayList<>();

        for (int i = 0; i < responseArray.size(); i++) {
            JSONObject responseItem = (JSONObject) responseArray.get(i);
            if (profName.equals((responseItem.get("instructor")).toString().toUpperCase())) {
                JSONObject coursesStats = (JSONObject) responseItem.get("stats");
                Double average = (Double) coursesStats.get("average");
                termAverages.add(average);
            }
        }
        return termAverages;
    }

    // EFFECTS: retrieves and returns the course averages for 5 winter terms between 2014-2018
    public List<Double> retrieveFiveYearAverage(String courseID, String courseNumber, String profName)
            throws ParseException, IOException {

        List<Double> averageForFiveYears = new ArrayList<>();
        for (int i = 2014; i < 2019; i++) {
            List<Double> termAverages = retrieveAverageForYear(Integer.toString(i), courseID, courseNumber, profName);
            averageForFiveYears.addAll(termAverages);
        }
        return averageForFiveYears;
    }

}
