package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
/**
 * Drawhandler handles all the draws on screen. Implements Runnable for an executable thread.
 *
 */
public class DrawHandler extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	/** Boolean isRunning = if true, game will be running */
	private boolean isRunning = true;
	
	/** int fps = holds the value of the frames per seconds */
	private int fps = 60;
	
	/** int windowWidth = holds the value of the width of the frame */
	private int windowWidth = 1280;
	
	/** int windowHeight = holds the value of the height of the frame */ 
	private int windowHeight = 720;
	
	/** Insets insets = Keeps the screen in the right resolution */
	private Insets insets;
	
	/** String gameTitle = Show the title of the game */
	private String gameTitle = "Test Title";
	
	/** BufferedImage backBuffer = Holds all the drawn objects before they are drawn on the screen */
	private BufferedImage backBuffer;
	
	/** Main main = holds the thread of main */
	Main main;
	
	/** BufferedImage playField = Hold the image */
	private BufferedImage playField = null;
	private BufferedImage backCard = null;
	private BufferedImage commonCard = null;
	private BufferedImage uncommonCard = null;
	private BufferedImage rareCard = null;
	private BufferedImage legendaryCard = null;
	private BufferedImage godlyCard = null;
	private BufferedImage selectCard = null;
	private BufferedImage heroEarth = null;
	private BufferedImage heroLight = null;
	/*
	 * int array for buttons to be drawn.
	 * x,y,active,tasks.
	 * tasks:
	 * 0 = selecting hero
	 * 1 = placing on board
	 * 2 = attacking/casting with card or spell card
	 * 3 = end turn
	 */
	public static int[][] buttons = {
			{
				0,0,0,0
			},
			{
				0,0,0,0
			},
			{
				0,0,0,0
			},
			{
				1124,235,1,1
			},
			{
				1136,445,1,3
			}
			
	};

	/** void run() = Initialize, set thread sleep time(1000ms / frames per second), and run the game */
	public void run() {
		initialize();
		while (isRunning) {
			long time = System.currentTimeMillis();

			draw();

			time = (1000 / fps) - (System.currentTimeMillis() - time);
			if (time > 0) {
				try {
					Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
		setVisible(false);
	}
	
	/** void initialize() = sets the game variables (title, insets, size, backbuffer, main, images) */
	void initialize() {
		setTitle(gameTitle);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		insets = getInsets();
		setSize(insets.left + windowWidth + insets.right, insets.top
				+ windowHeight + insets.bottom);
		backBuffer = new BufferedImage(windowWidth, windowHeight,
				+BufferedImage.TYPE_INT_RGB);
		main = new Main(this);
		imageLoader();

	}

	/** void draw() = Draws all non-static objects */
	void draw() {
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		bbg.setColor(Color.BLACK);
		bbg.drawImage(playField, 0, 0, this);
		
		//buttons
		for(int i = 0; i < buttons.length; i++){
			if (buttons[i][2] == 1 ){
				bbg.fillRoundRect(buttons[i][0], buttons[i][1] , 100, 40, 10, 10);
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		//cards
		//enemy deck
		if(main.enemyDeck.deck.size() != 0) {
			bbg.drawImage(backCard, 50, 20, this);
		}
		//enemy graveyard
		if(main.enemyDeck.graveyard.size() != 0) {
			bbg.drawImage(backCard, 1146, 560, this);
		}
		// emeny deck
		int arraySize = main.enemyDeck.inHand.size();
		for(int i = 0; i < arraySize; i++) {
				bbg.drawImage(backCard, 215 + (95*i), -94 , this);
			
		}
		//enemy table
		arraySize = main.enemyDeck.onTable.size();
		System.out.println(arraySize);
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.enemyDeck.getOnTable(i).getRarity();
			if(main.enemyDeck.getOnTable(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 220 + (95*i),  380, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 220 + (95*i),  380, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 220 + (95*i),  380, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 220 + (95*i),  380, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 220 + (95*i),  380, this);
							break;
				}	
			}
			
		}
		//enemy spells
		arraySize = main.enemyDeck.onSpells.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.enemyDeck.getOnSpells(i).getRarity();
			if(main.enemyDeck.getOnSpells(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 20 + (95*i),  380, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 20 + (95*i),  380, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 20 + (95*i),  380, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 20 + (95*i),  380, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 20 + (95*i),  380, this);
							break;
				}	
			}	
		}
		
		
		
		//player deck
		if(main.playerDeck.deck.size() != 0) {
			bbg.drawImage(backCard, 50, 560, this);
		}
		//player graveyard
		if(main.playerDeck.graveyard.size() != 0) {
			bbg.drawImage(backCard, 1146, 560, this);
		}
		//player hand
		arraySize = main.playerDeck.inHand.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.playerDeck.getHandCard(i).getRarity();
			if(main.playerDeck.getHandCard(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 215 + (95*i),  674, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 215 + (95*i),  674, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 215 + (95*i),  674, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 215 + (95*i),  674, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 215 + (95*i),  674, this);
							break;
			}	
			}
			
		}
		//player table
		arraySize = main.playerDeck.onTable.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.playerDeck.getOnTable(i).getRarity();
			if(main.playerDeck.getOnTable(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 220 + (95*i),  380, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 220 + (95*i),  380, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 220 + (95*i),  380, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 220 + (95*i),  380, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 220 + (95*i),  380, this);
							break;
				}	
			}
			
		}
		//player spells
		arraySize = main.playerDeck.onSpells.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.playerDeck.getOnSpells(i).getRarity();
			if(main.playerDeck.getOnSpells(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 20 + (95*i),  380, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 20 + (95*i),  380, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 20 + (95*i),  380, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 20 + (95*i),  380, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 20 + (95*i),  380, this);
							break;
			}	
			}	
		}
		
		//sellection draw
		bbg.setColor(Color.RED);
		arraySize = main.playerDeck.inHand.size();
		for (int i = 0; i < arraySize; i++){
			if(Main.inHandBoxes[i][1] == 1){
				int rarity = main.playerDeck.getHandCard(i).getRarity();
				
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, windowWidth/2-90, windowHeight/2-120, 180,240,this);
							break;
				case 1:		bbg.drawImage(uncommonCard, windowWidth/2-90, windowHeight/2-120, 180,240,this);
							break;
				case 2: 	bbg.drawImage(rareCard, windowWidth/2-90, windowHeight/2-120, 180,240,this);
							break;
				case 3:		bbg.drawImage(legendaryCard, windowWidth/2-90, windowHeight/2-120, 180,240,this);
							break;
				case 4:		bbg.drawImage(godlyCard, windowWidth/2-90, windowHeight/2-120, 180,240,this);
							break;
				}
			}
			
		}
		arraySize = main.playerDeck.onTable.size();
		for (int i = 0; i < arraySize; i++){
			if(Main.playerOnTableBoxes[i][1] == 1){
				
				bbg.drawImage(selectCard, 220 + 95*i, 380, this);
			}
			
		}
		
		
		
				
		g.drawImage(backBuffer, insets.left, insets.top, this);
	}
	
	/** void imageLoader() = Sets all image variables to a working image */
	private void imageLoader(){
		try{
			playField = ImageIO.read(getClass().getResourceAsStream("images/Playfield.png"));
			backCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Back.png"));
			commonCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Common.png"));
			uncommonCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Uncommon.png"));
			rareCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Rare.png"));
			legendaryCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Legendary.png"));
			godlyCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Godly.png"));
			selectCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Border.png"));
			heroEarth = ImageIO.read(getClass().getResourceAsStream("images/Deck1-Hero.png"));
			heroLight = ImageIO.read(getClass().getResourceAsStream("images/Deck2-Hero.png"));
		}catch(IOException e){
			
		}
	}
}
