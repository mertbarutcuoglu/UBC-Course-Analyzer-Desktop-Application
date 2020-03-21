package ui;

import model.CourseList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DetailedCourseList extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private CourseList courseList;

    private DetailedCourseListPanel detailedCourseListPanel;

    public DetailedCourseList(CourseList courseList) {
        this.courseList = courseList;
        detailedCourseListPanel = new DetailedCourseListPanel(courseList);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addWindowListener(new QuitOptionsPane(this, courseList));
        add(detailedCourseListPanel);
        pack();

    }

}
