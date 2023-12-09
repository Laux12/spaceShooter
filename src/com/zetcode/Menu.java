package com.zetcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class Menu extends MouseAdapter {
	private boolean playIsHover = false;
	private boolean optionIsHover = false;
	private boolean exitIsHover = false;
	private boolean optionIsClicked = false;
	int opacity1 = 0;
	int opacity2 = 0;
	int opacity3 = 0;
	String label = "OFF MUSIC";
	Image menuBG = new ImageIcon("menuBG.jpg").getImage();
	Board b;
	FontMetrics strWidth;

	Menu(Board b) {
		this.b = b;
	}

	public void draw(Graphics2D g2d) {
		strWidth = g2d.getFontMetrics();
		g2d.drawImage(menuBG, 0, 0, 550, 558, null);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.white);
		g2d.drawRect(170, 130, 210, 40);
		g2d.setColor(new Color(25, 12, 170, opacity1));
		g2d.fillRect(170, 131, 210, 39);
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString("PLAY", 242, 157);
		g2d.drawRect(170, 190, 210, 40);
		g2d.setColor(new Color(25, 12, 170, opacity2));
		g2d.fillRect(170, 190, 210, 40);
		g2d.setColor(Color.white);
		g2d.drawString(label, 248 - strWidth.stringWidth(label) / 2, 217);
		g2d.drawRect(170, 250, 210, 40);
		g2d.setColor(new Color(25, 12, 170, opacity3));
		g2d.fillRect(170, 250, 210, 40);
		g2d.setColor(Color.white);
		g2d.drawString("EXIT", 247, 277);
	}

	public void mousePressed(MouseEvent e) {
		if (playIsHover) {
			Board.gameState = Board.playState;
			b.gameBG.play();
			b.gameBG.loop();
			b.menuBG.stop();
		}
		if (optionIsHover) {
			if (!optionIsClicked) {
				b.gameBG.offMusic();
				b.menuBG.offMusic();
				label = "ON MUSIC";
				optionIsClicked = true;

			} else {
				b.gameBG.onMusic();
				b.menuBG.onMusic();
				label = "OFF MUSIC";
				optionIsClicked = false;
			}
		}
		if (exitIsHover) {
			System.exit(1);
		}
	}

	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();

		if (mouseOver(mx, my, 170, 130, 210, 40)) {
			playIsHover = true;
			opacity1 = 200;
		} else {
			opacity1 = 0;
			playIsHover = false;
		}

		if (mouseOver(mx, my, 170, 190, 210, 40)) {
			optionIsHover = true;
			opacity2 = 200;
		} else {
			opacity2 = 0;
			optionIsHover = false;
		}
		if (mouseOver(mx, my, 170, 250, 210, 40)) {
			exitIsHover = true;
			opacity3 = 200;
		} else {
			opacity3 = 0;
			exitIsHover = false;
		}

	}

	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if ((mx > x && mx < x + width) && (my > y && my < y + height)) {
			return true;
		} else {
			return false;
		}
	}
}
