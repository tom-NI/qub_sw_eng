package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is the ElementTest class used to unit test the methods in the Element class.
 * @author Ryan N
 */

class ElementTest {

    Element element;

    @BeforeEach
    void setUp() throws Exception {
        // Creates a new instance of Element for each unit test method
        element = new Element();
    }

    /**
     * Test method for default constructor. Testing not null.
     */
    @Test
    void testElementDefaultConstructor() {
        assertNotNull(element);
    }

    /**
     * Test method for the main full constructor
     */
    @Test
    void testElementFullConstructor() {
        GameSystem testSystem = new GameSystem("TestSystem");
        element = new Element("TestName", 50,100, 400, 50, testSystem);
        assertNotNull(element);
    }

    /**
     * Test method for getTotalMinorDevelopments method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getTotalMinorDevelopments() {
        int expected = 3;
        int actual = Element.getTotalMinorDevelopments();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getTotalMajorDevelopments method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getTotalMajorDevelopments() {
        int expected = 1;
        int actual = Element.getTotalMajorDevelopments();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getMinorDevLevelOneName method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getMinorDevLevelOneName() {
        String expected = "Sourcing materials, parts and labour";
        String actual = Element.getMinorDevLevelOneName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getMinorDevLevelTwoName method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getMinorDevLevelTwoName() {
        String expected = "Construction";
        String actual = Element.getMinorDevLevelTwoName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getMinorDevLevelThreeName method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getMinorDevLevelThreeName() {
        String expected = "Final Assembly";
        String actual = Element.getMinorDevLevelThreeName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getMajorDevName method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getMajorDevName() {
        String expected = "Testing and Commissioning";
        String actual = Element.getMajorDevName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getRentCostName method. Testing that it returns the final var in the Element class.
     */
    @Test
    void getRentCostName() {
        String expected = "transfer of securities";
        String actual = Element.getRentCostName();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getting and setting the element name
     */
    @Test
    void testGetAndSetElementName() {
        element.setElementName("Element Name");
        String expected = "Element Name";
        assert(element.getElementName().equals(expected));
    }

    /**
     * Test method for getting a major development cost
     */
    @Test
    void testGetAndSetMajorDevelopmentCost() {
        element.setMajorDevelopmentCost(50);
        assert(element.getMajorDevelopmentCost() == 50);
    }

    /**
     * Test method for getting a major development cost
     */
    @Test
    void testGetAndSetMinorDevelopmentCost() {
        element.setMinorDevelopmentCost(10);
        assert(element.getMinorDevelopmentCost() == 10);
    }

    /**
     * Test method for getPurchaseCost and setPurchaseCost methods.
     */
    @Test
    void testGetAndSetPurchaseCost() {
        int expected = 250;
        element.setPurchaseCost(expected);
        int actual = element.getPurchaseCost();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getBaseResidentCost and setBaseResidentCost methods.
     */
    @Test
    void testGetAndSetResidentCost() {
        int expected = 50;
        element.setBaseResidentCost(expected);
        int actual = element.getBaseResidentCost();
        assertEquals(expected, actual);
    }

    /**
     * Test method for getElementOwner and setElementOwner methods.
     */
    @Test
    void testGetAndSetElementOwner() {
        String expected = "Player 1";
        element.setElementOwner(expected);
        String actual = element.getElementOwner();
        assertEquals(expected, actual);
    }

    /**
     * test setting and getting the parent system
     */
    @Test
    void testGetAndSetParentSystem() {
        GameSystem testGameSystem = new GameSystem("TestGameSystem");
        element.setParentSystem(testGameSystem);
        assert(element.getParentSystem().equals(testGameSystem));
    }

    /**
     * test method to check the checkElementFullyDeveloped() method for a fully developed element
     */
    @Test
    void checkElementFullyDeveloped() {
        element.setCurrentDevelopmentLevel(4);
        assertTrue(element.checkElementFullyDeveloped());
    }

    /**
     * test method to check the checkElementFullyDeveloped() method for a fully developed element
     */
    @Test
    void checkElementNotFullyDeveloped() {
        element.setCurrentDevelopmentLevel(3);
        assertFalse(element.checkElementFullyDeveloped());
    }

    /**
     * Test method for calculateResidentCost method. Testing valid dev level 1.
     */
    @Test
    void calculateResidentCostValid1() {
        element.setCurrentDevelopmentLevel(1);
        element.setBaseResidentCost(50);
        int actual = element.calculateResidentCost();
        int expected = 100;
        assertEquals(expected, actual);
    }

    /**
     * Test method for calculateResidentCost method. Testing valid dev level 2.
     */
    @Test
    void calculateResidentCostValid2() {
        element.setCurrentDevelopmentLevel(2);
        element.setBaseResidentCost(50);
        int actual = element.calculateResidentCost();
        int expected = 150;
        assertEquals(expected, actual);
    }

    /**
     * Test method for calculateResidentCost method. Testing valid dev level 3.
     */
    @Test
    void calculateResidentCostValid3() {
        element.setCurrentDevelopmentLevel(3);
        element.setBaseResidentCost(50);
        int actual = element.calculateResidentCost();
        int expected = 200;
        assertEquals(expected, actual);
    }

    /**
     * Test method for calculateResidentCost method. Testing valid dev level 2.
     */
    @Test
    void calculateResidentCostValid4() {
        element.setCurrentDevelopmentLevel(4);
        element.setBaseResidentCost(50);
        int actual = element.calculateResidentCost();
        int expected = 250;
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCurrentDevelopmentName method. Testing valid dev level 1.
     */
    @Test
    void getCurrentDevelopmentNameValid1() {
        element.setCurrentDevelopmentLevel(1);
        String actual = element.getCurrentDevelopmentName();
        String expected = "Sourcing materials, parts and labour";
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCurrentDevelopmentName method. Testing valid dev level 2.
     */
    @Test
    void getCurrentDevelopmentNameValid2() {
        element.setCurrentDevelopmentLevel(2);
        String actual = element.getCurrentDevelopmentName();
        String expected = "Construction";
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCurrentDevelopmentName method. Testing valid dev level 3.
     */
    @Test
    void getCurrentDevelopmentNameValid3() {
        element.setCurrentDevelopmentLevel(3);
        String actual = element.getCurrentDevelopmentName();
        String expected = "Final Assembly";
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCurrentDevelopmentName method. Testing valid dev level 4.
     */
    @Test
    void getCurrentDevelopmentNameValid4() {
        element.setCurrentDevelopmentLevel(4);
        String actual = element.getCurrentDevelopmentName();
        String expected = "Testing and Commissioning";
        assertEquals(expected, actual);
    }

    /**
     * Test method for getCurrentDevelopmentName method. Testing valid dev level 0.
     */
    @Test
    void getCurrentDevelopmentNameValid0() {
        element.setCurrentDevelopmentLevel(0);
        String actual = element.getCurrentDevelopmentName();
        String expected = "Element currently undeveloped";
        assertEquals(expected, actual);
    }

    /**
     * Test method for checkMinorDevCompleted method. Testing false.
     */
    @Test
    void testCheckMinorDevCompletedFalse() {
    element.setCurrentDevelopmentLevel(1);
    assertFalse(element.checkMinorDevCompleted());
    }

    /**
     * Test method for checkMinorDevCompleted method. Testing true.
     */
    @Test
    void testCheckMinorDevCompletedTrue() {
        element.setCurrentDevelopmentLevel(3);
        assertTrue(element.checkMinorDevCompleted());
    }


}