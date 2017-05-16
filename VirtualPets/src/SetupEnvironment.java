import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Andrew Davidson (ada130)
 * This class is used to prompt the user for information required to set up a game, and then to create a game environment
 * using that information.
 */
public class SetupEnvironment {
	private GameEnvironment currentGameEnvironment;
	Scanner scanner;
	
	public SetupEnvironment(Scanner scanner) {
		this.scanner = scanner;
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
	private int getInt(String prompt) {
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
	
	/**
	 * Main menu for the game.
	 * @return
	 * True if the menu should be opened again, false if the program should close
	 */
	public boolean menu() {
		String command;
		
		System.out.println("\n~~~~ Virtual Pets ~~~~\n");
		System.out.println("Commands:\n"
				+ "'new' - new game\n"
				+ "'com' - list all possible commands\n"
				+ "'help' - explain the game mechanics\n"
				+ "'exit' - exit the game");
		
		while (true) {
			System.out.print("Enter a command: ");
			command = scanner.next();
			
			if (Helpers.match(command, "new")) {
				newGame();
				currentGameEnvironment.playGame();
				return true;
			}
			else if (Helpers.match(command, "com"))
				System.out.println("Commands: 'new', 'com', 'help', 'exit'.");
			else if (Helpers.match(command, "help"))
				System.out.println("Do stuff.");
			else if (Helpers.match(command, "exit")) {
				System.out.println();
				return false;
			}
			else
				System.out.println("Command not recognised. Enter 'com' to list possible commands.");
		}
	}
	
	/**
	 * Sets up the game environment. The user may add new species, toy types, or food types. Then, each player will be
	 * named and given a number of pets (the user must specify each pet's name and species). A favourite food and toy 
	 * are randomly assigned to each pet.
	 * @return
	 * The game environment that was set up
	 */
	private void newGame() {
		Species[] species = {
				new Species("Cat", 20, 10, 15, 5, 40, 70),
				new Species("Doggo", 40, 15, 25, 10, 30, 55),
				new Species("Pupperrrrr", 25, 12, 18, 20, 15, 35)
		};
		
		ToyType[] toyTypes = {
				new ToyType("Proleteriat", -100, 100),
				new ToyType("Bell", 10, 15),
				new ToyType("Mouse", 25, 50)
		};
		
		FoodType[] foodTypes = {
				new FoodType("Lasagne", 20, 50, 25, 10),
				new FoodType("Berries", 5, -100, -100, 5),
				new FoodType("Mushrooms", 10, 20, 100, 5)
		};

		ArrayList<String> usedPlayerNames = new ArrayList<String>();
		ArrayList<String> usedPetNames = new ArrayList<String>();
		String playerName;
		String petName;
		String petSpecies;
		int numberOfPets;
		Pet[] playerPets;
		Species chosenSpecies;
		
		int longestSpecies = 7;
		for (Species speciesType: species)
			if (speciesType.getName().length() > longestSpecies)
				longestSpecies = speciesType.getName().length();
		
		String speciesFormat = "%-" + longestSpecies +"s | %-14d | %-11d | %-11d | %-14d | %4d - %-4d\n";
		String speciesList = String.format("Species: (gain/loss stats are per turn)\n%-"+ longestSpecies +"s | Optimum weight | Hunger gain | Energy loss | Happiness loss | Toy damage\n", "Species");
		for (Species speciesType: species)
			speciesList += String.format(speciesFormat, speciesType.getName(), speciesType.getOptimumWeight(), speciesType.getHungerGain(), 
					speciesType.getEnergyLoss(), speciesType.getHappinessLoss(), speciesType.getMinToyDamage(), speciesType.getMaxToyDamage());
		
		System.out.println();
		int numberOfPlayers = getInt("Enter the number of players (1-3): ");
		while (numberOfPlayers < 1 || numberOfPlayers > 3) {
			numberOfPlayers = getInt("Must have 1-3 players. Enter a valid number of players: ");
		}

		int numberOfDays = getInt("Enter the number of days to play for: ");
		while (numberOfDays < 1){
			numberOfDays = getInt("Cannot play for less than 1 day. Enter a valid number of days: ");
		}
		
		Player[] players = new Player[numberOfPlayers];
		
		// Instantiate all players
		for (int i=0; i<players.length; i++) {
			System.out.print(String.format("Enter the name of player %d: ", i+1));
			playerName = scanner.next();
			while (usedPlayerNames.contains(playerName.toLowerCase())) {
				System.out.print(String.format("There is already a player called %s. Enter a unique name for player %d: ", playerName.toLowerCase(), i+1));
				playerName = scanner.next();
			}
			usedPlayerNames.add(playerName.toLowerCase());

			numberOfPets = getInt(String.format("Enter the number of pets for %s (1-3): ", playerName));
			while (numberOfPets < 1 || numberOfPets > 3) {
				numberOfPets = getInt(String.format("Cannot have that number of pets. Enter a valid number of pets for %s (1-3): ", playerName));
			}
			
			playerPets = new Pet[numberOfPets];
			// Instantiate all pets for the player
			for (int j=0; j<playerPets.length; j++) {
				System.out.print(String.format("Enter the name of %s's next pet: ", playerName));
				petName = scanner.next();
				while (usedPetNames.contains(petName.toLowerCase())) {
					System.out.print(String.format("There is already a pet called %s. Enter a unique name for this pet: ", petName.toLowerCase()));
					petName = scanner.next();
				}
				usedPetNames.add(petName.toLowerCase());
				
				// Get pet species, check to make sure input is a valid species

				chosenSpecies = null;
				while (chosenSpecies == null) {
					System.out.print(String.format("Enter %s's species or enter 'species' to list all species: ", petName));
					petSpecies = scanner.next();
					if (Helpers.match(petSpecies, "species")) {
						System.out.print(speciesList);
					}
					else {
						for (Species speciesType: species)
							if (Helpers.match(speciesType.getName(), petSpecies)) {
								chosenSpecies = speciesType;
								break;
							}
						if (chosenSpecies == null)
							System.out.println("That is not a valid species.");
					}
				}
				
				// Randomly assign a favourite toy and food to the pet and instantiate it
				playerPets[j] = new Pet(petName, chosenSpecies, toyTypes[ThreadLocalRandom.current().nextInt(0, toyTypes.length)], foodTypes[ThreadLocalRandom.current().nextInt(0, foodTypes.length)]);
			}
			players[i] = new Player(playerName, playerPets);
		}
		currentGameEnvironment = new GameEnvironment(players, species, toyTypes, foodTypes, numberOfDays, longestSpecies, scanner);
		System.out.println();
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\r\n|\n");
		
		SetupEnvironment setupEnvironment = new SetupEnvironment(scanner);
		while (setupEnvironment.menu());
		scanner.close();
	}
}
