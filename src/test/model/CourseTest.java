package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(courseAverages, course.getCourseAveragesForYears());
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

    @Test
    public void testToString(){
        String profName = "ICHIKAWA, JONATHAN";


        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(0.0);
        courseAverages.add(0.0);
        courseAverages.add(79.65);
        courseAverages.add(77.87);
        courseAverages.add(79.96);

        course = new Course("PHIL", "220", "005", profName, courseAverages);

        String expectedCourseString = "[ course id: PHIL; course number: 220; course section: 005; " +
                "professor: ICHIKAWA, JONATHAN; 2014 average: 0.00; 2015 average: 0.00; " +
                "2016 average: 79.65; 2017 average: 77.87; 2018 average: 79.96]";

        String courseString = course.toString();

        assertEquals(expectedCourseString, courseString);

    }

    @Test
    public void testGetFullCourseName(){
        String profName = "ICHIKAWA, JONATHAN";

        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(79.65);
        courseAverages.add(77.87);
        courseAverages.add(79.96);

        course = new Course("PHIL", "220", "005", profName, courseAverages);
        assertEquals("PHIL 220 005", course.getCourseFullName());
    }

}



