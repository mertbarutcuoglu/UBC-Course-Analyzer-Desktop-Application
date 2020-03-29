package model;

import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Represents a course list having a list of courses.
public class CourseList implements Saveable {
    private List<Course> listOfCourses;
    private static CourseList instance;

    //EFFECTS: construct an empty course list
    private CourseList() {
        listOfCourses = new ArrayList<>();
    }

    // EFFECTS: returns the courseList as an instance, if not instantiated, creates a new one
    public static CourseList getInstance() {
        if (instance == null) {
            instance = new CourseList();
        }
        return instance;
    }

    // MODIFIES: this
    // EFFECTS: adds a course to course list
    public void addCourse(Course course) {
        listOfCourses.add(course);
    }

    // MODIFIES: this
    // EFFECTS: removes the course at given index, i
    // REQUIRES: index < listOfCourse.size()
    public void removeCourse(int index) {
        listOfCourses.remove(index);
    }

    // EFFECTS: returns the course in the given index, i
    // REQUIRES: index < listOfCourse.size()
    public Course getCourse(int index) {
        return listOfCourses.get(index);
    }

    // EFFECTS: returns the course list
    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    @Override
    public void save(PrintWriter printWriter) {
        for (Course course : listOfCourses) {
            course.save(printWriter);
        }
    }
}
