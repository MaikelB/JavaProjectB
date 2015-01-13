package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DrawHandler extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private boolean isRunning = true;
	private int fps = 60;
	private int windowWidth = 1280;
	private int windowHeight = 720;
	private Insets insets;
	private String gameTitle = "Test Title";
	private BufferedImage backBuffer;
	Main main;
	
	private BufferedImage playField = null;

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

	void draw() {
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		bbg.setColor(Color.BLACK);
		bbg.drawImage(playField, 0, 0, this);

		bbg.setColor(Color.RED);

		g.drawImage(backBuffer, insets.left, insets.top, this);
	}
	private void imageLoader(){
		try{
			playField = ImageIO.read(getClass().getResourceAsStream("images/Playfield_layout.png"));
			
		}catch(IOException e){
			
		}
	}
}
