//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class View extends JFrame implements ActionListener {

	private class MyPanel extends JPanel {
		Controller controller;

		MyPanel(Controller c) {
			controller = c;
			addMouseListener(c);

		}

		public void paintComponent(Graphics g) {
			controller.update(g);
			revalidate();
		}
	}

	public View(Controller c) throws Exception {

		// KEY LISTENER
		addKeyListener(c);

		// Create menu
		JMenuBar menu = new JMenuBar();

			// pause
			JMenuItem pause = new JMenuItem("Pause");
			pause.addActionListener((ActionEvent event) -> {
				Controller.pauseUnpause();
			});
	
			// instructions
			JMenuItem instructions = new JMenuItem("Instructions");
			instructions.addActionListener((ActionEvent event) -> {
				InstructionsView iView;
				try {
					iView = new InstructionsView();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	
			// save
			JMenuItem save = new JMenuItem("Save");
			save.addActionListener((ActionEvent event) -> {
				try {
					Controller.save();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	
			// load
			JMenuItem load = new JMenuItem("Load");
			load.addActionListener((ActionEvent event) -> {
				try {
					Controller.load();
				} catch (IOException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	
			// exit
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener((ActionEvent event) -> {
				System.exit(0);
			});

		// Add menu items
		menu.add(pause);
		menu.add(instructions);
		menu.add(save);
		menu.add(load);
		menu.add(exit);
		setJMenuBar(menu);

		// Frame setup
		setTitle("Together Forever");
		setSize(750, 1000);
		setResizable(false);
		getContentPane().add(new MyPanel(c));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent evt) {
		repaint();
	}

}
