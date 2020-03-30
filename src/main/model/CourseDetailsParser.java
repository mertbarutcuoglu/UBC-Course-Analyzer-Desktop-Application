package model;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Represents a class with methods for parsing course details
public class CourseDetailsParser {
    private static String API_BASE_URL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests

    public CourseDetailsParser() {
    }

    // REQUIRES: responseArray is not empty && response is a valid JSON data && active internet connection
    // MODIFIES: this
    // EFFECTS: parses the given JSON response to get average for the given professor
    public List<Double> parseAverage(String response, String profName) throws ParseException {

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

    // REQUIRES: responseArray is not empty && response is a valid JSON data && active internet connection
    // MODIFIES: this
    // EFFECTS: parses the given JSON response to get grade distribution for the given professor
    public void parseGradeDistribution(String response, String profName,
                                                       Map<String, Integer> overallGradeDistribution)
            throws ParseException {

        JSONParser jsonParser = new JSONParser();
        JSONArray responseArray = null;
        List<String> gradeRanges = createGradeRanges();
        responseArray = (JSONArray) jsonParser.parse(response);

        for (int i = 0; i < responseArray.size(); i++) {
            JSONObject responseItem = (JSONObject) responseArray.get(i);
            if (profName.equals((responseItem.get("instructor")).toString().toUpperCase())) {
                Map gradeDistribution = (HashMap<String, Integer>) responseItem.get("grades");
                for (String range : gradeRanges) {
                    Integer numPeopleForRange = ((Number) gradeDistribution.get(range)).intValue();
                    Integer currentNumPeopleForRange = overallGradeDistribution.get(range);
                    if (currentNumPeopleForRange == null) {
                        currentNumPeopleForRange = new Integer(numPeopleForRange);
                        overallGradeDistribution.put(range, currentNumPeopleForRange);
                    } else {
                        overallGradeDistribution.put(range, currentNumPeopleForRange + numPeopleForRange);
                    }
                }
            }
        }
    }

    // EFFECTS: creates the string for grade ranges in a systematic way and returns it as an array
    private static List<String> createGradeRanges() {
        List<String> gradeRanges = new ArrayList<>();
        gradeRanges.add("<50%");

        Integer range = 50;
        Integer increment = 4;
        for (int i = 0; i < 9; i++) {
            if (i < 2) {
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            }
            if (i > 1 && i < 7) {
                increment = 3;
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            }
            if (i > 6 && i < 9) {
                increment = 4;
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            }
        }

        gradeRanges.add("90-100%");
        return gradeRanges;
    }

    // EFFECTS: parses the professor name from the given html page and returns it
    public String parseProfName(HtmlPage profNamePage) {
        HtmlElement tableElement = (HtmlElement) profNamePage.getByXPath("//table").get(2);
        HtmlElement profNameTable = (HtmlElement) tableElement.getChildNodes().get(0).getChildNodes().get(0);
        HtmlElement tableData = (HtmlElement) profNameTable.getChildNodes().get(1);
        String profName = tableData.getChildNodes().get(0).asText();
        return profName;
    }
}
