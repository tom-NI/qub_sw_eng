package main;

import java.util.ArrayList;

/**
 * class for each player in any game
 * @author tomkilpatrick & Kerry
 */
public class Player {
    private static final int PLAYER_INITIAL_SQUARE_NUMBER = 0;
    private static final int MINIMUM_SOLVENT_BALANCE = 1;
    private final int PLAYER_STARTING_MONEY = 800;

    private String playerName = "";
    private int totalTurnsTaken;
    private ArrayList<GameSystem> allOwnedSystems = new ArrayList<>();
    private ArrayList<Element> allPlayersOwnedElements = new ArrayList<>();
    private int totalSystemsFullyDeveloped = 0;
    private int currentSquareNumber = 0;
    private int currentBalance = 0;

    /**
     * constructor using just player name
     * constructor also sets the initial player balance and first square position as these will always be needed to be set at construction
     * @param playerName - the players entered name, previously checked for uniqueness?
     */
    public Player(String playerName) {
        this.playerName = playerName;
        this.setCurrentBalance(getPLAYER_STARTING_MONEY());
        this.setCurrentSquareNumber(getPlayerInitialSquareNumber());
    }

    /**
     * getter for the minimum solvent balance
     * @return
     */
    public static int getMinimumSolventBalance() {
        return MINIMUM_SOLVENT_BALANCE;
    }

    /**
     * getter for a players initial game starting money
     * @return
     */
    public int getPLAYER_STARTING_MONEY() {
        return PLAYER_STARTING_MONEY;
    }

    /**
     * get a players initial square number
     * @return
     */
    public static int getPlayerInitialSquareNumber() {
        return PLAYER_INITIAL_SQUARE_NUMBER;
    }

    /**
     * getter for name
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * setter for player number
     * @param playerNumber
     */
    public void setPlayerNumber(int playerNumber) {
    }

    /**
     * getter for total player moves
     * @return - total players moves in one game
     */
    public int getTotalTurnsTaken() {
        return totalTurnsTaken;
    }

    /**
     * set the total turns taken
     * @param totalTurnsTaken
     */
    public void setTotalTurnsTaken(int totalTurnsTaken) {
        this.totalTurnsTaken = totalTurnsTaken;
    }

    /**
     * getter for a players full list of owned elements
     * @return
     */
    public ArrayList<Element> getAllPlayersOwnedElements() {
        return allPlayersOwnedElements;
    }

    /**
     * add a newly bought element to a players list
     * @param newlyBoughtElement
     */
    public void addOwnedElement(Element newlyBoughtElement) {
        getAllPlayersOwnedElements().add(newlyBoughtElement);
    }

    /**
     * getter for fully developed systems
     * @return
     */
    public int getTotalSystemsFullyDeveloped() {
        return totalSystemsFullyDeveloped;
    }

    /**
     * setter for the systems that player has fully developed
     * @param totalSystemsFullyDeveloped
     */
    public void setTotalSystemsFullyDeveloped(int totalSystemsFullyDeveloped) {
        this.totalSystemsFullyDeveloped = totalSystemsFullyDeveloped;
    }

    /**
     * getter for current square number
     * @return
     */
    public int getCurrentSquareNumber() {
        return currentSquareNumber;
    }

    /**
     * setter for current square number
     * @param currentSquareNumber
     */
    public void setCurrentSquareNumber(int currentSquareNumber) {
        this.currentSquareNumber = currentSquareNumber;
    }

    /**
     * getter for current players balance
     * @return
     */
    public int getCurrentBalance() {
        return currentBalance;
    }

    /**
     * setter for the players current balance
     * @param currentBalance
     */
    public void setCurrentBalance(int currentBalance) {
        this.currentBalance = currentBalance;
    }

    /**
     * get a list of all the names that a player owns
     * @return
     */
    public ArrayList<GameSystem> getAllOwnedSystems() {
        return allOwnedSystems;
    }

    /**
     * add the name of an element that a player owns
     * @param systemName
     */
    public void addOwnedSystems(GameSystem systemName) {
        this.getAllOwnedSystems().add(systemName);
    }

    /**
     * setter for total moves.
     */
    public void incrementTotalTurnsTaken() {
        int totalTurns = getTotalTurnsTaken();
        totalTurns++;
        this.setTotalTurnsTaken(totalTurns);
    }

    /**
     * work out a players new balance after a transaction
     * @param amount - amount to change the balance by
     * @param addingToBalance - a boolean to decide whether amount is being added or subtracted to the balance
     */
    public void calculateAndSetNewBalance(int amount, boolean addingToBalance) {
        int newBalance = 0;
        int currentBal = this.getCurrentBalance();

        if (addingToBalance) {
            newBalance = currentBal + amount;
        } else {
            newBalance = currentBal - amount;
        }
        this.setCurrentBalance(newBalance);
    }

    /**
     * function to check if a player is bankrupt or not
     * only called after spending money
     * @param playerBalance
     * @return
     */
    public boolean checkPlayerBankrupt(int playerBalance) {
        return playerBalance < getMinimumSolventBalance();
    }

    /**
     * check if all a players owned systems are developed
     * @return
     */
    public boolean checkPlayersSystemsAllDeveloped() {
        int allDevelopedSystems = 0;
        boolean allPlayersSystemsAreDeveloped = false;

        for (GameSystem gameSystem : this.getAllOwnedSystems()) {
            if (gameSystem.isSystemFullyDeveloped()) {
                allDevelopedSystems++;
            }
        }
        if (allDevelopedSystems == this.getAllOwnedSystems().size()) {
            allPlayersSystemsAreDeveloped = true;
        }
        return allPlayersSystemsAreDeveloped;
    }

    /**
     * print out all info about a player to screen
     */
    public void printPlayerState(boolean gameIsFinished) {
        String stringToPrint;
        StringBuilder elementState = new StringBuilder();
        String moveVerb = "";
        String systemString = " system";
        String systemsString = " systems";
        String ownedSystemNoun = "";
        String developedSystemNoun = "";

        if (this.getTotalTurnsTaken() == 1) {
            moveVerb = " move";
        } else {
            moveVerb = " moves.";
        }

        if (this.getAllOwnedSystems().size() == 1) {
            ownedSystemNoun = systemString;
        } else {
            ownedSystemNoun = systemsString;
        }
        if (getTotalSystemsFullyDeveloped() == 1) {
            developedSystemNoun = systemString;
        } else {
            developedSystemNoun = systemsString;
        }

        if (gameIsFinished) {
            stringToPrint = getPlayerName() + " owned " + getAllOwnedSystems().size() + ownedSystemNoun + " and developed " +
                    getTotalSystemsFullyDeveloped() + developedSystemNoun + " in " + getTotalTurnsTaken() + moveVerb + " \n" +
                getPlayerName() + " - your final balance is " + getCurrentBalance() + " " + Game.getGameMoneyName() + ".\n";
            System.out.println(stringToPrint);
        } else {
            stringToPrint = "\nHi " + getPlayerName() + ", its your turn.\n" +
            "You currently own " + getAllOwnedSystems().size() + ownedSystemNoun + ", " + getTotalSystemsFullyDeveloped() +
                    " of these are fully developed and you have made " + getTotalTurnsTaken() + moveVerb +
            "\nYour current balance is " + getCurrentBalance() + " " + Game.getGameMoneyName() + ".";
            // now print a players list of elements and their development level, if they have any elements
            if (getAllPlayersOwnedElements().size() > 0) {
                elementState.append("You own the following elements at present;");
                for (Element element : getAllPlayersOwnedElements()) {
                    elementState.append("\n\t")
                        .append(element.getElementName())
                        .append(" (which is part of the ")
                        .append(element.getParentSystem().getSystemName())
                        .append(" system) and is developed to level ")
                        .append(element.getCurrentDevelopmentLevel())
                        .append(" (")
                        .append(element.getCurrentDevelopmentName())
                        .append(")");
                }
            } else {
                elementState.append("You don't currently own any elements");
            }
            System.out.println(stringToPrint);
            System.out.println(elementState);
        }
    }

    /**
     * print out an players acquisition and new balance
     * @param acquiringPlayer
     * @param landedSquare
     */
    public static void printPlayerAcquisition(Player acquiringPlayer, String landedSquare) {
        System.out.println(acquiringPlayer.getPlayerName() + " just bought the " + landedSquare + " element.");
        System.out.println(acquiringPlayer.getPlayerName() + " - your new balance is " + acquiringPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
    }
}

