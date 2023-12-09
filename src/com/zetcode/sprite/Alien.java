package com.zetcode.sprite;

import java.io.File;

import javax.swing.ImageIcon;

import com.zetcode.Music;

public class Alien extends Sprite {
	public Music music = new Music();
	private Bomb bomb;
	File shootEffect = new File("PlayerShoots.wav");

	public Alien(int x, int y) {

		initAlien(x, y);
		music.setFile(shootEffect);
	}

	private void initAlien(int x, int y) {

		this.x = x;
		this.y = y;

		bomb = new Bomb(x, y);

		var alienImg = "src/images/alien.png";
		var ii = new ImageIcon(alienImg);

		setImage(ii.getImage());
	}

	public void act(int direction) {

		this.x += direction * 2;
	}

	public Bomb getBomb() {

		return bomb;
	}

	public class Bomb extends Sprite {

		private boolean destroyed;

		public Bomb(int x, int y) {

			initBomb(x, y);
		}

		private void initBomb(int x, int y) {

			setDestroyed(true);

			this.x = x;
			this.y = y;

			var bombImg = "src/images/bomb.png";
			var ii = new ImageIcon(bombImg);
			setImage(ii.getImage());
		}

		public void setDestroyed(boolean destroyed) {

			this.destroyed = destroyed;
		}

		public boolean isDestroyed() {

			return destroyed;
		}
	}
}
