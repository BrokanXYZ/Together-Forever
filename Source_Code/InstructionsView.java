//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class InstructionsView extends JFrame implements ActionListener {

	private class MyPanel extends JPanel {

		MyPanel() {

		}

		public void paintComponent(Graphics g) {

			Image image = null;
			try {
				image = ImageIO.read(new File("\Images\instructions.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.drawImage(image, 0, 0, 630, 430, null);
			revalidate();
		}
	}

	public InstructionsView() throws Exception {

		// Frame setup
		setTitle("Instructions!");
		setSize(630, 430);
		setResizable(false); 
		getContentPane().add(new MyPanel());
		setVisible(true);

	}

	public void actionPerformed(ActionEvent evt) {
		repaint();
	}

}
