package ui;

import model.CourseList;

import javax.swing.*;
import java.awt.*;

// Detailed course list page
public class DetailedCourseList extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private CourseList courseList;
    private DetailedCourseListPanel detailedCourseListPanel;

    // Constructs DetailedCourseList for given courseList
    public DetailedCourseList() {
        this.courseList = CourseList.getInstance();
        detailedCourseListPanel = new DetailedCourseListPanel();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addWindowListener(new QuitOptionsPane(this));
        add(detailedCourseListPanel);
        pack();

    }

}
