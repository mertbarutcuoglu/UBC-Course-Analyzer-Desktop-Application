package model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CourseDetailsParserTest {
    CourseDetailsParser parser;

    @BeforeEach
    public void runBefore() {
        parser = new CourseDetailsParser();
    }

    @Test
    public void testParseAverageNoErrors() {
        File jsonFile = new File("src/test/model/json/2018W_PHIL_220.json");
        String response = "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();
        List<Double> expectedAverages = new ArrayList<>();
        expectedAverages.add(79.65);
        expectedAverages.add(77.87);

        List<Double> averages = new ArrayList<>();
        try {
            averages = parser.parseAverage(response, "ICHIKAWA, JONATHAN");
        } catch (ParseException e) {
            fail("Parsing error!");
        }
        assertEquals(averages, expectedAverages);
    }

    @Test
    public void testParseAverageWithInvalidJSONResponse(){
        File jsonFile = new File("src/test/model/json/invalidJSONResponse.json");
        String response = "";

        Scanner scanner = null;
        try {
            scanner = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            fail("File not found!");
        }

        while (scanner.hasNext()) {
            response = response + scanner.nextLine();
        }
        scanner.close();

        try {
            List<Double> averages = parser.parseAverage(response, "ICHIKAWA, JONATHAN");
            fail("Didn't catch the exception!");
        } catch (ParseException e) {
            System.out.println("Catched the ParseException!");
        }
    }

    }

