package model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseTest {
    Course course;
    CourseDetailsParser courseDetailsParser;

    @Test
    public void testCourse(){
        // expecting to have no errors with valid course details
        // exceptions are tested for CourseDetailsParser
        courseDetailsParser = new CourseDetailsParser();
        String profName = null;
        try {
             profName = courseDetailsParser.retrieveProfName("PHIL", "220","005");
        } catch (IOException e) {
            fail("IOException occurred");
        }

        List<Double> courseAverages = new ArrayList<>();
        try {
            courseAverages = courseDetailsParser.retrieveFiveYearAverage("PHIL", "220", profName);
        } catch (ParseException | IOException e) {
            fail("Exception occurred");
        }
        course = new Course("PHIL", "220", "005", profName, courseAverages);
        double courseAverage = (79.65 + 77.87 + 79.96) / 3.0;
        assertEquals(course.getCourseID(), "PHIL");
        assertEquals(course.getCourseNumber(), "220");
        assertEquals(course.getCourseSection(), "005");
        assertEquals(course.getProfName(), "ICHIKAWA, JONATHAN");
        assertEquals(course.getCourseFiveYearAverage(), courseAverage) ;
    }

}



