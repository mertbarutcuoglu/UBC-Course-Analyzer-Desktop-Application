package model;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import persistence.Reader;
import persistence.Saveable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Represents a course having an ID, number, section, professor name, list of class averages for last five years
// ,and average of those five years.
public class Course implements Saveable {
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

    // EFFECTS: returns course with its fields as a string
    // CREDITS: TellerApp
    //         Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/
    @Override
    public String toString() {
        List<String> fiveYearAverageString = new ArrayList<>();

        for (Double average: courseAveragesForYears) {
            String averageString = String.format("%.2f", average);
            fiveYearAverageString.add(averageString);
        }

        String courseAsString = "[ course id: " + courseID + "; course number: " + courseNumber;
        courseAsString = courseAsString + "; course section: " + courseSection + "; professor: " + profName;
        courseAsString = courseAsString + "; 2014 average: " + fiveYearAverageString.get(0);
        courseAsString = courseAsString + "; 2015 average: " + fiveYearAverageString.get(1);
        courseAsString = courseAsString + "; 2016 average: " + fiveYearAverageString.get(2);
        courseAsString = courseAsString + "; 2017 average: " + fiveYearAverageString.get(3);
        courseAsString = courseAsString + "; 2018 average: " + fiveYearAverageString.get(4) + "]";

        return courseAsString;
    }

    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(courseID);
        printWriter.print(Reader.DELIMETER);
        printWriter.print(courseNumber);
        printWriter.print(Reader.DELIMETER);
        printWriter.print(courseSection);
        printWriter.print(Reader.DELIMETER);
        printWriter.print(profName);
        printWriter.print(Reader.DELIMETER);
        for (Double average: courseAveragesForYears) {
            printWriter.print(average);
            printWriter.print(Reader.DELIMETER);
        }
        printWriter.println();
    }
}