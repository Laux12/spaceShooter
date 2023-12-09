package com.zetcode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zetcode.sprite.Alien;
import com.zetcode.sprite.Player;
import com.zetcode.sprite.Shot;

public class Board extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<Alien> aliens;
	private Player player;
	private Shot shot;
	Menu mainMenu = new Menu(this);
	Music fireSound = new Music();
	Music menuBG = new Music();
	Music gameBG = new Music();
	private int direction = -1;
	File shootEffect = new File("playerFireSound.wav");
	File menuMusic = new File("menuBGMusic.wav");
	File inGameBGMusic = new File("inGameBGMusic.wav");
	private boolean inGame = true;
	private String explImg = "src/images/explosion.png";
	private String message = "Game Over";
	HighScore highScore;
	HighScoreControl hScoreControl;
	private Timer timer;
	public boolean isGameOver = false;
	public static final int playState = 1;
	public static final int menuState = 2;
	public static int gameState = menuState;
	Image inGameBG = new ImageIcon("spacebackground.jpg").getImage();
	int score = 0;

	public Board() {

		initBoard();
		gameInit();
		this.addMouseListener(mainMenu);
		this.addMouseMotionListener(mainMenu);
		fireSound.setFile(shootEffect);
		menuBG.setFile(menuMusic);
		gameBG.setFile(inGameBGMusic);
		menuBG.play();
		menuBG.loop();
		hScoreControl = new HighScoreControl();
		try {
			highScore = hScoreControl.loadScores();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);

		setBackground(Color.black);

		timer = new Timer(Commons.DELAY, new GameCycle());
		timer.start();

		gameInit();
	}

	private void gameInit() {

		aliens = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 6; j++) {

				var alien = new Alien(Commons.ALIEN_INIT_X + -18 * j, Commons.ALIEN_INIT_Y + -18 * i);
				aliens.add(alien);
			}
		}

		player = new Player();
		shot = new Shot();
	}

	private void drawAliens(Graphics g) {

		for (Alien alien : aliens) {

			if (alien.isVisible()) {

				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}

			if (alien.isDying()) {

				alien.die();
			}
		}
	}

	private void drawPlayer(Graphics g) {

		if (player.isVisible()) {

			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		if (player.isDying()) {

			player.die();
			inGame = false;
		}
	}

	private void drawShot(Graphics g) {

		if (shot.isVisible()) {

			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
		}
	}

	private void drawBombing(Graphics g) {

		for (Alien a : aliens) {

			Alien.Bomb b = a.getBomb();

			if (!b.isDestroyed()) {

				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if (gameState == playState) {
			doDrawing(g);

		}

		else if (gameState == menuState) {
			mainMenu.draw(g2d);
		}
	}

	private void doDrawing(Graphics g) {

		g.drawImage(inGameBG, 0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT, null);
		g.setColor(Color.green);
		if (inGame) {

			g.drawLine(0, Commons.GROUND, Commons.BOARD_WIDTH, Commons.GROUND);
			g.setFont(new Font("Helvetica", Font.BOLD, 15));
			g.setColor(Color.white);
			g.drawString("Score: " + String.valueOf(score), 10, 505);
			g.drawString("High Score: " + String.valueOf(highScore.getScore()), 420, 505);

			drawAliens(g);
			drawPlayer(g);
			drawShot(g);
			drawBombing(g);

		} else {

			gameOver(g);
			isGameOver = true;
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void gameOver(Graphics g) {

		g.setColor(Color.black);
		g.fillRect(0, 0, Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

		g.setColor(new Color(0, 32, 48));
		g.fillRect(150, 150, 250, 60);
		g.setColor(Color.white);
		g.drawRect(150, 150, 250, 60);

		Font small = new Font("Helvetica", Font.BOLD, 20);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, 220, 185);
	}

	private void update() {

		// player
		player.act();

		// shot
		if (shot.isVisible()) {

			int shotX = shot.getX();
			int shotY = shot.getY();

			for (Alien alien : aliens) {

				int alienX = alien.getX();
				int alienY = alien.getY();
				if (alien.isVisible() && shot.isVisible()) {
					if (shotX >= (alienX) && shotX <= (alienX + Commons.ALIEN_WIDTH) && shotY >= (alienY)
							&& shotY <= (alienY + Commons.ALIEN_HEIGHT)) {

						var ii = new ImageIcon(explImg);
						alien.setImage(ii.getImage());
						alien.setDying(true);
						fireSound.stop();
						score++;
						shot.die();
					}
				}
			}

			int y = shot.getY();
			y -= 10;

			if (y < 0) {
				shot.die();
				fireSound.stop();
			} else {
				shot.setY(y);
			}
		}

		// aliens

		for (Alien alien : aliens) {

			int x = alien.getX();

			if (x >= Commons.BOARD_WIDTH - Commons.BORDER_RIGHT && direction != -1) {

				direction = -1;

				Iterator<Alien> i1 = aliens.iterator();

				while (i1.hasNext()) {

					Alien a2 = i1.next();
					a2.setY(a2.getY() + Commons.GO_DOWN);
				}
			}

			if (x <= Commons.BORDER_LEFT && direction != 1) {

				direction = 1;

				Iterator<Alien> i2 = aliens.iterator();

				while (i2.hasNext()) {

					Alien a = i2.next();
					a.setY(a.getY() + Commons.GO_DOWN);
				}
			}
		}

		Iterator<Alien> it = aliens.iterator();

		while (it.hasNext()) {

			Alien alien = it.next();

			if (alien.isVisible()) {

				int y = alien.getY();

				if (y > Commons.GROUND - Commons.ALIEN_HEIGHT) {
					inGame = false;
					message = "Invasion!";
				}

				alien.act(direction);
			}
		}

		// bombs
		var generator = new Random();

		for (Alien alien : aliens) {

			int shot = generator.nextInt(15);
			Alien.Bomb bomb = alien.getBomb();

			if (shot == Commons.CHANCE && alien.isVisible() && bomb.isDestroyed() && alien.getY() > 0) {

				bomb.setDestroyed(false);
				bomb.setX(alien.getX());
				bomb.setY(alien.getY());
				alien.music.play();
			}

			int bombX = bomb.getX();
			int bombY = bomb.getY();
			int playerX = player.getX();
			int playerY = player.getY();

			if (player.isVisible() && !bomb.isDestroyed()) {

				if (bombX >= (playerX) && bombX <= (playerX + Commons.PLAYER_WIDTH) && bombY >= (playerY)
						&& bombY <= (playerY + Commons.PLAYER_HEIGHT)) {

					var ii = new ImageIcon(explImg);
					player.setImage(ii.getImage());
					player.setDying(true);
					bomb.setDestroyed(true);
					if (score > highScore.getScore()) {
						highScore.setScore(score);
						try {
							hScoreControl.saveScores(highScore);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					gameBG.stop();
					message = "Game Over!";
					score = 0;
				}
			}

			if (!bomb.isDestroyed()) {

				bomb.setY(bomb.getY() + 2);

				if (bomb.getY() >= Commons.GROUND - Commons.BOMB_HEIGHT) {

					bomb.setDestroyed(true);
				}
			}
		}
	}

	private void doGameCycle() {
		if (gameState == playState && !isGameOver) {
			update();
		}
		repaint();
	}

	private class GameCycle implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			doGameCycle();

		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {

			player.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);

			int x = player.getX();
			int y = player.getY();

			int key = e.getKeyCode();

			if (key == KeyEvent.VK_SPACE) {

				if (inGame) {

					if (!shot.isVisible()) {
						fireSound.play();
						shot = new Shot(x, y);
					}
				}

			}
			if (isGameOver) {
				gameState = menuState;
				gameInit();

				menuBG.play();
				menuBG.loop();
				inGame = true;
				isGameOver = false;
			}

		}
	}
}
