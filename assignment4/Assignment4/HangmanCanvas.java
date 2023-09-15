/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	/*scaffold offset length in pixels */
	private double scaffoldYOffset = 20;
	
/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		resetVariables();
		double scaffoldX = getWidth() /2 - BEAM_LENGTH;
		
		GLine scaffold = new GLine(scaffoldX, scaffoldYOffset,scaffoldX, scaffoldYOffset + SCAFFOLD_HEIGHT );
		add(scaffold);
		
		GLine beam = new GLine (scaffoldX,scaffoldYOffset, getWidth()/2 , scaffoldYOffset );
		add(beam);
		
		GLine rope = new GLine(getWidth()/2 ,scaffoldYOffset,getWidth()/2 , scaffoldYOffset + ROPE_LENGTH);
		add(rope);
	}
	
	// resents instance variables
		private void resetVariables() {
			LABEL_CHANGE = 0;
			guessesLeft = 8;
			incorrectLetters = "";
		}

	// private instance variable 
	private GLabel guessedLetters;
	
	// count number of times label changes
	private int LABEL_CHANGE = 0;
	
	// label width
	private int offsetFromLeft = 50;
	
	// distance form the body
	private double offsetFromBody = 60;
/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		if(LABEL_CHANGE != 0) {
		remove(guessedLetters);
		}
		guessedLetters = new GLabel(word);
		guessedLetters.setFont("Helvetica-20");
		add(guessedLetters,offsetFromLeft, offsetFromBody  + SCAFFOLD_HEIGHT );
		LABEL_CHANGE++;
	}
	
	// number of wrong guesses 
	private int guessesLeft = 8;
/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		addingincorrectLetters(letter);
		addHumanParts();
		guessesLeft--;

	}
	
	// private instance variable 
	private String incorrectLetters = "";
	
	// distance from the word label
	private double offsetFromWordLabel = 80;
	
	// add incorrectly guessed letters on the canvas
	private void addingincorrectLetters(char letter) {
		incorrectLetters += letter;
		GLabel incorrectLettersLabel = new GLabel(incorrectLetters);
		incorrectLettersLabel.setFont("Helvetica-15");
		add(incorrectLettersLabel, offsetFromLeft , offsetFromWordLabel + SCAFFOLD_HEIGHT);
	}

	// adds human parts according to number of wrong guesses left
	private void addHumanParts() {
		
		if (guessesLeft == 8) {
			GOval head = new GOval (2*HEAD_RADIUS, 2*HEAD_RADIUS);
			add(head,getWidth()/2 - HEAD_RADIUS , scaffoldYOffset + ROPE_LENGTH );
		}
		
		double bodyOffset = scaffoldYOffset + ROPE_LENGTH + 2* HEAD_RADIUS;
		
		if (guessesLeft == 7) {
			GLine body = new GLine (getWidth()/2, bodyOffset, getWidth()/2, bodyOffset + BODY_LENGTH);
			add (body);
		}
		
		double armOffset = bodyOffset + ARM_OFFSET_FROM_HEAD;
		
		if (guessesLeft == 6) {
			GLine leftUpperArm = new GLine(getWidth()/2 - UPPER_ARM_LENGTH, armOffset, getWidth()/2,armOffset);
			add(leftUpperArm);
			GLine leftLowerArm = new GLine(getWidth()/2 - UPPER_ARM_LENGTH, armOffset, getWidth()/2 - UPPER_ARM_LENGTH,armOffset + LOWER_ARM_LENGTH );
			add(leftLowerArm);
		}
		
		if (guessesLeft == 5) {
			GLine rightUpperArm = new GLine(getWidth()/2, armOffset, getWidth()/2 + UPPER_ARM_LENGTH,armOffset);
			add(rightUpperArm);
			GLine rightLowerArm = new GLine(getWidth()/2 + UPPER_ARM_LENGTH, armOffset, getWidth()/2 + UPPER_ARM_LENGTH,armOffset + LOWER_ARM_LENGTH );
			add(rightLowerArm);
		}
		
		double legOffset = bodyOffset + BODY_LENGTH;
		
		if (guessesLeft == 4) {
			GLine leftHip = new GLine (getWidth()/2 -HIP_WIDTH,legOffset, getWidth()/2,legOffset);
			add(leftHip);
			GLine leftLeg = new GLine (getWidth()/2 -HIP_WIDTH,legOffset , getWidth()/2 -HIP_WIDTH,legOffset + LEG_LENGTH);
			add(leftLeg);
		}
		
		if (guessesLeft == 3) {
			GLine rightHip = new GLine (getWidth()/2  + HIP_WIDTH,legOffset, getWidth()/2,legOffset);
			add(rightHip);
			GLine rightLeg = new GLine (getWidth()/2 + HIP_WIDTH,legOffset , getWidth()/2 + HIP_WIDTH,legOffset + LEG_LENGTH);
			add(rightLeg);
		}
		
		double footOffset = legOffset + LEG_LENGTH;
		
		if (guessesLeft == 2) {
			GLine leftFoot = new GLine( getWidth()/2 -HIP_WIDTH , footOffset, getWidth()/2 -HIP_WIDTH - FOOT_LENGTH, footOffset);
			add(leftFoot);
		}
		
		if (guessesLeft == 1) {
			GLine rightFoot = new GLine( getWidth()/2 + HIP_WIDTH , footOffset, getWidth()/2 + HIP_WIDTH + FOOT_LENGTH, footOffset);
			add(rightFoot);
		}		
	}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
