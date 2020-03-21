package ui;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import model.Course;
import model.CourseDetailsParser;
import model.CourseList;
import model.DataRetriever;
import org.json.simple.parser.ParseException;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// UI Functionality and methods are implemented from Teller App. Link below:
//      https://github.students.cs.ubc.ca/CPSC210/TellerApp/tree/master/src/main/ca/ubc/cpsc210/bank/model

// Course Analyzer desktop application
public class CourseAnalyzer extends JFrame implements ActionListener {
    public static final int WELCOME_WIDTH = 400;
    public static final int WELCOME_HEIGHT = 125;

    private Scanner input;
    private CourseList courseList;
    private static String apiBaseURL = "https://ubcgrades.com/api/grades/"; // API URL to perform requests;

    public CourseAnalyzer() {
        super("Course Analyzer");
        this.courseList = new CourseList();

        displayWelcomeMenu();
    }


    // TODO: Documentation
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

    // TODO: Documentation
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
