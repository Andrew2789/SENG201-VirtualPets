package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SaveGameHandler {
	
	public static void writeGameToFile(Game game, File saveFile) throws IOException {
		ObjectOutputStream gameOut = null;
		gameOut = new ObjectOutputStream(new FileOutputStream(saveFile));
		gameOut.writeObject(game);
		
		if (gameOut != null) {
			try {
				gameOut.close();
			}
			catch (IOException e) {
				System.err.println("Closing the save game file failed unexpectedly.");
			}
		}
	}
	
	public static Game readGameFromFile(File saveFile) throws IOException, ClassNotFoundException {
		ObjectInputStream gameIn = null;
		Game savedGame = null;
		gameIn = new ObjectInputStream(new FileInputStream(saveFile));
		savedGame = (Game) gameIn.readObject();

		if (gameIn != null) {
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
