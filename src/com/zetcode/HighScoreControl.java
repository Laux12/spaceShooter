package com.zetcode;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HighScoreControl {
	private final String filePath = "HighScore.ser";

	public void saveScores(HighScore hScores) throws IOException {

		FileOutputStream fileOut = new FileOutputStream(filePath);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(hScores);
		out.close();
		fileOut.close();
	}

	public HighScore loadScores() throws ClassNotFoundException, IOException {
		HighScore hScore = null;
		FileInputStream fileIn = new FileInputStream(filePath);
		ObjectInputStream in = new ObjectInputStream(fileIn);

		hScore = (HighScore) in.readObject();
		fileIn.close();
		in.close();

		return hScore;

	}
}
