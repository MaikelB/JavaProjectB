package Game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;




public class Main implements MouseListener{
	private int fps = 60;
	private boolean isRunning = true;
	private static Deck playerDeck = null;
	private static Deck enemyDeck = null;
	/*
	 * arrays for the mouse hover and click-boxes
	 */
	static private int[][] inHandBoxes = new int[9][2];
	static private int[][] playerOnTableBoxes = new int[9][2];
	static private int[][] playerOnSpellsBoxes = new int[2][2];
	static private int[][] enemyOnTableBoxes = new int[9][2];
	static private int[][] gameButtonBoxes = new int[10][2];
	static private int[][] heroBoxes = new int[2][2];
		
	
	public static void main(String[] args) {
		Main game = new Main();
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
		int arrayListSize = Deck.inHandPlayer.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			inHandBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 9 - arrayListSize && arrayListSize != 0; i++){
			inHandBoxes[i][0] = 0;
		}
		arrayListSize = Deck.onTablePlayer.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			playerOnTableBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 9 - arrayListSize && arrayListSize != 0; i++){
			playerOnTableBoxes[i][0] = 0;
		}
		arrayListSize = Deck.onSpellsPlayer.size();
		for (int i = 0 ; i < arrayListSize && arrayListSize != 0; i++){
			playerOnSpellsBoxes[i][0] = 1;
		}
		for (int i = 0 ; i < 2 - arrayListSize && arrayListSize != 0; i++){
			playerOnSpellsBoxes[i][0] = 0;
		}
	}
	private void buttonClickBoxes(int x, int y){
		
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
