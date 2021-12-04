package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * all the menus required for the game
 * menus written as functions to allow calling up and down menu trees, or recalling a menu if user input is inappropriate
 * @author tomkilpatrick
 */
public class Menus {
    /**
     * menu for a poor input from a user
     */
    private static final String UNKNOWN_INPUT_ALERT = "That option wasn't recognised, please check your entry and try again";

    /**
     * getter for the unknown input menu string
     * @return
     */
    public static String getUnknownInputAlert() {
        return UNKNOWN_INPUT_ALERT;
    }

    /**
     * set the total number of players for the game
     * @param scan
     * @return
     */
    public static void enterNumberOfPlayers(Scanner scan) {
        String totalPlayers = "";
        System.out.println("To start the game, please enter number of players between " +
                ManagePlayers.getMinPlayers() + " and " + ManagePlayers.getMaxPlayers() + "...");

        totalPlayers = scan.next().trim();
        try {
            int numberCheck = Integer.parseInt(totalPlayers);

            // make sure the total players is correct before proceeding!
            if (numberCheck < ManagePlayers.getMinPlayers() || numberCheck > ManagePlayers.getMaxPlayers()) {
                System.out.println("Invalid number, please try again");
                enterNumberOfPlayers(scan);
            } else {
                ManagePlayers.setTotalPlayers(numberCheck);
            }
        } catch (NumberFormatException exception) {
            System.out.println(getUnknownInputAlert());
            enterNumberOfPlayers(scan);
        }
    }


    /**
     * the main game menu from which all others run
     * offer the player chance to roll dice or quit
     * @param scanner
     * @param dice
     * @param board
     * @param currentPlayer
     * @param managePlayers
     * @return
     */
    public static void offerDiceRoll(Scanner scanner, Dice dice, Board board, Player currentPlayer, ManagePlayers managePlayers) {
        System.out.println("Would you like to roll the dice? Please type 'yes' to roll, or 'quit' to quit the game for " +
                "everyone...");
        String playerPlayingOrQuitting = scanner.next().trim();

        if (playerPlayingOrQuitting.equalsIgnoreCase("yes")) {
            // roll dice
            int diceResult = dice.rollDice();

            // add balance if player has passed go OR is on the start square
            if (board.checkPlayerPassedStart(diceResult, currentPlayer.getCurrentSquareNumber())) {
                int collectPassingAmount = StartSquare.getCOLLECT_PASSING_AMOUNT();
                currentPlayer.calculateAndSetNewBalance(collectPassingAmount, true);
                System.out.println("You passed the " + StartSquare.getStartSquareName() + " and collected " + StartSquare.getCOLLECT_PASSING_AMOUNT()
                        + " " + Game.getGameMoneyName() + ", your new balance is " + currentPlayer.getCurrentBalance()
                        + " " + Game.getGameMoneyName());
            }

            // move player to new square on the board based on dice roll
            int newPosition = board.movePlayer(diceResult, currentPlayer.getCurrentSquareNumber());
            currentPlayer.setCurrentSquareNumber(newPosition);

            // increment the players turns by 1
            currentPlayer.incrementTotalTurnsTaken();

            // now check which square player landed on and show appropriate states
            if (board.getAllGameSquares().get(newPosition) instanceof StartSquare) {
                currentPlayer.calculateAndSetNewBalance(StartSquare.getCOLLECT_PASSING_AMOUNT(), true);

                System.out.println(currentPlayer.getPlayerName() + ", you landed on the " + StartSquare.getStartSquareName() + ", and collected "
                        + StartSquare.getCOLLECT_PASSING_AMOUNT() + " " + Game.getGameMoneyName() + ". You cant own or develop this element");
                System.out.println("Your new balance is " + currentPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
            } else if (board.getAllGameSquares().get(newPosition) instanceof DoNothingSquare) {
                System.out.println(currentPlayer.getPlayerName() + ", you have landed on the " +
                        DoNothingSquare.getDoNothingSquareName() + ".  As you are in a Federal building, you cannot purchase this element");
            } else {
                // player is on an element - get the element
                Element landedSquare = (Element) board.getAllGameSquares().get(newPosition);
                System.out.println("You landed on the " + landedSquare.getElementName() + " element, which is part " +
                        "of the " + landedSquare.getParentSystem().getSystemName() + " system");

                // check if square is owned by another player
                if (landedSquare.getElementOwner().length() > 0 && !landedSquare.getElementOwner().equals(currentPlayer.getPlayerName())) {
                    // square is owned by another player so the rent must be offered by currentPlayer
                    int costToOfferOwner = landedSquare.calculateResidentCost();
                    String squareOwnerName = landedSquare.getElementOwner();
                    Player squareOwner = ManagePlayers.findPlayerByName(squareOwnerName);

                    // inform player they landed on an owned element and tell them they need to pay
                    Menus.landedOnOwnedSquareMenu(scanner, squareOwner, currentPlayer, costToOfferOwner);
                } else if (landedSquare.getElementOwner().length() > 0 && landedSquare.getElementOwner().equals(currentPlayer.getPlayerName())) {
                    // Else this square is owned by the current player!
                    System.out.println("You own this element");
                } else {
                    // the square player landed on isn't owned by anyone... so...
                    // first check if another player owns any part of the system to prevent deadlock

                    // count the number of elements within a system that are owned
                    int ownedElements = 0;

                    // set a boolean if > 0 elements are owned
                    boolean partOfSystemOwnedByAnotherPlayer = false;

                    // get the owner(s) name(s) of owned systems for the subsequent menu to display to users
                    String elementOwnerName = null;

                    Player elementOwnerPlayer = null;

                    // get a list of the owned element names for the menu
                    ArrayList<String> ownedElementName = new ArrayList<>();

                    // check current squares parent system to see if any of the other elements within it are owned by any other player
                    if (GameSystem.checkSystemPartOwnedByAnotherPlayer(currentPlayer, landedSquare)) {
                        // record owner and element name(s) if true to display in menus
                        GameSystem gameSystemToCheck = landedSquare.getParentSystem();
                        for (Element element : gameSystemToCheck.getAllElementsWithinSystem()) {
                            if (element.getElementOwner().length() > 0 && !element.getElementOwner().equals(currentPlayer.getPlayerName())) {
                                ownedElements++;
                                elementOwnerName = element.getElementOwner();
                                ownedElementName.add(element.getElementName());
                            }
                        }
                    }

                    // track if part of the system is already owned to change menus appropriately
                    if (ownedElements > 0) {
                        partOfSystemOwnedByAnotherPlayer = true;
                        elementOwnerPlayer = ManagePlayers.findPlayerByName(elementOwnerName);
                    }

                    if (partOfSystemOwnedByAnotherPlayer) {
                        for (String elementName : ownedElementName) {
                            System.out.println("The " + elementName + " is part of this system and is owned by " + elementOwnerName);
                        }

                        Menus.offerElementToSystemOwnerMenu(elementOwnerPlayer, scanner, landedSquare, managePlayers);
                    } else {
                        // none of the system is owned, so offer the current player the chance to buy the square they landed on
                        // submenu for other players to purchase is within the mainElementPurchase menu
                        Menus.mainElementPurchaseMenu(currentPlayer, scanner, landedSquare, managePlayers);
                    }
                }
            }
            // now the player has moved to their new square, they get the chance to develop other owned systems
            if (managePlayers.checkAllPlayersHaveResources()) {
                ManagePlayers.allowDevelopingIfSystemsOwned(currentPlayer, scanner, board, managePlayers);
            }
        } else if (playerPlayingOrQuitting.equalsIgnoreCase("quit")) {
            ManagePlayers.setHasAnyPlayerQuit(true);
            System.out.println(currentPlayer.getPlayerName() + " quit the game before completion.");
        } else {
            System.out.println(Menus.getUnknownInputAlert());
            offerDiceRoll(scanner, dice, board, currentPlayer, managePlayers);
        }
    }

    /**
     * menu to show when a user has landed on an owned element
     * @param scanner
     * @param squareOwner
     * @param currentPlayer
     * @param costToOfferOwner
     */
    public static void landedOnOwnedSquareMenu(Scanner scanner, Player squareOwner, Player currentPlayer, int costToOfferOwner) {
        System.out.println("This is owned by " + squareOwner.getPlayerName() + ". Type yes to offer " +
                squareOwner.getPlayerName() + " the " + Element.getRentCostName() + " sum of " + costToOfferOwner + " " + Game.getGameMoneyName());
        String userEntry = scanner.next().trim();

        // continue the game based on current players entry
        if (userEntry.equalsIgnoreCase("yes")) {
            // allow the owner of a square the chance to accept or reject rent
            Menus.rentOfferDecision(scanner, squareOwner, currentPlayer, costToOfferOwner);
        } else {
            // current player hasn't entered a known answer, ask them to reenter
            System.out.println(Menus.getUnknownInputAlert());
            landedOnOwnedSquareMenu(scanner, squareOwner, currentPlayer, costToOfferOwner);
        }
    }

    /**
     * menu to call when a element owner is offered the cost of sitting on one of their squares
     * @param scanner
     * @param squareOwner
     * @param currentPlayer
     * @param costToOfferOwner
     * @return
     */
    public static void rentOfferDecision(Scanner scanner, Player squareOwner, Player currentPlayer, int costToOfferOwner) {
        System.out.println("\n" +squareOwner.getPlayerName() + " - do you accept or reject the offer of " + costToOfferOwner + " " + Game.getGameMoneyName() + "?");
        System.out.println(currentPlayer.getPlayerName() + " has a current balance of " + currentPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
        System.out.println("You have a current balance of " + squareOwner.getCurrentBalance() + " " + Game.getGameMoneyName());
        System.out.println("Type yes to accept or no to reject");
        String userEntry = scanner.next().trim();

        // decide what to do based on elements owners reply
        if (userEntry.equalsIgnoreCase("yes")) {
            // money switches between players so...
            // add onto the owners balance
            squareOwner.calculateAndSetNewBalance(costToOfferOwner, true);

            // deduct from the residents balance
            currentPlayer.calculateAndSetNewBalance(costToOfferOwner, false);

            // now check if the player who had money removed is now bankrupt and change game state accordingly
            if (currentPlayer.checkPlayerBankrupt(currentPlayer.getCurrentBalance())) {
                ManagePlayers.setAllPlayersHaveResources(false);
                System.out.println(currentPlayer.getPlayerName() + " is bankrupt!  That's the game ended for everyone!");
            } else {
                System.out.println(currentPlayer.getPlayerName() + " - your new balance is " + currentPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
                System.out.println(squareOwner.getPlayerName() + " - your new balance is " + squareOwner.getCurrentBalance() + " " + Game.getGameMoneyName());
            }
        } else if (userEntry.equalsIgnoreCase("no")) {
            System.out.println(squareOwner.getPlayerName() + " didn't want to be paid!");
        } else {
            // square owner hasn't entered an appropriate answer, ask them to reenter
            System.out.println(Menus.getUnknownInputAlert());
            rentOfferDecision(scanner, squareOwner, currentPlayer, costToOfferOwner);
        }
    }

    /**
     * menu to call when a user has the opportunity to purchase an unowned element
     * @param currentPlayer
     * @param scanner
     * @param landedSquare
     * @return
     */
    public static void mainElementPurchaseMenu(Player currentPlayer, Scanner scanner, Element landedSquare, ManagePlayers managePlayers) {
        System.out.println(landedSquare.getElementName() + " isn't currently owned, would you like to purchase it?");
        System.out.println("Its cost is " + landedSquare.getPurchaseCost() + " " + Game.getGameMoneyName() + " " +
                "and your current balance is " + currentPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
        System.out.println("Type yes to purchase this element, or no to offer it to other players (if none of its parent system is owned)");

        String userEntry = scanner.next().trim();

        // move forward based on users answer
        if (userEntry.equalsIgnoreCase("yes")) {
            // update player state after purchase
            managePlayers.updatePlayerStateAfterPurchase(currentPlayer, landedSquare);
        } else if (userEntry.equalsIgnoreCase("no")) {
            // only offer other players the chance to buy an element if its parent system isnt part owned by the current player
            if (!GameSystem.checkSystemPartOwnedByCurrentPlayer(currentPlayer, landedSquare)) {
                // check if any other player wants to buy this element?
                Menus.offerElementToOtherPlayersMenu(currentPlayer, scanner, landedSquare, managePlayers);
            } else {
                System.out.println("This system is currently part owned and cannot be developed by other players");
            }
        } else {
            System.out.println(Menus.getUnknownInputAlert());
            mainElementPurchaseMenu(currentPlayer, scanner, landedSquare, managePlayers);
        }
    }

    /**
     * when a player lands on an element that isnt owned, but the system is owned, this function calls the appropriate menu
     * allows the system owner the chance to buy the square another player landed on
     * @param elementOwner
     * @param scanner
     * @param landedSquare
     * @param managePlayers
     */
    public static void offerElementToSystemOwnerMenu(Player elementOwner, Scanner scanner,
                                                     Element landedSquare, ManagePlayers managePlayers) {
        System.out.println("To prevent deadlock, no part of this system may be purchased by anyone other than " + elementOwner.getPlayerName());
        System.out.println("\n" + elementOwner.getPlayerName() + ", would you like to purchase this element? It costs " +
                landedSquare.getPurchaseCost() + " " + Game.getGameMoneyName() + " and your current balance is " + elementOwner.getCurrentBalance() +
                " " + Game.getGameMoneyName());
        System.out.println("Type yes to purchase or no to proceed with current players turn");
        String userEntry = scanner.next().trim();

        if (userEntry.equalsIgnoreCase("yes")) {
            // update player and square state
            managePlayers.updatePlayerStateAfterPurchase(elementOwner, landedSquare);
        } else if (userEntry.equalsIgnoreCase("no")) {
            System.out.println(elementOwner.getPlayerName() + " did not purchase the " + landedSquare.getElementName() + " element");
            // then this will exit to allow the current player the chance to develop their own elements
        } else {
            // no idea what player entered - call menu again
            System.out.println(Menus.getUnknownInputAlert());
            offerElementToSystemOwnerMenu(elementOwner, scanner, landedSquare, managePlayers);
        }
    }

    /**
     * menu to offer an element to another player
     * @param currentPlayer - the current turn player
     * @param scanner
     * @param landedSquare
     * @param managePlayers
     */
    public static void offerElementToOtherPlayersMenu(Player currentPlayer, Scanner scanner, Element landedSquare,
                                                      ManagePlayers managePlayers) {
        System.out.println("If any other player wishes to buy this element please type your name " +
                "here, otherwise type no - here is everyone's balance;");
        managePlayers.printAllNonTurnPlayersBalances(currentPlayer);

        String userEntry = scanner.next().trim();

        // decide on next move depending on user input
        if (userEntry.equalsIgnoreCase("no")) {
            System.out.println("Element has not been purchased by anyone...");
            // then this will exit to allow the current player the chance to develop their own elements
        } else {
            // make sure the player exists before retrieving them!
            if (ManagePlayers.findPlayerByName(userEntry) != null) {
                // update player state
                Player acquiringPlayer = ManagePlayers.findPlayerByName(userEntry);

                // update player state after purchase
                managePlayers.updatePlayerStateAfterPurchase(acquiringPlayer, landedSquare);
            } else {
                System.out.println(Menus.getUnknownInputAlert());
                // recall this same menu
                offerElementToOtherPlayersMenu(currentPlayer, scanner, landedSquare, managePlayers);
            }
        }
    }

    /**
     * the master menu to call when a user is to be offered the chance for them to develop owned systems
     * @param currentPlayer
     * @param scanner
     * @param board
     */
    public static void masterDevelopmentMenu(Player currentPlayer, Scanner scanner, Board board, ManagePlayers managePlayers) {
        if (currentPlayer.getAllOwnedSystems().size() > 0) {
            String systemNoun = "systems";
            String systemAction = "those systems";
            String joiningWord = "are";

            // change menu wording if only 1 system is owned
            if (currentPlayer.getAllOwnedSystems().size() == 1) {
                systemNoun = "system";
                systemAction = "this system";
            }

            // only show the development menu if the player systems are not all fully developed
            if (!currentPlayer.checkPlayersSystemsAllDeveloped()) {
                int systemsFullyDeveloped = 0;
                // count how many systems are developed first for the menu
                for (GameSystem gameSystem : currentPlayer.getAllOwnedSystems()) {
                    if (gameSystem.isSystemFullyDeveloped()) {
                        systemsFullyDeveloped++;
                    }
                }
                // change the joining word if only 1 system is developed, to look good!
                if (systemsFullyDeveloped == 1) {
                    joiningWord = "is";
                }

                System.out.println(currentPlayer.getPlayerName() + " - you own " + currentPlayer.getAllOwnedSystems().size() + " " + systemNoun + " " +
                                "and " + systemsFullyDeveloped + " of these " + joiningWord + " fully developed " +
                                " - would you like to develop any elements within " + systemAction + "? " +
                                "\nType yes to develop more elements or end to end your turn");
                String userEntry = scanner.next().trim();

                if (userEntry.equalsIgnoreCase("yes")) {
                    showUndevelopedSystemsMenu(currentPlayer, scanner, board, managePlayers);
                } else if (userEntry.equalsIgnoreCase("end")) {
                    System.out.println("You have stopped developing, moving on to the next player");
                    ManagePlayers.moveToNextPlayer();
                } else {
                    System.out.println(Menus.getUnknownInputAlert());
                    Menus.masterDevelopmentMenu(currentPlayer, scanner, board, managePlayers);
                }
            } else {
                System.out.println("Your current systems are all fully developed! - Moving on to the next players turn");
                ManagePlayers.moveToNextPlayer();
            }
        } else {
            // player doesnt own any systems, so skip to next player with a notification to the player
            System.out.println(currentPlayer.getPlayerName() + " - you don't currently own any full systems, moving on to next players turn");
            ManagePlayers.moveToNextPlayer();
        }
    }

    /**
     * call all owned systems menu
     * @param currentPlayer
     * @param scanner
     * @param board
     */
    public static void showUndevelopedSystemsMenu(Player currentPlayer, Scanner scanner, Board board, ManagePlayers managePlayers) {
        String userEntry = "";
        int systemCounter = 0;
        ArrayList<Integer> allSystemNumbersPrinted = new ArrayList<>();

        System.out.println("You've chosen to develop more elements, first you need to select a system");
        System.out.println(currentPlayer.getPlayerName() + " - here are all your owned systems that are not fully developed");
        for (GameSystem ownedSystemName : currentPlayer.getAllOwnedSystems()) {
            // show the undeveloped systems for user selection
            if (!ownedSystemName.isSystemFullyDeveloped()) {
                // dynamically get the system number from the arraylist
                systemCounter = currentPlayer.getAllOwnedSystems().indexOf(ownedSystemName);

                // add one so the display number always starts from 1
                systemCounter++;

                // add to arraylist for retrieval later once user inputs the number
                // done like this to make the menu and user selections dynamic and adaptable
                allSystemNumbersPrinted.add(systemCounter);

                // print out the counter and the current system name
                System.out.println("\t" + systemCounter + ") " + ownedSystemName.getSystemName() + " system");
            }
        }
        System.out.println("Enter the system number shown to develop that system, or type end to end your turn");
        userEntry = scanner.next().trim();

        if (userEntry.equalsIgnoreCase("end")) {
            ManagePlayers.moveToNextPlayer();
        } else {
            try {
                // user entered a number - change the string entered to an int
                int usersSelectedNumber = Integer.parseInt(userEntry);

                if (allSystemNumbersPrinted.contains(usersSelectedNumber)) {
                    // get the users selected object from their own "all owned systems" list.
                    usersSelectedNumber--;
                    GameSystem selectedSystem = currentPlayer.getAllOwnedSystems().get(usersSelectedNumber);

                    // now get the elements within that system and show that menu
                    showAllUndevelopedElementsMenu(selectedSystem, currentPlayer, scanner, board, managePlayers);
                } else {
                    System.out.println("That number is out of range, please try again");
                    showUndevelopedSystemsMenu(currentPlayer, scanner, board, managePlayers);
                }
            } catch (Exception exception) {
                // otherwise the player entered something unknown, ask them to reenter
                System.out.println(Menus.getUnknownInputAlert());
                showUndevelopedSystemsMenu(currentPlayer, scanner, board, managePlayers);
            }
        }
    }

    /**
     * element level user menu
     * coded in a function so it can be called top down or bottom up
     * @param selectedSystem
     * @param currentPlayer
     * @param scanner
     * @param board
     */
    public static void showAllUndevelopedElementsMenu(GameSystem selectedSystem, Player currentPlayer, Scanner scanner,
                                                      Board board, ManagePlayers managePlayers) {
        String userEntry = "";
        ArrayList<Integer> allElementNumbersPrinted = new ArrayList<>();
        int elementCounter = 0;

        // get the selected systems elements
        // switch statement to check the selectedSystem type?
        System.out.println("The remaining elements to develop within the " + selectedSystem.getSystemName() + " system are;");
        for (Element element : selectedSystem.getAllElementsWithinSystem()) {
            // only print elements out if not fully developed
            if (element.getCurrentDevelopmentLevel() < (Element.getTotalMinorDevelopments() + Element.getTotalMajorDevelopments())) {
                // count the systems for user selection?
                elementCounter = selectedSystem.getAllElementsWithinSystem().indexOf(element);

                // add one onto the index so the printed list always starts from 1
                elementCounter++;
                allElementNumbersPrinted.add(elementCounter);
                System.out.println("\tElement number " + elementCounter + " = " + element.getElementName());
            }
        }
        System.out.println("Select an element number listed to develop it, or type back to go up to the systems menu");
        userEntry = scanner.next().trim();

        if (userEntry.equalsIgnoreCase("back")) {
            showUndevelopedSystemsMenu(currentPlayer, scanner, board, managePlayers);
        } else {
            try {
                // get the system selected and print all its elements out for selection
                // get the users selected element
                int userElementSelected = Integer.parseInt(userEntry);

                if (allElementNumbersPrinted.contains(userElementSelected)) {
                    // reduce the number from the display number to arraylist index number
                    userElementSelected--;

                    // get the element from the arraylist
                    Element element = selectedSystem.getAllElementsWithinSystem().get(userElementSelected);

                    // now ask the user if they want to develop that element in a menu call
                    developElementMenu(selectedSystem, element, currentPlayer, scanner, board, managePlayers);
                } else {
                    // otherwise the player entered something unknown, ask them to reenter
                    System.out.println(Menus.getUnknownInputAlert());
                    showAllUndevelopedElementsMenu(selectedSystem, currentPlayer, scanner, board, managePlayers);
                }
            } catch (InputMismatchException exception) {
                // otherwise the player didnt enter a number - ask them to reenter
                System.out.println(Menus.getUnknownInputAlert());
                showAllUndevelopedElementsMenu(selectedSystem, currentPlayer, scanner, board, managePlayers);
            }
        }
    }

    /**
     * last menu to call once a user has selected an element to develop.
     * @param selectedSystem
     * @param element
     * @param currentPlayer
     * @param scanner
     * @param board
     */
    public static void developElementMenu(GameSystem selectedSystem, Element element, Player currentPlayer,
                                          Scanner scanner, Board board, ManagePlayers managePlayers) {
        int devCost = 0;
        int currentDevLevel = element.getCurrentDevelopmentLevel();
        if (!element.checkElementFullyDeveloped()) {
            // get the devcost based on whether the element is major or minor
            if (element.checkMinorDevCompleted()) {
                devCost = element.getMajorDevelopmentCost();
            } else {
                devCost = element.getMinorDevelopmentCost();
            }
            System.out.println("The " + element.getElementName() + " element is currently at development level "
                    + currentDevLevel + " (" + element.getCurrentDevelopmentName() + ")");
            System.out.println("The next development will cost " + devCost + " " + Game.getGameMoneyName());
            System.out.println("Your current budget is " + currentPlayer.getCurrentBalance() + " " + Game.getGameMoneyName());
            System.out.println("Do you wish to develop this element? \nType yes to develop this element, " +
                    "back to return to previous menu or end to end your turn");
            String userEntry = scanner.next().trim();

            if (userEntry.equalsIgnoreCase("yes")) {
                // update player balance and element development state
                currentPlayer.calculateAndSetNewBalance(devCost, false);

                // change game state if that move bankrupted a player!
                if (currentPlayer.checkPlayerBankrupt(currentPlayer.getCurrentBalance())) {
                    ManagePlayers.setAllPlayersHaveResources(false);
                    System.out.println("You overspent!  That's the game ended for everyone!");
                } else {
                    // update state of... every object involved
                    currentDevLevel++;
                    element.setCurrentDevelopmentLevel(currentDevLevel);

                    // check if the element is fully developed and change if true
                    if (element.checkElementFullyDeveloped()) {

                        // then since an element has changed to be fully developed, check if its parent system is now fully developed
                        if (element.getParentSystem().checkSystemFullyDeveloped()) {
                            // since the parent system check says system is fully developed, set it to be fully developed
                            element.getParentSystem().setSystemFullyDeveloped(true);

                            // then increment the players developed systems
                            int currentDevelopments = currentPlayer.getTotalSystemsFullyDeveloped();
                            currentDevelopments++;
                            currentPlayer.setTotalSystemsFullyDeveloped(currentDevelopments);
                        }
                    }

                    board.checkBoardFullyDeveloped();
                    System.out.println(currentPlayer.getPlayerName() + " developed the " + element.getElementName() +
                    " element, it is now at development level " + element.getCurrentDevelopmentLevel() +
                    " (" + element.getCurrentDevelopmentName() + ")\n");

                    // now call the main development menu again to allow the user to development more elements!
                    ManagePlayers.allowDevelopingIfSystemsOwned(currentPlayer, scanner, board, managePlayers);
                }
            } else if (userEntry.equalsIgnoreCase("back")) {
                showAllUndevelopedElementsMenu(selectedSystem, currentPlayer, scanner, board, managePlayers);
            } else if (userEntry.equalsIgnoreCase("end")) {
                System.out.println("You have moved on to the next players turn");
                ManagePlayers.moveToNextPlayer();
            } else {
                System.out.println(Menus.getUnknownInputAlert());
                developElementMenu(selectedSystem, element, currentPlayer, scanner, board, managePlayers);
            }
        }
    }
}
