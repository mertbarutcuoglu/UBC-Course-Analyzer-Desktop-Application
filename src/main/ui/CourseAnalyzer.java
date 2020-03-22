package ui;

import model.CourseList;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

// Course Analyzer Welcome Menu
public class CourseAnalyzer extends JFrame implements ActionListener {
    public static final int WELCOME_WIDTH = 400;
    public static final int WELCOME_HEIGHT = 125;

    private CourseList courseList;

    public CourseAnalyzer() {
        super("Course Analyzer");
        this.courseList = new CourseList();

        displayWelcomeMenu();
    }


    // MODIFIES: this
    // EFFECTS: Prepares and displays the welcome menu and its components
    private void displayWelcomeMenu() {
        setPreferredSize(new Dimension(WELCOME_WIDTH, WELCOME_HEIGHT));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel welcomeMessage = new JLabel("Welcome to Course Analyzer!");
        welcomeMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startNewButton = new JButton("Start!");
        startNewButton.setActionCommand("newSession");
        startNewButton.addActionListener(this);
        startNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loadDataButton = new JButton("Load Data From Previous Session");
        loadDataButton.setActionCommand("loadData");
        loadDataButton.addActionListener(this);
        loadDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(welcomeMessage);
        add(startNewButton);
        add(loadDataButton);
        setVisible(true);
        pack();
    }

    // MODIFIES: this, mainMenu
    // EFFECTS: closes the current window and opens the main menu window
    private void displayMainMenu() {
        MainMenu mainMenu = new MainMenu(courseList);
        mainMenu.setVisible(true);
        dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("newSession")) {
            displayMainMenu();
        } else if (e.getActionCommand().equals("loadData")) {
            try {
                courseList = Reader.readCourses(new File("./data/txt/savedCourses.txt"));
                displayMainMenu();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error! No saved data found!");
            }
        }

    }
}
