package model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

// Represents a course having an ID, number, section, professor name, URL address from SSC, list of class averages for
// last five years and average of those five years.
public class Course {
    private static String API_BASE_URL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests
    private static String UBC_BASE_URL = "https://courses.students.ubc.ca/cs/courseschedule?";

    private String courseID;
    private String courseNumber;
    private String courseSection;
    private String profName;
    private String courseURL;
    private List<Double> courseAveragesForYears;
    private Double courseFiveYearAverage;

    /*
     * REQUIRES: given courseID, courseNumber and courseSection are for an actual course on SSC
     * EFFECTS: constructs a course with given courseID, courseNumber and courseSection. Creates a course URL
     *          and parses professor name. Retrieves and calculates the five year average of the course.
     */
    public Course(String courseID, String courseNumber, String courseSection) {
        this.courseID = courseID.toUpperCase();
        this.courseNumber = courseNumber;
        this.courseSection = courseSection;
        this.courseURL = createCourseUrl();
        this.profName = retrieveProfName();
        this.courseAveragesForYears = retrieveAverageForFiveYears();
        this.courseFiveYearAverage = calculateFiveYearAverage();
    }

    public String getCourseID() {
        return courseID;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseSection() {
        return courseSection;
    }

    public String getProfName() {
        return profName;
    }

    public Double getCourseFiveYearAverage() {
        return courseFiveYearAverage;
    }

    // REQUIRES: there must be a course on SSC with the given parameters
    // MODIFIES: this
    // EFFECTS: creates SSC URL for the course and returns it
    private String createCourseUrl() {
        String courseURL = UBC_BASE_URL + "pname=subjarea&tname=subj-section&dept=";
        courseURL = courseURL + courseID + "&course=" + courseNumber;
        courseURL = courseURL + "&section=" + courseSection;
        return courseURL;
    }

    // MODIFIES: this
    // EFFECTS: retrieves name of the professor for the course from SSC and returns it
    private String retrieveProfName() {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        try {
            HtmlPage page = client.getPage(courseURL);
            HtmlElement tableElement = (HtmlElement) page.getByXPath("//table").get(2);
            HtmlElement profNameTable = (HtmlElement) tableElement.getChildNodes().get(0).getChildNodes().get(0);
            HtmlElement tableData = (HtmlElement) profNameTable.getChildNodes().get(1);
            profName = tableData.getChildNodes().get(0).asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profName;
    }

    // REQUIRES: responseArray is not empty
    // MODIFIES: this
    // EFFECTS: retrieves the average for the course for the given year's winter term
    private List<Double> retrieveAverageForYear(String year) {
        String term = year + "W";
        String apiUrl = API_BASE_URL + term + "/" + courseID + "/" + courseNumber;

        Parser parser = new Parser(apiUrl);
        parser.sendGetRequest();

        String response = parser.getResponseAsString();
        JSONParser jsonParser = new JSONParser();
        JSONArray responseArray = null;

        try {
            responseArray = (JSONArray) jsonParser.parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
    private List<Double> retrieveAverageForFiveYears() {
        List<Double> averageForFiveYears = new ArrayList<>();
        for (int i = 2014; i < 2019; i++) {
            List<Double> termAverages = retrieveAverageForYear(Integer.toString(i));
            averageForFiveYears.addAll(termAverages);
        }
        return averageForFiveYears;
    }

    // EFFECTS: calculates and returns the five year average for the course
    private Double calculateFiveYearAverage() {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < courseAveragesForYears.size(); i++) {
            if (courseAveragesForYears.get(i) != 0.0) {
                sum = sum + courseAveragesForYears.get(i);
                count++;
            }
        }
        return sum / count;
    }
}