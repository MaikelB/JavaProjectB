package Game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;




public class Main implements MouseListener{
	private int fps = 60;
	private boolean isRunning = true;
	public static Deck playerDeck = null;
	public static Deck enemyDeck = null;
	private int[] buttonSize = {100,40}; 
	public static int gameState = 1;
	/*
	 * arrays for the mouse hover and click-boxes
	 */
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.run();

	}
	public Main(){
		
	}
	public Main(Component d){
		d.addMouseListener(this);
	}
	private void initialize(){
		//warning!!! This is is for testing. needs to be edited for release
		for (int i = 0; i < 9 ; i++){
			inHandBoxes[i][0] = 1;
			inHandBoxes[i][1] = 0;
			
		}
		
		playerDeck.drawCard();
		playerDeck.drawCard();
		playerDeck.drawCard();
	
	}
	
	public void run() {
		(new Thread(new DrawHandler())).start();
		initialize();
		while (isRunning) {
			long time = System.currentTimeMillis();
			//Main menu
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
		if( gameS == 0){
			
		}
		if( gameS == 1){
			playerBoxes();
			
		}
		if (gameS == 2){
			
		}
	}
	private void playCard( ) {
		for(int i = 0; i < inHandBoxes.length; i++) {
			if(inHandBoxes[i][1]==1) {
					playerDeck.cardPlay(i);
					clearSelected();
			}
		}
	}
	private void buttonClickBoxes(int x, int y){
		for( int i =0 ; i < DrawHandler.buttons.length; i++){
			if(x > DrawHandler.buttons[i][0] && x< DrawHandler.buttons[i][0]+buttonSize[0] && y > DrawHandler.buttons[i][1] && y < DrawHandler.buttons[i][1]+ buttonSize[1] && DrawHandler.buttons[i][2] == 1 ){				
				switch(DrawHandler.buttons[i][3]){
				case 0: /*chooseHero();*/ System.out.println("buttoncheck hero");
				break;
				case 1: playCard();	System.out.println("buttoncheck play");
										
				break;
				case 2: /*attackCard();*/ System.out.println("buttoncheck attack");
				break;
				
				}
			}
		}
	}
	private void clearSelected() {
		for(int i = 0; i < 9; i++) {
			inHandBoxes[i][1] 			= 0;
			playerOnTableBoxes[i][1] 	= 0;
			enemyOnTableBoxes[i][1] 	= 0;
		}
		for(int i = 0; i < 2; i++) {
			playerOnSpellsBoxes[i][1] 	= 0;
		}
		
	}
	private void clickBoxes(int x, int y){
		System.out.println(x + " - " + y);
		//inHand
		
		for(int i =0 ; i < 9 ; i++){
			if (x > 215 + 95*i && x < 305 + 95*i && y > 674 && inHandBoxes[i][0] == 1){
				System.out.println("inhandbox: " + i);
				if(inHandBoxes[i][1]==1) {
					inHandBoxes[i][1] = 0;
				} else {
					clearSelected();
					inHandBoxes[i][1] = 1;
				}
				
							
			}
		}
		//onTable player
		for(int i = 0; i < 9; i++){
			if (x > 220 + 95*i && x < 310 + 95*i && y > 380 && y < 520 && playerOnTableBoxes[i][0] == 1){
				System.out.println("player onTablebox: " + i);
				
				if(playerOnTableBoxes[i][1]==1) {
					playerOnTableBoxes[i][1] = 0;
				} else {
					clearSelected();
					playerOnTableBoxes[i][1] = 1;	
				}
				
			}
		}
		//onTable enemy
		for(int i = 0; i < 9; i++){
			if (x > 220 + 95*i && x < 310 + 95*i && y > 200 && y < 340 && enemyOnTableBoxes[i][0] == 1){
				System.out.println("enemy onTablebox: " + i);
				if(enemyOnTableBoxes[i][1]==1) {
					enemyOnTableBoxes[i][1] = 0;
				} else {
					enemyOnTableBoxes[i][1] = 1;	
				}
			}
		}
		//onSpells player
		for(int i = 0; i < 2; i++){
			if (x > 20 + 95*i && x < 110 + 95*i && y > 380 && y < 520 && playerOnSpellsBoxes[i][0] == 1){
				System.out.println("player onSpells: " + i);
				if(playerOnSpellsBoxes[i][1]==1) {
					playerOnSpellsBoxes[i][1] = 0;
				} else {
					clearSelected();
					playerOnSpellsBoxes[i][1] = 1;	
				}
				
			}
		}
		//Buttons
		buttonClickBoxes(x, y);
		
		//heros -- TODO write hero click boxes
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
	public static void setPlayerDeck(Deck a){
		playerDeck = a;
	}
	public static void setEnemyDeck(Deck a){
		enemyDeck = a;
	}

	public static void playerBoxes() {
		int arrayListSize = playerDeck.inHand.size();
		//System.out.println(arrayListSize);
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			inHandBoxes[i][0] = 1;
		}
		for (int i = arrayListSize ; i < 9; i++){
			inHandBoxes[i][0] = 0;
		}
		arrayListSize = playerDeck.onTable.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			playerOnTableBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 9 - arrayListSize && arrayListSize != 0; i++){
			playerOnTableBoxes[i][0] = 0;
		}
		arrayListSize = playerDeck.onSpells.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			playerOnSpellsBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 2 - arrayListSize && arrayListSize != 0; i++){
			playerOnSpellsBoxes[i][0] = 0;
		}
		
	}
}
