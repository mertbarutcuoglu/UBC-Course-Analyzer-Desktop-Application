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
import java.util.Scanner;

// Represents a course having an ID, number, section, professor name, list of class averages for last five years
// ,and average of those five years.
public class Course {
    private String courseID;
    private String courseNumber;
    private String courseSection;
    private String profName;
    private List<Double> courseAveragesForYears;
    private Double courseFiveYearAverage;


    //EFFECTS: constructs a course with given parameters, and calculates five year average from the given averages
    public Course(String courseID, String courseNum, String courseSection, String profName, List<Double> termAverages) {
        this.courseID = courseID.toUpperCase();
        this.courseNumber = courseNum;
        this.courseSection = courseSection;
        this.profName = profName.toUpperCase();
        this.courseAveragesForYears = termAverages;
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


    // EFFECTS: calculates and returns the five year average for the course
    private Double calculateFiveYearAverage() {
        double sum = 0.0;
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