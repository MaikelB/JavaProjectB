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
	public static int gameState = 0;
	/*
	 * arrays for the mouse hover and click-boxes
	 */
	static private int[][] inHandBoxes = new int[9][2];
	static private int[][] playerOnTableBoxes = new int[9][2];
	static private int[][] playerOnSpellsBoxes = new int[2][2];
	static private int[][] enemyOnTableBoxes = new int[9][2];
	static private int[][] heroBoxes = new int[2][2];
		
	
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
		
		
		int arrayListSize = playerDeck.inHand.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			inHandBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 9 - arrayListSize && arrayListSize != 0; i++){
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
	private void buttonClickBoxes(int x, int y){
		for( int i =0 ; i < DrawHandler.buttons.length; i++){
			if(x > DrawHandler.buttons[i][0] && x< DrawHandler.buttons[i][0]+buttonSize[0] && y > DrawHandler.buttons[i][1] && y < DrawHandler.buttons[i][1]+ buttonSize[1] && DrawHandler.buttons[i][2] == 1 ){				
				switch(DrawHandler.buttons[i][3]){
				case 0: /*chooseHero();*/ System.out.println("buttoncheck hero");
				break;
				case 1: /*playCard();*/	System.out.println("buttoncheck play");
				break;
				case 2: /*attackCard();*/ System.out.println("buttoncheck attack");
				break;
				
				}
			}
		}
	}
	private void clickBoxes(int x, int y){
		System.out.println(x + " - " + y);
		//inHand
		for(int i =0 ; i < 9 ; i++){
			if (x > 215 + 95*i && x < 305 + 95*i && y > 674 && inHandBoxes[i][0] == 1){
				System.out.println("inhandbox: " + i);
			}
		}
		//onTable player
		for(int i = 0; i < 9; i++){
			if (x > 220 + 95*i && x < 310 + 95*i && y > 380 && y < 520 && playerOnTableBoxes[i][0] == 1){
				System.out.println("player onTablebox: " + i);
			}
		}
		//onTable enemy
		for(int i = 0; i < 9; i++){
			if (x > 220 + 95*i && x < 310 + 95*i && y > 200 && y < 340 && enemyOnTableBoxes[i][0] == 1){
				System.out.println("enemy onTablebox: " + i);
			}
		}
		//onSpells player
		for(int i = 0; i < 2; i++){
			if (x > 20 + 95*i && x < 110 + 95*i && y > 380 && y < 520 && playerOnSpellsBoxes[i][0] == 1){
				System.out.println("player onSpells: " + i);
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

}
