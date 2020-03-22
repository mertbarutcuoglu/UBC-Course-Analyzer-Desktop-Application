package ui;

import model.Course;
import model.CourseList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Page that shows the data of a given course
public class CoursePage extends JFrame implements ActionListener {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JLabel courseNameLabel;
    private JLabel profNameLabel;
    private JLabel fiveYearAverageLabel;
    private JButton addToCourseListButton;
    private JButton goBackToMainMenuButton;
    private GridBagConstraints constraints;
    private CourseList courseList;
    private Course course;

    // MODIFIES: this
    // EFFECTS: constructs a course page with given course, and its components
    public CoursePage(Course course, CourseList courseList) {
        super(course.getCourseFullName());

        this.courseList = courseList;
        this.course = course;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        setupCourseNameLabel(course);
        setupProfNameLabel(course);
        setupFiveYearAverageLabel(course);
        setupFiveYearAverageGraph(course);
        setupAddToCourseListButton();
        setupGoBackToMainMenuButton();
        addWindowListener(new QuitOptionsPane(this, courseList));

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
        add(createPlot(createXYDataset(course)), constraints);
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


    // CREDITS: https://www.boraji.com/jfreechart-xy-line-chart-example
    // EFFECTS: creates an XYDataset from the five year average data of the given course
    private XYDataset createXYDataset(Course course) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        List<Double> termAverages = course.getCourseAveragesForYears();
        XYSeries termAveragesSeries = new XYSeries("Average");

        int term = 0;
        for (Double average: termAverages) {
            if (!(average == 0)) {
                termAveragesSeries.add(term, average);
                term++;
            }
        }
        dataset.addSeries(termAveragesSeries);
        return dataset;
    }

    // CREDITS: https://www.programcreek.com/java-api-examples/?api=org.jfree.chart.renderer.xy.XYSplineRenderer
    // EFFECTS: plots the given course yearly average data and returns it
    private ChartPanel createPlot(XYDataset dataset) {
        XYSplineRenderer renderer = new XYSplineRenderer();

        NumberAxis yearAxis = new NumberAxis("nth Term Since 2014");
        yearAxis.getAutoRangeIncludesZero();

        NumberAxis averageAxis =  new NumberAxis("Average");
        averageAxis.setRange(50, 100);

        XYPlot plot = new XYPlot(dataset, yearAxis, averageAxis, renderer);
        String title = "Course Average Distribution";

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartUtilities.applyCurrentTheme(chart);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setRangeZoomable(false);
        chartPanel.setSize(new Dimension(175, 150));
        return chartPanel;
    }


    // MODIFIES: this, mainMenu
    // EFFECTS: closes the current window and opens the main menu window
    private void goBackToMainMenu() {
        MainMenu mainMenu = new MainMenu(courseList);
        mainMenu.setVisible(true);
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addToCourseList")) {
            courseList.addCourse(course);
        } else if (e.getActionCommand().equals("goBack")) {
            goBackToMainMenu();
        }
    }
}
