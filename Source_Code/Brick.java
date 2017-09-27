import java.awt.Graphics;

public class Brick extends Sprite {
	
	// Constructor
	public Brick(String jpgName, int posx, int posy) {

		// Sprite constructor call
		super(jpgName, 20, 60);

		setX(posx);
		setY(posy);
		
	}

	public void updateImage(Graphics g) {
		super.updateImage(g);
	}
	
}
