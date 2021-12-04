package main;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * control object to manage all the games players
 * @author Kerrie and tomkilpatrick
 */
public class ManagePlayers {
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 4;
	private static int totalPlayers = 0;
	private static ArrayList<Player> allPlayers = new ArrayList<>();
	private static int currentPlayerNumber = 0;

	/**
	 * main control boolean for the game - if one player quits, the game ends for all players
	 */
	private static boolean hasAnyPlayerQuitTheGame = false;

	/**
	 * control boolean for the game, if one player goes into a negative budget, game ends for all players
	 */
	private static boolean allPlayersHaveResources = true;

	/**
	 * default constructor
	 */
	public ManagePlayers() {
	}

	/**
	 * Getter for minimum players in the game
	 * @return
	 */
	public static int getMinPlayers() {
		return MIN_PLAYERS;
	}

	/**
	 * Getter for maximum players in the game
	 * @return
	 */
	public static int getMaxPlayers() {
		return MAX_PLAYERS;
	}

	/**
	 * getter for the allPlayers arraylist
	 * @return
	 */
	public static ArrayList<Player> getAllPlayers() {
		return allPlayers;
	}

	/**
	 * getter for total players
	 * @return
	 */
	public static int getTotalPlayers() {
		return totalPlayers;
	}

	/**
	 * setter for total players
	 * @param totalPlayers
	 */
	public static void setTotalPlayers(int totalPlayers) {
		ManagePlayers.totalPlayers = totalPlayers;
	}

	/**
	 * getter for current player number
	 * @return
	 */
	public static int getCurrentPlayerNumber() {
		return currentPlayerNumber;
	}

	/**
	 * set the current player number
	 * @param currentPlayerNumber
	 */
	public static void setCurrentPlayerNumber(int currentPlayerNumber) {
		ManagePlayers.currentPlayerNumber = currentPlayerNumber;
	}

	/**
	 * get if any player has quit
	 * @return
	 */
	public static boolean getIfAnyPlayerHasQuit() {
		return hasAnyPlayerQuitTheGame;
	}

	/**
	 * set if any player has quit
	 * @param hasAnyPlayerQuitTheGame
	 */
	public static void setHasAnyPlayerQuit(boolean hasAnyPlayerQuitTheGame) {
		ManagePlayers.hasAnyPlayerQuitTheGame = hasAnyPlayerQuitTheGame;
	}

	/**
	 * check if all players have resources
	 * @return
	 */
	public static boolean checkAllPlayersHaveResources() {
		return allPlayersHaveResources;
	}

	/**
	 * set if all players don't have resources
	 * @param allPlayersHaveResources
	 */
	public static void setAllPlayersHaveResources(boolean allPlayersHaveResources) {
		ManagePlayers.allPlayersHaveResources = allPlayersHaveResources;
	}

	/**
	 * check the player name entered is unique
	 * @param name
	 * @return
	 */
	public boolean checkPlayerNameIsUnique(String name) {
		boolean nameIsUnique = true;

		if (getAllPlayers().size() > 0) {
			for (Player player : getAllPlayers()) {
				if (name.equals(player.getPlayerName())) {
					// name isnt unique
					nameIsUnique = false;
				}
			}
		}
		return nameIsUnique;
	}

	/**
	 * check the player name entered is unique
	 * @param name
	 * @return
	 */
	public static Player findPlayerByName(String name) {
		Player returnedPlayer = null;
		if (getAllPlayers().size() > 0) {
			for (Player player : getAllPlayers()) {
				if (name.equalsIgnoreCase(player.getPlayerName())) {
					returnedPlayer = player;
				}
			}
		}
		return returnedPlayer;
	}

	/**
	 * initialise all players and check names provided are unique
	 * this will build the arraylist of all players
	 * @param scan - scanner object to retrieve and store user input
	 */
	public void registerAllPlayers(Scanner scan) {
		for (int loop = 1; loop <= getTotalPlayers(); loop++) {
			// enter a players name
			System.out.println("Player " + loop + ", please enter your name...");
			String currentPlayerName = scan.next().trim().toUpperCase();

			//check an empty string isn't input
			while (currentPlayerName.isEmpty()) {
				System.out.println("Invalid name, please try again");
				currentPlayerName = scan.next().trim().toUpperCase();
			}
				// check that players name is unique
				boolean nameIsUnique = checkPlayerNameIsUnique(currentPlayerName);

				// keep pushing user for a unique name
				while (!nameIsUnique) {
					System.out.println("That name is already taken, please enter a unique name...");
					currentPlayerName = scan.next().trim().toUpperCase();
					nameIsUnique = checkPlayerNameIsUnique(currentPlayerName);
				}

				// otherwise, if the name is unique for that player, create a new player object
				if (nameIsUnique) {
					//  initialise a player object
					Player player = new Player(currentPlayerName);
					player.setPlayerNumber(loop);
					getAllPlayers().add(player);
				}
			}
		}

	/**
	 * change the current player number dynamically depending on the size of the total players arraylist
	 * @return
	 */
	public static void moveToNextPlayer() {
		int currentPlayerNumber = getCurrentPlayerNumber();
		currentPlayerNumber++;
		setCurrentPlayerNumber((currentPlayerNumber) % getAllPlayers().size());
	}

	/**
	 * print all players final game state
	 */
	public void printAllPlayersFinalState() {
		for (Player player : getAllPlayers()) {
			player.printPlayerState(true);
		}
	}

	/**
	 * print all the players (except the current one) name and balances
	 * used when players are offered to buy an element when its not presently their turn to remind them of their balances
	 * @param currentPlayer
	 */
	public void printAllNonTurnPlayersBalances(Player currentPlayer) {
		for (Player player : getAllPlayers()) {
			if (!player.getPlayerName().equals(currentPlayer.getPlayerName())) {
				System.out.println(player.getPlayerName() + " has a balance of " + player.getCurrentBalance() + " " + Game.getGameMoneyName());
			}
		}
	}

	/**
	 * control method to check if a player owns any systems,
	 * if they do allow them to develop those systems, otherwise skip to next player
	 * @param currentPlayer
	 * @param scanner
	 * @param board
	 */
	public static void allowDevelopingIfSystemsOwned(Player currentPlayer, Scanner scanner, Board board, ManagePlayers managePlayers) {
		// player can now develop other elements if they own a system,
		if (currentPlayer.getAllOwnedSystems().size() > 0) {
			Menus.masterDevelopmentMenu(currentPlayer, scanner, board, managePlayers);
		} else {
			// only move onto next player if everyone is solvent
			if (checkAllPlayersHaveResources()) {
				//otherwise move to next player
				System.out.println(currentPlayer.getPlayerName() + " its still your turn, however you dont own " +
						"any full systems so you cant yet develop any elements you own.  Moving on to next players turn");
				ManagePlayers.moveToNextPlayer();
			}
		}
	}

	/**
	 * element to call to update players state after a purchase.
	 * updates ownerships, balances and check bankruptcy
	 * @param currentPlayer
	 * @param landedSquare
	 */
	public void updatePlayerStateAfterPurchase(Player currentPlayer, Element landedSquare) {
		currentPlayer.calculateAndSetNewBalance(landedSquare.getPurchaseCost(), false);
		if (currentPlayer.checkPlayerBankrupt(currentPlayer.getCurrentBalance())) {
			// player is bankrupt! end game
			ManagePlayers.setAllPlayersHaveResources(false);
			System.out.println(currentPlayer.getPlayerName() + " is bankrupt!  That's the game ended for everyone!");
		} else {
			// player isn't bankrupt, keep the game going
			currentPlayer.addOwnedElement(landedSquare);

			// update element (square) owner
			landedSquare.setElementOwner(currentPlayer.getPlayerName());

			// check and update if a system is now fully owned
			// if the system is fully owned, increment the players ownerships
			GameSystem.updateSystemOwnershipStatus(currentPlayer, landedSquare);

			// print the player who bought the element and their new balance
			Player.printPlayerAcquisition(currentPlayer, landedSquare.getElementName());
		}
	}
}
