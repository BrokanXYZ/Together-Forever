//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

// Known issues
// 1. Loading a game will *sometimes* make the game unfailable
// 2. Issues with ball clipping through bricks and paddles (most often the 'unbreakable bricks')

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class Controller implements MouseListener, KeyListener {
	static Model model;
	static View view;
	static File save = new File("savedData.txt");

	static boolean gameover = true;
	static boolean pause = false;
	static boolean initialStart = true;
	static int curSpeed = 15;

	static private ViewUpdater movesThings;
	static private Thread moverThread;
	static private SpriteMover updatesSprites;
	static private Thread updateThread;
	static private PaddleMover movesPaddles;
	static private Thread paddleThread;
	static private BallMover movesBall;
	static private Thread ballThread;

	Controller() throws IOException, Exception {
		model = new Model();
		view = new View(this);
	}

	public void update(Graphics g) {
		model.update(g);
	}

	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			// Gets here is left mouse button was clicked

		} else if (SwingUtilities.isRightMouseButton(e)) {
			// Gets here if right mouse button was clicked

		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public static void main(String[] args) throws Exception {
		// Use the following line to determine which directory your program
		// is being executed from, since that is where the image files will
		// need to be.
		// System.out.println("cwd=" + System.getProperty("user.dir"));
		new Controller();
	}

	public void keyTyped(KeyEvent e) {

		// pause game
		if (e.getKeyChar() == ' ' && !gameover) {
			if (!pause) {
				movesThings.stop();
				movesPaddles.stop();
				movesBall.stop();
				updatesSprites.stop();
				pause = true;
				System.out.println("paused");
			} else {
				movesThings.resume();
				movesBall.updateAndResume(curSpeed);
				movesPaddles.updateAndResume('!', false);
				updatesSprites.resume();
				pause = false;
				System.out.println("resume");
			}
		}

	}

	public void keyPressed(KeyEvent e) {

		// Start moving paddle1 left
		if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('a', true);
		}

		// Start moving paddle1 right
		if ((e.getKeyChar() == 'd' || e.getKeyChar() == 'D') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('d', true);
		}

		// Start moving paddle2 left
		if ((e.getKeyChar() == 'j' || e.getKeyChar() == 'J') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('j', true);
		}

		// Start moving paddle2 right
		if ((e.getKeyChar() == 'l' || e.getKeyChar() == 'L') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('l', true);
		}

		// s = Start game or restart if GAMEOVER
		if ((e.getKeyChar() == 's' || e.getKeyChar() == 'S') && gameover) {

			Model.initialize();

			// Initialize and start threads
			if (initialStart) {
				// Paddles thread!
				movesPaddles = new PaddleMover(model, view);
				paddleThread = new Thread(movesPaddles);
				paddleThread.start();

				// Update view thread!
				movesThings = new ViewUpdater(view);
				moverThread = new Thread(movesThings);
				moverThread.start();

				// Update sprites thread!
				updatesSprites = new SpriteMover(model);
				updateThread = new Thread(updatesSprites);
				updateThread.start();

				// Ball thread!
				movesBall = new BallMover(model, view);
				ballThread = new Thread(movesBall);
				ballThread.start();
			} else {
				// New speed! Faster! start = 15.... 10... 8
				// Must be resumed after creation of the ball in SpriteList
				if (curSpeed == 15) {
					curSpeed = 10; // 2nd lvl
				} else {
					curSpeed = 8; // 3rd and + lvls
				}
				movesBall.updateAndResume(curSpeed);
				updatesSprites.resume();
			}

			gameover = false;
			pause = false;
			initialStart = false;
		}

	}

	public void keyReleased(KeyEvent e) {

		// STOP moving paddle1 left
		if ((e.getKeyChar() == 'a' || e.getKeyChar() == 'A') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('a', false);
		}

		// STOP moving paddle1 right
		if ((e.getKeyChar() == 'd' || e.getKeyChar() == 'D') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('d', false);
		}

		// STOP moving paddle2 left
		if ((e.getKeyChar() == 'j' || e.getKeyChar() == 'J') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('j', false);
		}

		// STOP moving paddle2 right
		if ((e.getKeyChar() == 'l' || e.getKeyChar() == 'L') && !pause && !gameover) {
			movesPaddles.stop();
			movesPaddles.updateAndResume('l', false);
		}

	}

	// FOR MENU PAUSING
	public static void pauseUnpause() {

		if (!gameover) {
			if (!pause) {
				movesThings.stop();
				movesBall.stop();
				movesPaddles.stop();
				updatesSprites.stop();
				pause = true;
				System.out.println("paused");
			} else {
				movesThings.resume();
				movesBall.updateAndResume(curSpeed);
				movesPaddles.updateAndResume('!', false);
				updatesSprites.resume();
				pause = false;
				System.out.println("resume");
			}
		}

	}

	public static void failureStopStart() {

		if (!gameover) {
			gameover = true;
			pause = true;
			movesBall.stop(); // RESUME ONCE BALL HAS BEEN CREATED (look in 's'
								// press)
			movesPaddles.stop();
			updatesSprites.stop(); // RESUME ONCE BALL HAS BEEN CREATED (look in
									// 's' press)

		} else {
			System.out.println("new game");
			movesPaddles.updateAndResume('!', false);
			gameover = false;
			pause = false;
		}

	}

	public static void save() throws IOException {

		if (!gameover && !pause) {
			pauseUnpause();
			JOptionPane.showMessageDialog(null, "Paused and saved current game state.");

		} else if (!gameover && pause) {
			JOptionPane.showMessageDialog(null, "Saved current game state.");
		} else {
			JOptionPane.showMessageDialog(null, "You are not playing. Will not save.");
		}

		if ((!gameover && !pause) || (!gameover && pause)) {

			FileWriter writer = new FileWriter(save, false);
			BufferedWriter bWriter = new BufferedWriter(writer);

			ArrayList<Sprite> spriteList = Model.getSpriteList();

			/*
			 * //Controller vars bWriter.write(String.valueOf(gameover));
			 * //Always false bWriter.newLine();
			 * bWriter.write(String.valueOf(pause)); //Always true
			 * bWriter.newLine(); bWriter.write(String.valueOf(initialStart));
			 * //Always false bWriter.newLine();
			 */

			// Speed
			bWriter.write(String.valueOf(curSpeed));
			bWriter.newLine();

			// Score
			bWriter.write(String.valueOf(Model.getScore()));
			bWriter.newLine();

			// Ball coords + xRatio/yRatio
			bWriter.write(String.valueOf(spriteList.get(0).getX()));
			bWriter.newLine();
			bWriter.write(String.valueOf(spriteList.get(0).getY()));
			bWriter.newLine();
			bWriter.write(String.valueOf(Ball.getXRatio()));
			bWriter.newLine();
			bWriter.write(String.valueOf(Ball.getYRatio()));
			bWriter.newLine();

			// Paddles Coords
			bWriter.write(String.valueOf(spriteList.get(1).getX()));
			bWriter.newLine();
			bWriter.write(String.valueOf(spriteList.get(1).getY()));
			bWriter.newLine();
			bWriter.write(String.valueOf(spriteList.get(2).getX()));
			bWriter.newLine();
			bWriter.write(String.valueOf(spriteList.get(2).getY()));
			bWriter.newLine();

			// Breakable bricks #
			bWriter.write(String.valueOf(spriteList.size() - 11));

			// Breakable bricks coords
			for (int x = 11; x < spriteList.size(); x++) {
				bWriter.newLine();
				bWriter.write(String.valueOf(spriteList.get(x).getX()));
				bWriter.newLine();
				bWriter.write(String.valueOf(spriteList.get(x).getY()));
			}

			// Close file
			bWriter.close();
		}

	}

	public static void load() throws IOException, InterruptedException {

		FileReader reader = new FileReader(save);
		BufferedReader bReader = new BufferedReader(reader);
		ArrayList<Sprite> newSpriteList = new ArrayList<Sprite>();

		// set curSpeed
		curSpeed = Integer.parseInt(bReader.readLine());

		// set Score
		Model.setScore(Integer.parseInt(bReader.readLine()));

		// Add Ball
		newSpriteList.add(new Ball(Integer.parseInt(bReader.readLine()), Integer.parseInt(bReader.readLine())));
		Ball.setXRatio(Integer.parseInt(bReader.readLine()));
		Ball.setYRatio(Integer.parseInt(bReader.readLine()));

		// Add paddle1
		newSpriteList.add(
				new Paddle("\Images\paddle1.jpg", Integer.parseInt(bReader.readLine()), Integer.parseInt(bReader.readLine())));

		// Add paddle2
		newSpriteList.add(
				new Paddle("\Images\paddle2.jpg", Integer.parseInt(bReader.readLine()), Integer.parseInt(bReader.readLine())));

		// Add unbreakable bricks
		// UNBREAKABLE LEFT
		newSpriteList.add(new Brick("\Images\brick1.jpg", 160, 435));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 130, 455));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 190, 455));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 160, 475));

		// UNBREAKABLE RIGHT
		newSpriteList.add(new Brick("\Images\brick1.jpg", 520, 435));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 490, 455));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 550, 455));
		newSpriteList.add(new Brick("\Images\brick1.jpg", 520, 475));

		// Add breakable bricks
		int numBricks = Integer.parseInt(bReader.readLine());
		for (int x = 0; x < numBricks; x++) {
			newSpriteList.add(new Brick("\Images\brick2.jpg", Integer.parseInt(bReader.readLine()),
					Integer.parseInt(bReader.readLine())));
		}

		// close file
		bReader.close();

		// Set Model Bool
		Model.setAtLeastOneGame(true);

		// Set spriteList
		Model.setSpriteList(newSpriteList);

		//If player is on the title screen... start all threads and pause them
		if (initialStart) {
			// Paddles thread!
			movesPaddles = new PaddleMover(model, view);
			paddleThread = new Thread(movesPaddles);
			paddleThread.start();

			// Update view thread!
			movesThings = new ViewUpdater(view);
			moverThread = new Thread(movesThings);
			moverThread.start();

			// Update sprites thread!
			updatesSprites = new SpriteMover(model);
			updateThread = new Thread(updatesSprites);
			updateThread.start();

			// Ball thread!
			movesBall = new BallMover(model, view);
			ballThread = new Thread(movesBall);
			ballThread.start();

			//Wait.... then stop threads (without sleep these threads break!)
			Thread.sleep(10);
			movesPaddles.stop();
			movesThings.stop();
			updatesSprites.stop();
			movesBall.stop();

			// Set Controller booleans
			gameover = false;
			pause = true;
			initialStart = false;
		} else {
		
			if (pause) {
				view.repaint();
			} else {
				pauseUnpause();
			}

			// Set Controller booleans
			gameover = false;
			pause = true;
		}

		JOptionPane.showMessageDialog(null, "Saved state has been loaded. Game is currently paused.");
	}

}
