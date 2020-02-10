package model;

import java.util.ArrayList;
import java.util.List;

// Represents a course list having a list of courses.
public class CourseList {
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
        listOfCourses.remove(index);
    }

}
