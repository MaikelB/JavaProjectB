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
			fileHandler.loadDeck(Enemy.getHero(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		int arraySize = playerDeck.inHand.size();
		playerDeck.notPlayable.clear();
		for (int i = 0; i < arraySize; i++) {
			int mana = Player.getMana()
					- playerDeck.getHandCard(i).getManaCost();
			if (playerDeck.onSpells.size() >= 2
					&& playerDeck.inHand.get(i).getSpecial() == 4) {
				playerDeck.notPlayable.add(playerDeck.inHand.get(i));
			}
			if (mana < 0) {
				playerDeck.notPlayable.add(playerDeck.inHand.get(i));
			}
			if (playerDeck.inHand.get(i).getSpecial() != 4
					&& playerDeck.onTable.size() >= 9) {
				playerDeck.notPlayable.add(playerDeck.inHand.get(i));
			}
		}

		int arrayEnemySize = enemyDeck.inHand.size();
		enemyDeck.notPlayable.clear();
		for (int i = 0; i < arrayEnemySize; i++) {
			int mana = Enemy.getMana() - enemyDeck.getHandCard(i).getManaCost();
			if (enemyDeck.onSpells.size() >= 2
					&& enemyDeck.inHand.get(i).getSpecial() == 4) {
				enemyDeck.notPlayable.add(enemyDeck.inHand.get(i));
			} /*
			 * if (mana < 0) {
			 * enemyDeck.notPlayable.add(enemyDeck.inHand.get(i)); }
			 */
			if (enemyDeck.inHand.get(i).getSpecial() != 4
					&& enemyDeck.onTable.size() >= 9) {
				enemyDeck.notPlayable.add(enemyDeck.inHand.get(i));
			}
		}
		if (gameS == 0) {

		}
		if (gameS == 1) {
			myTurn = true;
			AI.enemyTurn = false;

			playerBoxes();

		}
		if (gameS == 2) {
			AI.enemyTurn = true;
			myTurn = false;
			enemyDeck.notPlayable.clear();

		}
	}

	private static void pullCard() {
		if (gameState == 1) {
			playerDeck.drawCard();
		}
		if (gameState == 2) {
			enemyDeck.drawCard();
		}
	}

	private void playCard() {
		for (int i = 0; i < playerDeck.inHand.size(); i++) {
			if (inHandBoxes[i][1] == 1
					&& !playerDeck.notPlayable.contains(playerDeck.inHand
							.get(i))) {
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
				int cost = playerDeck.getHandCard(i).getManaCost();
				playerDeck.cardPlay(i);
				Player.setMana(Player.getMana() - cost);
				clearSelected();
			}

		}
	}

	public static void enemyAttack() {

	}

	public static void enemyEnd() {
		endEnemyTurn();
	}

	public static void selectCard(int i) {
		enemyInHand[i][1] = 1;
	}

	public static void playEnemyCard(int i) {
		if (enemyInHand[i][1] == 1
				&& !enemyDeck.notPlayable.contains(enemyDeck.inHand.get(i))) {

			int cost = enemyDeck.getHandCard(i).getManaCost();
			int mana = Enemy.getMana() - cost;
			System.out.println("Future mana " + mana);
			if (mana >= 0) {
				if (enemyDeck.getHandCard(i).getSpecial() == 2) {
					int tableSize = enemyDeck.onTable.size();
					for (int a = 0; a < tableSize; a++) {
						enemyDeck.onTable.get(a).buffDefense(1);
					}
				} else if (enemyDeck.getHandCard(i).getSpecial() == 3) {
					int tableSize = enemyDeck.onTable.size();
					for (int b = 0; b < tableSize; b++) {
						enemyDeck.onTable.get(b).buffAttack(1);
					}
				} else if (enemyDeck.getHandCard(i).getSpecial() == 4) {
					int tableSize = enemyDeck.onTable.size();
					int extraAttack = 0;
					for (int d = 0; d < tableSize; d++) {
						if (enemyDeck.onTable.get(d).getSpecial() == 1) {
							extraAttack += 1;
						}
					}
					enemyDeck.getHandCard(i).buffAttack(extraAttack);

				}

				enemyDeck.cardPlay(i);
				Enemy.setMana(Enemy.getMana() - cost);

				System.out.println(Enemy.getMana());

			}
		}

	}
	
	private void attackCard() {
		int playerMinionAttack = 0;
		int playerMinionHealth = 0;
		int playerMinionIndex = 0;
		for(int a = 0; a < playerOnTableBoxes.length; a++) {
			if(playerOnTableBoxes[a][1] == 1) {
				playerMinionAttack = playerDeck.getOnTable(a).getAttack();
				playerMinionHealth = playerDeck.getOnTable(a).getDefense();	
				playerMinionIndex = a;
			}
		}
		int enemyMinionHP = 0;
		int enemyMinionAtt = 0;
		for(int i = 0; i < enemyOnTableBoxes.length; i++) {
			if(enemyOnTableBoxes[i][1] == 1) {
				enemyMinionHP = enemyDeck.getOnTable(i).getDefense();
				enemyMinionAtt = enemyDeck.getOnTable(i).getAttack();
				
				enemyDeck.getOnTable(i).debuffDefense(playerMinionAttack);
				playerDeck.getOnTable(playerMinionIndex).debuffDefense(enemyMinionAtt);
				System.out.println(enemyDeck.getOnTable(i).getAttack());
			}
		}
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
				case 2:  attackCard(); 
					System.out.println("buttoncheck attack");
					break;
				case 3:
					endTurn();
					System.out.println("buttoncheck end");
					break;

				}
			}
		}
	}

	public static void endEnemyTurn() {
		gameState = 1;
		Player.setMana(Player.getMana() + 10);
		pullCard();
	}

	private void endTurn() {
		gameState = 2;
		Enemy.setMana(Enemy.getMana() + 10);
		pullCard();
	}

	static void clearSelected() {
		for (int i = 0; i < 9; i++) {
			inHandBoxes[i][1] = 0;
			playerOnTableBoxes[i][1] = 0;
			enemyOnTableBoxes[i][1] = 0;
			enemyInHand[i][1] = 0;
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
					DrawHandler.buttons[3][2] = 0;
				} else {
					clearSelected();
					inHandBoxes[i][1] = 1;
					DrawHandler.buttons[3][2] = 1;
					DrawHandler.buttons[3][3] = 1;
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
					DrawHandler.buttons[3][2] = 0;
				} else {
					clearSelected();
					playerOnTableBoxes[i][1] = 1;
					DrawHandler.buttons[3][3] = 2;
					DrawHandler.buttons[3][2] = 1;
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
