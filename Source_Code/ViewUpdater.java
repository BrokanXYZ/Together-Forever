//	Project:		Honors Programming Paradigms - Multithreaded Video Game
//	Class:			CSCE 3193
//	Author:			Brokan Stafford
//	Last Updated: 	12/5/16

public class ViewUpdater implements Runnable {

	private View view;
	private boolean stop = false;

	public ViewUpdater(View v) {
		view = v;
	}

	public void run() {

		try {
			while (!stop) {

				// System.out.println("running");

				// Redraw scene
				view.repaint();

				// Refresh speed
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
