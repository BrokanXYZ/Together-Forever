
//Assignment 4
//CSCE 3193
//Brokan Stafford 09/19/2016

import java.awt.Graphics;
import java.util.Random;

public class Ball extends Sprite {

	// Attributes
	private static int xRatio;
	private static int yRatio;
	private static Random rando = new Random();
	private static boolean negativeX = false;
	private static boolean negativeY = false;

	// Constructor
	public Ball(int posx, int posy) {

		// Sprite constructor call
		super("\Images\ball.jpg", 10, 10);

		yRatio = 0;

		// Random starting speed + direction
		xRatio = 0;
		while (yRatio == 0)
			yRatio = rando.nextInt(7) - 3; // random int -3 -> 3 (excluding 0)

		setX(posx);
		setY(posy);

	}

	public void updateState(int frameW, int frameH) {

		// Check if ball is out of the frame!
		if (this.getX() > frameW - 25 || this.getX() < 0)
			if (negativeX == true) {
				negativeX = false;
			} else {
				negativeX = true;
			}

		if (this.getY() > frameH - 70 || this.getY() < 0)
			if (negativeY == true) {
				negativeY = false;
			} else {
				negativeY = true;
			}

		// Move the sprite
		if (!negativeX && !negativeY) {
			setX(getX() + xRatio);
			setY(getY() + yRatio);
		} else if (negativeX && negativeY) {
			setX(getX() - xRatio);
			setY(getY() - yRatio);
		} else if (negativeX) {
			setX(getX() - xRatio);
			setY(getY() + yRatio);
		} else {
			setX(getX() + xRatio);
			setY(getY() - yRatio);
		}

	}

	public void push(Sprite pusher) {
		/*
		 * //Having trouble with precision pushing //so currently its just
		 * random... xRatio = rando.nextInt(11) - 5; //random int -5 -> 5
		 * if(yRatio<0){ yRatio = rando.nextInt(2) + 2; //random int 2 or 3
		 * }else{ yRatio = rando.nextInt(2) -3; //random int -2 or -3 }
		 */

		double hitMarker; // -34 -> 34, 0 = middle
		int newYratio = 0;

		// Gen #
		newYratio = rando.nextInt(4) + 1; // random int 1 -> 4

		// Determine correct sign for correct push
		if (yRatio > 0) {
			newYratio = -newYratio;
		} else {
			// NOTHING b/c newYratio should be positive
		}

		// Generate hitmarker!
		if (pusher instanceof Brick) {
			hitMarker = (this.getX() - pusher.getX() - 25) - 2; // weird
																// correction
																// for bricks...
																// ??
		} else {
			hitMarker = (this.getX() - pusher.getX() - 25);
		}

		// Zone definitions
		if (hitMarker >= -34 && hitMarker <= -29) { // --- zone
			this.xRatio = rando.nextInt(2) - 4; // random int -3 or -4
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("---");
		} else if (hitMarker >= -28 && hitMarker <= -18) { // -- zone
			this.xRatio = rando.nextInt(2) - 3; // random int -2 or -3
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("--");
		} else if (hitMarker >= -17 && hitMarker <= -6) { // - zone
			this.xRatio = rando.nextInt(2) - 2; // random int -1 or -2
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("-");
		} else if (hitMarker >= -5 && hitMarker <= 5) { // Middle zone
			this.xRatio = 0;
			this.yRatio = newYratio + 1; // compensate for xRatio=0
			// System.out.println("0");
		} else if (hitMarker >= 6 && hitMarker <= 17) { // + zone
			this.xRatio = rando.nextInt(2) + 1; // random int 1 or 2
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("+");
		} else if (hitMarker >= 18 && hitMarker <= 28) { // ++ zone
			this.xRatio = rando.nextInt(2) + 2; // random int 2 or 3
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("++");
		} else if (hitMarker >= 29 && hitMarker <= 34) { // +++ zone
			this.xRatio = rando.nextInt(2) + 3; // random int 3 or 4
			this.yRatio = newYratio;
			this.negativeX = false;
			// System.out.println("+++");
		}

	}

	public void updateImage(Graphics g) {
		super.updateImage(g);
	}

	// Getters
	public static int getXRatio() {
		return xRatio;
	}

	public static int getYRatio() {
		return yRatio;
	}

	// Setters
	public static void setXRatio(int x) {
		xRatio = x;
	}

	public static void setYRatio(int y) {
		yRatio = y;
	}

}
