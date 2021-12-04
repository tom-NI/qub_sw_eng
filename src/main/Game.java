package main;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Where the game is ran from
 * @author Kerrie and tomkilpatrick
 */
public class Game {
	/**
	 * Set the name used for resources throughout the game
	 */
	private static final String GAME_MONEY_NAME = "Federal Bonds";

	/**
	 * number used for the final countdown on game completion!
	 */
	private static int countDownNumber = 5;

	/**
	 * Time between a game completions news bulletins;
	 */
	private static final int MILLISECONDS_BETWEEN_NEWS_BULLETINS = 4000;

	/**
	 * getter for the money name
	 * @return
	 */
	public static String getGameMoneyName() {
		return GAME_MONEY_NAME;
	}

	/**
	 * getter for the news bulletin interval timing
	 * @return
	 */
	public static int getTIME_BETWEEN_NEWS_BULLETINS() {
		return MILLISECONDS_BETWEEN_NEWS_BULLETINS;
	}

	/**
	 * program main method to run the game
	 * @param args
	 */
	public static void main(String[] args) {
		// create instance of scanner for all inputs throughout the game
		Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

		// create a board to play on and dice to "roll"
		Board board = new Board();
		Dice dice = new Dice();

		// instantiate all spacesuit elements along with the parent system
		GameSystem spacesuits = new GameSystem("Spacesuits");
		Element teflonSuit = new Element("Teflon Suit", 50, 100, 200, 50, spacesuits);
		Element lifeSupport = new Element("Life Support System", 50, 100, 250, 50, spacesuits);
		// add the elements to the system
		spacesuits.addElementToSystem(teflonSuit);
		spacesuits.addElementToSystem(lifeSupport);

		// instantiate all lunar lander elements along with the parent system
		GameSystem lunarLanders = new GameSystem("Lunar Landers");
		Element integratedLanderVehicle = new Element("Blue Origins: Integrated Lander Vehicle", 50, 100, 300, 50, lunarLanders);
		Element humanLandingSystem = new Element("Dynetics: Dynetics Human Landing System", 50, 100, 350, 50, lunarLanders);
		Element starshipHLS = new Element("Space X : Starship HLS", 50, 100, 400, 50, lunarLanders);
		// add the elements to the system
		lunarLanders.addElementToSystem(integratedLanderVehicle);
		lunarLanders.addElementToSystem(humanLandingSystem);
		lunarLanders.addElementToSystem(starshipHLS);

		// instantiate all "space launch systems" elements along with the parent system
		GameSystem spaceLaunchSystem = new GameSystem("Space Launch");
		Element launchAbort = new Element("Launch Abort System", 50, 100, 400, 50, spaceLaunchSystem);
		Element coreAndUpperStages = new Element("Core and Upper Stages", 50, 100, 450, 50, spaceLaunchSystem);
		Element crewModule = new Element("Crew Module", 50, 100, 500, 50, spaceLaunchSystem);

		// add the elements to the system
		spaceLaunchSystem.addElementToSystem(launchAbort);
		spaceLaunchSystem.addElementToSystem(coreAndUpperStages);
		spaceLaunchSystem.addElementToSystem(crewModule);

		// instantiate all gateway's elements along with the parent system
		GameSystem gateway = new GameSystem("Gateway");
		Element enginesAndPower = new Element("Engines and Power", 50, 100, 550, 50, gateway);
		Element mainModule = new Element("Main Module", 50, 100, 600, 50, gateway);

		// add the elements to the system
		gateway.addElementToSystem(enginesAndPower);
		gateway.addElementToSystem(mainModule);

		// add all the systems to the board gameSystems list that tracks them
		board.addGameSystem(spacesuits);
		board.addGameSystem(lunarLanders);
		board.addGameSystem(spaceLaunchSystem);
		board.addGameSystem(gateway);

		StartSquare startSquare = new StartSquare();
		DoNothingSquare doNothingSquare = new DoNothingSquare();

		// now add all elements (i.e. game squares) to the board gameSquares list that tracks them
		// this is used to position players when they roll the dice
		board.addGameSquare(startSquare);

		for (Element element : spacesuits.getAllElementsWithinSystem()) {
			board.addGameSquare(element);
		}
		for (Element element : lunarLanders.getAllElementsWithinSystem()) {
			board.addGameSquare(element);
		}

		board.addGameSquare(doNothingSquare);

		for (Element element : spaceLaunchSystem.getAllElementsWithinSystem()) {
			board.addGameSquare(element);
		}
		for (Element element : gateway.getAllElementsWithinSystem()) {
			board.addGameSquare(element);
		}

		// instantiate a new managePlayers object (control object for all players)
		ManagePlayers managePlayers = new ManagePlayers();

		// get the total development levels dynamically for game intro
		int totalLevelsToDevelop = Element.getTotalMinorDevelopments() + Element.getTotalMajorDevelopments();

		// print game introduction
		System.out.println("\nWelcome to the ArtemisLite board game!");
		System.out.println("Here you will develop all future NASA systems for the future of space exploration.\n");
		System.out.println("There are " + Board.countTotalElements() + " elements in the game, each with " + totalLevelsToDevelop
				+ " levels to develop - each NASA element is a game square.");
		System.out.println("Each element is part of a system, you need to first own the whole system to develop any elements within that system.");
		System.out.println("You can also land in the " + DoNothingSquare.getDoNothingSquareName() + " square or in the " + StartSquare.getStartSquareName()
				+ " square (where funding is topped up) - neither of these elements can be owned or developed.");
		System.out.println("Once you have purchased an element in a system, no other player may purchase an element in that system; " +
							"but beware when taking over a system, a player without anything to develop may get bored and quit the game, ending it for all!");
		System.out.println("The aim of this game is cooperation as all systems need to be developed fully for a successful mission take off.");
		System.out.println("NASA lifts off for space when all elements are developed, but be careful, the game will end if you overspend and go bankrupt!\n");

		// enter and validate player numbers
		Menus.enterNumberOfPlayers(scanner);

		// register all players and check names are unique
		managePlayers.registerAllPlayers(scanner);
		Player currentPlayer;

		// main game loop - loop around the game until;
		// all squares are developed, or a player quits, or a player runs out of resources
		while (!Board.isBoardFullyDeveloped() && !ManagePlayers.getIfAnyPlayerHasQuit() && ManagePlayers.checkAllPlayersHaveResources()) {
			// roll dice, move, do stuff, loop around the players until game is complete
			currentPlayer = ManagePlayers.getAllPlayers().get(ManagePlayers.getCurrentPlayerNumber());

			// print player current state and invite to play or quit
			currentPlayer.printPlayerState(false);

			// offer the player the chance to roll the dice
			// from this menu, almost all other menus are derived
			Menus.offerDiceRoll(scanner, dice, board, currentPlayer, managePlayers);
		}
		// game is over
		// only print congratulations if nobody quit!
		if (!ManagePlayers.getIfAnyPlayerHasQuit() && ManagePlayers.checkAllPlayersHaveResources()) {
			// print nice things for gamers
			// now print out the completed game summary by year
			int currentYear = Calendar.getInstance().get(Calendar.YEAR);
			System.out.println("CONGRATULATIONS!!\nYou finished developing all Systems for ArtemisLite.");
			System.out.println("The President of the United States called to offer his congratulations!");

			System.out.println("\nBREAKING NEWS : Artemis commissioning and testing has been completed in " + currentYear);
			System.out.println("We now go to Kennedy Space Center, Cape Canaveral, Florida, for lift-off of the next phase of American space exploration.\n");

			// pause between each "news" bulletin
			try {
				Thread.sleep(getTIME_BETWEEN_NEWS_BULLETINS());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

			// do a proper countdown for lift-off
			while (countDownNumber >= 0) {
				try {
					// sleep for one second interval countdowns
					Thread.sleep(1000);

					// change printout based on the number
					if (countDownNumber != 0) {
						System.out.println(countDownNumber + "...");
					} else {
						System.out.println("\nWE HAVE LIFT-OFF!\n");
					}
					countDownNumber--;
				} catch (InterruptedException exception) {
					exception.printStackTrace();
				}
			}

			System.out.println("Artemis 1 is to be an uncrewed mission around the Moon");

			// pause between each "news" bulletin
			try {
				Thread.sleep(getTIME_BETWEEN_NEWS_BULLETINS());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

			currentYear += 2;
			System.out.println("Artemis 2 is planned to orbit Earth's satellite with a crew in " + currentYear);

			// pause between each "news" bulletin
			try {
				Thread.sleep(getTIME_BETWEEN_NEWS_BULLETINS());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

			currentYear += 2;
			System.out.println("Artemis 3 is planned to put astronauts on lunar soil in " + currentYear  + ", including the first woman.\n");

			// pause between each "news" bulletin
			try {
				Thread.sleep(getTIME_BETWEEN_NEWS_BULLETINS());
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		} else if (!ManagePlayers.checkAllPlayersHaveResources()) {
			// someone went bankrupt - print message
			System.out.println("BANKRUPT! - We arent going to get into space working like this!");
			System.out.println("A Federal inquiry will be convened regarding the waste of taxpayers money\n");
		}


		// print every players final state
		System.out.println("All Players final state;");
		managePlayers.printAllPlayersFinalState();

		// print the boards final state
		Board.printBoardFinalState();

		System.out.println("\nThank you for playing ArtemisLite, please restart the program to play again.");
		scanner.close();
	}
}



