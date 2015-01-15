package Game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Main implements MouseListener {
	
	/** int fps = frames per second */
	private int fps = 60;
	
	/** boolean isRunning = Holds true/false if the threat is running */
	private boolean isRunning = true;
	
	/** boolean myTurn = true if playerturn false if enemyturn */
	public static boolean myTurn = false;
	
	/** Deck playerDeck = deck of the Player */
	public static Deck playerDeck = null;
	
	/** Deck playerDeck = deck of the Enemy */
	public static Deck enemyDeck = null;
	
	/** int[] buttonSize = holds the size of the buttons [0] width [1] height */
	private int[] buttonSize = { 100, 40 };
	
	/** int gameState = holds the gameState (0 = menu, 1 = playerTurn, 2 = enemyTurn */
	public static int gameState = 1;
	
	/** int[][] enemyInHand = holds values for active states and selected states of the enemyinHand drawboxes
	 * int[][] inHandBoxes = holds values for active states and selected states of the playerinHand drawboxes
	 * int[][] playerOnTableBoxes = holds values for active states and selected states of the playerOnTable drawboxes
	 * int[][] playerOnSpellsBoxes = holds values for active states and selected states of the playerOnSpells drawboxes
	 * int[][] enemyOnTableBoxes = holds values for active states and selected states of the enemyOnTable drawboxes
	 * int[][] heroBoxes = holds values for active states and selected states of the heroBoxes drawboxes
	 * [i][0] = active/inactive
	 * [i][1] = selected/deselected
	 */
	static public int[][] enemyInHand = new int[9][2];
	static public int[][] inHandBoxes = new int[9][2];
	static public int[][] playerOnTableBoxes = new int[9][2];
	static public int[][] playerOnSpellsBoxes = new int[2][2];
	static public int[][] enemyOnTableBoxes = new int[9][2];
	static public int[][] heroBoxes = new int[2][2];
	
	/** void main = Initializes threads and loads decks */
	public static void main(String[] args) {
		Main game = new Main();
		FileHandler fileHandler = new FileHandler();
		try {
			fileHandler.loadDeck(Player.getHero(), true);
			fileHandler.loadDeck(Enemy.getHero(), false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.run();

	}
	
	public Main() {

	}
	
	/** main(Component for mouselistener) = adds a mouselistener to the playfield */
	public Main(Component d) {
		d.addMouseListener(this);
	}
	
	/** void initialize() = draws player cards and initializes the game */
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
	
	/** void run() = starts all the threads, updates gamestate and makes the thread run on a 60 fps */
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
	
	/** update(gameState) = updates the gameState and checks what cards both player and enemy are able to play */
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
		checkDie();
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
	
	/** void checkDie() = checks if a card has less than 1 hp and transfers the card to the graveyard */
	void checkDie(){
		for( int i = 0 ; i < playerDeck.onTable.size(); i++){
			if( playerDeck.onTable.get(i).card_stats[1] < 1){
				playerDeck.cardDie(i);
			}
		}
		for( int i = 0 ; i < enemyDeck.onTable.size(); i++){
			if( enemyDeck.onTable.get(i).card_stats[1] < 1){
				enemyDeck.cardDie(i);
			}
		}
	}
	
	/** void pullCard() = draws a card depending on the gameState */
	private static void pullCard() {
		if (gameState == 1) {
			playerDeck.drawCard();
		}
		if (gameState == 2) {
			enemyDeck.drawCard();
		}
	}
	
	/** void playCard() = checks if the player can play a card and processes the right mechanics of the card */
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
	
	/** void enemyEnd() = ends the turn of the nemy */
	public static void enemyEnd() {
		endEnemyTurn();
	}

	/** void selectCard( index of inhand card ) = simulates a mouse click for the AI */
	public static void selectCard(int i) {
		enemyInHand[i][1] = 1;
	}
	
	/** void playEnemyCard(index of inhand card ) = checks if the ememy can play a card and processes the right mechanics of the card */ 
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
	/** void attackCard() = lets the player attack a card */
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
		clearSelected();
		DrawHandler.buttons[3][2] = 0;
	}
	
	/** void buttonClickBoxes = checks what button is clicked and executes a function according to the button case */
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
	
	/** void endEnemyTurn() = simulates end turn mouseclick for the enemy */
	public static void endEnemyTurn() {
		gameState = 1;
		Player.setMana(Player.getMana() + 10);
		pullCard();
	}
	
	/** void endTurn() = ends the turn of the player */
	private void endTurn() {
		gameState = 2;
		Enemy.setMana(Enemy.getMana() + 10);
		pullCard();
	}
	
	/** void clearSelected() = clears all the selected cards */
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
	
	/** void clickBoxes( x axle, y axle ) = checks on what button you clicked and changes selected state accordingly */
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
					clearSelected();
				} else {
					clearSelected();
					playerOnTableBoxes[i][1] = 1;
					DrawHandler.buttons[3][3] = 2;
					//DrawHandler.buttons[3][2] = 1;
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
					for (int a = 0; a < 9; a++) {
						enemyOnTableBoxes[a][1] = 0;
					}
					
				} else {
					for (int a = 0; a < 9; a++) {
						enemyOnTableBoxes[a][1] = 0;
					}
					enemyOnTableBoxes[i][1] = 1;
					DrawHandler.buttons[3][2] = 1;
				}
			}
		}
		
		// onSpells player
		for (int i = 0; i < 2; i++) {
			if (x > 20 + 95 * i && x < 110 + 95 * i && y > 380 && y < 520
					&& playerOnSpellsBoxes[i][0] == 1) {
				System.out.println("player onSpells: " + i);
				if (playerOnSpellsBoxes[i][1] == 1) {
					clearSelected();
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
	
	/** void mouseClicked(MouseEvent e) = happens when the mouse is clicked */
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
	
	/** void setPlayerDeck(Deck of the player) = sets the deck of the player so the main can use it */
	public static void setPlayerDeck(Deck a) {
		playerDeck = a;
	}
	
	/** void setEnemyDeck(Deck of the enemy) = sets the deck of the enemy so the main can use it */
	public static void setEnemyDeck(Deck a) {
		System.out.println(a.deck_name);
		enemyDeck = a;
	}
	
	/** boolean boardHasTaunt() = checks if the board of the player contains a taunt */
	public static boolean boardHasTaunt() {
		int arrayListSize = playerDeck.onTable.size();
		for (int i = 0; i < arrayListSize; i++) {
			if (playerDeck.onTable.get(i).getRarity() == 5) {
				return true;
			}
		}
		return false;
	}
	
	/** boolean boardHasTauntEnemy() = checks if the board of the enemy contains a taunt */
	public static boolean boardHasTauntEnemy() {
		int arrayListSize = enemyDeck.onTable.size();
		for (int i = 0; i < arrayListSize; i++) {
			if (enemyDeck.onTable.get(i).getRarity() == 5) {
				return true;
			}
		}
		return false;
	}
	
	/** void playerBoxes() = changes the active state of buttons according to mechanics and cards that lay on top of it */
	public static void playerBoxes() {
		
		if(DrawHandler.buttons[3][3] == 2){
			int arrayListSize = enemyDeck.onTable.size();
			if (!boardHasTauntEnemy() ) {
				for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
					enemyOnTableBoxes[i][0] = 1;
				}
			} else {
				for (int i = 0; i < arrayListSize && arrayListSize != 0; i++) {
					if (enemyDeck.onTable.get(i).getSpecial() == 5) {
						enemyOnTableBoxes[i][0] = 1;
					} else {
						enemyOnTableBoxes[i][0] = 0;
					}
				}
			}
			
		}
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
				if (playerDeck.onTable.get(i).getSpecial() == 5) {
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
