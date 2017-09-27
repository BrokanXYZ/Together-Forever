//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

public class SpriteMover implements Runnable {

	private Model model;
	private boolean stop = false;

	public SpriteMover(Model m) {
		model = m;
	}

	public void run() {

		try {
			while (!stop) {

				// Update scene
				Model.updateScene(750, 1000);

				// Update speed
				Thread.sleep(1);

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

	synchronized void resume() {
		stop = false;
		notify(); // stop waiting
	}

}
