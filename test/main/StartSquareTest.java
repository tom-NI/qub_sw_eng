package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This is the StartSquareTest class used to unit test the methods in the StartSquare class.
 * @author Ryan N
 */

class StartSquareTest {

    StartSquare startSquare;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of StartSquare each unit test method
        startSquare = new StartSquare();
    }

    /**
     * Test method for default constructor. Testing not null.
     */
    @Test
    void testStartSquareDefaultConstructor() {
        assertNotNull(startSquare);
    }

    /**
     * Test method for getStartSquareName method. Testing that it returns the final var in the StartSquare class.
     */
    @Test
    void getStartSquareName() {
        String expected = "Funding Package";
        String actual = StartSquare.getStartSquareName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCOLLECT_PASSING_AMOUNT method. Testing that it returns the final var in the StartSquare class.
     */
    @Test
    void getCOLLECT_PASSING_AMOUNT() {
        int expected = 200;
        int actual = StartSquare.getCOLLECT_PASSING_AMOUNT();
        assertEquals(expected, actual);
    }
}