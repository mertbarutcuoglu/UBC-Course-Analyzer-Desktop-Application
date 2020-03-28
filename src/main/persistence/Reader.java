package persistence;

import model.Course;
import model.CourseList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

// A reader to read course data from a file
// CREDITS: TellerApp
//          Link: https://github.students.cs.ubc.ca/CPSC210/TellerApp/
public class Reader {
    public static final String DELIMETER = ";";

    // EFFECTS: constructs the reader
    public Reader(){

    }

    // EFFECTS: returns CourseList that was parsed from the file
    public static CourseList readCourses(File file) throws IOException {
        List<String> fileContent = readFile(file);
        CourseList parsedContent = parseContent(fileContent);
        return parsedContent;
    }

    // EFFECTS: reads and returns all the lines in the file as a list of strings
    private static List<String> readFile(File file) throws IOException {
        List<String> fileContent = Files.readAllLines(file.toPath());
        return fileContent;
    }

    // EFFECTS: returns a CourseList from a list of strings,
    //          each string contains information about a Course
    private static CourseList parseContent(List<String> fileContent) {
        CourseList courseList = new CourseList();

        for (String line : fileContent) {
            ArrayList<String> lineComponents = splitString(line);
            courseList.addCourse(parseCourse(lineComponents));
        }
        return courseList;
    }

    // EFFECTS: returns a list of string that is splitted from a line by DELIMETER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMETER);
        ArrayList<String> listOfSplits = new ArrayList<>(Arrays.asList(splits));
        return listOfSplits;
    }

    // EFFECTS: constructs a course from the components and returns it
    private static Course parseCourse(List<String> lineComponents) {
        String courseName = lineComponents.get(0);
        String courseNo = lineComponents.get(1);
        String courseSection = lineComponents.get(2);
        String profName = lineComponents.get(3);

        Map<String, Integer> gradeDist = new LinkedHashMap<>();
        List<String> gradeRanges = createGradeRanges();
        // All components from 4th one to 14th one are grade distribution values.
        for (int i = 4; i < 15; i++) {
            gradeDist.put(gradeRanges.get(i - 4), Integer.parseInt(lineComponents.get(i)));
        }

        List<Double> courseAverages = new ArrayList<>();
        // All components after the 15th component until the last one are grades
        for (int i = 15; i < lineComponents.size(); i++) {
            String average = lineComponents.get(i);
            courseAverages.add(Double.parseDouble(average));
        }

        Course parsedCourse = new Course(courseName, courseNo, courseSection, profName, courseAverages, gradeDist);

        return parsedCourse;
    }

    // EFFECTS: creates the string for grade ranges in a systematic way and returns it as an array
    private static List<String> createGradeRanges() {
        List<String> gradeRanges = new ArrayList<>();
        gradeRanges.add("<50%");

        Integer range = 50;
        Integer increment = 4;

        for (int i = 0; i < 10; i++) {

            if (i < 2) {
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            } else if (i > 1 && i < 7) {
                increment = 3;
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            } else if (i > 6 && i < 9) {
                increment = 4;
                gradeRanges.add(range + "-" + (range + increment) + "%");
                range = range + increment + 1;
            } else {
                gradeRanges.add("90-100%");
            }

        }
        return gradeRanges;
    }
}
