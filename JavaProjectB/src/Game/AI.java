package Game;

public class AI implements Runnable {

	/** Boolean isRunning = if true, game will be running. */
	private boolean isRunning = true;

	/** Boolean enemyTurn = if true, it will be the AI's turn. */
	public static boolean enemyTurn = true;

	/** int fps = holds the value of the frames per seconds. */
	private int fps = 10;

	/** Main main = holds the Thread main. */
	Main main;

	/** run = thread will check if enemy turn and execute enemyPlay() every second while true. */
	public void run() {

		while (isRunning) {
			long time = System.currentTimeMillis();
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			if (enemyTurn) {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
				enemyPlay();
			}

			time = (1000 / fps) - (System.currentTimeMillis() - time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

	/** int enemyInhand = Initializes enemyInhand for later use. */
	static int enemyInhand = 0;

	/** getEnemyHand() = sets and return the enemyInhand value. */
	int getEnemyHand() {
		enemyInhand = Main.enemyDeck.inHand.size();
		return enemyInhand;
	}

	/** Checks if Enemy has cards, if so then runs main loop for AI */
	void enemyPlay() {
		if (getEnemyHand() > 0) {
			for (int i = 0; i < getEnemyHand(); i++) {
				Main.clearSelected();
				Main.selectCard(i);
				Main.playEnemyCard(i);
				Main.enemyAttack();
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		}
		Main.enemyEnd();
	}
}
