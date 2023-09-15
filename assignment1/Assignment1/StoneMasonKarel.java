
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears fixes damaged arches.
 * it solves the "repair the quad" problem from Assignment 1. 
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {
	public void run() {
		while (frontIsClear()) {
			repairArch();
			goSouth();
			goToArch();
		}
		repairArch();
		goSouth();
	}

	/*
	 * karel repairs arch 
	 * precondition: Karel facing east; at the bottom of the arch
	 * postcondition: arch repaired; karel at the top, facing north
	 */
	private void repairArch() {
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
		}
		if (noBeepersPresent()) {
			putBeeper();
		}
	}

	/*
	 * karel gets to the bottom of the Arch 
	 * precondition: karel facing north, at thetop of the Arch 
	 * postcondition: karel at the bottom, facing east
	 */
	private void goSouth() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}

	/*
	 * karel moves to another arch
	 */
	private void goToArch() {
		for (int i = 0; i < 4; i++) {
			move();
		}
	}
}
