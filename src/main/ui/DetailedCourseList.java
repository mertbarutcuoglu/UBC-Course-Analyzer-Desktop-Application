package ui;

import model.CourseList;

import javax.swing.*;
import java.awt.*;

// Detailed course list page
public class DetailedCourseList extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private DetailedCourseListPanel detailedCourseListPanel;
    private QuitOptionsPane quitOptionsPane;

    // Constructs DetailedCourseList for given courseList
    public DetailedCourseList() {
        detailedCourseListPanel = new DetailedCourseListPanel();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.quitOptionsPane = new QuitOptionsPane(this);
        addWindowListener(quitOptionsPane);
        add(detailedCourseListPanel);
        pack();

    }

}
