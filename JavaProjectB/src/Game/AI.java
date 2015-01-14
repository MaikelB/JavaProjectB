package Game;

public class AI implements Runnable {

	/** Boolean isRunning = if true, game will be running */
	private boolean isRunning = true;

	/** int fps = holds the value of the frames per seconds */
	private int fps = 60;
	
	Main main;
	
	void initialize() {
		
	}
	
	public void run() {
		initialize();
		while(isRunning){
			long time = System.currentTimeMillis();
			
			
			
			time = (1000 / fps) - (System.currentTimeMillis() - time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

	void enemyPlay() {
		
		
		
	}
}
