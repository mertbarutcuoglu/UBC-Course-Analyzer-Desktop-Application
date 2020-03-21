package ui;

import model.Course;
import model.CourseList;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

// CREDITS: http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Ask_user_if_wants_to_quit_via_JOptionPane.htm
public class QuitOptionsPane extends WindowAdapter {
    Component parentComponent;
    CourseList courseList;

    // Constructs a panel in the given component
    public QuitOptionsPane(Component parentComponent, CourseList courseList) {
        this.parentComponent = parentComponent;
        this.courseList = courseList;
    }

    // EFFECTS: Gets activated when user tries to quit, and asks if they want to save the data.
    @Override
    public void windowClosing(WindowEvent evt) {
        Object[] options = {"Save & Quit", "Quit"};
        int answer = JOptionPane.showOptionDialog(parentComponent,
                "Do you want to save before you quit?", "Quit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);

        if (answer == JOptionPane.YES_OPTION) {
            try {
                Writer writer = new Writer(new File("./data/txt/savedCourses.txt"));
                writer.write(courseList);
                writer.close();
                System.exit(0);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error! File Not Found!");
            } catch (UnsupportedEncodingException e) {
                JOptionPane.showMessageDialog(null, "Error! Unsupported encoding!");
            }
        }
        if (answer == JOptionPane.NO_OPTION) {
            System.exit(0);
        }

    }
}
