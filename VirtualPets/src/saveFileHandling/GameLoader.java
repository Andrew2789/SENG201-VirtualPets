package saveFileHandling;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;

import application.Game;

import java.io.IOException;

public class GameLoader {
	public static Game readGameFromFile(File saveFile) {
		ObjectInputStream gameIn = null;
		Game savedGame = null;
		
		try {
			gameIn = new ObjectInputStream(new FileInputStream(saveFile));
			savedGame = (Game) gameIn.readObject();
		}
		catch (IOException e) {
			System.err.println("Loading game file failed due to an unexpected IO error while reading the file.");
		}
		catch (ClassNotFoundException e) {
			System.err.println("Loading game file failed as the Class for the objects saved within could not be found.");
		}
		finally {
			if (gameIn != null)
				try {
					gameIn.close();
				}
				catch (IOException e) {
					System.err.println("Closing the save game file failed unexpectedly.");
				}
		}
		return savedGame;
	}
}
