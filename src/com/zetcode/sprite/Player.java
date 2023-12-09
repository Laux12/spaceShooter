package com.zetcode.sprite;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import com.zetcode.Commons;

public class Player extends Sprite {

	private int width;

	public Player() {

		initPlayer();
	}

	private void initPlayer() {

		var playerImg = "src/images/player.png";
		var ii = new ImageIcon(playerImg);

		width = ii.getImage().getWidth(null);
		setImage(ii.getImage());
		int START_X = 270;
		setX(START_X);

		int START_Y = 480;
		setY(START_Y);
	}

	public void act() {

		x += dx;

		if (x <= 2) {

			x = 2;
		}

		if (x >= Commons.BOARD_WIDTH - 2 * width) {

			x = Commons.BOARD_WIDTH - 2 * width;
		}
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {

			dx = -4;
		}

		if (key == KeyEvent.VK_RIGHT) {

			dx = 4;
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {

			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {

			dx = 0;
		}
	}
}
