package ui;

import model.Course;
import model.CourseList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CourseListPanel extends JPanel  implements ActionListener {
    private DefaultListModel courseNames;
    private JList courseNamesList;
    private JScrollPane courseNamesScrollPane;
    private JLabel descriptionLabel;
    private JButton removeCourseButton;
    private CourseList courseList;

    // TODO: Documentation
    public CourseListPanel(CourseList courseList) {
        courseNames = setupCourseNamesList(courseList);
        this.courseList = courseList;
        setLayout(new BorderLayout()); // Panel will have BorderLayout as LayoutManager for the desired orientation

        courseNamesList = new JList(courseNames);
        courseNamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courseNamesList.setLayoutOrientation(JList.VERTICAL);
        courseNamesList.setSelectedIndex(0);
        courseNamesList.setVisibleRowCount(5);


        courseNamesScrollPane = new JScrollPane(courseNamesList);
        courseNamesScrollPane.createVerticalScrollBar();

        descriptionLabel = new JLabel("Saved Courses Overview");
        descriptionLabel.setLabelFor(this);

        removeCourseButton = new JButton("Remove Selected Course");
        removeCourseButton.setActionCommand("removeCourse");
        removeCourseButton.addActionListener(this);

        add(courseNamesScrollPane);
        add(descriptionLabel, BorderLayout.NORTH);
        add(removeCourseButton, BorderLayout.AFTER_LAST_LINE);
    }

    // TODO: Documentation
    private DefaultListModel setupCourseNamesList(CourseList courseList) {
        DefaultListModel courseNames = new DefaultListModel();
        List<Course>  courses = courseList.getListOfCourses();
        for (Course c: courses) {
            courseNames.addElement(c.getCourseFullName());
        }
        return courseNames;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("removeCourse")) {
            int selectedIndex = this.courseNamesList.getSelectedIndex();
            if (selectedIndex != -1) {
                this.courseNames.remove(selectedIndex);
                this.courseList.removeCourse(selectedIndex);
            }
        }
    }
}
