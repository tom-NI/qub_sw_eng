package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This is the DoNothingSquareTest class used to unit test the methods in the DoNothingSquare class.
 * @author Ryan N
 */

class DoNothingSquareTest {

    DoNothingSquare doNothingSquare;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of DoNothingSquare each unit test method
        doNothingSquare = new DoNothingSquare();
    }

    /**
     * Test method for default constructor. Testing not null.
     */
    @Test
    void testDoNothingSquareDefaultConstructor() {
        assertNotNull(doNothingSquare);
    }

    /**
     * Test method for getSquareNumber and setSquareNumber methods.
     */
    @Test
    void testGetAndSetSquareNumber() {
        int expected = 2;
        doNothingSquare.setSquareNumber(expected);
        int actual = doNothingSquare.getSquareNumber();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getDoNothingSquareName method.
     */
    @Test
    void getDoNothingSquareName() {
        String expected = "Federal Audit";
        String actual = DoNothingSquare.getDoNothingSquareName();
        assertEquals(expected, actual);
    }
}