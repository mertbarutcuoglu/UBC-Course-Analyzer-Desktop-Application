package persistence;

import model.Course;
import model.CourseList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/txt/testCourseListToWrite.txt";
    private Writer testWriter;
    private Course course1;
    private Course course2;
    private CourseList testList;

    @BeforeEach
    void runBefore() {
        try {
            testWriter = new Writer(new File(TEST_FILE));
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException!");
        } catch (UnsupportedEncodingException e) {
            fail("UnsupportedEncodingException!");
        }
        List<Double> course1Averages = new ArrayList<>();
        course1Averages.add(70.01);
        course1Averages.add(80.02);
        course1Averages.add(72.33);
        course1Averages.add(78.84);
        course1Averages.add(62.93);
        Map<String, Integer> gradeDistributions = getGradeDistributionSample();

        course1 = new Course("PHIL", "220", "005", "ICHIKAWA, JONATHAN",
                course1Averages, gradeDistributions);

        List<Double> course2Averages = new ArrayList<>();
        course2Averages.add(73.00);
        course2Averages.add(81.03);
        course2Averages.add(72.81);
        course2Averages.add(62.13);
        course2Averages.add(53.40);
        course2 = new Course("PHIL", "220", "003", "AYDEDE, MURAT",
                course2Averages, gradeDistributions);

        testList = new CourseList();
        testList.addCourse(course1);
        testList.addCourse(course2);
    }

    @Test
    void testSaveCourseList() {
        testWriter.write(testList);
        testWriter.close();

        try {
            CourseList savedCourses = Reader.readCourses(new File(TEST_FILE));
            Course testCourse1 = savedCourses.getCourse(0);
            assertEquals("PHIL", testCourse1.getCourseID());
            assertEquals("220", testCourse1.getCourseNumber());
            assertEquals("005", testCourse1.getCourseSection());
            assertEquals("ICHIKAWA, JONATHAN", testCourse1.getProfName());
            assertEquals((70.01 + 80.02 + 72.33 + 78.84 + 62.93) / 5, testCourse1.getCourseFiveYearAverage());
            assertEquals(getGradeDistributionSample(), testCourse1.getGradeDistribution());

            Course testCourse2 = savedCourses.getCourse(1);
            assertEquals("PHIL", testCourse2.getCourseID());
            assertEquals("220", testCourse2.getCourseNumber());
            assertEquals("003", testCourse2.getCourseSection());
            assertEquals("AYDEDE, MURAT", testCourse2.getProfName());
            assertEquals((73.00 + 81.03 + 72.81 + 62.13 + 53.40) / 5, testCourse2.getCourseFiveYearAverage());
            assertEquals(getGradeDistributionSample(), testCourse2.getGradeDistribution());


        } catch (IOException e) {
            fail("IOException occured!" );
        }
    }

    @Test
    void testSaveCourseListIOException() {
        try {
            Reader.readCourses(new File("./path/to/fail.txt"));
        } catch (IOException e) {
            System.out.println("IOException successfully! ");
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
