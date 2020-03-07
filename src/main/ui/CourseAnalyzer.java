package ui;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.Course;
import model.CourseDetailsParser;
import model.CourseList;
import model.DataRetriever;
import org.json.simple.parser.ParseException;
import persistence.Reader;
import persistence.Writer;
import sun.tools.jps.Jps;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// UI Functionality and methods are implemented from Teller App. Link below:
//      https://github.students.cs.ubc.ca/CPSC210/TellerApp/tree/master/src/main/ca/ubc/cpsc210/bank/model

// Course Analyzer desktop application
public class CourseAnalyzer extends JFrame implements ActionListener {
    public static final int MAIN_WIDTH = 1000;
    public static final int MAIN_HEIGHT = 1000;

    public static final int WELCOME_WIDTH = 400;
    public static final int WELCOME_HEIGHT = 125;

    private Scanner input;
    private CourseList courseList;
    private static String apiBaseURL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests;

    public CourseAnalyzer() {
        super("Course Analyzer");
        this.courseList = new CourseList();

        displayWelcomeMenu();
        runCourseAnalyzer();
    }


    // TODO: Documentation
    private void displayWelcomeMenu() {
        setPreferredSize(new Dimension(WELCOME_WIDTH, WELCOME_HEIGHT));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcomeMessage = new JLabel("Welcome to Course Analyzer!");
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startNewButton = new JButton("Start!");
        startNewButton.setActionCommand("newSession");
        startNewButton.addActionListener(this);
        startNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loadDataButton = new JButton("Load Data From Previous Session");
        loadDataButton.setActionCommand("loadData");
        loadDataButton.addActionListener(this);
        loadDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(welcomeMessage);
        add(startNewButton);
        add(loadDataButton);
        setVisible(true);
        pack();
    }

    // TODO: Documentation
    private void reloadData() throws IOException {
        input = new Scanner(System.in);
        System.out.println("Do you want to reload your course list from the previous session? (Yes(Y) / No(N))");
        String reloadDecision = input.next().toUpperCase();
        if (reloadDecision.equals("Y")) {
            courseList = Reader.readCourses(new File("./data/txt/savedCourses.txt"));
        }

    }

    // MODIFIES: this
    // EFFECTS: takes the input from the user
    private void runCourseAnalyzer() {
        boolean isRunning = true;
        String command = null;

        while (isRunning) {
            displayMenu();
            command = input.next();

            if (command.equals("3")) {
                isRunning = quit();
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

    // TODO: documentation
    private boolean quit() {
        boolean isRunning;
        System.out.println("Do you want to save your current save list for future use ? (Yes(Y) / No(N))");
        String saveDecision = input.next().toUpperCase();
        System.out.println(saveDecision);
        if (saveDecision.equals("Y")) {
            try {
                saveCourseList();
            } catch (FileNotFoundException e) {
                System.out.println("File not found!");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Unsupported file encoding!");
            }
        }

        isRunning = false;
        System.out.println("Quiting...");
        return isRunning;
    }

    private void saveCourseList() throws FileNotFoundException, UnsupportedEncodingException {
        Writer writer = new Writer(new File("./data/txt/savedCourses.txt"));
        writer.write(courseList);
        writer.close();
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
            System.out.println("COURSE " + count);
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
        List<String> inputs = getInputsForCourse();
        String courseID = inputs.get(0);
        String courseNo = inputs.get(1);
        String courseSection = inputs.get(2);

        CourseDetailsParser parser = new CourseDetailsParser();

        DataRetriever retriever = new DataRetriever();
        HtmlPage profNamePage = retriever.retrieveProfName(courseID, courseNo, courseSection);
        String profName = parser.parseProfName(profNamePage);

        List<Double> fiveYearAverage = new ArrayList<>();
        // Requests average for five winter terms from 2014 to 2019, not including 2019
        for (int i = 2014; i < 2019; i++) {
            String term = i + "W";
            String apiUrl = apiBaseURL + term + "/" + courseID + "/" + courseNo;
            String apiResponse = retriever.getResponseAsStringFromURL(apiUrl);
            List<Double> termAverages = parser.parseAverage(apiResponse, profName);
            fiveYearAverage.addAll(termAverages);
        }

        Course course = new Course(courseID, courseNo, courseSection, profName, fiveYearAverage);
        displayCourse(course);

        presentAddCourseOption(course);
        System.out.println("Redirecting to main menu...");
    }

    private void presentAddCourseOption(Course course) {
        System.out.println("Do you want to add this course to your course list? (Y for yes/ N for no)");

        String addToCourseList = input.next().toUpperCase();
        if (addToCourseList.equals("Y")) {
            courseList.addCourse(course);
        }
    }

    private List<String> getInputsForCourse() {
        List<String> inputs = new ArrayList<>();

        System.out.println("Enter the course ID (eg. CPSC: )");
        String courseID = input.next().toUpperCase();

        System.out.println("Enter the course number: ");
        String courseNo = input.next();

        System.out.println("Enter section number: ");
        String courseSection = input.next();

        inputs.add(courseID);
        inputs.add(courseNo);
        inputs.add(courseSection);
        System.out.println("Processing the information...");
        return inputs;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newSession")) {
            System.out.println("new session");
        } else if (e.getActionCommand().equals("loadData")) {
            try {
                courseList = Reader.readCourses(new File("./data/txt/savedCourses.txt"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error! No saved data found!");
            }
        }

    }
}
