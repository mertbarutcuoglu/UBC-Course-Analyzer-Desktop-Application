package ui;

import model.Course;
import model.CourseList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private ChartPanel gradeDistributionChart;
    private ChartPanel historicalAverageChart;
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
        createAveragePlot(createXYDatasetForAverages(course));
        add(historicalAverageChart, constraints);
    }

    // MODIFIES: this
    // EFFECTS: creates and places the graphs that plots the five year average
    private void setupGradeDistributionChart(Course course) {
        constraints.gridx = 0;
        constraints.gridy = 4;
        createGradeDistributionBarChart(createGradeDistributionDataset(course));
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


    // CREDITS: https://www.boraji.com/jfreechart-xy-line-chart-example
    // EFFECTS: creates an XYDataset from the five year average data of the given course
    private XYDataset createXYDatasetForAverages(Course course) {
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
    private void createAveragePlot(XYDataset dataset) {
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
        chartPanel.setVisible(false);
        this.historicalAverageChart = chartPanel;
    }

    // EFFECTS: takes the grade distributions of a course and creates a category dataset from it and returns it
    private CategoryDataset createGradeDistributionDataset(Course course) {
        Map<String, Integer> gradeDistributions = course.getGradeDistribution();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Integer> grades = new ArrayList<>(gradeDistributions.values());
        List<String> ranges = new ArrayList<>(gradeDistributions.keySet());
        for (int i = 0; i < grades.size(); i++) {
            dataset.addValue(grades.get(i), course.getCourseFullName(), ranges.get(i));
        }
        return dataset;
    }

    // MODIFIES: this
    // EFFECTS: creates a bar chart from given dataset for grade distributions
    // Credits: http://zetcode.com/java/jfreechart/
    private void createGradeDistributionBarChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Grade Distributions",
                "Grade Ranges",
                "Number of Students",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBackground(Color.white);
        chartPanel.setMouseZoomable(false);
        chartPanel.setSize(new Dimension(175, 150));
        this.gradeDistributionChart = chartPanel;
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
                historicalAverageChart.setVisible(true);
                switchGraphButton.setText("Switch to Grade Distribution Chart");
                this.isGradeDistributionChartVisible = false;
            } else {
                gradeDistributionChart.setVisible(true);
                historicalAverageChart.setVisible(false);
                switchGraphButton.setText("Switch to Historical Average Graph");
                this.isGradeDistributionChartVisible = true;
            }
        }
    }
}
