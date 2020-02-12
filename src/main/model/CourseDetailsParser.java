package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

// Represents a class with methods for parsing course details
public class CourseDetailsParser {
    private static String API_BASE_URL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests

    public CourseDetailsParser(){
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

}
