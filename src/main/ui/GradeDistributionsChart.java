package ui;

import model.Course;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GradeDistributionsChart extends JPanel {
    CategoryDataset dataset;

    public GradeDistributionsChart(Course course) {
        createGradeDistributionDataset(course);
        createGradeDistributionBarChart();
    }

    // MODIFIES: this
    // EFFECTS: creates a bar chart from given dataset for grade distributions
    // Credits: http://zetcode.com/java/jfreechart/
    private void createGradeDistributionBarChart() {
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
        add(chartPanel);
    }

    // EFFECTS: takes the grade distributions of a course and creates a category dataset from it and returns it
    private void createGradeDistributionDataset(Course course) {
        Map<String, Integer> gradeDistributions = course.getGradeDistribution();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Integer> grades = new ArrayList<>(gradeDistributions.values());
        List<String> ranges = new ArrayList<>(gradeDistributions.keySet());
        for (int i = 0; i < grades.size(); i++) {
            dataset.addValue(grades.get(i), course.getCourseFullName(), ranges.get(i));
        }
        this.dataset = dataset;
    }
}
