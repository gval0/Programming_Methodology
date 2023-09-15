
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Color;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Starting y velocity */
	private double vy = 3.0;

	/** Random generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/** Random x velocity */
	private double vx = rgen.nextDouble(1.0, 3.0);

	/** Private instance variable */
	private GOval ball;

	/** Private instance variable */
	private GRect paddle;

	/** Duration of pause */
	private static final int pause = 10;

	/** Number of bricks on the canvas */
	private int NUMBER_OF_BRICKS_REMAINING = NBRICK_ROWS * NBRICKS_PER_ROW;

	/** Private instance variable */
	private GLabel label;
	
	/** Diameter of the oval showing number of turns left */
	private static final int LIFECOUNT_BALL_D = 15;
	
	/** Separation between oval showing number of turns  */
	private static final int LIFECOUNT_BALL_SEP = 10;
	
	/** Private instance variable */
	private GOval LIFECOUNT_BALL;
	
	/** Private instance variable helps identify location of life count ovals */
	private int a = NTURNS - 1;

	/** Boolean showing if game is running */
	private boolean GAME_IN_ACTION = true;

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		addMouseListeners();
		gameSetUp();
		play();
	}

	/**
	 * precondition: canvas is empty 
	 * postcondition: canvas is right size, all the bricks and paddle are present
	 */
	private void gameSetUp() {
		setCanvasSize();
		setBricks();
		setPaddle();
		addLifecountBall();
	}

	/**
	 * set canvas size
	 */
	private void setCanvasSize() {
		Canvas maincanvas = new Canvas();
		maincanvas.setSize(WIDTH, HEIGHT);
	}

	/**
	 * determine row number
	 */
	private void setBricks() {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			addBricks(i);
		}
	}

	/**
	 * add bricks according to the row number
	 */
	private void addBricks(int i) {
		for (int j = 0; j < NBRICKS_PER_ROW; j++) {
			GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
			brick.setFilled(true);
			brick.setColor(brickColor(i));
			add(brick, j * (BRICK_WIDTH + BRICK_SEP), BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP));
		}
	}

	/**
	 * return the color of the bricks based on the row number
	 */
	private Color brickColor(int i) {
		int colorNumber = i % 10;
		if (colorNumber == 0 || colorNumber == 1)
			return Color.RED;
		if (colorNumber == 2 || colorNumber == 3)
			return Color.ORANGE;
		if (colorNumber == 4 || colorNumber == 5)
			return Color.YELLOW;
		if (colorNumber == 6 || colorNumber == 7)
			return Color.GREEN;
		if (colorNumber == 8 || colorNumber == 9) {
			return Color.CYAN;
		} else
			return null;
	}

	/**
	 * add paddle
	 */
	private void setPaddle() {
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.BLACK);
		add(paddle, getWidth() / 2 - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);

	}

	/**
	 * paddle moves along with mouse paddle until player has not lost
	 * paddle does not go beyond the window borders
	 */
	public void mouseMoved(MouseEvent e) {
		if (GAME_IN_ACTION) {
			add(paddle, e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			pause(10);
			if (e.getX() >= getWidth() - PADDLE_WIDTH / 2)
				add(paddle, getWidth() - PADDLE_WIDTH, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
			if (e.getX() - PADDLE_WIDTH / 2 <= 0)
				add(paddle, 0, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}

	}
	
	/**
	 * add life count ovals
	 */
	private void addLifecountBall() {
		for (int i = 1; i <= NTURNS; i++) {
			LIFECOUNT_BALL = new GOval(LIFECOUNT_BALL_D, LIFECOUNT_BALL_D);
			LIFECOUNT_BALL.setFilled(true);
			LIFECOUNT_BALL.setColor(Color.gray);
			double ballY = BRICK_Y_OFFSET / 2;
			int ballX = getWidth() - i * (LIFECOUNT_BALL_SEP + LIFECOUNT_BALL_D);
			add(LIFECOUNT_BALL, ballX, ballY);
		}

	}

	/**
	 * play game
	 */
	private void play() {
		for (int i = NTURNS; i > 0; i--) {
			addStartingLabel();
			createBall();
			determineVX();
			collisionCheck();
			if (NUMBER_OF_BRICKS_REMAINING == 0) break;
		}
		if (NUMBER_OF_BRICKS_REMAINING != 0) {
			label = new GLabel("Oops, GAME OVER :(");
		}
		FinalMessage();
	}
	
	/**
	 * add label indicating that player should click on canvas to start the game
	 */
	private void addStartingLabel() {
		GLabel startLabel = new GLabel("Click to start");
		startLabel.setFont("Helvetica-20");
		startLabel.setColor(Color.BLUE);
		double labelX = getWidth() / 2 - startLabel.getWidth() / 2;
		double labelY = getHeight() / 2 - startLabel.getAscent() / 2;
		add(startLabel, labelX, labelY);
		waitForClick();
		remove(startLabel);
	}

	/**
	 * add ball
	 */
	private void createBall() {
		ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball, getWidth() / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);

	}

	/**
	 * Determine random ball direction on x
	 */
	private void determineVX() {
		if (rgen.nextBoolean(0.5))
			vx = -vx;
	}

	/**
	 * if collided to brick removes brick
	 * count amount of bricks on canvas
	 */
	private void collisionCheck() {
		while (ball.getY() + 2 * BALL_RADIUS < getHeight()) {
			if (NUMBER_OF_BRICKS_REMAINING > 0) {
				moveBall();
				changeDirection();
				collide();
				pause(pause);
			} else {
				label = new GLabel("YAY, YOU WON! <3");
				break;
			}
		}
		remove(ball);
		// if ball touches bottom wall, then ball count will show one less oval
		remove(getElementAt(getWidth() - LIFECOUNT_BALL_SEP - LIFECOUNT_BALL_D / 2 - a * (LIFECOUNT_BALL_SEP + LIFECOUNT_BALL_D),BRICK_Y_OFFSET / 2 + LIFECOUNT_BALL_D / 2));
		a--;

	}

	/**
	 * move ball
	 */
	private void moveBall() {
		ball.move(vx, vy);
	}

	/**
	 * determine direction of the ball
	 */
	private void changeDirection() {
		if (ball.getX() + 2 * BALL_RADIUS >= getWidth())
			vx = -vx;
		if (ball.getX() <= 0)
			vx = -vx;
		if (ball.getY() <= 0)
			vy = -vy;
	}

	/**
	 * if ball collides with bricks they disappear 
	 * if ball collides with paddle it bounces off
	 */
	private void collide() {
		GObject collider = getCollidingObject(ball.getX(), ball.getY());
		if (collider != null && collider != paddle && collider.getColor() != Color.gray) {
			remove(collider);
			NUMBER_OF_BRICKS_REMAINING--;
			vy = -vy;
		}
		if (collider == paddle) {
			ball.move(0, -2 * BALL_RADIUS);
			vy = -vy;
		}

	}

	/**
	 * check if any of the 4 points around the ball has collided with other GObject
	 */
	private GObject getCollidingObject(double x, double y) {
		if (getElementAt(x, y) != null)
			return getElementAt(x, y);
		if (getElementAt(x + 2 * BALL_RADIUS, y) != null)
			return getElementAt(x + 2 * BALL_RADIUS, y);
		if (getElementAt(x, y + 2 * BALL_RADIUS) != null)
			return getElementAt(x, y + 2 * BALL_RADIUS);
		if (getElementAt(x + 2 * BALL_RADIUS, y + 2 * BALL_RADIUS) != null) {
			return getElementAt(x + 2 * BALL_RADIUS, y + 2 * BALL_RADIUS);
		} else {
			return null;
		}
	}

	/**
	 * show message whether player has won the game or not
	 */
	private void FinalMessage() {
		GAME_IN_ACTION = false;
		
		GRect rect = new GRect(getWidth(), getHeight());
		rect.setFilled(true);
		rect.setColor(Color.cyan);
		add(rect);
		
		label.setFont("Helvetica-30");
		label.setColor(Color.blue);
		double labelX = getWidth() / 2 - label.getWidth() / 2;
		double labelY = getHeight() / 2 - label.getAscent() / 2;
		add(label, labelX, labelY);

	}
}