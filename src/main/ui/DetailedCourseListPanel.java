package ui;

import model.Course;
import javax.swing.*;
import java.awt.*;

// Panel for DetailedCourseList
public class DetailedCourseListPanel extends CourseListPanel {

    private JButton viewCourseButton;
    private JButton goBackButton;

    // Constructs DetailedCourseListPanel for given courseList, also adds a view course details button
    public DetailedCourseListPanel() {
        super();
        super.descriptionLabel.setText("My Courses: (Name Number Section Average)");
        super.descriptionLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        super.setSize(800, 200);
        super.courseNamesList.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

        goBackButton = new JButton("Back to Main Menu");
        goBackButton.setActionCommand("back");
        goBackButton.addActionListener(this);
        super.optionButtonsPanel.add(goBackButton, BorderLayout.SOUTH);

        viewCourseButton = new JButton("View Course Details");
        viewCourseButton.setActionCommand("seeCourse");
        viewCourseButton.addActionListener(this);
        add(viewCourseButton, BorderLayout.EAST);
    }

    @Override
    protected DefaultListModel setupCourseNamesList() {
        DefaultListModel courseNames = new DefaultListModel();
        for (Course c: courseList) {
            courseNames.addElement(c.getCourseFullName() + " " + c.getCourseFiveYearAverage());
        }
        return courseNames;
    }
}
