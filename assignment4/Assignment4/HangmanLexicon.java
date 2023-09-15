/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import acm.util.*;

public class HangmanLexicon {
	
	// private instance variable 
	private BufferedReader rd;
	
	// private instance variable 
	private ArrayList<String> wordList = new ArrayList<String>();
	
	// This is the HangmanLexicon constructor 
	public HangmanLexicon() { 
		try {
			 rd = new  BufferedReader (new FileReader("HangmanLexicon.txt "));
		} catch (IOException ex) {
			return;
		}
		
		try {
			while (true) {
				String line = rd.readLine();
				wordList.add(line);
				if (line == null) break;
			}
			rd.close();
			} catch (IOException ex) {
				return;
			}
		} 



/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordList.size();	
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return wordList.get(index);
	}
	}
