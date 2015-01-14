package Game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Main implements MouseListener {
	private int fps = 60;
	private boolean isRunning = true;
	public static boolean myTurn = false;
	public static Deck playerDeck = null;
	public static Deck enemyDeck = null;
	private int[] buttonSize = { 100, 40 };
	public static int gameState = 1;
	/*
	 * arrays for the mouse hover and click-boxes
	 */
	static public int[][] enemyInHand = new int[9][2];
	static public int[][] inHandBoxes = new int[9][2];
	static public int[][] playerOnTableBoxes = new int[9][2];
	static public int[][] playerOnSpellsBoxes = new int[2][2];
	static public int[][] enemyOnTableBoxes = new int[9][2];
	static public int[][] heroBoxes = new int[2][2];

	public static void main(String[] args) {
		Main game = new Main();
		FileHandler fileHandler = new FileHandler();
		try {
			fileHandler.loadDeck("deck1", true);
			fileHandler.loadDeck("deck2", false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("testing: " + playerDeck.deck_name);
		System.out.println("testing: " + enemyDeck.deck_name);
		game.run();

	}

	public Main() {

	}

	public Main(Component d) {
		d.addMouseListener(this);
	}

	private void initialize() {
		// warning!!! This is is for testing. needs to be edited for release
		for (int i = 0; i < 9; i++) {
			inHandBoxes[i][0] = 1;
			inHandBoxes[i][1] = 0;

		}

		playerDeck.drawCard();
		playerDeck.drawCard();
		playerDeck.drawCard();
		playerDeck.drawCard();
		playerDeck.drawCard();
		playerDeck.drawCard();
		enemyDeck.drawCard();
		enemyDeck.drawCard();
		enemyDeck.drawCard();
		enemyDeck.drawCard();
		enemyDeck.drawCard();


	}

	public void run() {
		(new Thread(new DrawHandler())).start();
		(new Thread(new AI())).start();
		
		initialize();
		while (isRunning) {
			long time = System.currentTimeMillis();
			// Main menu
			update(gameState);

			
			
			
			time = (1000 / fps) - (System.currentTimeMillis() - time);

			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

	void update(int gameS) {
		if (gameS == 0) {

		}
		if (gameS == 1) {
			myTurn = true;
			AI.enemyTurn = false;
			playerDeck.notPlayable.clear();
			int arraySize = playerDeck.inHand.size();
			for (int i = 0; i < arraySize; i++) {
				if (playerDeck.onSpells.size() >= 2
						&& playerDeck.inHand.get(i).getRarity() == 4) {
					playerDeck.notPlayable.add(playerDeck.inHand.get(i));
				}
			}

			playerBoxes();

		}
		if (gameS == 2) {
			AI.enemyTurn = true;
			myTurn = false;
			enemyDeck.notPlayable.clear();
			int arraySize = enemyDeck.inHand.size();
			for (int i = 0; i < arraySize; i++) {
				if (enemyDeck.onSpells.size() >= 2
						&& enemyDeck.inHand.get(i).getRarity() == 4) {
					enemyDeck.notPlayable.add(enemyDeck.inHand.get(i));
				}
			}
		}
	}

	private void playCard() {
		for (int i = 0; i < playerDeck.inHand.size(); i++) {
			if (inHandBoxes[i][1] == 1) {
				if (playerDeck.getHandCard(i).getSpecial() == 2) {
					int tableSize = playerDeck.onTable.size();
					for (int a = 0; a < tableSize; a++) {
						playerDeck.onTable.get(a).buffDefense(1);
					}
				} else if (playerDeck.getHandCard(i).getSpecial() == 3) {
					int tableSize = playerDeck.onTable.size();
					for (int b = 0; b < tableSize; b++) {
						playerDeck.onTable.get(b).buffAttack(1);
					}
				} else if (playerDeck.getHandCard(i).getSpecial() == 4) {
					int tableSize = playerDeck.onTable.size();
					int extraAttack = 0;
					for (int d = 0; d < tableSize; d++) {
						if (playerDeck.onTable.get(d).getSpecial() == 1) {
							extraAttack += 1;
						}
					}
					playerDeck.getHandCard(i).buffAttack(extraAttack);

				}
				playerDeck.cardPlay(i);
				clearSelected();
			}

		}
	}

	public static void enemyAttack() {
		buttonClickEnemy(2);
	}

	public static void enemyEnd() {
		buttonClickEnemy(3);
	}

	public static void selectCard(int i) {
		enemyInHand[i][1] = 1;
	}

	public static void playEnemyCard() {
			if (!enemyDeck.notPlayable.contains(enemyDeck.inHand.get(0))) {
				if (enemyDeck.getHandCard(0).getSpecial() == 2) {
					int tableSize = enemyDeck.onTable.size();
					for (int a = 0; a < tableSize; a++) {
						enemyDeck.onTable.get(a).buffDefense(1);
					}
				} else if (enemyDeck.getHandCard(0).getSpecial() == 3) {
					int tableSize = enemyDeck.onTable.size();
					for (int b = 0; b < tableSize; b++) {
						enemyDeck.onTable.get(b).buffAttack(1);
					}
				} else if (enemyDeck.getHandCard(0).getSpecial() == 4) {
					int tableSize = enemyDeck.onTable.size();
					int extraAttack = 0;
					for (int d = 0; d < tableSize; d++) {
						if (enemyDeck.onTable.get(d).getSpecial() == 1) {
							extraAttack += 1;
						}
					}
					enemyDeck.getHandCard(0).buffAttack(extraAttack);

				}
				enemyDeck.cardPlay(0);
			}
		
	}

	public static void buttonClickEnemy(int i) {
		switch (i) {
		case 0: /* chooseEnemyHero(); */
			System.out.println("buttoncheck enemy hero");
			break;
		case 1:
			playEnemyCard();
			System.out.println("buttoncheck enemy play");
			break;
		case 2: /* attackEnemyCard(); */
			System.out.println("buttoncheck enemy attack");
			break;
		case 3:
			endEnemyTurn();
			System.out.println("buttoncheck enemy end");
			break;
		}
	}
	
	public static void endEnemyTurn(){
		gameState = 1;
	}

	private void buttonClickBoxes(int x, int y) {
		for (int i = 0; i < DrawHandler.buttons.length; i++) {
			if (x > DrawHandler.buttons[i][0]
					&& x < DrawHandler.buttons[i][0] + buttonSize[0]
					&& y > DrawHandler.buttons[i][1]
					&& y < DrawHandler.buttons[i][1] + buttonSize[1]
					&& DrawHandler.buttons[i][2] == 1) {
				switch (DrawHandler.buttons[i][3]) {
				case 0: /* chooseHero(); */
					System.out.println("buttoncheck hero");
					break;
				case 1:
					playCard();
					System.out.println("buttoncheck play");
					break;
				case 2: /* attackCard(); */
					System.out.println("buttoncheck attack");
					break;
				case 3: endTurn();
					System.out.println("buttoncheck end");
					break;

				}
			}
		}
	}

	private void endTurn(){
		gameState = 2;
	}
	
	private void clearSelected() {
		for (int i = 0; i < 9; i++) {
			inHandBoxes[i][1] = 0;
			playerOnTableBoxes[i][1] = 0;
			enemyOnTableBoxes[i][1] = 0;
		}
		for (int i = 0; i < 2; i++) {
			playerOnSpellsBoxes[i][1] = 0;
		}

	}

	private void clickBoxes(int x, int y) {
		System.out.println(x + " - " + y);
		// inHand

		for (int i = 0; i < 9; i++) {
			if (x > 215 + 95 * i && x < 305 + 95 * i && y > 674
					&& inHandBoxes[i][0] == 1) {
				System.out.println("inhandbox: " + i);
				if (inHandBoxes[i][1] == 1) {
					inHandBoxes[i][1] = 0;
				} else {
					clearSelected();
					inHandBoxes[i][1] = 1;
				}

			}
		}
		// onTable player
		for (int i = 0; i < 9; i++) {
			if (x > 220 + 95 * i && x < 310 + 95 * i && y > 380 && y < 520
					&& playerOnTableBoxes[i][0] == 1) {
				System.out.println("player onTablebox: " + i);

				if (playerOnTableBoxes[i][1] == 1) {
					playerOnTableBoxes[i][1] = 0;
				} else {
					clearSelected();
					playerOnTableBoxes[i][1] = 1;
				}

			}
		}
		// onTable enemy
		for (int i = 0; i < 9; i++) {
			if (x > 220 + 95 * i && x < 310 + 95 * i && y > 200 && y < 340
					&& enemyOnTableBoxes[i][0] == 1) {
				System.out.println("enemy onTablebox: " + i);
				if (enemyOnTableBoxes[i][1] == 1) {
					enemyOnTableBoxes[i][1] = 0;
				} else {
					enemyOnTableBoxes[i][1] = 1;
				}
			}
		}
		// onSpells player
		for (int i = 0; i < 2; i++) {
			if (x > 20 + 95 * i && x < 110 + 95 * i && y > 380 && y < 520
					&& playerOnSpellsBoxes[i][0] == 1) {
				System.out.println("player onSpells: " + i);
				if (playerOnSpellsBoxes[i][1] == 1) {
					playerOnSpellsBoxes[i][1] = 0;
				} else {
					clearSelected();
					playerOnSpellsBoxes[i][1] = 1;
				}

			}
		}
		// Buttons
		buttonClickBoxes(x, y);

		// heros -- TODO write hero click boxes
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickBoxes(e.getX() - 3, e.getY() - 26);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void setPlayerDeck(Deck a) {
		playerDeck = a;
	}

	public static void setEnemyDeck(Deck a) {
		System.out.println(a.deck_name);
		enemyDeck = a;
	}

	public static boolean boardHasTaunt() {
		int arrayListSize = playerDeck.onTable.size();
		for (int i = 0; i < arrayListSize; i++) {
			if (playerDeck.onTable.get(i).getRarity() == 5) {
				return true;
			}
		}
		return false;
	}

	public static void playerBoxes() {

		int arrayListSize = playerDeck.inHand.size();
		// System.out.println(arrayListSize);
		for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
			inHandBoxes[i][0] = 1;
		}
		for (int i = arrayListSize; i < 9; i++) {
			inHandBoxes[i][0] = 0;
		}
		arrayListSize = playerDeck.onTable.size();
		if (!boardHasTaunt()) {
			for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
				playerOnTableBoxes[i][0] = 1;
			}
		} else {
			for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
				if (playerDeck.onTable.get(i).getRarity() == 5) {
					playerOnTableBoxes[i][0] = 1;
				} else {
					playerOnTableBoxes[i][0] = 0;
				}
			}
		}

		for (int i = arrayListSize; i < 9; i++) {
			playerOnTableBoxes[i][0] = 0;
		}
		arrayListSize = playerDeck.onSpells.size();
		for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
			playerOnSpellsBoxes[i][0] = 1;
		}
		for (int i = 0; i < arrayListSize; i++) {
			playerOnSpellsBoxes[i][0] = 0;
		}

	}
}
