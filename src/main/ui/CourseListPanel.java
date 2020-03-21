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
    protected JList courseNamesList;
    private JScrollPane courseNamesScrollPane;
    protected JLabel descriptionLabel;
    protected JPanel optionButtonsPanel;
    private JButton removeCourseButton;
    private CourseList courseList;

    // TODO: Documentation
    public CourseListPanel(CourseList courseList) {
        courseNames = setupCourseNamesList(courseList);
        this.courseList = courseList;
        setLayout(new BorderLayout());

        // panel for buttons in the bottom of the content panel
        optionButtonsPanel = new JPanel();
        optionButtonsPanel.setLayout(new BorderLayout());

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
        optionButtonsPanel.add(removeCourseButton, BorderLayout.NORTH);

        add(courseNamesScrollPane);
        add(descriptionLabel, BorderLayout.NORTH);
        add(optionButtonsPanel, BorderLayout.SOUTH);

    }

    // TODO: Documentation
    protected DefaultListModel setupCourseNamesList(CourseList courseList) {
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
        if (e.getActionCommand().equals("seeCourse")) {
            int selectedIndex = this.courseNamesList.getSelectedIndex();
            CoursePage coursePage = new CoursePage(this.courseList.getCourse(selectedIndex),this.courseList);
            coursePage.setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose(); // disposes the window that contains the panel
        }
        if (e.getActionCommand().equals("back")) {
            MainMenu mainMenu = new MainMenu(courseList);
            mainMenu.setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }
}
