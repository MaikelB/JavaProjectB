package Game;

public class AI implements Runnable {

	/** Boolean isRunning = if true, game will be running */
	private boolean isRunning = true;

	public static boolean enemyTurn = true;

	/** int fps = holds the value of the frames per seconds */
	private int fps = 60;

	Main main;

	void initialize() {

	}

	public void run() {
		initialize();
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

	int enemyInhand;

	int getEnemyHand() {
		enemyInhand = Main.enemyDeck.inHand.size();
		return enemyInhand;
	}

	void enemyPlay() {
		getEnemyHand();
		if (getEnemyHand() > 0) {
			for (int i = 0; i < enemyInhand; i++) {
				Main.selectCard(i);
				Main.playEnemyCard();
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
