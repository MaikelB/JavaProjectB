package Game;


public class Main {
	private int fps = 10;
	private boolean isRunning = true;
	
	
	
	public static void main(String[] args) {
		Main game = new Main();
		game.run();

	}
	public void run() {
		(new Thread(new DrawHandler())).start();
		
		while (isRunning) {
			long time = System.currentTimeMillis();

			update();

			time = (1000 / fps) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

	void update() {

	}

}
