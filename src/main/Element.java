package main;

/**
 * the design of each of the games systems to reflect a NASA system for Artemis
 * @author tomkilpatrick
 */
public class Element extends Square {
    //final vars for no. of developments per element
    private static final int TOTAL_MINOR_DEVELOPMENTS = 3;
    private static final int TOTAL_MAJOR_DEVELOPMENTS = 1;

    // all element development stage names.
    // kept the same for all elements to allow players to develop some familiarity with development levels
    private static final String MINOR_DEV_LEVEL_ONE_NAME = "Sourcing materials, parts and labour";
    private static final String MINOR_DEV_LEVEL_TWO_NAME = "Construction";
    private static final String MINOR_DEV_LEVEL_THREE_NAME = "Final Assembly";
    private static final String MAJOR_DEV_NAME = "Testing and Commissioning";
    private static final String RENT_COST_NAME = "transfer of securities";

    //instance vars for element information
    private String elementName = "";
    private int currentDevelopmentLevel = 0;
    private int minorDevelopmentCost = 0;
    private int majorDevelopmentCost = 0;
    private int purchaseCost = 0;
    private int baseResidentCost = 0;
    private GameSystem parentSystem = null;
    private String elementOwner = "";

    /**
     * default constructor
     */
    public Element(){
    }

    /**
     * constructor with args
     */
    public Element(String elementName, int minorDevelopmentCost, int majorDevelopmentCost,
                   int purchaseCost, int baseResidentCost, GameSystem parentSystem) {
        this.elementName = elementName;
        this.minorDevelopmentCost = minorDevelopmentCost;
        this.majorDevelopmentCost = majorDevelopmentCost;
        this.purchaseCost = purchaseCost;
        this.baseResidentCost = baseResidentCost;
        this.parentSystem = parentSystem;
    }

    //getters & setters
    /**
     * getter for total minor developments
     * @return
     */
    public static int getTotalMinorDevelopments() {
        return TOTAL_MINOR_DEVELOPMENTS;
    }

    /**
     * getter for total major developments
     * @return
     */
    public static int getTotalMajorDevelopments() {
        return TOTAL_MAJOR_DEVELOPMENTS;
    }

    /**
     * getter for minor dev level one name
     * @return
     */
    public static String getMinorDevLevelOneName() {
        return MINOR_DEV_LEVEL_ONE_NAME;
    }

    /**
     * getter for minor dev level two name
     * @return
     */
    public static String getMinorDevLevelTwoName() {
        return MINOR_DEV_LEVEL_TWO_NAME;
    }

    /**
     * getter for minor dev level three name
     * @return
     */
    public static String getMinorDevLevelThreeName() {
        return MINOR_DEV_LEVEL_THREE_NAME;
    }

    /**
     * getter for major development name
     * @return
     */
    public static String getMajorDevName() {
        return MAJOR_DEV_NAME;
    }

    /**
     * getter for rent cost name
     * @return
     */
    public static String getRentCostName() {
        return RENT_COST_NAME;
    }

    /**
     * getter for elementName
     * @return elementName
     */
    public String getElementName() {
        return elementName;
    }


    /**
     * setter for elementName
     * @param elementName
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }


    /**
     * getter for currentDevelopmentLevel
     * @return currentDevelopmentLevel
     */
    public int getCurrentDevelopmentLevel() {
        return currentDevelopmentLevel;
    }


    /**
     * setter for currentDevelopmentLevel
     * @param currentDevelopmentLevel -
     */
    public void setCurrentDevelopmentLevel(int currentDevelopmentLevel) {
        this.currentDevelopmentLevel = currentDevelopmentLevel;
    }


    /**
     * getter for minorDevelopmentCost
     * @return minorDevelopmentCost
     */
    public int getMinorDevelopmentCost() {
        return minorDevelopmentCost;
    }


    /**
     * setter for minorDevelopmentCost
     * @param minorDevelopmentCost
     */
    public void setMinorDevelopmentCost(int minorDevelopmentCost) {
        this.minorDevelopmentCost = minorDevelopmentCost;
    }


    /**
     * getter for majorDevelopmentCost
     * @return majorDevelopmentCost
     */
    public int getMajorDevelopmentCost() {
        return majorDevelopmentCost;
    }


    /**
     * setter for majorDevelopmentCost
     * @param majorDevelopmentCost
     */
    public void setMajorDevelopmentCost(int majorDevelopmentCost) {
        this.majorDevelopmentCost = majorDevelopmentCost;
    }


    /**
     * getter for purchaseCost
     * @return purchaseCost
     */
    public int getPurchaseCost() {
        return purchaseCost;
    }


    /**
     * setter for purchaseCost
     * @param purchaseCost
     */
    public void setPurchaseCost(int purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    /**
     * getter for baseResidentCost
     * @return baseResidentCost
     */
    public int getBaseResidentCost() {
        return baseResidentCost;
    }

    /**
     * setter for baseResidentCost
     * @param baseResidentCost
     */
    public void setBaseResidentCost(int baseResidentCost) {
        this.baseResidentCost = baseResidentCost;
    }

    /**
     * getter for elementOwner
     * @return elementOwner
     */
    public String getElementOwner() {
        return elementOwner;
    }

    /**
     * setter for elementOwner
     * @param elementOwner
     */
    public void setElementOwner(String elementOwner) {
        this.elementOwner = elementOwner;
    }

    /**
     * getter for getting the parent system name
     * @return
     */
    public GameSystem getParentSystem() {
        return parentSystem;
    }

    /**
     * setter for the parent system name
     * @param parentSystem
     */
    public void setParentSystem(GameSystem parentSystem) {
        this.parentSystem = parentSystem;
    }

    /**
     * check if the element is fully developed or not
     */
    public boolean checkElementFullyDeveloped() {
        return this.getCurrentDevelopmentLevel() == (getTotalMinorDevelopments() + getTotalMajorDevelopments());
    }

    //calculate resident cost () : int
    //uses baseresident cost + current development level
    /**
     * dynamically calculates the amount a player must pay if they land on this element
     * based on development level
     * @return residentCost
     */
    public int calculateResidentCost() {
        // the resident cost for dev level 0 will be the baseResidentCost
        int residentCost = getBaseResidentCost();

        if (getCurrentDevelopmentLevel() == 1) {
            residentCost = getBaseResidentCost() + 50;
        }else if (getCurrentDevelopmentLevel() == 2) {
            residentCost = getBaseResidentCost() + 100;
        } else if (getCurrentDevelopmentLevel() == 3) {
            residentCost = getBaseResidentCost() + 150;
        } else if (getCurrentDevelopmentLevel() == 4) {
            residentCost = getBaseResidentCost() + 200;
        }
        return residentCost;
    }

    /**
     * check if all minor developments have been completed
     */
    public boolean checkMinorDevCompleted() {
        return this.getCurrentDevelopmentLevel() == getTotalMinorDevelopments();
    }

    /**
     * send the current development name based on object development level
     * @return
     */
    public String getCurrentDevelopmentName() {
        String name = "";
        int currentDevLevel = getCurrentDevelopmentLevel();
        switch(currentDevLevel) {
            case 1:
                name = getMinorDevLevelOneName();
                break;
            case 2:
                name = getMinorDevLevelTwoName();
                break;
            case 3:
                name = getMinorDevLevelThreeName();
                break;
            case 4:
                name = getMajorDevName();
                break;
            default:
                name = "Element currently undeveloped";
                break;
        }
        return name;
    }
}
    
    
    
    
    
    
    
    