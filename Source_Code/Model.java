//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;

class Model {
	private static ArrayList<Sprite> spriteList = new ArrayList<Sprite>();
	private static int score;
	private static boolean gameover = true;
	private static boolean atLeastOneGame = false;
	private static boolean clear = false;

	Model() throws IOException {

		spriteList = new ArrayList<Sprite>();

		spriteList.add(new Sprite("\Images\Title.jpg", 1000, 750));

	}

	public static void initialize() {

		if (atLeastOneGame == true) {
			// Resume Paddle + Ball movers threads
			Controller.failureStopStart();
		}

		// Start game (Called when 's' is pressed) or Restart if failure
		gameover = false;
		atLeastOneGame = true;

		// delete current spriteList
		spriteList.clear();

		// Reset score
		score = 0;

		// Add ball and paddles
		spriteList.add(new Ball(362, 460));
		spriteList.add(new Paddle("\Images\paddle1.jpg", 337, 880));
		spriteList.add(new Paddle("\Images\paddle2.jpg", 337, 45));

		// UNBREAKABLE LEFT
		spriteList.add(new Brick("\Images\brick2.jpg", 160, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 130, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 190, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 160, 475));

		// UNBREAKABLE RIGHT
		spriteList.add(new Brick("\Images\brick2.jpg", 520, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 490, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 550, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 520, 475));

		// BREAKABLES!
		spriteList.add(new Brick("\Images\brick2.jpg", 130, 495));
		spriteList.add(new Brick("\Images\brick2.jpg", 190, 495));
		spriteList.add(new Brick("\Images\brick2.jpg", 100, 475));
		spriteList.add(new Brick("\Images\brick2.jpg", 220, 475));
		spriteList.add(new Brick("\Images\brick2.jpg", 250, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 70, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 220, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 100, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 130, 415));
		spriteList.add(new Brick("\Images\brick2.jpg", 190, 415));

		spriteList.add(new Brick("\Images\brick2.jpg", 490, 495));
		spriteList.add(new Brick("\Images\brick2.jpg", 550, 495));
		spriteList.add(new Brick("\Images\brick2.jpg", 460, 475));
		spriteList.add(new Brick("\Images\brick2.jpg", 580, 475));
		spriteList.add(new Brick("\Images\brick2.jpg", 610, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 430, 455));
		spriteList.add(new Brick("\Images\brick2.jpg", 580, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 460, 435));
		spriteList.add(new Brick("\Images\brick2.jpg", 490, 415));
		spriteList.add(new Brick("\Images\brick2.jpg", 550, 415));

	}

	public void update(Graphics g) {

		// Update all sprites
		synchronized (spriteList) {
			for (int x = 0; x < spriteList.size(); x++)
				(spriteList.get(x)).updateImage(g);
		}

		// Update score
		if (atLeastOneGame) {
			g.drawString("SCORE: " + Integer.toString(score), 600, 20);
		}

	}

	public static void updateScene(int frameW, int frameH) throws InterruptedException {

		// Check for brick/paddle push
		for (int x = 1; x < spriteList.size(); x++) {

			if ((spriteList.get(x)).overlaps((spriteList.get(0)))) {
				((Ball) spriteList.get(0)).push(spriteList.get(x));
				if (x > 10) {// if a brick... remove and increment score!
					spriteList.remove(x);
					score += 10;
				}

				// once yellow bricks are cleared... next push reset!
				if (clear == true) {

					// Clear Bonus
					score += 100;

					// BREAKABLES!
					spriteList.add(new Brick("\Images\brick2.jpg", 130, 495));
					spriteList.add(new Brick("\Images\brick2.jpg", 190, 495));
					spriteList.add(new Brick("\Images\brick2.jpg", 100, 475));
					spriteList.add(new Brick("\Images\brick2.jpg", 220, 475));
					spriteList.add(new Brick("\Images\brick2.jpg", 250, 455));
					spriteList.add(new Brick("\Images\brick2.jpg", 70, 455));
					spriteList.add(new Brick("\Images\brick2.jpg", 220, 435));
					spriteList.add(new Brick("\Images\brick2.jpg", 100, 435));
					spriteList.add(new Brick("\Images\brick2.jpg", 130, 415));
					spriteList.add(new Brick("\Images\brick2.jpg", 190, 415));

					spriteList.add(new Brick("\Images\brick2.jpg", 490, 495));
					spriteList.add(new Brick("\Images\brick2.jpg", 550, 495));
					spriteList.add(new Brick("\Images\brick2.jpg", 460, 475));
					spriteList.add(new Brick("\Images\brick2.jpg", 580, 475));
					spriteList.add(new Brick("\Images\brick2.jpg", 610, 455));
					spriteList.add(new Brick("\Images\brick2.jpg", 430, 455));
					spriteList.add(new Brick("\Images\brick2.jpg", 580, 435));
					spriteList.add(new Brick("\Images\brick2.jpg", 460, 435));
					spriteList.add(new Brick("\Images\brick2.jpg", 490, 415));
					spriteList.add(new Brick("\Images\brick2.jpg", 550, 415));

					clear = false;
				}

				Thread.sleep(100); // wait so ball won't get stuck
				break; // only 1 brick break per check
			}
		}

		// GAMEOVER check
		if ((spriteList.get(0).getY() < 15 || spriteList.get(0).getY() > 910) && !gameover) {
			failure();
		}

		// Clear bonus!
		if (spriteList.size() == 11) {
			clear = true;
		}

	}

	public static void updateBall(int frameW, int frameH) {
		// Update state of ball
		(spriteList.get(0)).updateState(frameW, frameH);
	}

	public static void updatePaddle(int frameW, int frameH, int RorL, int paddleNum) {
		// Update state of paddle 1 or 2
		(spriteList.get(paddleNum)).updateState(frameW, frameH, RorL);
	}

	public static void failure() {

		// stop paddle + ball mover threads
		Controller.failureStopStart();

		// Remove score
		gameover = true;

		// delete current spriteList
		spriteList.clear();

		// show Game Over
		spriteList.add(new Sprite("\Images\gameover.jpg", 1000, 750));

	}

	// Getters
	public static ArrayList<Sprite> getSpriteList() {
		return spriteList;
	}

	public static int getScore() {
		return score;
	}

	// Setters
	public static void setSpriteList(ArrayList<Sprite> sl) {
		spriteList = sl;
	}

	public static void setScore(int sc) {
		score = sc;
	}

	public static void setAtLeastOneGame(boolean bool) {
		atLeastOneGame = bool;
	}

}
