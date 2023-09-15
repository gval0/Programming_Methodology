
/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	public void run() {
		fillFirstRow();
		while (frontIsClear())
			fillColumns();
		if (frontIsBlocked())
			fillLastColumn();
	}

	/*
	 * precondition:no beepers on the board, karel in (1,1), facing east
	 * postcondition: karel fills fist row with beepers in checherboard pattern
	 * karel in (1,1) facing east
	 */
	private void fillFirstRow() {
		if (frontIsClear()) {
			fillRow();
			returnBack();
			turnAround();
		} else {
			putBeeper();
		}
	}

	/*
	 * precondition: line is empty 
	 * postcondition: row is filled with beepers checkerboard pattern
	 */
	private void fillRow() {
		while (frontIsClear()) {
			if (frontIsClear()) {
				putBeeper();
				move();
				if (frontIsClear()) {
					move();
				}
			}
		}
		checkLastCorner();
	}
	/*
	 * precondition: there may not be beeper in the last corner, if number of
	 * corners in a row is odd. 
	 * postcondition:if number of corners in a row is odd,
	 * there will be beeper at the end of the line, if number of corners in a column
	 * is even there will be no beepers
	 */

	private void checkLastCorner() {
		turnAround();
		move();
		turnAround();
		if (beepersPresent()) {
			move();
		} else {
			move();
			putBeeper();
		}
	}

	/*
	 * karel returns back
	 */
	private void returnBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
	}

	/*
	 * precondition: karel is in (1;1), only first horizontal row is filled with
	 * beepers checkerboard pattern, karel facing east 
	 * postcondition: the whole board is filled with checherboard pattern
	 */
	private void fillColumns() {
		if (beepersPresent()) {
			moveType1();
			returnBack();
			turnLeft();
		} else {
			moveType2();
			returnBack();
			turnLeft();
		}
		if (frontIsClear()) {
			move();
		}
	}

	/*
	 * precondition: there is only one beeper at the southest point of one vertical
	 * column, karel facing east 
	 * postcondition: the whole vertical line is filled
	 * with chechboard pattern, karel facing east in the southest point
	 */
	private void moveType1() {
		turnLeft();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}

	/*
	 * precondition: there is no beeper at the southest point of one vertical line,
	 * karel facing east postcondition: the whole vertical line is filled with
	 * chechboard pattern, karel facing east in the southest point
	 */
	private void moveType2() {
		turnLeft();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				putBeeper();
				move();

			} else {
				putBeeper();
			}
		}
	}

	/*
	 * precondition: last column is not filled with beepers. karel facing east, at
	 * the end of first row postcondition: the board is filled with beepers
	 * checkerboard style. karel facing north
	 */
	private void fillLastColumn() {
		if (beepersPresent()) {
			moveType1();
		} else {
			moveType2();
		}
	}
}
