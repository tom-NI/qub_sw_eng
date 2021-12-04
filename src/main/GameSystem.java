package main;
import java.util.ArrayList;

/**
 * the design of each of the games systems to reflect a NASA system for Artemis
 * @author tomkilpatrick
 */
public class GameSystem {
    private boolean isOwned;
    private boolean systemFullyDeveloped;
    private final ArrayList<Element> allElementsWithinSystem = new ArrayList<>();
    private String systemName = "";

    /**
     * constructor
     */
    public GameSystem(String systemName) {
        this.isOwned = false;
        this.systemFullyDeveloped = false;
        this.systemName = systemName;
    }

    /**
     * getter to see if the system is owned
     * @return - whether the system is owned or not
     */
    public boolean isOwned() {
        return isOwned;
    }

    /**
     * setter for the owned attribute
     * @param owned - set the owned parameter
     */
    public void setOwned(boolean owned) {
        isOwned = owned;
    }

    /**
     * getter to see if the system is fully developed
     * @return - system is fully developed or not
     */
    public final boolean isSystemFullyDeveloped() {
        return systemFullyDeveloped;
    }

    /**
     * setter for system fully developed
     * @param systemFullyDeveloped - set whether the system is developed or not
     */
    public final void setSystemFullyDeveloped(boolean systemFullyDeveloped) {
        this.systemFullyDeveloped = systemFullyDeveloped;
    }

    /**
     * get the all elements arraylist
     * @return
     */
    public final ArrayList<Element> getAllElementsWithinSystem() {
        return allElementsWithinSystem;
    }

    /**
     * add an element to the arraylist for all elements
     * @param elementName - the element to add the to arraylist
     */
    public final void addElementToSystem(Element elementName) {
        allElementsWithinSystem.add(elementName);
    }

    /**
     * getter for the system name
     * @return
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * setter for the system name
     * @param systemName
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    /**
     * method to maintain the state of the system development boolean
     * call this after a player acquires an element
     */
    public final boolean checkIfSystemIsFullyOwned() {
        int elementsOwned = 0;
        boolean systemIsFullyOwned = false;

        for (Element element : getAllElementsWithinSystem()) {
            if (element.getElementOwner().length() > 0) {
                elementsOwned++;
            }
        }
        if (elementsOwned == getAllElementsWithinSystem().size()) {
            systemIsFullyOwned = true;
        }
        return systemIsFullyOwned;
    }

    /**
     * check if a system is part owned by another player
     * @param currentPlayer
     * @param landedSquare
     * @return
     */
    public static boolean checkSystemPartOwnedByAnotherPlayer(Player currentPlayer, Element landedSquare) {
        boolean systemIsOwnedByAnotherPlayer = false;
        GameSystem gameSystemToCheck = landedSquare.getParentSystem();

        for (Element element : gameSystemToCheck.getAllElementsWithinSystem()) {
            if (element.getElementOwner().length() > 0 && !element.getElementOwner().equals(currentPlayer.getPlayerName())) {
                systemIsOwnedByAnotherPlayer = true;
            }
        }
        return systemIsOwnedByAnotherPlayer;
    }

    /**
     * check if a system is part owned by current player
     * @param currentPlayer
     * @param landedSquare
     * @return
     */
    public static boolean checkSystemPartOwnedByCurrentPlayer(Player currentPlayer, Element landedSquare) {
        boolean systemIsPartOwnedByCurrentPlayer = false;
        GameSystem gameSystemToCheck = landedSquare.getParentSystem();

        for (Element element : gameSystemToCheck.getAllElementsWithinSystem()) {
            if (element.getElementOwner().length() > 0 && element.getElementOwner().equals(currentPlayer.getPlayerName())) {
                systemIsPartOwnedByCurrentPlayer = true;
            }
        }
        return systemIsPartOwnedByCurrentPlayer;
    }

    /**
     * control method to update a systems ownership status and linked objects
     * @param currentPlayer
     * @param landedSquare
     */
    public static void updateSystemOwnershipStatus(Player currentPlayer, Element landedSquare) {
        // check and update if a system is now fully owned
        // if the system is owned, increment the players ownerships
        if (landedSquare.getParentSystem().checkIfSystemIsFullyOwned()) {
            landedSquare.getParentSystem().setOwned(true);

            // now increment the number of systems a player owns
            currentPlayer.addOwnedSystems(landedSquare.getParentSystem());
        }
    }

    /**
     * check if the system is fully developed or not
     */
    public boolean checkSystemFullyDeveloped() {
        int fullyDevelopedCounter = 0;
        boolean isFullyDeveloped = false;

        for (Element element : this.getAllElementsWithinSystem()) {
            if (element.checkElementFullyDeveloped()) {
                fullyDevelopedCounter++;
            }
        }
        if (fullyDevelopedCounter == this.getAllElementsWithinSystem().size()) {
            isFullyDeveloped = true;
        }
        return isFullyDeveloped;
    }
}
