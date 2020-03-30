package ui;

import model.Course;
import model.CourseList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Page that shows the data of a given course
public class CoursePage extends JFrame implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JLabel courseNameLabel;
    private JLabel profNameLabel;
    private JLabel fiveYearAverageLabel;
    private JButton addToCourseListButton;
    private JButton goBackToMainMenuButton;
    private JButton switchGraphButton;
    private GridBagConstraints constraints;
    private CourseList courseList;
    private Course course;

    private JPanel gradeDistributionChart;
    private JPanel historicalAverageGraph;
    private boolean isGradeDistributionChartVisible;

    // MODIFIES: this
    // EFFECTS: constructs a course page with given course, and its components
    public CoursePage(Course course) {
        super(course.getCourseFullName());

        this.courseList = CourseList.getInstance();
        this.course = course;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        setupCourseNameLabel(course);
        setupProfNameLabel(course);
        setupFiveYearAverageLabel(course);
        setupFiveYearAverageGraph(course);
        setupGradeDistributionChart(course);
        this.isGradeDistributionChartVisible = true;

        setupAddToCourseListButton();
        setupGoBackToMainMenuButton();
        setupSwitchGraphButton();
        addWindowListener(new QuitOptionsPane(this));

        pack();
    }

    // MODIFIES: this
    // EFFECTS: creates and places the label that has the given course's name
    private void setupCourseNameLabel(Course course) {
        String courseName = "<html><span style=\"font-family:Dialog;font-size:12px;\">Course Name: </span>";
        courseName = courseName + course.getCourseFullName() + "</html>";
        courseNameLabel = new JLabel(courseName);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.WEST;
        add(courseNameLabel, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the label that has the given course's five year average
    private void setupFiveYearAverageLabel(Course course) {
        String fiveYearAverage =  "<html><span style=\"font-family:Dialog;font-size:12px;\">Five Year Average: </span>";
        fiveYearAverage = fiveYearAverage + course.getCourseFiveYearAverage() + "</html>";
        fiveYearAverageLabel = new JLabel(fiveYearAverage);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(fiveYearAverageLabel, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the label that has the given course's professor's name
    private void setupProfNameLabel(Course course) {
        String profName =  "<html><span style=\"font-family:Dialog;font-size:12px;\">Professor: </span>";
        profName = profName + course.getProfName() + "</html>";
        profNameLabel = new JLabel(profName);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(profNameLabel, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the graphs that plots the five year average
    private void setupFiveYearAverageGraph(Course course) {
        constraints.gridx = 0;
        constraints.gridy = 4;
        historicalAverageGraph = new FiveYearAverageGraph(course);
        add(historicalAverageGraph, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the graphs that plots the five year average
    private void setupGradeDistributionChart(Course course) {
        constraints.gridx = 0;
        constraints.gridy = 4;
        gradeDistributionChart = new GradeDistributionsChart(course);
        add(gradeDistributionChart, constraints);
    }


    // MODIFIES: this
    // EFFECTS: creates and places the go back to main menu button
    private void setupGoBackToMainMenuButton() {
        goBackToMainMenuButton = new JButton("Back to Main Menu");
        goBackToMainMenuButton.setActionCommand("goBack");
        goBackToMainMenuButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        add(goBackToMainMenuButton, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the go back to main menu button
    private void setupSwitchGraphButton() {
        switchGraphButton = new JButton("Switch to Course Average Graph");
        switchGraphButton.setActionCommand("switchGraph");
        switchGraphButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(switchGraphButton, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the add to course list button
    private void setupAddToCourseListButton() {
        addToCourseListButton = new JButton("Add to Course List");
        addToCourseListButton.addActionListener(this);
        addToCourseListButton.setActionCommand("addToCourseList");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        add(addToCourseListButton, constraints);
    }

    // MODIFIES: this, mainMenu
    // EFFECTS: closes the current window and opens the main menu window
    private void goBackToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addToCourseList")) {
            courseList.addCourse(course);
        }
        if (e.getActionCommand().equals("goBack")) {
            goBackToMainMenu();
        }
        if (e.getActionCommand().equals("switchGraph")) {
            if (isGradeDistributionChartVisible) {
                gradeDistributionChart.setVisible(false);
                historicalAverageGraph.setVisible(true);
                switchGraphButton.setText("Switch to Grade Distribution Chart");
                isGradeDistributionChartVisible = false;
            } else {
                gradeDistributionChart.setVisible(true);
                historicalAverageGraph.setVisible(false);
                switchGraphButton.setText("Switch to Historical Average Graph");
                isGradeDistributionChartVisible = true;
            }
        }
    }
}
