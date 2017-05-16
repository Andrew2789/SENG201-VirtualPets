import java.util.Scanner;

public class GameEnvironment {
	Player[] players;
	Species[] species;
	ToyType[] toyTypes;
	FoodType[] foodTypes;
	int numberOfDays;
	Scanner scanner;
	
	String roundCommands = "Commands: 'select [pet name]', 'revive [pet name]', 'shop food', 'shop toys', 'events', 'inventory', 'pet status', 'score', 'com', 'end'";
	String petCommands = "Commands: 'eat [food name]', 'play [toy name]', 'toilet', 'sleep', 'cure', 'discipline', 'inventory', 'pet status', 'com', 'exit'";
	String foodCommands = "Commands: 'buy [food name]', 'buy [food name] [amount]', 'show shop', 'inventory', 'pet status', 'com', 'exit'";
	String toyCommands = "Commands: 'buy [toy name]', 'show shop', 'inventory', 'pet status', 'com', 'exit'";
	
	String petHeader;
	String petFormat;
	String toyHeader;
	String toyFormat;
	String foodHeader;
	String foodFormat;
	
	/**
	 * @author Alex Tompkins (ato47)
	 * Initialises all instance variables used by a game
	 * @param players
	 * @param species
	 * @param toyTypes
	 * @param foodTypes
	 * @param numberOfDays
	 */
	public GameEnvironment(Player[] players, Species[] species, ToyType[] toyTypes, FoodType[] foodTypes, int numberOfDays, int longestSpecies, Scanner scanner) {
		this.players = players;
		this.species = species;
		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
		this.numberOfDays = numberOfDays;
		this.scanner = scanner;
		
		int longestToy = 7;
		for (ToyType toyType: toyTypes)
			if (toyType.getName().length() > longestToy)
				longestToy = toyType.getName().length();
		toyHeader = String.format("%-"+ Integer.toString(longestToy) +"s | Price     | Happiness Gain", "Toy");
		toyFormat = "%-"+ Integer.toString(longestToy) +"s | $%-8.2f | %-5d";
		
		int longestFood = 8;
		for (FoodType foodType: foodTypes)
			if (foodType.getName().length() > longestFood)
				longestFood = foodType.getName().length();
		foodHeader = String.format("%-"+ Integer.toString(longestFood) +"s | Price     | Nutrition | Tastiness ", "Food");
		foodFormat = "%-"+ Integer.toString(longestFood) +"s | $%-8.2f | %-9d | %-9d";
		
		int longestPet = 8;
		for (Player player: players)
			for (Pet pet: player.getPets())
				if (pet.getName().length() > longestPet)
					longestPet = pet.getName().length();
		petHeader = String.format("%-"+ Integer.toString(longestPet) +"s | %-"+ Integer.toString(longestSpecies) +"s | AP | Hunger | Energy | Happiness | Weight | Weight Dev | Healthy "
				+ "| Behaving | Alive | %-"+ Integer.toString(longestFood) +"s | %-"+ Integer.toString(longestToy) +"s", "Pet name", "Species", "Fav Food", "Fav Toy");
		petFormat = "%-"+ Integer.toString(longestPet) +"s | %-"+ Integer.toString(longestSpecies) +"s | %-2d | %-6d | %-6d | %-9d "
				+ "| %-6d | %s%-9d | %-7b | %-8b | %-5b | %-"+ Integer.toString(longestFood) +"s | %-"+ Integer.toString(longestToy) +"s";
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
		System.out.println("===== Game over =====");
		System.out.print("Press Enter to continue... ");
		scanner.next();
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Runs a single round (day) of the game.
	 */
	private void round() {
		int roundScore;
		String input;
		Pet activePet;
		
		for (Player player: players) {
			// Initialise this player's turn
			roundScore = 0;
			System.out.println(String.format("Player %s's turn.", player.getName()));
			String events = checkPetEvents(player.getPets());
			System.out.println(events);
			inventory(player);
			petStatus(player);
			System.out.println(roundCommands);
			
			do {
				System.out.print("Enter a command: ");
				input = scanner.next();
				
				if (input.length() >= 7 && Helpers.match(input.substring(0, 7), "select ")) {
					activePet = checkIsPet(input.substring(7), player);
					if (activePet == null)
						System.out.println(String.format("No pet called '%s'.", input.substring(7)));
					
					else if (activePet.getActionPoints() == 0)
						System.out.println(activePet.getName() + " has no action points and cannot be selected.");
					
					else if (!activePet.isAlive())
						System.out.println(activePet.getName() + " is dead and cannot be selected.");
					
					else
						interactWithPet(player, activePet);
						System.out.println(roundCommands);
				}
				else if (input.length() >= 7 && Helpers.match(input.substring(0, 7), "revive ")) {
					activePet = checkIsPet(input.substring(7), player);
					if (activePet == null)
						System.out.println(String.format("No pet called '%s'.", input.substring(7)));
					
					else if (activePet.isAlive())
						System.out.println("This pet is not dead!");
					
					else if (!activePet.isRevivable())
						System.out.println("This pet has already been revived once, and is permanently dead now.");
					
					else if (player.getMoney() < 50)
						System.out.println("It costs $50 to revive a pet. You do not have enough money.");
					
					else {
						activePet.revive();
						player.changeMoney(-50);
						System.out.println(String.format("%s was revived! It cost $50. They have 0 AP for this turn while they recover.", activePet.getName()));
						petStatus(player);
					}
				}
				
				else if (Helpers.match(input, "shop food")) {
					foodShop(player);
					System.out.println(roundCommands);
				}
				
				else if (Helpers.match(input, "shop toys")) {
					toyShop(player);
					System.out.println(roundCommands);
				}
				
				else if (Helpers.match(input, "events"))
					System.out.println(events);
				
				else if (Helpers.match(input, "inventory"))
					inventory(player);
				
				else if (Helpers.match(input, "pet status"))
					petStatus(player);
				
				else if (Helpers.match(input, "score"))
					System.out.println(player.getScore());
				
				else if (Helpers.match(input, "com"))
					System.out.println(roundCommands);
				
				else if (Helpers.match(input, "end"))
					continue;
				
				else
					System.out.println("Input was not a recognised command.");
				
			} while (!Helpers.match(input, "end"));
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
	private String checkPetEvents(Pet[] pets) {
		boolean[] statusEffectsSet;
		String events = "New events: ";
		
		//Check status newly set status effects
		for (Pet pet: pets) {
			statusEffectsSet = pet.genRandomEvents();
			
			if (statusEffectsSet[0]) 
				events += pet.getName() + " became sick, ";
			
			if (statusEffectsSet[1])
				events += pet.getName() + " has started to misbehave, ";
			
			if (statusEffectsSet[2])
				events += pet.getName() + " died, ";
		}
		
		//Alert player to status effect implications/starvation
		for (Pet pet: pets) {
			if (pet.getHunger() >= 90)
				System.out.println(String.format("%s is starving and is quickly losing weight, energy, and happiness.", pet.getName()));
			if (!pet.isHealthy())
				System.out.println(String.format("%s is sick and is losing 10 energy and 10 happiness per turn. They will continue to be sick until you cure them.", pet.getName()));
			if (!pet.isBehaving())
				System.out.println(String.format("%s is misbehaving and is losing 20 happiness per turn. They will continue to misbehave until you discipline them.", pet.getName()));
		}
		if (events.length() == 12)
			events += "None";
		else
			events = events.substring(0, events.length()-2);
		return events;
	}
	
	/**
	 * Check if toMatch corresponds to the name of the specified player's pets. Returns the pet if so.
	 * @param toMatch
	 * The string to check against the player's pet names
	 * @param player
	 * The player whose pet names will be checked against toMatch
	 * @return
	 * The pet matched. Null if no match was found
	 */
	private Pet checkIsPet(String toMatch, Player player) {
		for (Pet pet: player.getPets())
			if (Helpers.match(pet.getName(), toMatch))
				return pet;
		return null;
	}
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Prints the player's inventory to the console.
	 * @param player
	 * The player to display the inventory of
	 */
	private void inventory(Player player) {
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
	}	
	
	/**
	 * @author Andrew Davidson (ada130)
	 * Prints the status of all of a player's pets to the console.
	 * @param player
	 * The player whose pets status will be displayed
	 */
	private void petStatus(Player player) {
		System.out.println("Pets: (AP is action points, Fav is short for favourite, Weight Diff is weight difference from optimal weight)\n" + petHeader);
		for (Pet pet: player.getPets()) {
			System.out.println(pet.presentAs(petFormat));
		}
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
			
			if (input.length() >= 4 && Helpers.match(input.substring(0, 4), "eat "))
				eat(player, activePet, input.substring(4));
			
			else if (input.length() >= 5 && Helpers.match(input.substring(0, 5), "play "))
				play(player, activePet, input.substring(5));
			
			else if (Helpers.match(input, "toilet")) {
				activePet.goToToilet();
				System.out.println(activePet.getName() + " went to the toilet.");
				petStatus(player);
			}
			
			else if (Helpers.match(input, "sleep")) {
				activePet.sleep();
				System.out.println(activePet.getName() + " slept.");
				petStatus(player);
			}
			
			else if (Helpers.match(input, "cure")) {
				if (activePet.isHealthy())
					System.out.println(String.format("Cannot cure %s because they are healthy.", activePet.getName()));
				else if (player.getMoney() < 25)
					System.out.println("Curing a pet costs $25, you do not have enough money.");
				else {
					activePet.cure();
					System.out.println(activePet.getName() + " was cured and is no longer sick. It cost $25.");
					player.changeMoney(-25);
					petStatus(player);
				}
			}
			
			else if (Helpers.match(input, "discipline")) {
				if (activePet.isHealthy())
					System.out.println(String.format("Cannot discipline %s because they are behaving.", activePet.getName()));
				else {
					activePet.discipline();
					System.out.println(activePet.getName() + " was disciplined and is no longer misbehaving. They lost 30 happiness because of the disciplining.");
					petStatus(player);
				}
			}

			else if (Helpers.match(input, "inventory"))
				inventory(player);
			
			else if (Helpers.match(input, "pet status"))
				petStatus(player);
			
			else if (Helpers.match(input, "com"))
				System.out.println(petCommands);
			
			else if (Helpers.match(input, "exit"))
				System.out.println(activePet.getName() + " deselected.\n");
			
			else
				System.out.println("Input was not a recognised command.");
			
			if (activePet.getActionPoints() == 0)
				System.out.println(String.format(activePet.getName() + " is out of action points and has been deselected."));
			
		} while (!Helpers.match(input, "exit") && activePet.getActionPoints() > 0);
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
			if (Helpers.match(food.getName(), foodName)) {
				System.out.println(String.format("Fed %s %s.", activePet.getName(), food.getName()));
				player.feed(activePet, food);
				if (activePet.getFavouriteFood() == food)
					System.out.println(String.format("1.5x more happiness was gained because %s is %s's favourite food!", food.getName(), activePet.getName()));
				petStatus(player);
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
			if (Helpers.match(toyType.getName(), toyName)) {
				chosenToyType = toyType;
				break;
			}
		}
		
		if (!(chosenToyType == null)) {
			for (int i=0; i<player.getToys().size(); i++) {
				if (player.getToys().get(i).getToyType() == chosenToyType) {
					System.out.println(String.format("Played with %s using a %s.", activePet.getName(), chosenToyType.getName()));
					if (!player.playWith(activePet, i))
						System.out.println("The toy broke.");
					else
						System.out.println(String.format("The toys durability is now %d.", player.getToys().get(i).getDurability()));
					if (activePet.getFavouriteToy() == chosenToyType)
						System.out.println(String.format("1.5x more happiness was gained because %s is %s's favourite toy!", chosenToyType.getName(), activePet.getName()));
					petStatus(player);
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
		//System.out.println("Food            | Price       | Nutrition  | Tastiness ");
		System.out.println(foodHeader);
		for (FoodType foodType : foodTypes) {
			//System.out.println(String.format("%-15s | $%-10.2f | %-10d | %-10d", foodType.getName(), foodType.getPrice(), foodType.getNutrition(), foodType.getTastiness()));
			System.out.println(String.format(foodFormat, foodType.getName(), foodType.getPrice(), foodType.getNutrition(), foodType.getTastiness()));
		}
		
		// Section for user input.
		//System.out.println("Enter 'buy [food name]' to buy that food, 'buy [food name] [number] to buy multiple of that food, or 'exit' to exit.");
		System.out.println(foodCommands);
		do {
			System.out.println(String.format("\nYou have $%.2f.", player.getMoney()));
			System.out.print("What do you want to do? ");
			input = scanner.next();
			
			if (input.length() >= 4 && Helpers.match(input.substring(0, 4), "buy ")) {
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
						if (Helpers.match(foodType.getName().toLowerCase(), inputParts[0].toLowerCase()))
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
			
			else if (Helpers.match(input, "show shop")) {
				System.out.println("\nWelcome to the food shop!");
				System.out.println("These foods are for sale:");
				System.out.println(foodHeader);
				for (FoodType foodType : foodTypes) 
					System.out.println(String.format(foodFormat, foodType.getName(), foodType.getPrice(), foodType.getNutrition(), foodType.getTastiness()));
			}
			
			else if (Helpers.match(input, "inventory"))
				inventory(player);
			
			else if (Helpers.match(input, "pet status"))
				petStatus(player);
			
			else if (Helpers.match(input, "com"))
				System.out.println(foodCommands);
			
			else if (Helpers.match(input, "exit"))
				System.out.println("Thank you, come again!\n");
			
			else
				System.out.println(String.format("'%s' is not a valid input. Please try again.", input));
			
		} while (!Helpers.match(input, "exit"));
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
		//System.out.println("Toy             | Price       | Happiness Gain");
		System.out.println(toyHeader);
		for (ToyType toyType : toyTypes) {
			//System.out.println(String.format("%-15s | $%-10.2f | %-10d", toyType.getName(), toyType.getPrice(), toyType.getHappinessGain()));
			System.out.println(String.format(toyFormat, toyType.getName(), toyType.getPrice(), toyType.getHappinessGain()));
		}
		
		// Section for user input.
		//System.out.println("Enter 'buy [toy name]' to buy one of that toy, or 'exit' to exit.");
		System.out.println(toyCommands);
		do {
			System.out.println(String.format("\nYou have $%.2f.", player.getMoney()));
			System.out.print("What do you want to do? ");
			input = scanner.next();
			
			// Check the format of the user input.
			if (input.length() >= 4 && Helpers.match(input.substring(0, 4), "buy ")) {
				// Search the known toy types to try find a match for user input.
				desiredToy = null;
				for (ToyType toyType : toyTypes) {
					if (Helpers.match(toyType.getName().toLowerCase(), input.substring(4).toLowerCase()))
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
			
			else if (Helpers.match(input, "show shop")) {
				System.out.println("\nWelcome to the toy shop!");
				System.out.println("These toys are for sale:");
				System.out.println(toyHeader);
				for (ToyType toyType : toyTypes)
					System.out.println(String.format(toyFormat, toyType.getName(), toyType.getPrice(), toyType.getHappinessGain()));
			}
			
			else if (Helpers.match(input, "inventory"))
				inventory(player);
			
			else if (Helpers.match(input, "pet status"))
				petStatus(player);
			
			else if (Helpers.match(input, "com"))
				System.out.println(toyCommands);
			
			else if (Helpers.match(input, "exit"))
				System.out.println("Thank you, come again!\n");
			
			else
				System.out.println(String.format("'%s' is not a valid input. Please try again.", input));
					
		} while (!Helpers.match(input, "exit"));
	}
}
