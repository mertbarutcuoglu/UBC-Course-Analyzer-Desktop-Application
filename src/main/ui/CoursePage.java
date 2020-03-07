package ui;

import model.Course;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CoursePage extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private JLabel courseNameLabel;
    private JLabel profNameLabel;
    private JLabel fiveYearAverageLabel;
    private GridBagConstraints constraints;

    public CoursePage(Course course) {
        super(course.getCourseFullName());

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();

        setupCourseNameLabel(course);
        setupProfNameLabel(course);
        setupFiveYearAverageLabel(course);

        pack();
    }

    private void setupCourseNameLabel(Course course) {
        courseNameLabel = new JLabel("COURSE: " + course.getCourseFullName());
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(courseNameLabel, constraints);
    }

    private void setupFiveYearAverageLabel(Course course) {
        fiveYearAverageLabel = new JLabel("FIVE YEAR AVERAGE: " + course.getCourseFiveYearAverage());
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(fiveYearAverageLabel, constraints);
    }

    private void setupProfNameLabel(Course course) {
        profNameLabel = new JLabel("PROFESSOR: " + course.getProfName());
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(profNameLabel, constraints);
    }

    // CREDITS: https://www.boraji.com/jfreechart-xy-line-chart-example
    private XYDataset createXYDataset(Course course) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        List<Double> termAverages = course.getCourseAveragesForYears();
        XYSeries termAveragesSeries = new XYSeries("Average");

        int year = 2014;
        for (Double average: termAverages) {
            if (!(average == 0)) {
                termAveragesSeries.add(year, average);
                year++;
            }
        }
        dataset.addSeries(termAveragesSeries);
        return dataset;
    }



}
