package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        course = new Course("PHIL", "220", "005", profName, courseAverages, gradeDistributions);
        double courseAverage = (79.65 + 77.87 + 79.96) / 3.0;
        assertEquals(course.getCourseID(), "PHIL");
        assertEquals(course.getCourseNumber(), "220");
        assertEquals(course.getCourseSection(), "005");
        assertEquals(course.getProfName(), "ICHIKAWA, JONATHAN");
        assertEquals(course.getCourseFiveYearAverage(), courseAverage) ;
        assertEquals(courseAverages, course.getCourseAveragesForYears());
        assertEquals(gradeDistributions, course.getGradeDistribution());
    }



    @Test
    public void testCourseIncludingZeroAverage(){
        String profName = "BELLEVILE, PATRICE";


        List<Double> courseAverages = new ArrayList<>();
        courseAverages.add(69.65);
        courseAverages.add(71.87);
        courseAverages.add(72.96);
        courseAverages.add(0.0);
        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        course = new Course("CPSC", "121", "202", profName, courseAverages, gradeDistributions);
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
        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        course = new Course("PHIL", "220", "005", profName, courseAverages, gradeDistributions);

        String expectedCourseString = "[ course id: PHIL; course number: 220; course section: 005; " +
                "professor: ICHIKAWA, JONATHAN;  <50%: 0;  50-54%: 1;  55-59%: 2;  60-63%: 3;  64-67%: 4;  " +
                "68-71%: 5;  72-75%: 6;  76-79%: 7;  80-84%: 8;  85-89%: 9;  90-100%: 10; 2014 average: 0.00; " +
                "2015 average: 0.00; 2016 average: 79.65; 2017 average: 77.87; 2018 average: 79.96]";

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
        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        course = new Course("PHIL", "220", "005", profName, courseAverages, gradeDistributions);
        assertEquals("PHIL 220 005", course.getCourseFullName());
    }

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



