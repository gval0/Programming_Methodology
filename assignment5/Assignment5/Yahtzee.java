/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/*
	 * initializes name and score matrix
	 * plays game
	 * counts scores at the end of the game
	 */
	private void playGame() {
		initialize();
		for (int i =0; i <N_SCORING_CATEGORIES; i++) {
			for (int j=0; j < nPlayers; j++) {
				roll(j);
			}
		}
		finishingSetUp();
	}

	//initialize private variable
	private void initialize() {
		categoryListing = new int [N_CATEGORIES][nPlayers] ; 
		for (int i=0; i<N_CATEGORIES; i++) {
			for (int j=0; j< nPlayers; j++) {
				categoryListing[i][j] = defaultForMatrix;
			}
		}
		
	}
	
	// rolls dice
	private void roll(int player) {
		addWhoseTurnMessage(player);
		display.waitForPlayerToClickRoll(player + 1);
		rollDice();
		
		addRerollMessage(player);
		display.waitForPlayerToSelectDice();
		rollDiceAgain();
		
		addRerollMessage(player);
		display.waitForPlayerToSelectDice();
		rollDiceAgain();
		
		check(player);
		
	}
	
	//randomly determines values on dice
	private void rollDice() {
		for (int i=0 ; i<N_DICE; i++) {
			rolledDice[i] = rgen.nextInt(1,6);
		}
		display.displayDice(rolledDice);
	}
	
	//rerolls dice which player chose
	private void rollDiceAgain() {
		for (int i=0 ; i<N_DICE; i++) {
			if (display.isDieSelected(i)) rolledDice[i] = rgen.nextInt(1,6);
		}
		display.displayDice(rolledDice);
	}
	
	//tells which players turn it is
	private void addWhoseTurnMessage(int n) {
		display.printMessage(playerNames[n] + "'s turn! Click ''Roll Dice'' button to roll the dice.");
	}
	
	//adds reroll directions
	private void addRerollMessage(int n) {
		display.printMessage(playerNames[n] + " select the dice you wish to reroll and click ''Roll again''.");
	}
	
	/*
	 * tells player to choose the category
	 * checks if the dice values are correct for the category 
	 * displays score 
	 * adds score to player and score matrix
	 */
	private void check(int player) {
		display.printMessage(playerNames[player] + " select category for this roll.");
		
		while (true) {
			int score = 0 ;
		int category = display.waitForPlayerToSelectCategory();
		if (categoryListing[category-1][player] == defaultForMatrix) {
			if(checkCategory(category)) {
			score = calculateScore(category);
			} 
			display.updateScorecard(category,player + 1 , score);
			categoryListing[category-1][player] = score;
		 break;
		} else {
			display.printMessage(playerNames[player] + " this category was already choesen. Choose another category.");
		}
		}
	}
	
	// based on category checks dice values and returns boolean acordingly
	private boolean checkCategory(int category) {
		switch(category){
			case 1: return true;
			case 2: return true;
			case 3: return true;
			case 4: return true;
			case 5: return true;
			case 6: return true;
			case 9: return checkThreeOfKind();
			case 10: return checkFourOfKind();
			case 11: return checkFullHouse();
			case 12: return checkSmallStraight();
			case 13: return checkLargeStraight();
			case 14: return checkYahtzee();
			case 15: return true;
			default: return false;
		} 
	}
	
	// checks if dice values contain tree of the same values
	// returns boolean
	private boolean checkThreeOfKind() {
		int a = 0;
		for (int i=0; i<rolledDice.length; i++) {
			for (int j=i; j<rolledDice.length; j++) {
				if (rolledDice[i] == rolledDice[j]) a++;
				if (a == 3) {
					return true;
				}
			}
			a = 0;
		}
		return false;
	}
	
	// checks if dice values contain four of the same values
	// returns boolean
	private boolean checkFourOfKind() {
		int a = 0;
		for (int i=0; i<rolledDice.length; i++) {
			for (int j=i; j<rolledDice.length; j++) {
				if (rolledDice[i] == rolledDice[j]) a++;
				if (a == 4) {
					return true;
				}
			}
			a = 0;
		}
		return false;
	}
	
	// checks if dice values contain tree of the kind and two of the kind
	// returns boolean
	private boolean checkFullHouse() {
		int a=0;
		int remember = 0;
		int b=0;
		for (int i=0; i<rolledDice.length; i++) {
			if (rolledDice[0] == rolledDice[i]) {
				a++;
			}
		}
		for (int i=0; i<rolledDice.length; i++) {
			if (rolledDice[0] != rolledDice[i]) {
				remember = rolledDice[i]; break;
			}
		}
		for (int i=0; i<rolledDice.length; i++) {
			if (remember == rolledDice[i]) {
				b++;
			}
		}
		
		if (a==2 && b==3) return true;
		if (a==3 && b==2) return true;
				return false;
	}
	
	// checks if four dice values make arithmetic progression
	// returns boolean
	private boolean checkSmallStraight() {
		int smallestDice = 6;
		for (int i=0; i<rolledDice.length; i++) {
			if (rolledDice[i] < smallestDice) 
				smallestDice = rolledDice[i];
		}
		
		int secondSmallestDice = 6;
		for (int i=0; i<rolledDice.length; i++) {
			if (rolledDice[i] < secondSmallestDice && rolledDice[i]> smallestDice) 
				secondSmallestDice = rolledDice[i];
		}
		int fromSmallestDice = 0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<rolledDice.length;j++) {
				if (smallestDice + i == rolledDice[j]) {
					fromSmallestDice ++;
					break;
				} 
			}
		}
		int fromSecondSmallestDice = 0;
		for (int i=0; i<4; i++) {
			for (int j=0; j<rolledDice.length;j++) {
				if (secondSmallestDice + i == rolledDice[j]) {
					fromSecondSmallestDice ++;
					break;
				} 
			}
		}
		if (fromSmallestDice == 4 || fromSecondSmallestDice == 4) return true;
		else return false;
	}
	
	// checks if five dice values make arithmetic progression
	// returns boolean
	private boolean checkLargeStraight() {
		int smallestDice = 6;
		for (int i=0; i<rolledDice.length; i++) {
			if (rolledDice[i] < smallestDice) 
				smallestDice = rolledDice[i];
		}
		int number = 0;
		for (int i=0; i<5; i++) {
			for (int j=0; j<rolledDice.length;j++) {
				if (smallestDice + i == rolledDice[j]) {
					number ++;
					break;
				} 
			}
		}
		if (number == 5)return true;
		else return false;
	}
	
	// checks if all of the dice values are same
	// returns boolean
	private boolean checkYahtzee() {
		int result = 0;
		for (int i = 0; i<rolledDice.length;i++) {
			if (rolledDice[0] == rolledDice[i]) result++;
		}
		if (result == N_DICE) return true;
		else return false;
	}

	// based on category returns score 
	private int calculateScore(int category) {
		switch(category){
			case 1: return sumOf(category);
			case 2: return sumOf(category);
			case 3: return sumOf(category);
			case 4: return sumOf(category);
			case 5: return sumOf(category);
			case 6: return sumOf(category);
			case 9: return sumOfAll();
			case 10: return sumOfAll();
			case 11: return 25;
			case 12: return 30;
			case 13: return 40;
			case 14: return 50;
			case 15: return sumOfAll();
			default: return 0;
		} 
	}
	
	// sum of the one kind based on category
	private int sumOf(int n) {
		int result = 0;
		for (int i = 0; i<rolledDice.length;i++) {
			if (rolledDice[i] == n) result++;
		}
		return result * n;
	}
	
	// sum of dice values
	private int sumOfAll() {
		int result = 0;
		for (int i = 0; i<rolledDice.length;i++) {
			result+=rolledDice[i];
		}
		return result;
	}
	
	/*
	 * adds upper and lower scores and bonus
	 * lets the players know who is winner
	 */
	private void finishingSetUp() {
		for (int player=0; player<nPlayers; player++) {
			int upperScoreSum = score(0,7, player);
			display.updateScorecard(7,player + 1 , upperScoreSum);
			categoryListing[6][player] = upperScoreSum;
			
			bonus(player,upperScoreSum);
			
			int lowerScoreSum = score(8,16, player);
			display.updateScorecard(16,player + 1 , lowerScoreSum);
			categoryListing[15][player] = lowerScoreSum;
			
			totalScore(player);
		}
		determineWinner();
	}
	
	// adds scores from matrix
	private int  score (int a,int category, int n) {
		int result = 0;
		for (int i=a; i<category-1; i++) {
			result+= categoryListing[i][n];
		}
		return result;
	}
	
	//adds if player has enough point for bonus
	private void bonus(int player, int a) {
		if (a >= bonusMinimumScore) {
			display.updateScorecard(8,player + 1 , 35);
			categoryListing[7][player] = 35;
		}else {
			display.updateScorecard(8,player + 1 , 0);
			categoryListing[7][player] = 0;
		}
	}
	
	private void totalScore(int player) {
		int sum = categoryListing[6][player] + categoryListing[7][player] + categoryListing[15][player];
		display.updateScorecard(17,player + 1 , sum);
		categoryListing[16][player] = sum;
	}
	
	// determines winner based on highest score
	// declares message who is winner
	private void determineWinner() {
		int max = 0;
		for (int i=0; i<categoryListing[16].length;i++) {
			if (max<categoryListing[16][i]) {
				max = categoryListing[16][i];
			}
		}
		
		String winners = "";
			for (int i=0; i<categoryListing[16].length;i++) {
				if (max == categoryListing[16][i]);
				winners += playerNames[i] + " ";
			}
			
		display.printMessage(winners + "won with " + max + " points.");
	}
		
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	/* Private instance variables, rolled dice values */	
	private int[] rolledDice = new int[N_DICE];
	/* Private instance variables, scores and names*/
	private int[][] categoryListing; 
	/* default value for matrix */
	private static int defaultForMatrix = -1;
	/* minimum score for getting bonus */
	private static int bonusMinimumScore = 63;

}
