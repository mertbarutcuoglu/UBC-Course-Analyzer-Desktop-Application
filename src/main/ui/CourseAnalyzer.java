package ui;

import model.Course;
import model.CourseDetailsParser;
import model.CourseList;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// UI Functionality and methods are implemented from Teller App. Link below:
//      https://github.students.cs.ubc.ca/CPSC210/TellerApp/tree/master/src/main/ca/ubc/cpsc210/bank/model

// Course Analyzer desktop application
public class CourseAnalyzer {
    private Scanner input;
    private CourseList courseList;
    private CourseDetailsParser courseDetailsParser;

    public CourseAnalyzer() {
        this.courseList = new CourseList();
        this.courseDetailsParser = new CourseDetailsParser();
        runCourseAnalyzer();
    }

    // MODIFIES: this
    // EFFECTS: takes the input from the user
    private void runCourseAnalyzer() {
        boolean isRunning = true;
        String command = null;
        input = new Scanner(System.in);

        while (isRunning) {
            displayMenu();
            command = input.next();

            if (command.equals("3")) {
                isRunning = false;
                System.out.println("Quiting...");
            } else {
                try {
                    processCommand(command);
                } catch (ParseException e) {
                    System.out.println("Parsing error! Please try again.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Couldn't find the given course");
                } catch (IOException e) {
                    System.out.println("Couldn't connect to the server.");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the given command
    private void processCommand(String command) throws IOException, ParseException {
        if (command.equals("1")) {
            analyzeCourse();
        } else if (command.equals("2")) {
            displayCourseList();
        } else {
            System.out.println("Option is invalid. ");
        }
    }

    // EFFECTS: displays course list
    private void displayCourseList() {
        List<Course> courseList = this.courseList.getListOfCourses();
        int count = 1;
        for (Course course : courseList) {
            System.out.println("COURSE " + Integer.toString(count));
            displayCourse(course);
            count++;
        }
        displayCourseListOptions();
    }

    // EFFECTS: displays options for the course list interface
    private void displayCourseListOptions() {
        System.out.println("Choose from the following options: ");
        System.out.println("0. Go Back to Main Menu");
        System.out.println("1. Delete a course from the list");
        int option = input.nextInt();

        if (option == 1) {
            removeCourse();
        }
    }

    private void removeCourse() {
        System.out.println("Enter the number of the course that you want to remove from the list.(Eg. 1, 2 etc.)");
        int courseIndex = input.nextInt();
        courseList.removeCourse(courseIndex);
    }

    // EFFECTS: analyzes the given course
    // MODIFIES: this
    private void analyzeCourse() throws ParseException, IndexOutOfBoundsException, IOException {
        System.out.println("Enter the course ID (eg. CPSC: )");
        String courseID = input.next();

        System.out.println("Enter the course number: ");
        String courseNumber = input.next();

        System.out.println("Enter section number: ");
        String courseSection = input.next();

        System.out.println("Processing the information...");

        String profName = courseDetailsParser.retrieveProfName(courseID, courseNumber, courseSection);

        List<Double> fiveYearAverage = courseDetailsParser.retrieveFiveYearAverage(courseID, courseNumber, profName);

        Course course = new Course(courseID, courseNumber, courseSection, profName, fiveYearAverage);
        displayCourse(course);
        System.out.println("Do you want to add this course to your course list? (Y for yes/ N for no)");

        String addToCourseList = input.next().toUpperCase();
        if (addToCourseList.equals("Y")) {
            courseList.addCourse(course);
        }
        System.out.println("Redirecting to main menu...");
    }

    //EFFECTS: displays course information
    private void displayCourse(Course course) {
        System.out.println("COURSE ID: " + course.getCourseID());
        System.out.println("COURSE NO: " + course.getCourseNumber());
        System.out.println("COURSE SECTION: " + course.getCourseSection());
        System.out.println("INSTRUCTOR: " + course.getProfName());
        System.out.println("FIVE YEAR AVERAGE: " + course.getCourseFiveYearAverage());
        System.out.println("--------------------------------------------------------");
    }

    //EFFECTS: displays welcome message and explains the usage of UI
    private void displayWelcomeMessage() {
        System.out.println("Welcome to Course Analyzer!");
        System.out.println("Please choose from the following options by typing its option number.");
    }

    // EFFECTS: Displays menu of options
    private void displayMenu() {
        System.out.println("\n1. Analyze a course.");
        System.out.println("2. My courses");
        System.out.println("3. Quit");
    }

}
