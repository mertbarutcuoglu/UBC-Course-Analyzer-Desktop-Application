package model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseDetailsParserTest {
    CourseDetailsParser detailsParser;

    @BeforeEach
    public void runBefore(){
        detailsParser = new CourseDetailsParser();
    }

    @Test
    public void testRetrieveProfNameWithNoErrors(){
        String profName = null;
        try {
            profName = detailsParser.retrieveProfName("PHIL","220", "005");
        } catch (IOException e) {
            fail("IOException occurred!");
        } catch (IndexOutOfBoundsException e){
            fail("IndexOutOfBondsException occurred!");
        }
        assertEquals("ICHIKAWA, JONATHAN", profName);
    }

    @Test
    public void testRetrieveProfNameWithInvalidInfo(){
        // IndexOutOfBondsException occurs because of the invalid course detail
        String profName = null;
        try {
            profName = detailsParser.retrieveProfName("ASCZ","1230", "123");
        } catch (IOException e) {
            fail("IOException occurred!");
        } catch (IndexOutOfBoundsException e){
            System.out.println("Exception caught!");
        }
    }


    @Test
    public void testRetrieveFiveYearAverage(){
        List<Double> courseAverages = new ArrayList<>();
        List<Double> expectedAverages = new ArrayList<>();
        expectedAverages.add(79.96);
        expectedAverages.add(79.65);
        expectedAverages.add(77.87);
        try {
            courseAverages = detailsParser.retrieveFiveYearAverage("PHIL", "220", "ICHIKAWA, JONATHAN");
        } catch (ParseException e) {
           fail("Parse exception occured!");
        } catch (IOException e) {
            fail("IOException occured");
        }
        assertEquals(courseAverages, expectedAverages);
    }


}
