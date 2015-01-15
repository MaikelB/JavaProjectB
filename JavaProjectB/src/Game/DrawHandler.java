package Game;

import java.awt.Color;
import java.awt.Font;
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
	private BufferedImage buttonImage = null;
	private BufferedImage heroDark = null;
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
		bbg.setFont(new Font("TimesRoman", Font.PLAIN, 11));
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);
		bbg.setColor(Color.BLACK);
		bbg.drawImage(playField, 0, 0, this);
		
		//buttons
		bbg.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		for(int i = 0; i < buttons.length; i++){
			if (buttons[i][2] == 1 ){
				bbg.drawImage(buttonImage, buttons[i][0], buttons[i][1], this);
				if (buttons[i][3] == 1){
					bbg.drawString("Play Card", buttons[i][0]+5, buttons[i][1]+25);
				}
				if (buttons[i][3] == 2){
					bbg.drawString("Attack", buttons[i][0]+5, buttons[i][1]+25);
				}
				if (buttons[i][3] == 3){
					bbg.drawString("End Turn", buttons[i][0]+5, buttons[i][1]+25);
				}
			}
		}
		bbg.setFont(new Font("TimesRoman", Font.PLAIN, 11));
		
		//Mana display
		bbg.drawString("" + Enemy.getMana() + "/40", 1005, 150);
		bbg.drawString("" + Player.getMana() + "/40", 1005, 625);
		
		
		
		
		
		
		
		//cards
		int arraySize;
		
		//enemy deck
		if(main.enemyDeck.deck.size() != 0) {
			bbg.drawImage(backCard, 50, 20, this);
		}
		//enemy graveyard
		if(main.enemyDeck.graveyard.size() != 0) {
			bbg.drawImage(backCard, 1146, 20, this);
		}
		// emeny deck
		arraySize = main.enemyDeck.inHand.size();
		for(int i = 0; i < arraySize; i++) {
				bbg.drawImage(backCard, 215 + (95*i), -94 , this);
			
		}
		//enemy table
		arraySize = main.enemyDeck.onTable.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.enemyDeck.getOnTable(i).getRarity();
			if(main.enemyDeck.getOnTable(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 674, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 220 + (95*i),  200, this);
							
							break;
				case 1:		bbg.drawImage(uncommonCard, 220 + (95*i),  200, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 220 + (95*i),  200, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 220 + (95*i),  200, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 220 + (95*i),  200, this);
							break;
				}
				String s1 = Main.enemyDeck.getOnTable(i).card_name;
				String s2 = "";
				if (s1.length() > 14 && s1 != null){
					int q = (int)((s1.length()/14)+1);
					int w = s1.length()%14;
					for (int a = 0 ; a < q ; a++ ){
						if(a == q-1 ){
							s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
						}
						else{
							s2 = s1.substring(0 + 13*a, 13+13*a);
							s2 = s2 + "-";
						}
						bbg.drawString(s2, 230+95*i , 217+10*a);
					}
				}
				else{
					s2 = s1;
					bbg.drawString(s2, 230+95*i , 217);
				}
				s1 = "" + Main.enemyDeck.getOnTable(i).getAttack();
				bbg.drawString(s1, 250 + (95*i), 295);
				s1 = "" + Main.enemyDeck.getOnTable(i).getDefense();
				bbg.drawString(s1, 290 + (95*i), 295);
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
				case 0:		bbg.drawImage(commonCard, 20 + (95*i),  200, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 20 + (95*i),  200, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 20 + (95*i),  200, this);
							break;
				case 3:		bbg.drawImage(legendaryCard, 20 + (95*i),  200, this);
							break;
				case 4:		bbg.drawImage(godlyCard, 20 + (95*i),  200, this);
							break;
				}
				String s1 = Main.enemyDeck.getOnSpells(i).card_name;
				String s2 = "";
				if (s1.length() > 14 && s1 != null){
					int q = (int)((s1.length()/14)+1);
					int w = s1.length()%14;
					for (int a = 0 ; a < q ; a++ ){
						if(a == q-1 ){
							s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
						}
						else{
							s2 = s1.substring(0 + 13*a, 13+13*a);
							s2 = s2 + "-";
						}
						bbg.drawString(s2, 30+95*i , 217+10*a);
					}
				}
				else{
					s2 = s1;
					bbg.drawString(s2, 30+95*i , 217);
				}
				s1 = "" + Main.enemyDeck.getOnSpells(i).getAttack();
				bbg.drawString(s1, 50 + (95*i), 295);
				s1 = "-";
				bbg.drawString(s1, 90 + (95*i), 295);
				
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
				String s1 = Main.playerDeck.getHandCard(i).card_name;
				String s2 = "";
				if (s1.length() > 14 && s1 != null){
					int q = (int)((s1.length()/14)+1);
					int w = s1.length()%14;
					for (int a = 0 ; a < q ; a++ ){
						if(a == q-1 ){
							s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
						}
						else{
							s2 = s1.substring(0 + 13*a, 13+13*a);
							s2 = s2 + "-";
						}
						bbg.drawString(s2, 225+95*i , 691+10*a);
					}
				}
				else{
					s2 = s1;
					bbg.drawString(s2, 225+95*i , 691);
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
				String s1 = Main.playerDeck.getOnTable(i).card_name;
				String s2 = "";
				if (s1.length() > 14 && s1 != null){
					int q = (int)((s1.length()/14)+1);
					int w = s1.length()%14;
					for (int a = 0 ; a < q ; a++ ){
						if(a == q-1 ){
							s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
						}
						else{
							s2 = s1.substring(0 + 13*a, 13+13*a);
							s2 = s2 + "-";
						}
						bbg.drawString(s2, 230+95*i , 397+10*a);
					}
				}
				else{
					s2 = s1;
					bbg.drawString(s2, 230+95*i , 397);
				}
				s1 = "" + Main.playerDeck.getOnTable(i).getAttack();
				bbg.drawString(s1, 250 + (95*i), 475);
				s1 = "" + Main.playerDeck.getOnTable(i).getDefense();
				bbg.drawString(s1, 290 + (95*i), 475);
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
			String s1 = Main.playerDeck.getOnSpells(i).card_name;
			String s2 = "";
			if (s1.length() > 14 && s1 != null){
				int q = (int)((s1.length()/14)+1);
				int w = s1.length()%14;
				for (int a = 0 ; a < q ; a++ ){
					if(a == q-1 ){
						s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
					}
					else{
						s2 = s1.substring(0 + 13*a, 13+13*a);
						s2 = s2 + "-";
					}
					bbg.drawString(s2, 30+95*i , 397+10*a);
				}
			}
			else{
				s2 = s1;
				bbg.drawString(s2, 30+95*i , 397);
			}
			s1 = "" + Main.playerDeck.getOnSpells(i).getAttack();
			bbg.drawString(s1, 50 + (95*i), 475);
			s1 = "-";
			bbg.drawString(s1, 90 + (95*i), 475);
		}
		
		//sellection draw
		//bbg.setColor(Color.RED);
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
				bbg.setFont(new Font("TimesRoman", Font.PLAIN, 22));
				String s1 = Main.playerDeck.getHandCard(i).card_name;
				String s2 = "";
				if (s1.length() > 14 && s1 != null){
					int q = (int)((s1.length()/14)+1);
					int w = s1.length()%14;
					for (int a = 0 ; a < q ; a++ ){
						if(a == q-1 ){
							s2 = s1.substring(0 + 13*a, 13+(13*(a-1))+w+1);
						}
						else{
							s2 = s1.substring(0 + 13*a, 13+13*a);
							s2 = s2 + "-";
						}
						bbg.drawString(s2, windowWidth/2-90+17 , windowHeight/2-120+34 + 20*a);
					}
				}
				else{
					s2 = s1;
					bbg.drawString(s2, windowWidth/2-90+17 , windowHeight/2-120 + 34);
				}
				s1 = "" + Main.playerDeck.getHandCard(i).getAttack();
				bbg.drawString(s1, 600 , 405);
				s1 = "" + Main.playerDeck.getHandCard(i).getDefense();
				bbg.drawString(s1, 690 , 405);
				s1 = "Mana: " + Main.playerDeck.getHandCard(i).getManaCost();
				bbg.drawString(s1, windowWidth/2-90+17 , 435);
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				bbg.setFont(new Font("TimesRoman", Font.PLAIN, 11));
			}
			
		}
		
		arraySize = main.playerDeck.onTable.size();
		for (int i = 0; i < arraySize; i++){
			if(Main.playerOnTableBoxes[i][1] == 1){
				
				bbg.drawImage(selectCard, 220 + 95*i, 380, this);
			}
			
		}
		arraySize = main.enemyDeck.onTable.size();
		for (int i = 0; i < arraySize; i++){
			if(Main.enemyOnTableBoxes[i][1] == 1){
				
				bbg.drawImage(selectCard, 220 + 95*i, 200, this);
			}
			
		}
		switch(Main.enemyDeck.deck_name){
			case "Guardian Earth":
				bbg.drawImage(heroEarth,582,62,this); 
				break;
			case "Blazing Light":
				bbg.drawImage(heroLight,582,62,this); 
				break;
			case "Death Waltz":
				bbg.drawImage(heroDark,582,62,this); 
				break;
		}
		switch(Main.playerDeck.deck_name){
		case "Guardian Earth":
			bbg.drawImage(heroEarth,582,540,this); 
			break;
		case "Blazing Light":
			bbg.drawImage(heroLight,582,540,this); 
			break;
		case "Death Waltz":
			bbg.drawImage(heroDark,582,540,this); 
			break;
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
			buttonImage = ImageIO.read(getClass().getResourceAsStream("images/Button.png"));
			heroDark = ImageIO.read(getClass().getResourceAsStream("images/Deck3-Hero.png"));
		}catch(IOException e){
			
		}
	}
}
