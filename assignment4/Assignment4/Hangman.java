
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Hangman extends ConsoleProgram {
	/** Private instance variable */
	private RandomGenerator rgen = new RandomGenerator();
	
	/** Private instance variable */
	private String hyphenN = "";
	
	/* number of wrong guesses*/
	private int wrongGuessesN = 8;
	
	/**Private instance variable */
	private int numberOfGuessesLeft = wrongGuessesN;
	
	/** Private instance variable */
	private char guess;
	
	/** Private instance variable */
	private HangmanCanvas canvas;
	
	/** Private instance variable */
	private HangmanLexicon lex = new HangmanLexicon();
	
	/** Private instance variable */
	private boolean endGame = true;
	
	/**second canvas for graphics */
	public void init() { 
		canvas = new HangmanCanvas(); 
		add(canvas); 
		} 
	
	/** Method: run()*/
	public void run() {
		while(endGame) {
		int wordN = lex.getWordCount();
		int N = rgen.nextInt(0, wordN - 1);
		String word = lex.getWord(N);
		setUp(word);
		playGame(word);
		continueGame();
		}
	}

	
	private void setUp(String word) {
		welcome();
		WordLooksLike(word);
		guesses();
		
	}
	
	//display welcome message
	private void welcome() {
		println("Welcome to Hangman!");
	}

	// add hyphens according to letters in the word 
	private void WordLooksLike(String word) {
		for (int i = 0; i < word.length(); i++) {
			hyphenN += "-";
		}
		println("The word now looks like: " + hyphenN);
		canvas.displayWord(hyphenN);
	}

	// displays amount of wrong guesses player has overall 
	private void guesses() {
		println("You have " + numberOfGuessesLeft + " guesses left.");
		canvas.reset();
	}

	//play game until player has wrong guesses left
	private void playGame(String word) {
		while (numberOfGuessesLeft > 0) {
			guess();
			check(word);
			if (hyphenN.equals(word)) break;
		}
	}

	// check if inputed character is letter
	// convert letter to uppercase
	private void guess() {
		while (true) {
			String a = readLine("Your guess: ");
			a = a.toUpperCase();
			guess = a.charAt(0);
			if (Character.isLetter(guess)) break;
			else println(guess + " is not a letter. Try again.");
		}
	}

	// check if word contains letter
	// if guess is right, hyphens are replaced with letters 
	private void check(String word) {
		if (word.indexOf(guess) != -1) {
			for (int i = 0; i < word.length(); i++) {
				if (guess == word.charAt(i))
					hyphenN = hyphenN.substring(0, i) + guess + hyphenN.substring(i + 1);
			}
			latterCorrectMessage(word);
		} else {
			latterNotCorrectMessage(word);
		}
		canvas.displayWord(hyphenN);
	}

	// display message if letter is correct
	private void latterCorrectMessage(String word) {
		println("That guess is correct.");
		if (hyphenN.equals(word)) {
			winningMessage(word);
		} else {
			println("The word now looks like this: " + hyphenN);
			println("You have " + numberOfGuessesLeft + " guesses left.");
		}
	}
	// display winning message if player guesses the word
	private void winningMessage(String word) {
		println("You guessed the word: " + word);
		println("You win.");
	}
	// display message if letter is incorrect
	private void latterNotCorrectMessage(String word) {
		println("There are no " + guess + "'s in the word.");
		println("The word now looks like this: " + hyphenN);
		canvas.noteIncorrectGuess(guess);
		numberOfGuessesLeft--;
		if (numberOfGuessesLeft != 0) {
			println("You have " + numberOfGuessesLeft + " guesses left.");
		// if no wrong guesses left display losing message
		} else {
			println("You are completely hung.");
			println("The word was: " + word);
			println("You lose.");
		}
	}
	
	// gives option to continue game or stop
	private void continueGame() {
		String str = readLine("If you want to stop playing enter 0: ");
		numberOfGuessesLeft = wrongGuessesN;
		hyphenN = "";
		if (str.equals("0")) {
			println("Good Game!");
			endGame=false;
		}
	}
}
