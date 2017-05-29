package saveFileHandling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

import application.Game;

public class GameSaver {
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
}
