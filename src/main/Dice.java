package main;

/**
 * the dice to throw for the game
 * @author Kerrie
 */
public class Dice {
	private static final int MAX_NUMBER = 6;
	private static final int MIN_NUMBER = 1;

	/**
	 * default constructor
	 */
	public Dice() {
	}

	/**
	 * get max number on the dice
	 * @return
	 */
	public static int getMaxNumber() {
		return MAX_NUMBER;
	}

	/**
	 * get min number on the dice
	 * @return
	 */
	public static int getMinNumber() {
		return MIN_NUMBER;
	}

	/**
	 * method to roll the dice
	 * @return - the total dice roll from 2 turns
	 */
	public int rollDice(){
		int diceResult1 = (int)(Math.random() * (getMaxNumber() - getMinNumber()+1) + getMinNumber());
		int diceResult2 = (int)(Math.random() * (getMaxNumber() - getMinNumber()+1) + getMinNumber());
		int diceTotal = diceResult1 + diceResult2;
		System.out.println("You've rolled a "+diceResult1+" and a "+diceResult2+" which makes "+diceTotal);
		return diceTotal;
	}
}
