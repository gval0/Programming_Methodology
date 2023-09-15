/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	/*value of sentinel*/
	private static final int Sentinel = 0;

	/*
	 * program allows to enter any number 
	 * after writing in sentinel it displays smallest and largest number
	 */
	public void run() {
			int i = readInt();
			if (i == Sentinel)
				println("You have entered sentinel");
			int min = i;
			int max = i;
			/*
			 * compares new imputed number to the last one
			 */
			while (i != Sentinel) {
				i = readInt();
				if (i != 0) {
					if (i > max)
						max = i;
					if (i < min)
						min = i;
				}
			}
			/*
			 * prints the smallest and the largest number out of the imputed numbers
			 */
			if (min != 0) {
				println("smallest: " + min);
				println("largest: " + max);
			}
	}
}
