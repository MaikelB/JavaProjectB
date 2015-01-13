package Game;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Main implements MouseListener{
	private int fps = 10;
	private boolean isRunning = true;
	
	/*
	 * arrays for the mouse hover and click-boxes
	 */
	private int[][] inHandBoxes = new int[9][2];
	private int[][] playerOnTableBoxes = new int[9][2];
	private int[][] playerOnSpellsBoxes = new int[2][2];
	private int[][] enemyOnTableBoxes = new int[9][2];
	private int[][] gameButtonBoxes = new int[10][2];
	private int[][] heroBoxes = new int[2][2];
	
	
	
	
	public static void main(String[] args) {
		Main game = new Main();
		game.run();

	}
	public Main(){
		
	}
	public Main(Component d){
		d.addMouseListener(this);
	}
	public void run() {
		(new Thread(new DrawHandler())).start();
		
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

	}
	private void clickBoxes(int x, int y){
		System.out.println(x + " - " + y);
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

}
