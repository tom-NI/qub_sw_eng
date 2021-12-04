package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the BoardTest class used to unit test the methods in the Board class.
 * @author Ryan N
 */

class BoardTest {

    Board board;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of Board each unit test method
         board = new Board();
    }

    /**
     * Test method for default constructor. Testing not null.
     */
    @Test
    void testBoardDefaultConstructor() {
        assertNotNull(board);
    }

    /**
     * Test method for setBoardFullyDeveloped and isBoardFullyDeveloped methods. Testing true.
     */
    @Test
    void testGetSetBoardFullyDevelopedTrue() {
        Board.setBoardFullyDeveloped(true);
        assertTrue(Board.isBoardFullyDeveloped());
    }

    /**
     * Test method for setBoardFullyDeveloped and isBoardFullyDeveloped methods. Testing false.
     */
    @Test
    void testGetSetBoardFullyDevelopedFalse() {
        Board.setBoardFullyDeveloped(false);
        assertFalse(Board.isBoardFullyDeveloped());
    }

    //TODO COMPLETE BOARD UNIT TESTS (does a full board need to be generated before testing these?)
    /**
     * Test method for getAllGameSquares method.
     */
    @Test
    void testGetAllGameSquares() {
        StartSquare startSquare = new StartSquare();
        board.addGameSquare(startSquare);
        assertEquals(Board.getAllGameSquares().size(), 1);

    }

    @Test
    void getAllGameSystems() {
        GameSystem testGameSystem1 = new GameSystem( "Test System 1");
        GameSystem testGameSystem2 = new GameSystem("Test System 2");

        ArrayList<GameSystem> testingArrayList = new ArrayList<>();
        testingArrayList.add(testGameSystem1);
        testingArrayList.add(testGameSystem2);
        board.addGameSystem(testGameSystem1);
        board.addGameSystem(testGameSystem2);
        assert(Board.getAllGameSystems().equals(testingArrayList));
    }

    @Test
    void addGameSquare() {
    }

    @Test
    void addGameSystem() {
    }

    @Test
    void countTotalElements() {
    }

    @Test
    void checkBoardFullyDeveloped() {
    }

    @Test
    void checkPlayerPassedStart() {
    }

    @Test
    void movePlayer() {
    }

    @Test
    void printBoardFinalState() {
    }
}