package model;

import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// Represents a course list having a list of courses.
public class CourseList implements Saveable {
    private List<Course> listOfCourses;

    //EFFECTS: construct an empty course list
    public CourseList() {
        listOfCourses = new ArrayList<>();
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
        listOfCourses.remove(index - 1);
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
