package persistence;

import model.Course;
import model.CourseList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {
    CourseList courses;
    @Test
    void testReader(){
        Reader testReader = new Reader();
        assertEquals(";", testReader.DELIMETER);
    }
    @Test
    void testReadACoursesSuccess() {
        try {
            courses = CourseList.getInstance();
            courses.clear(); // since the CourseList is the same across all classes, we have to clear it for other tests
            courses = Reader.readCourses(new File("./data/txt/testCourseListToRead.txt"));

            Course testCourse1 = courses.getCourse(0);
            assertEquals("TEST", testCourse1.getCourseID());
            assertEquals("100", testCourse1.getCourseNumber());
            assertEquals("101", testCourse1.getCourseSection());
            assertEquals("DOE, JOHN", testCourse1.getProfName());
            assertEquals((52.01 + 81.22 + 73.32 + 76.84 + 52.93) / 5, testCourse1.getCourseFiveYearAverage());
            assertEquals(getGradeDistributionSample(), testCourse1.getGradeDistribution());

            Course testCourse2 = courses.getCourse(1);
            assertEquals("TEST", testCourse2.getCourseID());
            assertEquals("101", testCourse2.getCourseNumber());
            assertEquals("102", testCourse2.getCourseSection());
            assertEquals("DOE, SUE", testCourse2.getProfName());
            assertEquals((73.12 + 84.03 + 52.81 + 52.82 + 63.42) / 5, testCourse2.getCourseFiveYearAverage());
            assertEquals(getGradeDistributionSample(), testCourse2.getGradeDistribution());

        } catch (IOException e) {
            fail("IOException occurred!");
        }
    }

    private Map<String, Integer> getGradeDistributionSample() {
        Map<String, Integer> gradeDistributions = new LinkedHashMap<>();
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
