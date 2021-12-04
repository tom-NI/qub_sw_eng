/**
 * This is the DoNothingSquare class which is a child of the Square class
 */
package main;

/**
 * the square that no player can buy or develop
 * @author Ryan N
 */
public class DoNothingSquare extends Square {
    /**
     * the name used for the blank (do nothing) square throughout the game
     */
    private static final String DO_NOTHING_SQUARE_NAME = "Federal Audit";

    /**
     * Default Constructor
     */
    public DoNothingSquare() {
    }

    /**
     * getter for the "do nothing square name" field
     * @return
     */
    public static String getDoNothingSquareName() {
        return DO_NOTHING_SQUARE_NAME;
    }
}
