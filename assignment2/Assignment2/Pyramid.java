/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {

		// based on the number of bricks in the line, following determines the
		// coordinates of the first brick
		for (int a = 0; a < BRICKS_IN_BASE; a++) {
			int brickX = (getWidth() - (BRICK_WIDTH * BRICKS_IN_BASE - ((a + 1) * BRICK_WIDTH) - BRICK_HEIGHT)) / 2;

			// according to bricks in line, following code puts bricks next to each other
			for (int i = 0; i < BRICKS_IN_BASE - a; i++) {
				GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				add(brick, brickX + i * BRICK_WIDTH, getHeight() - (a) * BRICK_HEIGHT - BRICK_HEIGHT);
			}
		}
	}
}