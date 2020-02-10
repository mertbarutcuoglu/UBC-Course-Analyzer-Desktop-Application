package ui;

import model.CourseList;

import java.util.Scanner;

// Course Analyzer desktop application
public class CourseAnalyzer {
    private Scanner input;
    private CourseList courseList;

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
            } else {
                processCommand(command);
            }
            System.out.println("Quiting...");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes the given command
    private void processCommand(String command) {
        if (command.equals("1")) {
            analyzeCourse();
        } else if (command.equals("2")) {
            displayCourseList();
        } else {
            System.out.println("Option is invalid. ");
        }
    }

    // EFFECTS: Displays course list
    private void displayCourseList() {

    }

    private void analyzeCourse() {

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
