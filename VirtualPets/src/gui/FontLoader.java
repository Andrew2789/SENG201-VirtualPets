package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {
	public static Font loadFont(String location) {
		File font_file = new File(location);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
			return font;
		}
		catch (FontFormatException e) {
			System.out.println("Font must be a truetype font.");
			return null;
		}
		catch (IOException e) {
			System.out.println("Failed to load font.");
			return null;
		}
	}
}
