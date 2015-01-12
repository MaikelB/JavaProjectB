package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

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

	}

	void draw() {
		Graphics g = getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, windowWidth, windowHeight);

		bbg.setColor(Color.BLACK);
		bbg.drawRect(windowWidth/2, windowHeight/2, 5, 5);

		bbg.setColor(Color.RED);

		g.drawImage(backBuffer, insets.left, insets.top, this);
	}
}
