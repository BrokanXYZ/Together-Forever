//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

public class PaddleMover implements Runnable {

	private Model model;
	private View view;
	private boolean stop = false;
	private boolean keyA = false;
	private boolean keyD = false;
	private boolean keyJ = false;
	private boolean keyL = false;

	public PaddleMover(Model m, View v) {
		model = m;
		view = v;
	}

	public void run() {

		try {
			while (!stop) {

				if (keyA) {
					Model.updatePaddle(view.getWidth(), view.getHeight(), 0, 1);
				}

				if (keyD) {
					Model.updatePaddle(view.getWidth(), view.getHeight(), 1, 1);
				}

				if (keyJ) {
					Model.updatePaddle(view.getWidth(), view.getHeight(), 0, 2);
				}

				if (keyL) {
					Model.updatePaddle(view.getWidth(), view.getHeight(), 1, 2);
				}

				// Paddle update speed (Game difficulty increase!)
				Thread.sleep(5);

				// Stop thread if pause is pressed
				synchronized (this) {
					while (stop) {
						wait();
					}
				}

			}
		} catch (InterruptedException e) {
		}

	}

	public void stop() {
		stop = true;
	}

	synchronized void updateAndResume(char ch, boolean bool) {

		// Update!
		if (ch == 'a') {
			keyA = bool;
		} else if (ch == 'd') {
			keyD = bool;
		} else if (ch == 'j') {
			keyJ = bool;
		} else if (ch == 'l') {
			keyL = bool;
		} else if (ch == '!') {
			keyA = bool;
			keyD = bool;
			keyJ = bool;
			keyL = bool;
		}

		stop = false;
		notify(); // stop waiting
	}

}
