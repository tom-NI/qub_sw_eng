/**
 * This is the Square parent class of DoNothingSquare, StartSquare and Element
 */
package main;

/**
 * the specification for a square, abstract as other types of square need to extend it
 * @author Ryan N
 */
public abstract class Square {
    // instance variables
    private int squareNumber;

    /**
     * Default Constructor
     */
    public Square(){
    }

    /**
     * Getter for squareNumber
     * @return squareNumber
     */
    public int getSquareNumber() {
        return squareNumber;
    }

    /**
     * Setter for squareNumber
     * @param squareNumber - the squareNumber var
     */
    public void setSquareNumber(int squareNumber) {
        this.squareNumber = squareNumber;
    }
}
