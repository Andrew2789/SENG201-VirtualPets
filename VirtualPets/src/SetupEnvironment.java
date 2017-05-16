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
	private int getInt(Scanner scanner, String prompt) {
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
	 * Sets up the game environment. The user may add new species, toy types, or food types. Then, each player will be
	 * named and given a number of pets (the user must specify each pet's name and species). A favourite food and toy 
	 * are randomly assigned to each pet.
	 * 
	 * @return
	 * The game environment that was set up
	 */
	public void setUpGame() {
		Species[] species = {
				new Species("Cat", 20, 10, 15, 5, 40, 70),
				new Species("Doggo", 40, 15, 25, 10, 30, 55),
				new Species("Pupper", 25, 12, 18, 20, 15, 35)
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
		Pet[] playerPets;
		int numberOfPets;
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\r\n|\n");
		String speciesList = "Species:\n";
		for (Species speciesInstance: species) {
			speciesList += String.format("Species name: %s | Optimum weight: %d | Hunger gain per turn: %d | "
					+ "Energy loss per turn: %d | Happiness loss per turn: %d | Toy damage: %d-%d\n", 
					speciesInstance.getName(), speciesInstance.getOptimumWeight(), speciesInstance.getHungerGain(), speciesInstance.getEnergyLoss(),
					speciesInstance.getHappinessLoss(), speciesInstance.getMinToyDamage(), speciesInstance.getMaxToyDamage());
		}
		
		int numberOfPlayers = getInt(scanner, "Enter the number of players (1-3): ");
		while (numberOfPlayers < 1 || numberOfPlayers > 3) {
			numberOfPlayers = getInt(scanner, "Must have 1-3 players. Enter a valid number of players: ");
		}

		int numberOfDays = getInt(scanner, "Enter the number of days to play for: ");
		while (numberOfDays < 1){
			numberOfDays = getInt(scanner, "Cannot play for less than 1 day. Enter a valid number of days: ");
		}
		
		Player[] players = new Player[numberOfPlayers];
		
		// Instantiate all players
		for (int i=0; i<players.length; i++) {
			System.out.print(String.format("Enter the name of player %d: ", i+1));
			playerName = scanner.next();
			while (usedPlayerNames.contains(playerName)) {
				System.out.print(String.format("There is already a player called %s. Enter a unique name for player %d: ", playerName, i+1));
				playerName = scanner.next();
			}
			usedPlayerNames.add(playerName);

			numberOfPets = getInt(scanner, String.format("Enter the number of pets for %s (1-3): ", playerName));
			while (numberOfPets < 1 || numberOfPets > 3) {
				numberOfPets = getInt(scanner, String.format("Cannot have that number of pets. Enter a valid number of pets for %s (1-3): ", playerName));
			}
			
			playerPets = new Pet[numberOfPets];
			// Instantiate all pets for the player
			for (int j=0; j<playerPets.length; j++) {
				System.out.print(String.format("Enter the name of %s's next pet: ", playerName));
				petName = scanner.next();
				while (usedPetNames.contains(petName)) {
					System.out.print(String.format("There is already a pet called %s. Enter a unique name for this pet: ", petName));
					petName = scanner.next();
				}
				usedPetNames.add(petName);
				
				// Get pet species, check to make sure input is a valid species

				int speciesIndex = 0;
				do {
					if (speciesIndex != 0) 
						System.out.println("That is not a valid species.");
					speciesIndex = 0;
					System.out.print(String.format("Enter %s's species or enter 'help' to list all species: ", petName));
					petSpecies = scanner.next();
					if (petSpecies.equals("help")) {
						System.out.print(speciesList);
						speciesIndex = species.length;
					}
					else
						for (; speciesIndex<species.length; speciesIndex++)
							if (species[speciesIndex].getName().equals(petSpecies))
								break;
				} while (speciesIndex == species.length);
				
				// Randomly assign a favourite toy and food to the pet
				int favouriteToyIndex = ThreadLocalRandom.current().nextInt(0, toyTypes.length);
				int favouriteFoodIndex = ThreadLocalRandom.current().nextInt(0, foodTypes.length);
				
				playerPets[j] = new Pet(petName, species[speciesIndex], toyTypes[favouriteToyIndex], foodTypes[favouriteFoodIndex]);
			}
			
			players[i] = new Player(playerName, playerPets);
			/*
			for (Pet pet: playerPets) {
				System.out.println(String.format("name: %s, species: %s, favtoy: %s, favfood: %s", pet.getName(), pet.getSpecies().getName(), pet.getFavouriteToy().getName(), pet.getFavouriteFood().getName()));
			}
			*/
		}
		currentGameEnvironment = new GameEnvironment(players, species, toyTypes, foodTypes, numberOfDays, scanner);
		System.out.println();
	}
	
	public static void main(String[] args) {
		SetupEnvironment setupEnvironment = new SetupEnvironment();
		setupEnvironment.setUpGame();
		setupEnvironment.currentGameEnvironment.playGame();
	}
}
