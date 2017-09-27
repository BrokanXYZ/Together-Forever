public class BallMover implements Runnable {

	private Model model;
	private View view;
	private boolean stop = false;
	private int gamespeed = 15;

	public BallMover(Model m, View v) {
		model = m;
		view = v;
	}

	public void run() {

		try {
			while (!stop) {
	
				
				//Update Ball
				Model.updateBall(view.getWidth(), view.getHeight());
				
				// Game speed
				Thread.sleep(gamespeed);
				
				// Stop thread if pause is pressed
				synchronized(this) {
		               while(stop) {
		                  wait();
		               }
		        }
				
			}
		} catch (InterruptedException e) {}
	
	}
	
	
	public void stop(){
		stop = true;
	}
	
	synchronized void updateAndResume(int speed){
		stop = false;
		gamespeed = speed;
		notify();	// stop waiting
	}
	
	
}
