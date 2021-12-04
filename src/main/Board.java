package main;
import java.util.ArrayList;

/**
 * the board object for the game, stores all squares for the game
 * @author Kerrie and tomkilpatrick
 */
public class Board {
    // make an arraylist of all game squares.
    private static final ArrayList<Object> ALL_GAME_SQUARES = new ArrayList<>();

    // make an arraylist of all game systems
    private static final ArrayList<GameSystem> ALL_GAME_SYSTEMS = new ArrayList<>();

    // control variable for a game completion
    private static boolean boardFullyDeveloped = false;

    /**
     * default constructor
     */
    public Board() {
    }

    /**
     * getter for the board being fully developed
     * @return - whether or not the board is fully developed
     */
    public static boolean isBoardFullyDeveloped() {
        return boardFullyDeveloped;
    }

    /**
     * setter for the board being fully developed
     * @param boardFullyDeveloped - the boolean to determine if the board is fully developed
     */
    public static void setBoardFullyDeveloped(boolean boardFullyDeveloped) {
        Board.boardFullyDeveloped = boardFullyDeveloped;
    }

    /**
     * getter for all game squares
     * @return - the arraylist of all game squares
     */
    public static ArrayList<Object> getAllGameSquares() {
        return ALL_GAME_SQUARES;
    }

    /**
     * getter for all game systems
     * @return - the arraylist of all game systems
     */
    public static ArrayList<GameSystem> getAllGameSystems() {
        return ALL_GAME_SYSTEMS;
    }

    /**
     * add a game square to the arraylist that tracks game squares
     * @param squareToAdd - square to add the the all game squares arraylist
     */
    public void addGameSquare(Object squareToAdd) {
        getAllGameSquares().add(squareToAdd);
    }

    /**
     * add a game system to the arraylist that tracks systems
     * @param systemToAdd - gameSystem to add the to arraylist
     */
    public void addGameSystem(GameSystem systemToAdd) {
        getAllGameSystems().add(systemToAdd);
    }

    /**
     * count the total elements on the board dynamically
     * @return - number of elements
     */
    public static int countTotalElements() {
        int elementCounter = 0;
        for (Object element : getAllGameSquares()) {
            if (element instanceof Element) {
                elementCounter++;
            }
        }
        return elementCounter;
    }

    /**
     * function to check if the board is fully developed
     */
    public void checkBoardFullyDeveloped() {
        int totalElementCount = 0;
        int fullyDevelopedElementCount = 0;

        for (Object gameSquare: getAllGameSquares()) {
            if (gameSquare instanceof Element) {
                totalElementCount++;
                Element currentGameSquare = (Element) gameSquare;

                // check if the square is fully developed
                if (currentGameSquare.checkElementFullyDeveloped()) {
                    fullyDevelopedElementCount++;
                }
            }
        }
        if (totalElementCount == fullyDevelopedElementCount) {
            // board is fully developed, so set it
            setBoardFullyDeveloped(true);
        }
    }

    /**
     * check if a player passed go or not
     * @param diceResult
     * @param playerPosition
     * @return
     */
    public boolean checkPlayerPassedStart(int diceResult, int playerPosition) {
        // assumes that start square is always the first square on the board!
        return (diceResult + playerPosition) > (getAllGameSquares().size());
    }

    /**
     * move a player to their new place on the board
     * @param diceResult
     * @param previousPlayerPosition
     * @return - new players position as calculated
     */
    public int movePlayer(int diceResult, int previousPlayerPosition) {
        return (previousPlayerPosition + diceResult) % getAllGameSquares().size();
    }

    /**
     * output the final board start for the end of the game
     * generally used when players have quit the game early
     */
    public static void printBoardFinalState() {
        StringBuilder allDevelopedElements = new StringBuilder();
        StringBuilder unfinishedElements = new StringBuilder();
        StringBuilder unStartedElements = new StringBuilder();
        int totalElementsDeveloped = 0;

        // loop through all board squares to get the final state of elements
        for (Object element : getAllGameSquares()) {
            // only print info from the squares that are elements, not blank or start
            if (element instanceof Element) {
                if (((Element) element).checkElementFullyDeveloped()) {
                    // get all fully developed elements first
                    allDevelopedElements.append("\t");
                    allDevelopedElements.append(((Element) element).getElementName());
                    allDevelopedElements.append(" (part of the ");
                    allDevelopedElements.append(((Element) element).getParentSystem().getSystemName());
                    allDevelopedElements.append(" system)");
                    allDevelopedElements.append("\n");
                    totalElementsDeveloped++;
                } else if (((Element) element).getCurrentDevelopmentLevel() > 0) {
                    // now get info on each development that is started but unfinished
                    unfinishedElements.append("\tThe ");
                    unfinishedElements.append(((Element) element).getElementName());
                    unfinishedElements.append(" final development level was level ");
                    unfinishedElements.append(((Element) element).getCurrentDevelopmentLevel());
                    unfinishedElements.append(" - (");
                    unfinishedElements.append(((Element) element).getCurrentDevelopmentName());
                    unfinishedElements.append(")\n");
                } else {
                    // then get elements that have never been started!
                    unStartedElements.append(((Element) element).getElementName()).append(", ");
                }
            }
        }
        // now print finished elements, unfinished elements and never started elements.
        if (totalElementsDeveloped == 0 && unfinishedElements.length() == 0) {
            // none of the game was developed
            System.out.println("Nothing was developed for the entire game!");
        } else if (countTotalElements() == totalElementsDeveloped) {
            // entire game was developed
            // handled at the end of the main game while loop
        } else {
            // the game was partly developed - split up and show the three states;
            // fully developed
            if (totalElementsDeveloped > 0) {
                if (totalElementsDeveloped == 1) {
                    System.out.println(totalElementsDeveloped + " element was fully developed;");
                } else {
                    System.out.println(totalElementsDeveloped + " elements were fully developed, they were;");
                }
                System.out.println(allDevelopedElements);
            }
            // partly developed
            if (unfinishedElements.length() > 0) {
                System.out.println("\nThese elements were partially developed;");
                System.out.println(unfinishedElements);
            }
            // not developed at all
            if (unStartedElements.length() > 0) {
                System.out.println("The following elements never had development commenced;\n"  + unStartedElements);
            }
        }
    }
}
