
/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	public void run() {
		putBeeper();
		if (frontIsClear()) {
			fillLastCorner();
		}
		while (noBeepersPresent()) {
			move();
			fillWithBeepers();
		}
		putSecondBeeper();
		pickBeepers();
		moveToBeeper();
	}
	/*
	 * precondition: karel on (1,1), facing east. 1 Beeper on (1,1) 
	 * postcondition: if the world is a x a; karel is on (a-1;1), facing west
	 */

	private void fillLastCorner() {
		while (frontIsClear()) {
			move();
		}
		putBeeper();
		turnAround();
		move();
	}

	/*
	 * karel fills first street with beepers precondition:if the world is a x a;
	 * karel is on (a-1;1), facing west 
	 * postcondition: first street is filled with beepers.
	 */
	private void fillWithBeepers() {
		while (noBeepersPresent()) {
			move();
		}
		turnAround();
		move();
		if (noBeepersPresent()) {
			putBeeper();
			move();
		}
	}

	/*
	 * karel puts second beeper in the middle of the street.
	 */
	private void putSecondBeeper() {
		turnAround();
		if (frontIsClear()) {
			move();
			putBeeper();
			turnAround();
			move();
		} else {
			putBeeper();
			turnAround();
		}
	}

	/*
	 * karel picks one beeper from every corner 
	 * precondition: first street is filled with beepers, in the 
	 * middle corner there is 2 beepers 
	 * postcondition: there is only one beeper in the middle of the street
	 */
	private void pickBeepers() {
		while (frontIsClear()) {
			pickBeeper();
			move();
		}
		pickBeeper();
		turnAround();
		while (frontIsClear()) {
			move();
			if (beepersPresent()) {
				pickBeeper();
			}
		}
	}

	/*
	 * karel returns to the beeper
	 *  precondition: there is only one beeper in the
	 * middle of the street, karel not there 
	 * postcondition: karel in the middle of the street
	 */
	private void moveToBeeper() {
		turnAround();
		while (noBeepersPresent()) {
			move();
		}
	}
}
