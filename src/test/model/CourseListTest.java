package model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for CourseList class
public class CourseListTest {
    CourseDetailsParser detailsParser;
    CourseList testCourseList;
    Course testCourse1;
    Course testCourse2;

    @BeforeEach
    public void runBefore() {
        testCourseList = new CourseList();
        detailsParser = new CourseDetailsParser();

        String profName1 = null;
        try {
             profName1 = detailsParser.retrieveProfName("PHIL", "220", "005");
        } catch (IOException e) {
            System.out.println("IOException occurred!");
        }
        List<Double> averages1 = new ArrayList<>();
        try {
            averages1 = detailsParser.retrieveFiveYearAverage("PHIL", "220", profName1);
        } catch (ParseException | IOException e) {
            System.out.println("Exception occurred!");
        }
        testCourse1 = new Course("PHIL", "220", "005", profName1, averages1);

        String profName2 = null;
        try {
            profName2 = detailsParser.retrieveProfName("CPSC", "121", "202");
        } catch (IOException e) {
            System.out.println("IOException occurred!");
        }
        List<Double> averages2 = new ArrayList<>();
        try {
            averages2 = detailsParser.retrieveFiveYearAverage("PHIL", "220", profName2);
        } catch (ParseException | IOException e) {
            System.out.println("Exception occurred!");
        }
        testCourse2 = new Course("CPSC", "121", "202", profName2, averages2);
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
        assertEquals(courseListSize, 1);

        testCourseList.removeCourse(1);
        courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 0);
        assertFalse(listOfCourses.contains(testCourse1));
    }

    @Test
    public void removeCourseFromMultipleItemList(){
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 2);

        testCourseList.removeCourse(2);
        courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 1);
        assertFalse(listOfCourses.contains(testCourse2));
        assertTrue(listOfCourses.contains(testCourse1));
    }

}





