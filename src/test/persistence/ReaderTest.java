package persistence;

import model.Course;
import model.CourseList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {

    @Test
    void testReader(){
        Reader testReader = new Reader();
        assertEquals(";", testReader.DELIMETER);
    }
    @Test
    void testReadAccountsSuccess() {
        try {
            CourseList courses = Reader.readCourses(new File("./data/txt/testCourseListToRead.txt"));

            Course testCourse1 = courses.getCourse(0);
            assertEquals("TEST", testCourse1.getCourseID());
            assertEquals("100", testCourse1.getCourseNumber());
            assertEquals("101", testCourse1.getCourseSection());
            assertEquals("DOE, JOHN", testCourse1.getProfName());
            assertEquals((52.01 + 81.22 + 73.32 + 76.84 + 52.93) / 5, testCourse1.getCourseFiveYearAverage());

            Course testCourse2 = courses.getCourse(1);
            assertEquals("TEST", testCourse2.getCourseID());
            assertEquals("101", testCourse2.getCourseNumber());
            assertEquals("102", testCourse2.getCourseSection());
            assertEquals("DOE, SUE", testCourse2.getProfName());
            assertEquals((73.12 + 84.03 + 52.81 + 52.82 + 63.42) / 5, testCourse2.getCourseFiveYearAverage());
        } catch (IOException e) {
            fail("IOException occurred!");
        }


    }
}
