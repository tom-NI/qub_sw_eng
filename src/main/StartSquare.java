/**
 * This is the StartSquare class which is a child of the Square class
 */
package main;

/**
 * the square where users start the game and collect money each time they pass
 * @author Ryan N
 */
public class StartSquare extends Square {
    private final static int COLLECT_PASSING_AMOUNT = 200;

    /**
     * name used for the start square throughout the game
     */
    private static final String START_SQUARE_NAME = "Funding Package";

    /**
     * Default Constructor
     */
    public StartSquare() {
    }

    /**
     * getter for the start square name
     * @return
     */
    public static String getStartSquareName() {
        return START_SQUARE_NAME;
    }

    /**
     * Getter for collectPassingAmount
     * @return collectPassingAmount
     */
    public static int getCOLLECT_PASSING_AMOUNT() {
        return COLLECT_PASSING_AMOUNT;
    }
}