package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A class with purely static methods for the purposes of reading and writing saved game files.
 * @author Alex Tompkins (ato47)
 */
public class SaveGameHandler {
	/**
	 * Writes the given game to a file by simply serializing it into byte form and storing it.
	 * @param game
	 * The game to be saved in a file.
	 * @param saveFile
	 * The file to save the game in.
	 * @throws IOException
	 * An IOException may be thrown if the output stream is interrupted when writing the game to a file, 
	 * or when closing the save game file fails unexpectedly.
	 */
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
	
	/**
	 * Reads the given game from a save file by reading it out of its byte form and making an object out of it.
	 * @param saveFile
	 * The file that the game is saved in.
	 * @return
	 * Returns a Game object to be used in resuming the game logic elsewhere.
	 * @throws IOException
	 * May throw IOException when the input stream is interrupted while the game is being reading from the file, 
	 * or when closing the save game file afterwards fails unexpectedly.
	 * @throws ClassNotFoundException
	 * May throw ClassNotFoundException if the Game object saved in the file contains objects of classes that
	 * this version of the game does not recognise. Hence it may occur if new versions of the game with new
	 * classes are released.
	 */
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
