package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for CourseList class
public class CourseListTest {
    CourseList testCourseList;
    Course testCourse1;
    Course testCourse2;

    @BeforeEach
    public void runBefore() {
        testCourseList = new CourseList();
        String profName = "ICHIKAWA, JONATHAN";


        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(79.65);
        courseAverages.add(77.87);
        courseAverages.add(79.96);

        testCourse1 = new Course("PHIL", "220", "005", profName, courseAverages);

        String profName2 = "BELLEVILE, PATRICE";


        List<Double> courseAverages2 = new ArrayList<>();
        courseAverages.add(73.05);
        courseAverages.add(71.85);
        courseAverages.add(74.26);

        testCourse2 = new Course("CPSC", "121", "202", profName2, courseAverages2);
    }

    @Test
    public void testCourseList() {
        int courseListSize = testCourseList.getListOfCourses().size();
        assertEquals(courseListSize, 0);
    }

    @Test
    public void addCourseOne() {
        testCourseList.addCourse(testCourse1);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();

        assertEquals(courseListSize, 1);
        assertTrue(listOfCourses.contains(testCourse1));
    }

    @Test
    public void addCourseMany() {
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();

        assertEquals(courseListSize, 2);
        assertTrue(listOfCourses.contains(testCourse1));
        assertTrue(listOfCourses.contains(testCourse2));
    }

    @Test
    public void removeCourseFromSingleItemList() {
        testCourseList.addCourse(testCourse1);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();
        assertEquals(1, courseListSize);

        testCourseList.removeCourse(0);
        courseListSize = listOfCourses.size();
        assertEquals(0, courseListSize);
        assertFalse(listOfCourses.contains(testCourse1));
    }

    @Test
    public void removeCourseFromMultipleItemList(){
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 2);

        testCourseList.removeCourse(1);
        courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 1);
        assertFalse(listOfCourses.contains(testCourse2));
        assertTrue(listOfCourses.contains(testCourse1));
    }

}





