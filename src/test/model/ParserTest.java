package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class ParserTest {
    Parser parser;
    @BeforeEach
    public void runBefore(){
        // requires well-formed URL so catch block will never work.
        try {
            parser = new Parser("http://ip.jsontest.com"); // Test URL for parsing
        } catch (MalformedURLException e) {
            fail("Malformel URL");
        }
    }

    @Test
    public void testGetResponseAsString(){
        // requires internet connection, so IOException will never occur.
        String response = null;
        String expectedResponse = "{\"ip\": \"128.189.152.133\"}";
        try {
             response = parser.getResponseAsString();
        } catch (IOException e) {
            fail("IOException occured!");
        }
        assertEquals(response, expectedResponse);
    }
}


