import java.util.Scanner;

public class GameEnvironment {
	Player[] players;
	Species[] species;
	ToyType[] toyTypes;
	FoodType[] foodTypes;
	int numberOfDays;
	Scanner scanner;
	String roundCommands = "Commands: 'select [pet name]', 'shop food', 'shop toys', 'status', 'help', 'end turn'";
	String petCommands = "Commands: 'eat [food name]', 'play [toy name]', 'toilet', 'help', 'deselect'";
	
	/**
	 * @author Alex Tompkins (ato47)
	 * Initialises all instance variables used by a game
	 * @param players
	 * @param species
	 * @param toyTypes
	 * @param foodTypes
	 * @param numberOfDays
	 */
	public GameEnvironment(Player[] players, Species[] species, 
			ToyType[] toyTypes, FoodType[] foodTypes, int numberOfDays, Scanner scanner) {
		
		this.players = players;
		this.species = species;
		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
		this.numberOfDays = numberOfDays;
		this.scanner = scanner;
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Runs the game.
	 */
	public void playGame() {
		for (int i=0; i<numberOfDays; i++) {
			System.out.println(String.format("===== Round %d =====", i+1));
			round();
			System.out.println();
		}
		scanner.close();
		System.out.println("===== Game over =====");
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Runs a single round (day) of the game.
	 */
	private void round() {
		int roundScore;
		String input;
		boolean turnDone = false;
		boolean interacted = false;
		
		for (Player player: players) {
			// Initialise this player's turn
			roundScore = 0;
			System.out.println(String.format("Player %s's turn.", player.getName()));
			checkPetEvents(player.getPets());
			showPlayerStatus(player);
			System.out.println(roundCommands);
			
			turnDone = false;
			do {
				System.out.print("Enter a command: ");
				input = scanner.next();
				
				if (input.length() >= 7 && input.substring(0, 7).equals("select ")) {
					for (Pet pet: player.getPets()) {
						if (pet.getName().equals(input.substring(7))) {
							if (pet.getActionPoints() > 0) {
								if (pet.isAlive()) {
									interactWithPet(player, pet);
									interacted = true;
									System.out.println(roundCommands);
								}
								else
									System.out.println(pet.getName() + " is dead and cannot be selected.");
								break;
							}
							else
								System.out.println(pet.getName() + " has no action points and cannot be selected.");
							break;
						}
					}
					if (!interacted)
						System.out.println(String.format("No pet called '%s'.", input.substring(7)));
				}
				
				else if (input.equals("shop food"))
					foodShop(player);
				
				else if (input.equals("shop toys"))
					toyShop(player);
				
				else if (input.equals("status"))
					showPlayerStatus(player);
				
				else if (input.equals("help"))
					System.out.println(roundCommands);
				
				else if (input.equals("end turn"))
					turnDone = true;
				
				else
					System.out.println("Input was not a recognised command.");
				
			} while (!turnDone);
			System.out.println();
			
			for (Pet pet: player.getPets()) {
				pet.turnDone();
			}
		}
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Generates and checks random event occurrences for an array of pets.
	 * @param pets
	 * The array of pets to be used
	 */
	private void checkPetEvents(Pet[] pets) {
		boolean[] statusEffectsSet;
		String events;
		
		System.out.print("New events: ");
		events = "";
		for (Pet pet: pets) {
			pet.resetActionPoints();
			statusEffectsSet = pet.genRandomEvents();
			
			if (statusEffectsSet[0]) 
				events += pet.getName() + " became sick, ";
			if (statusEffectsSet[1])
				events += pet.getName() + " has started to misbehave, ";
			if (statusEffectsSet[2])
				events += pet.getName() + " died, ";
		}
		if (events.length() == 0)
			System.out.println("None.");
		else
			System.out.println(events.substring(0, events.length()-2));
		System.out.println();
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Prints the player's status to the console.
	 * @param player
	 * The player to display status of
	 */
	private void showPlayerStatus(Player player) {
		System.out.println(String.format("Money: $%.2f", player.getMoney()));
		
		String outString = "";
		for (Toy toy: player.getToys())
			outString += toy + ", ";
		if (outString.length() == 0)
			System.out.println("Toys: None");
		else
			System.out.println(String.format("Toys: %s", outString.substring(0, outString.length()-2)));
		
		outString = "";
		for (FoodType foodType: player.getFood().keySet())
			outString += foodType.getName() + String.format(" x %d, ", player.getFood().get(foodType));
		if (outString.length() == 0)
			System.out.println("Food: None");
		else
			System.out.println(String.format("Food: %s", outString.substring(0, outString.length()-2)));
		
		System.out.println("Pets:");
		for (Pet pet: player.getPets())
			System.out.println(pet + "\n");
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Allows the user to interact with a selected pet.
	 * @param player
	 * The current player
	 * @param activePet
	 * The selected pet
	 */
	private void interactWithPet(Player player, Pet activePet) {
		String input;
		System.out.println(String.format("\nPet %s selected.", activePet.getName()));
		System.out.println(petCommands);
		
		do {
			System.out.print("Enter a command: ");
			input = scanner.next();
			
			// Check the input for a valid command
			
			if (input.length() >= 4 && input.substring(0, 4).equals("eat "))
				eat(player, activePet, input.substring(4));
			
			else if (input.length() >= 5 && input.substring(0, 5).equals("play "))
				play(player, activePet, input.substring(5));
			
			else if (input.equals("toilet")) {
				activePet.goToToilet();
				System.out.println(String.format("%s went to the toilet. New status: \n%s", activePet.getName(), activePet));
			}
			
			else if (input.equals("help"))
				System.out.println(petCommands);
			
			else if (input.equals("deselect"))
				continue;
			
			else
				System.out.println("Input was not a recognised command.");
			
			if (activePet.getActionPoints() == 0)
				System.out.println(String.format(activePet.getName() + " is out of action points and has been deselected."));
			
		} while (!(input.equals("deselect")) && activePet.getActionPoints() > 0);
		System.out.println();
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Check that the specified food exists and is owned, feed it to the specified pet if so.
	 * @param player
	 * The player who owns the pet to be fed
	 * @param activePet
	 * The pet to be fed
	 * @param foodName
	 * The name of the food to be fed to the pet
	 */
	private void eat(Player player, Pet activePet, String foodName) {
		boolean fed = false;
		for (FoodType food: player.getFood().keySet()) {
			if (food.getName().equals(foodName)) {
				System.out.println(String.format("Fed %s %s.", activePet.getName(), food.getName()));
				player.feed(activePet, food);
				fed = true;
				break;
			}
		}
		if (!fed)
			System.out.println(String.format("The food '%s' is not owned by %s or does not exist.", foodName, player.getName()));
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Check that the specified toy exists and is owned, use it to play with the specified pet if so.
	 * @param player
	 * The player that owns the pet to be played with
	 * @param activePet
	 * The pet to be played with
	 * @param toyName
	 * The name of the toy to use to play with the pet
	 */
	private void play(Player player, Pet activePet, String toyName) {
		boolean played = false;
		ToyType chosenToyType = null;
		
		for (ToyType toyType: toyTypes) {
			if (toyType.getName().equals(toyName)) {
				chosenToyType = toyType;
				break;
			}
		}
		if (!(chosenToyType == null)) {
			for (int i=0; i<player.getToys().size(); i++) {
				if (player.getToys().get(i).getToyType() == chosenToyType) {
					System.out.println(String.format("Played with %s using a %s.", 
							activePet.getName(), player.getToys().get(i).getToyType().getName()));
					if (!player.playWith(activePet, i))
						System.out.println("The toy broke.");
					else
						System.out.println(String.format("The toys durability is now %d.", player.getToys().get(i).getDurability()));
					played = true;
					break;
				}
			}
			if (!played)
				System.out.println(String.format("The toy '%s' is not owned by %s.", toyName, player.getName()));
		}
		else
			System.out.println(String.format("The toy %s does not exist.", toyName));
	}
	
	/**
	 * @author Alex Tompkins (ato47)
	 * Method to process a player visiting the food shop. A player may buy food there if they have enough money.
	 * Bought food will be added to the player's inventory and its price deducted from the player's balance.
	 * @param player
	 * The player visiting the food shop.
	 */
	private void foodShop(Player player) {
		String input;
		String[] inputParts;
		FoodType desiredFood;
		int numFood = -1;
		
		System.out.println("\nWelcome to the food shop!");
		
		// Section to show food types and their price, nutrition and tastiness.
		System.out.println("These foods are for sale:");
		System.out.println("Food            | Price       | Nutrition  | Tastiness ");
		for (FoodType foodType : foodTypes) {
			System.out.println(String.format("%-15s | $%-10.2f | %-10d | %-10d", foodType.getName(), foodType.getPrice(), foodType.getNutrition(), foodType.getTastiness()));
		}
		
		// Section for user input.
		System.out.println("Enter 'buy [food name]' to buy that food, 'buy [food name] [number] to buy multiple of that food, or 'leave' to leave.");
		do {
			System.out.println(String.format("\nYou have $%.2f.", player.getMoney()));
			System.out.print("What do you want to do? ");
			input = scanner.next();
			
			if (input.length() >= 4 && input.substring(0, 4).equals("buy ")) {
				inputParts = input.substring(4).split(" ");
				
				// Check format of input and set numFood to -1 if invalid, otherwise parse input for numFood.
				if (inputParts.length == 1)
					numFood = 1;
				else if (inputParts.length == 2) {
					try {
						numFood = Integer.parseInt(inputParts[1]);
						if (numFood <= 0) {
							numFood = -1;
							System.out.println("You can't buy less than one of a food.");
						}
					}
					catch (NumberFormatException e) {
						numFood = -1;
						System.out.println("That is not a valid number of food.");
					}
				}
				else {
					numFood = -1;
					System.out.println("That's not a valid input. Try again with the format 'buy [food name] [number]'.");
				}
				
				// Check if invalid buy format or numFood found.
				if (numFood != -1) {
					// Check that foodType given is valid.
					desiredFood = null;
					for (FoodType foodType : foodTypes) {
						if (foodType.getName().toLowerCase().equals(inputParts[0].toLowerCase()))
							desiredFood = foodType;
					}
					if (desiredFood == null)
						System.out.println(String.format("'%s' is not a valid food to buy.", inputParts[0]));
					
					// Check player has enough money for the given number of that FoodType.
					else if (player.getMoney() < desiredFood.getPrice()*numFood)
						System.out.println("You don't have enough money to buy that food.");
					
					// All checks passed, charge player the money and give them the given number of that food.
					else {
						player.changeMoney(-desiredFood.getPrice()*numFood);
						for (int i=0; i<numFood; i++)
							player.addFood(desiredFood);
						System.out.println(String.format("%d of %s was added to your inventory!", numFood, desiredFood.getName()));
					}
				}
			}
			
			else if (input.equals("leave"))
				System.out.println("Thank you, come again!\n");
			else
				System.out.println(String.format("'%s' is not a valid input. Please try again.", input));
			
		} while (!input.equals("leave"));
	}
	
	/**
	 * @author Alex Tompkins (ato47)
	 * Method to process a player visiting the toy shop. A player may buy toys there if they have enough money.
	 * Bought toys will be added to the player's inventory and their price deducted from the player's balance.
	 * @param player
	 * The player visiting the toy shop.
	 */
	private void toyShop(Player player) {
		String input;
		ToyType desiredToy;
		
		System.out.println("\nWelcome to the toy shop!");
		
		// Section to show toy types and their price and happiness gain.
		System.out.println("These toys are for sale:");
		System.out.println("Toy             | Price       | Happiness Gain");
		for (ToyType toyType : toyTypes) {
			System.out.println(String.format("%-15s | $%-10.2f | %-10d", toyType.getName(), toyType.getPrice(), toyType.getHappinessGain()));
		}
		
		// Section for user input.
		System.out.println("Enter 'buy [toy name]' to buy one of that toy, or 'leave' to leave.");
		do {
			System.out.println(String.format("\nYou have $%.2f.", player.getMoney()));
			System.out.print("What do you want to do? ");
			input = scanner.next();
			
			// Check the format of the user input.
			if (input.length() >= 4 && input.substring(0, 4).equals("buy ")) {
				// Search the known toy types to try find a match for user input.
				desiredToy = null;
				for (ToyType toyType : toyTypes) {
					if (toyType.getName().toLowerCase().equals(input.substring(4).toLowerCase()))
						desiredToy = toyType;
				}
				if (desiredToy == null)
					System.out.println(String.format("'%s' is not a valid toy to buy.", input.substring(4)));
				
				// Check that the player has enough money to buy that toy.
				else if (player.getMoney() < desiredToy.getPrice())
					System.out.println("You don't have enough money to buy that toy.");
				
				// All checks passed, charge player the money and a brand new toy of that type to their inventory.
				else {
					player.changeMoney(-desiredToy.getPrice());
					Toy boughtToy = new Toy(desiredToy);
					player.addToy(boughtToy);
					System.out.println(String.format("A brand new %s was added to your inventory!", desiredToy.getName()));
				}
			}
			
			else if (input.equals("leave"))
				System.out.println("Thank you, come again!\n");
			else
				System.out.println(String.format("'%s' is not a valid input. Please try again.", input));
					
		} while (!input.equals("leave"));
	}
}
