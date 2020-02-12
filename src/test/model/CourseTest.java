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

    @Test
    public void testCourse(){
        String profName = "ICHIKAWA, JONATHAN";


        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(79.65);
        courseAverages.add(77.87);
        courseAverages.add(79.96);

        course = new Course("PHIL", "220", "005", profName, courseAverages);
        double courseAverage = (79.65 + 77.87 + 79.96) / 3.0;
        assertEquals(course.getCourseID(), "PHIL");
        assertEquals(course.getCourseNumber(), "220");
        assertEquals(course.getCourseSection(), "005");
        assertEquals(course.getProfName(), "ICHIKAWA, JONATHAN");
        assertEquals(course.getCourseFiveYearAverage(), courseAverage) ;
    }


    @Test
    public void testCourseIncludingZeroAverage(){
        String profName = "BELLEVILE, PATRICE";


        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(69.65);
        courseAverages.add(71.87);
        courseAverages.add(72.96);
        courseAverages.add(0.0);

        course = new Course("CPSC", "121", "202", profName, courseAverages);
        double courseAverage = (69.65 + 71.87 + 72.96) / 3.0;
        assertEquals(course.getCourseID(), "CPSC");
        assertEquals(course.getCourseNumber(), "121");
        assertEquals(course.getCourseSection(), "202");
        assertEquals(course.getProfName(), "BELLEVILE, PATRICE");
        assertEquals(course.getCourseFiveYearAverage(), courseAverage) ;
    }

}



