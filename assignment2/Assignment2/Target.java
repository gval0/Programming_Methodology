
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {

	/** Radius of big red circle in cm */
	private static final double RED_BIG_CIRCLE_RADIUS = 2.54;

	/** Radius of big red circle in pixels */
	private static final int RED_BIG_CIRCLE_PIXEL_RADIUS = 72;

	/** Radius of white red circle in cm */
	private static final double WHITE_CIRCLE_RADIUS = 1.65;

	/** Radius of small red circle in cm */
	private static final double RED_SMALL_CIRCLE_RADIUS = 0.76;

	public void run() {
		circle(RED_BIG_CIRCLE_RADIUS, Color.red);
		circle(WHITE_CIRCLE_RADIUS, Color.white);
		circle(RED_SMALL_CIRCLE_RADIUS, Color.red);
	}

	/*
	 * reads radius of the oval and adds oval in the middle of the window reads
	 * color of the circle and colors it in accordingly
	 */
	private void circle(double radius, Color a) {
		radius = calulatePixel(radius);
		GOval oval = new GOval(2 * radius, 2 * radius);
		oval.setColor(a);
		oval.setFilled(true);
		oval.setFillColor(a);
		add(oval, getWidth() / 2 - radius, getHeight() / 2 - radius);

	}

	/* reads circle's radius in cm and return circle's radius in pixels */
	private double calulatePixel(double i) {
		double result = 0.0;
		result = i * RED_BIG_CIRCLE_PIXEL_RADIUS / RED_BIG_CIRCLE_RADIUS;
		return result;
	}

}
