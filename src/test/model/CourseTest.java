package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {
    Course testCourse;

    @BeforeEach
    public void runBefore(){
        testCourse = new Course("PHIL", "220", "005");
    }

    @Test
    public void testCourse(){
        double courseAverage = (79.65 + 77.87 + 79.96) / 3.0;
        assertEquals(testCourse.getCourseID(), "PHIL");
        assertEquals(testCourse.getCourseNumber(), "220");
        assertEquals(testCourse.getCourseSection(), "005");
        assertEquals(testCourse.getProfName(), "ICHIKAWA, JONATHAN");
        assertEquals(testCourse.getCourseFiveYearAverage(), courseAverage) ;
    }

}
