package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SaveGameHandler {
	
	public static void writeGameToFile(Game game, File saveFile) {
		ObjectOutputStream gameOut = null;
		
		try {
			gameOut = new ObjectOutputStream(new FileOutputStream(saveFile));
			gameOut.writeObject(game);
		}
		catch (IOException e) {
			System.err.println("Saving game file failed due to unexpected IO error when writing to the file.");
			e.printStackTrace();
		}
		finally {
			if (gameOut != null) {
				try {
					gameOut.close();
				}
				catch (IOException e) {
					System.err.println("Closing the save game file failed unexpectedly.");
				}
			}
		}
	}
	
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
