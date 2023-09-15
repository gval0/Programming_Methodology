/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	
	/*program accepts two values and calculates solution of c*/
	public void run() {
	println("Enter values to compute pythagorean theorem");
	int a = readInt("a: ");
	int b = readInt("b: ");
	theorem(a,b);
	}
	
	//using imputed numbers c is calculated
	private void theorem (int a, int b) {
	//if imputed numbers are positive then c is calculated
	if (a>0 && b>0) {
	int k = a*a+b*b; 
	double c = Math.sqrt(k);
	println("c = "+c);
	} else {
		println("Sides of the triangle must be natural numbers");
	}
	}
}
