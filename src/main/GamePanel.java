package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.tileManager;

public class GamePanel extends JPanel implements Runnable{

	final int originalTileSize = 16;
	public final int scale = 3;
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	public int maxWorldCol;
	public int maxWorldRow;

	int FPS = 60;

	tileManager tileM = new tileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player;

	public GamePanel() {
		this.setPreferredSize (new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
    
	public void setupGame() {
		tileM.loadMap("/maps/world_village.txt");
		player = new Player(this, keyH);
	}

	public void startGameThread() {
		setupGame();
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;

		while(gameThread != null) { 
			update();
			repaint();
			try {
				double remaningTime = nextDrawTime - System.nanoTime();
				remaningTime = remaningTime/1000000;
				if(remaningTime < 0) {
					remaningTime = 0;
				}
				Thread.sleep((long) remaningTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if (player != null) {
			player.update();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if (tileM != null) {
			tileM.draw(g2);
		}
		
		if (player != null) {
			player.draw(g2);
		}
		
		g2.dispose();
	}
}