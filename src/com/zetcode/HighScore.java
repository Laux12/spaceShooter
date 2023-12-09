package com.zetcode;

import java.io.Serializable;

public class HighScore implements Serializable {
	private static final long serialVersionUID = 1L;
	public int score;

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;

	}
}
