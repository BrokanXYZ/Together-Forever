//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class Sprite {
	private int locationX;
	private int locationY;
	private Image image;
	private int height;
	private int width;

	public Sprite(String jpgName, int h, int w) {
		setImage(jpgName);
		locationX = 0;
		locationY = 0;
		height = h;
		width = w;
	}

	public int getX() {
		return locationX;
	}

	public int getY() {
		return locationY;
	}

	public void setX(int x) {
		locationX = x;
	}

	public void setY(int y) {
		locationY = y;
	}

	public void setImage(String imagePath) {
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException ioe) {
			System.out.println("Unable to load image file.");
		}
	}

	public Image getImage() {
		return image;
	}

	public void updateImage(Graphics g) {
		g.drawImage(getImage(), getX(), getY(), width, height, null);
	}

	public void updateState(int frameW, int frameH) {

	}

	public boolean overlaps(Sprite s) {

		Rectangle r1 = new Rectangle(this.getX(), this.getY(), this.width, this.height);
		Rectangle r2 = new Rectangle(s.getX(), s.getY(), s.width, s.height);

		if (r1.intersects(r2))
			return true;

		return false;
	}

	public void updateState(int frameW, int frameH, int rorL) {
		// TODO Auto-generated method stub

	}

}