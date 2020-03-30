package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// Tests for CourseList class
public class CourseListTest {
    Course testCourse1;
    Course testCourse2;
    CourseList testCourseList;

    @BeforeEach
    public void runBefore() {
        String profName = "ICHIKAWA, JONATHAN";
        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        testCourseList = CourseList.getInstance();
        clearCourseList();

        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(79.65);
        courseAverages.add(77.87);
        courseAverages.add(79.96);

        testCourse1 = new Course("PHIL", "220", "005", profName, courseAverages,
                gradeDistributions);

        String profName2 = "BELLEVILE, PATRICE";


        List<Double> courseAverages2 = new ArrayList<>();
        courseAverages.add(73.05);
        courseAverages.add(71.85);
        courseAverages.add(74.26);

        testCourse2 = new Course("CPSC", "121", "202", profName2, courseAverages2,
                gradeDistributions);
    }

    @Test
    public void testGetInstance() {
        int courseListSize = testCourseList.getListOfCourses().size();
        assertEquals(0, courseListSize);
    }

    @Test
    public void addCourseOne() {
        testCourseList.addCourse(testCourse1);
        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();
        assertEquals(1, courseListSize);
        assertTrue(listOfCourses.contains(testCourse1));
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
    public void addCourseMany() {
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);

        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();

        assertEquals(2, courseListSize);
        assertTrue(listOfCourses.contains(testCourse1));
        assertTrue(listOfCourses.contains(testCourse2));
    }



    @Test
    public void removeCourseFromMultipleItemList() {
        testCourseList.addCourse(testCourse1);
        testCourseList.addCourse(testCourse2);
        List<Course> listOfCourses = testCourseList.getListOfCourses();
        int courseListSize = listOfCourses.size();
        assertEquals(2, courseListSize);

        testCourseList.removeCourse(1);
        courseListSize = listOfCourses.size();
        assertEquals(courseListSize, 1);
        assertFalse(listOfCourses.contains(testCourse2));
    }

    // since the CourseList is the same across all classes, removes everything for other class tests
    public void clearCourseList() {
        testCourseList.getListOfCourses().removeAll(testCourseList.getListOfCourses());
    }

    // EFFECTS: creates a sample grade distribution data for test purposes
    private Map<String, Integer> getGradeDistributionSample() {
        Map<String, Integer> gradeDistributions = new HashMap<>();
        gradeDistributions.put("<50%", 0);
        gradeDistributions.put("50-54%", 1);
        gradeDistributions.put("55-59%", 2);
        gradeDistributions.put("60-63%", 3);
        gradeDistributions.put("64-67%", 4);
        gradeDistributions.put("68-71%", 5);
        gradeDistributions.put("72-75%", 6);
        gradeDistributions.put("76-79%", 7);
        gradeDistributions.put("80-84%", 8);
        gradeDistributions.put("85-89%", 9);
        gradeDistributions.put("90-100%", 10);
        return gradeDistributions;
    }

}





