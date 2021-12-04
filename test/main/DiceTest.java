package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the DiceTest class used to unit test the methods in the Dice class.
 * @author Ryan N
 */

class DiceTest {

    Dice dice;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of Dice each unit test method
        dice = new Dice();
    }

    /**
     * Test method for default constructor. Testing not null.
     */
    @Test
    void testDiceDefaultConstructor() {
        assertNotNull(dice);
    }

    @Test
    void testGetMaxNumber() {
        int expected = 6;
        assertEquals(expected, Dice.getMaxNumber());
    }

    @Test
    void testGetMinNumber() {
        int expected = 1;
        assertEquals(expected, Dice.getMinNumber());
    }

    @Test
    void testRollDice() {
        int maxTotal = 12;
        int minTotal = 2;
        assertTrue(minTotal <= dice.rollDice() && dice.rollDice() <= maxTotal);
    }
}