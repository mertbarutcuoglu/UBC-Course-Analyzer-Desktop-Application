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
import java.util.*;

// Represents a course having an ID, number, section, professor name, list of class averages for last five years
// ,average of those five years, and grade distributions.
public class Course implements Saveable {
    private String courseID;
    private String courseNumber;
    private String courseSection;
    private String profName;

    private Map<String, Integer> gradeDistributions;
    private List<Double> courseAveragesForYears;
    private Double courseFiveYearAverage;


    //EFFECTS: constructs a course with given parameters, and calculates five year average from the given averages
    public Course(String courseID, String courseNum, String courseSection, String profName, List<Double> termAverages,
                  Map<String, Integer> gradeDistributions) {
        this.courseID = courseID.toUpperCase();
        this.courseNumber = courseNum;
        this.courseSection = courseSection;
        this.profName = profName.toUpperCase();
        this.courseAveragesForYears = termAverages;
        this.courseFiveYearAverage = calculateFiveYearAverage();
        this.gradeDistributions = gradeDistributions;
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

    public List<Double> getCourseAveragesForYears() {
        return courseAveragesForYears;
    }

    public Map<String, Integer> getGradeDistribution() {
        return gradeDistributions;
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

    // EFFECTS: returns the course name as courseID courseNumber courseSection
    public String getCourseFullName() {
        String courseName;
        courseName = courseID + " " + courseNumber + " " + courseSection;
        return courseName;
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
            if (i > 6) {
                increment = 4;
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            }
        }

        gradeRanges.add("90-100%");
        return gradeRanges;
    }

    // EFFECTS: returns course with its fields as a string
    // CREDITS: TellerApp
    //         Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/
    @Override
    public String toString() {
        List<String> fiveYearAverageString = new ArrayList<>();

        for (Double average : courseAveragesForYears) {
            String averageString = String.format("%.2f", average);
            fiveYearAverageString.add(averageString);
        }

        StringBuilder courseString = new StringBuilder("[ course id: " + courseID + "; course number: " + courseNumber);
        courseString.append("; course section: ").append(courseSection).append("; professor: ").append(profName);
        List<String> gradeRanges = createGradeRanges();
        for (String range: gradeRanges) {
            courseString.append(";  ").append(range).append(": ").append(gradeDistributions.get(range));
        }
        courseString.append("; 2014 average: ").append(fiveYearAverageString.get(0));
        courseString.append("; 2015 average: ").append(fiveYearAverageString.get(1));
        courseString.append("; 2016 average: ").append(fiveYearAverageString.get(2));
        courseString.append("; 2017 average: ").append(fiveYearAverageString.get(3));
        courseString.append("; 2018 average: ").append(fiveYearAverageString.get(4));
        courseString.append("]");

        return courseString.toString();
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
        for (Integer numPeople : gradeDistributions.values()) {
            printWriter.print(numPeople);
            printWriter.print(Reader.DELIMETER);
        }
        for (Double average : courseAveragesForYears) {
            printWriter.print(average);
            printWriter.print(Reader.DELIMETER);
        }
        printWriter.println();
    }

}