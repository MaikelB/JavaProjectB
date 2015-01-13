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
	
	/** BufferedImage playField = Hold the background image */
	private BufferedImage playField = null;
	private BufferedImage backCard = null;
	private BufferedImage commonCard = null;
	private BufferedImage uncommonCard = null;
	private BufferedImage rareCard = null;
	private BufferedImage legendaryCard = null;
	private BufferedImage godlyCard = null;

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
	private BufferedImage drawPlayerHand() {
		BufferedImage backBufferHand = new BufferedImage(windowWidth, windowHeight,
				+BufferedImage.TYPE_INT_RGB);
		
		Graphics bbg = backBufferHand.getGraphics();
		int arraySize = main.playerDeck.inHand.size();
		for(int i = 0; i < arraySize; i++) {
			int rarity = main.playerDeck.getCard(i).getRarity();
			if(main.playerDeck.getCard(i).getShowBack()) {
				bbg.drawImage(backCard, 215 + (95*i), 647, this);
			} else {
				switch(rarity) {
				case 0:		bbg.drawImage(commonCard, 215 + (95*i), 647, this);
							break;
				case 1:		bbg.drawImage(uncommonCard, 215 + (95*i), 647, this);
							break;
				case 2: 	bbg.drawImage(rareCard, 215 + (95*i), 647, this);
							break;
				case 3:		bbg.drawImage(godlyCard, 215 + (95*i), 647, this);
							break;
				case 4:		bbg.drawImage(legendaryCard, 215 + (95*i), 647, this);
							break;
			}	
			}
		}
		return backBufferHand;
	}
	/** void draw() = Draws all non-static objects */
	void draw() {
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		bbg.setColor(Color.BLACK);
		bbg.drawImage(playField, 0, 0, this);
		bbg.drawImage(drawPlayerHand(), 0,0, this);
		bbg.setColor(Color.RED);
		//drawRemainingDeck
		if(main.playerDeck.deck.size() != 0) {
			bbg.drawImage(backCard, 1137, 563, this);
		}
				
		g.drawImage(backBuffer, insets.left, insets.top, this);
	}
	
	/** void imageLoader() = Sets all image variables to a working image */
	private void imageLoader(){
		try{
			playField = ImageIO.read(getClass().getResourceAsStream("images/Playfield_layout.png"));
			backCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Back.png"));
			commonCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Common.png"));
			uncommonCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Uncommon.png"));
			rareCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Rare.png"));
			legendaryCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Legendary.png"));
			godlyCard = ImageIO.read(getClass().getResourceAsStream("images/Card_Godly.png"));
		}catch(IOException e){
			
		}
	}
}
