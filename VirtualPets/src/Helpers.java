import java.util.Scanner;

public class Helpers {
	/**
	 * Case insensitive string matcher.
	 * @return
	 * Whether the strings match
	 */
	public static boolean match(String s1, String s2) {
		return s1.toLowerCase().equals(s2.toLowerCase());
	}
	
	/**
	 * Gets an integer from a scanner with a specified prompt. Prompts for input until valid input is entered.
	 * 
	 * @param scanner
	 * The scanner to get input from
	 * @param prompt
	 * The prompt for input
	 * @return
	 * The integer from the scanner
	 */
	public static int getInt(Scanner scanner, String prompt) {
		String input;
		boolean valid;
		do {
			System.out.print(prompt);
			input = scanner.next();
			try {
				Integer.parseInt(input);
				valid = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Input was not an integer.");
				valid = false;
			}
		} while (!valid);
		return Integer.parseInt(input);
	}
}
