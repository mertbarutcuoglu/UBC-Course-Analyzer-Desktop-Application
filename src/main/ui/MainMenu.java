package ui;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.Course;
import model.CourseDetailsParser;
import model.CourseList;
import model.DataRetriever;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JFrame implements ActionListener {
    public static final int MAIN_WIDTH = 750;
    public static final int MAIN_HEIGHT = 250;
    private static String apiBaseURL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests;

    private JTextField courseCodeField;
    private JTextField courseNumField;
    private JTextField courseSectionField;
    private JButton startButton;
    private JButton saveButton;
    private JButton showCoursesButton;
    private CourseList courseList;
    private CourseListPanel courseListPanel;
    private GridBagConstraints constraints;

    //TODO: Documentation
    public MainMenu(CourseList courseList) {
        super("Course Analyzer");
        this.courseList = courseList;
        constraints = new GridBagConstraints();

        setPreferredSize(new Dimension(MAIN_WIDTH, MAIN_HEIGHT));
        setLayout(new GridBagLayout());
        setupCourseCodeLabelButton();
        setupCourseNumLabel();
        setupCourseSectionLabel();
        setupStartButton();
        setUpSaveButton();
        setupCourseListPanel();
        setupShowCoursesButton();

        pack();
        startButton.requestFocusInWindow();
    }

    // TODO: Documentation
    public MainMenu(Course course) {

    }

    // TODO: Documentation
    private void setupShowCoursesButton() {
        showCoursesButton = new JButton("Show Detailed Course List");
        showCoursesButton.setActionCommand("showCourses");
        showCoursesButton.addActionListener(this);
        constraints.gridx = 4;
        constraints.gridy = 2;
        add(showCoursesButton, constraints);
    }

    // TODO: Documentation
    private void setupStartButton() {
        startButton = new JButton("Start!");
        startButton.setActionCommand("startAnalyzer");
        startButton.addActionListener(this);
        constraints.gridx = 3;
        constraints.gridy = 0;
        add(startButton, constraints);
    }

    // TODO: Documentation
    private void setupCourseSectionLabel() {
        courseSectionField = new JTextField("Course section: ");
        constraints.gridx = 2;
        constraints.gridy = 0;
        courseSectionField.setForeground(Color.gray); // gray color gives place holder look
        add(courseSectionField, constraints);
    }

    // TODO: Documentation
    private void setUpSaveButton() {
        saveButton = new JButton("Save Course List");
        constraints.gridx = 4;
        constraints.gridy = 1;
        add(saveButton, constraints);
    }


    // TODO: Documentation
    private void setupCourseNumLabel() {
        courseNumField = new JTextField("Course number: ");
        constraints.gridx = 1;
        constraints.gridy = 0;
        courseNumField.setForeground(Color.gray); // gray color gives place holder look
        add(courseNumField, constraints);
    }

    // TODO: Documentation
    private void setupCourseCodeLabelButton() {
        courseCodeField = new JTextField("Course code: ");
        constraints.gridx = 0;
        constraints.gridy = 0;
        courseCodeField.setForeground(Color.gray); // gray color gives place holder look
        add(courseCodeField, constraints);
    }

    // TODO: Documentation
    private void setupCourseListPanel() {
        courseListPanel = new CourseListPanel(courseList);
        constraints.gridx = 4;
        constraints.gridy = 0;
        add(courseListPanel);
    }

    private void analyzeCourse(String courseCode, String courseNo, String courseSection)
            throws IOException, ParseException {
        CourseDetailsParser parser = new CourseDetailsParser();
        DataRetriever retriever = new DataRetriever();

        HtmlPage profNamePage = retriever.retrieveProfName(courseCode, courseNo, courseSection);
        String profName = parser.parseProfName(profNamePage);

        java.util.List<Double> fiveYearAverage = new ArrayList<>();

        // Requests average for five winter terms from 2014 to 2019, not including 2019
        for (int i = 2014; i < 2019; i++) {
            String term = i + "W";
            String apiUrl = apiBaseURL + term + "/" + courseCode + "/" + courseNo;
            String apiResponse = retriever.getResponseAsStringFromURL(apiUrl);
            List<Double> termAverages = parser.parseAverage(apiResponse, profName);
            fiveYearAverage.addAll(termAverages);
        }

        Course course = new Course(courseCode, courseNo, courseSection, profName, fiveYearAverage);
        displayCoursePage(course);

    }

    private void displayCoursePage(Course course) {
        CoursePage coursePage = new CoursePage(course);
        coursePage.setVisible(true);
        dispose();
    }

    // TODO
    private void displayCourseList() {
        //stub
    }

    // TODO: handle errors
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("startAnalyzer")) {
            try {
                analyzeCourse(courseCodeField.getText(), courseNumField.getText(), courseSectionField.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else if (e.getActionCommand().equals("showCourses")) {
            displayCourseList();
        }
    }
}
