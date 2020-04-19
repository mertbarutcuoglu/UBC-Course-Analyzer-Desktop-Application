package ui;

import model.Course;
import model.CourseList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// Panel that displays saved courses
public class CourseListPanel extends JPanel  implements ActionListener {
    protected JList courseNamesList;
    protected CourseList courseList;

    private DefaultListModel courseNames;
    private JScrollPane courseNamesScrollPane;
    protected JLabel descriptionLabel;
    protected JPanel optionButtonsPanel;
    private JButton removeCourseButton;

    // MODIFIES: this
    // EFFECTS: constructs the panel for the given courseList
    public CourseListPanel() {
        this.courseList = CourseList.getInstance();
        courseNames = setupCourseNamesList();
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

    // EFFECTS: creates a DefaultListModel from given CourseList
    protected DefaultListModel setupCourseNamesList() {
        DefaultListModel courseNames = new DefaultListModel();
        for (Course c: courseList) {
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
            CoursePage coursePage = new CoursePage(this.courseList.getCourse(selectedIndex));
            coursePage.setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose(); // disposes the window that contains the panel
        }
        if (e.getActionCommand().equals("back")) {
            HomePage mainMenu = new HomePage();
            mainMenu.setVisible(true);
            SwingUtilities.getWindowAncestor(this).dispose();
        }
    }
}
