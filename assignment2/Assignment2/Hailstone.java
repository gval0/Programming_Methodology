/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	public void run() {
		int n = readInt("Enter a number: ");
		int i = 0;
		while (n != 1) {
			/*
			 * checks if n is even
			 * if even divides by 2
			 */
			if (n % 2 == 0) {
				println(n + " is even so I make half:  " + (n/2));
				n /=2;
		} else {
			/*
			 * if n is not even multiplies by 3 and adds 1
			 */
			println(n + " is odd, so I take 3n+1: " + (3*n+1));
			n = 3*n+1;
		}
		i++;//counts number of steps needed to get to number 1
	}
		println("The process took " + i + " to reach " + n);
}
}

