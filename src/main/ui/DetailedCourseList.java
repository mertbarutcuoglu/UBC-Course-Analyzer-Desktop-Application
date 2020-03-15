package ui;

import model.CourseList;

import javax.swing.*;
import java.awt.*;

public class DetailedCourseList extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private DetailedCourseListPanel detaledCourseListPanel;

    public DetailedCourseList(CourseList courseList) {
        detaledCourseListPanel = new DetailedCourseListPanel(courseList);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(detaledCourseListPanel);
        pack();
    }
}
