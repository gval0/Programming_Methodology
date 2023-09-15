
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {
	/* Width of each class box in pixels */
	private static final int RECT_WIDHT = 200;

	/* Height of each class box in pixels */
	private static final int RECT_HEIGHT = 70;

	public void run() {
		addUpperBox();
		addBottomBoxes();
	}

	/*
	 * precondition: window is clear postcondition: upper box is centered and inside
	 * is wtitten "Program"
	 */
	private void addUpperBox() {
		GRect program = new GRect(RECT_WIDHT, RECT_HEIGHT);
		double rectX = getWidth() / 2 - RECT_WIDHT / 2;
		double rectY = getHeight() / 2 - RECT_HEIGHT - 25;
		add(program, rectX, rectY);

		GLabel label = new GLabel("Program");
		double labelX = getWidth() / 2 - label.getWidth() / 2;
		double labelY = getHeight() / 2 - RECT_HEIGHT / 2 - 25 + label.getAscent() / 2;
		add(label, labelX, labelY);
	}

	/*
	 * precondition: only upper box is present precondition: all the boxes and lines
	 * are present
	 */
	private void addBottomBoxes() {
		addRect(0, "GraphicsProgram");
		addRect(1, "ConsoleProgram");
		addRect(2, "DialogProgram");
	}

	/*
	 * postcondition: all the boxes, lables and lines are present
	 */
	private void addRect(int a, String name) {
		double distance = RECT_WIDHT + 50;

		// method reads number when number is 0 it gets coordinates for westest box
		// next box coordinates are 1 "distance" away and so on
		double rectX = getWidth() / 2 - RECT_WIDHT / 2 - 50;
		GRect rect = new GRect(RECT_WIDHT, RECT_HEIGHT);
		add(rect, rectX - RECT_WIDHT + a * distance, getHeight() / 2 + 25);

		// postcondition: labels are added to all boxes
		double labelX = getWidth() / 2 - RECT_WIDHT - 50;
		GLabel label = new GLabel(name);
		double c = (getHeight() / 2 + 25 + RECT_HEIGHT / 2 + label.getAscent() / 2);
		add(label, labelX + a * distance - label.getWidth() / 2, c);

		// connecting lines between boxes are added
		GLine line = new GLine(labelX + a * distance, getHeight() / 2 + 50 / 2, getWidth() / 2, getHeight() / 2 - 25);
		add(line);
	}

}