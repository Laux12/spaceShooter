package com.zetcode;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SpaceInvaders extends JFrame {
	private static final long serialVersionUID = 1L;

	public SpaceInvaders() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		setTitle("Space Invaders");
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {

			var ex = new SpaceInvaders();
			ex.setVisible(true);
		});
	}
}
