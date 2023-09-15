
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does its task.
 *  Karel walks to the door of its house, picks up the
 * newspaper (represented by a beeper, of course), and then returns
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		getKarelToNewspaper();
		pickBeeper();
		returnBack();
		turnAround();
	}

	/*
	 * Precondition: Karel is at the starting position; Facing east
	 * Postcondition:Karel is on the beeper; Facing east
	 */
	private void getKarelToNewspaper() {
		for (int i = 0; i < 2; i++)
			move();

		movePastWall();// postcondition: karel is at the door
		move();
	}

	/*
	 * karel goes past the door
	 */
	private void movePastWall() {
		turnRight();
		move();
		turnLeft();
	}

	/*
	 * Precondition: Karel is facing east; Karel has newspaper 
	 * Postcindiiton: Karel is in the starting position; Facing west
	 */
	private void returnBack() {
		turnAround();
		move();
		movePastWall();
		for (int i = 0; i < 2; i++)
			move();
	}

}
