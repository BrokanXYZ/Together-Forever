//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

import java.awt.Graphics;

public class Paddle extends Sprite {

	// Constructor
	public Paddle(String jpgName, int posx, int posy) {

		// Sprite constructor call
		super(jpgName, 10, 60);

		setX(posx);
		setY(posy);

	}

	public void updateState(int frameW, int frameH, int RorL) {

		if (RorL == 1 && getX() < 670) { // Move right
			setX(getX() + 2);
		} else if (RorL == 0 && getX() > 5) { // Move left
			setX(getX() - 2);
		}

	}

	public void updateImage(Graphics g) {
		super.updateImage(g);
	}

}
