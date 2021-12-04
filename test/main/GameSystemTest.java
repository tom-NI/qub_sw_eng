package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the GameSystemTest class used to unit test the methods in the GameSystem class.
 * @author Ryan N
 */

class GameSystemTest {

    GameSystem gameSystem;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of GameSystem for each unit test method
        gameSystem = new GameSystem("Test System 1");
    }

    /**
     * Test method for constructor with args. Testing not null.
     */
    @Test
    void testGameSystemConstructorWithArgs() {
        assertNotNull(gameSystem);
    }

    /**
     * Test method for isOwned and setOwned methods. Testing true.
     */
    @Test
    void testSetOwnedIsOwnedTrue() {
        gameSystem.setOwned(true);
        assertTrue(gameSystem.isOwned());
    }

    /**
     * Test method for isOwned and setOwned methods. Testing false.
     */
    @Test
    void testGetSetOwnedFalse() {
        gameSystem.setOwned(false);
        assertFalse(gameSystem.isOwned());
    }

    /**
     * Test method for isSystemFullyDeveloped and setSystemFullyDeveloped methods. Testing true.
     */
    @Test
    void testSetGetSystemFullyDevelopedTrue() {
        gameSystem.setSystemFullyDeveloped(true);
        assertTrue(gameSystem.isSystemFullyDeveloped());
    }

    /**
     * Test method for isSystemFullyDeveloped and setSystemFullyDeveloped methods. Testing false.
     */
    @Test
    void testSetGetSystemFullyDevelopedFalse() {
        gameSystem.setSystemFullyDeveloped(false);
        assertFalse(gameSystem.isSystemFullyDeveloped());
    }

    /**
     * Test method for getAllElementsWithinSystem and addElementToSystem methods. Testing 2 elements.
     */
    @Test
    void testAddGetElementsWithinSystem() {
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        assertEquals(2, gameSystem.getAllElementsWithinSystem().size());
    }

    /**
     * Test method for getSystemName and setSystemName methods.
     */
    @Test
    void testSetGetSystemName() {
        gameSystem.setSystemName("Changed name 1");
        assertEquals("Changed name 1",gameSystem.getSystemName());
    }

    /**
     * Test method for checkIfSystemIsFullyOwned method. Testing false.
     */
    @Test
    void testCheckIfSystemIsFullyOwnedFalse() {
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);

        assertFalse(gameSystem.checkIfSystemIsFullyOwned());
    }

    /**
     * Test method for checkIfSystemIsFullyOwned method. Testing true.
     */
    @Test
    void testCheckIfSystemIsFullyOwnedTrue() {
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        element1.setElementOwner("Player 1");
        element2.setElementOwner("Player 1");

        assertTrue(gameSystem.checkIfSystemIsFullyOwned());
    }

    /**
     * Test method for checkSystemPartOwnedByAnotherPlayer method. Testing true.
     */
    @Test
    void testCheckSystemPartOwnedByAnotherPlayerTrue() {
        Player player2 = new Player("Player 2");
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        element1.setParentSystem(gameSystem);
        element2.setParentSystem(gameSystem);
        element1.setElementOwner("Player 1");

        assertTrue(GameSystem.checkSystemPartOwnedByAnotherPlayer(player2, element1));

    }

    /**
     * Test method for checkSystemPartOwnedByAnotherPlayer method. Testing false.
     */
    @Test
    void testCheckSystemPartOwnedByAnotherPlayerFalse() {
        Player player2 = new Player("Player 2");
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        element1.setParentSystem(gameSystem);
        element2.setParentSystem(gameSystem);
        element1.setElementOwner("Player 2");

        assertFalse(GameSystem.checkSystemPartOwnedByAnotherPlayer(player2, element1));
    }

    /**
     * Test method for checkSystemPartOwnedByCurrentPlayer method. Testing true.
     */
    @Test
    void testCheckSystemPartOwnedByCurrentPlayerTrue() {
        Player player2 = new Player("Player 2");
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        element1.setParentSystem(gameSystem);
        element2.setParentSystem(gameSystem);
        element1.setElementOwner("Player 2");

        assertTrue(GameSystem.checkSystemPartOwnedByCurrentPlayer(player2, element1));
    }

    /**
     * Test method for checkSystemPartOwnedByCurrentPlayer method. Testing false.
     */
    @Test
    void testCheckSystemPartOwnedByCurrentPlayerFalse() {
        Player player2 = new Player("Player 2");
        Element element1 = new Element();
        Element element2 = new Element();
        gameSystem.addElementToSystem(element1);
        gameSystem.addElementToSystem(element2);
        element1.setParentSystem(gameSystem);
        element2.setParentSystem(gameSystem);
        element1.setElementOwner("Player 1");

        assertFalse(GameSystem.checkSystemPartOwnedByCurrentPlayer(player2, element1));
    }

    /**
     * Test method for updateSystemOwnershipStatus method. Testing true.
     */
    @Test
    void testUpdateSystemOwnershipStatus() {
        Player player1 = new Player("Player 1");
        Element element1 = new Element();
        gameSystem.addElementToSystem(element1);
        element1.setParentSystem(gameSystem);
        element1.setElementOwner("Player 1");
        GameSystem.updateSystemOwnershipStatus(player1, element1);

        assertTrue(gameSystem.isOwned());

    }

    /**
     * Test method for checkSystemFullyDeveloped method. Testing true.
     */
    @Test
    void testCheckSystemFullyDevelopedTrue() {
        Element element1 = new Element();
        gameSystem.addElementToSystem(element1);
        element1.setParentSystem(gameSystem);
        element1.setCurrentDevelopmentLevel(4);

        Element element2 = new Element();
        gameSystem.addElementToSystem(element2);
        element2.setParentSystem(gameSystem);
        element2.setCurrentDevelopmentLevel(4);

        assertTrue(gameSystem.checkSystemFullyDeveloped());

    }
    /**
     * Test method for checkSystemFullyDeveloped method. Testing false.
     */
    @Test
    void testCheckSystemFullyDevelopedFalse() {
        Element element1 = new Element();
        gameSystem.addElementToSystem(element1);
        element1.setParentSystem(gameSystem);
        element1.setCurrentDevelopmentLevel(4);

        Element element2 = new Element();
        gameSystem.addElementToSystem(element2);
        element2.setParentSystem(gameSystem);
        element2.setCurrentDevelopmentLevel(3);

        assertFalse(gameSystem.checkSystemFullyDeveloped());

    }


}