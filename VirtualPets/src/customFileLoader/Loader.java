package customFileLoader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import application.Game;
import application.Pet;
import application.Player;
import application.FoodType;
import application.Species;
import application.Toy;
import application.ToyType;

public class Loader
{
	// Generic Static Methods
	private static String[] readAllLines(InputStream inputStream)
	{
		BufferedReader buffer;
		ArrayList<String> lines = new ArrayList<String>();

		try
		{
			buffer = new BufferedReader(new InputStreamReader(inputStream));
			String line = buffer.readLine();
			while (line != null)
			{
				lines.add(line.trim());
				line = buffer.readLine();
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File not found.");
		}
		catch (IOException e)
		{
			System.err.println("Error reading the file.");
		}
		return lines.toArray(new String[lines.size()]);
	}

	private static HashMap<String, String> parseAttributesBlock(String[] validAttributes, List<String> block) 
	{
		// Initialise new attributes HashMap for this object
		HashMap<String, String> attributes = new HashMap<String, String>();

		for (String line : block)
			// Check if line is attribute assignment
			if (line.contains("="))
			{
				String[] lineParts = line.split("=");
				// Check through valid attributes to see if valid assignment line
				for (String validAttribute : validAttributes)
					if (lineParts[0].equals(validAttribute))
						attributes.put(lineParts[0], lineParts[1]);
			}

		return attributes;
	}

	private static Object[] parseCustomBlocks(String[] lines, LoadFormat loadFormat)
	{
		ArrayList<Object> customObjects = new ArrayList<Object>();

		int i = 0;
		while (i < lines.length)
		{
			if (lines[i].substring(0, 1).equals("$"))
			{
				// Record the index and label at the start of the block.
				int blockStartIndex = i + 1;
				String label = lines[i].substring(1);
				
				// Check through lines until end of block found.
				try
				{
					while (!lines[i].equals("/" + label))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e)
				{
					System.err.println(String.format("Could not find end of '%s' block while parsing file.", label));
				}

				// Creates a list reference to the lines which comprise the block found.
				List<String> block = Arrays.asList(lines).subList(blockStartIndex, i);
				
				// Then pass that list reference to the parseAttributesBlock method which will return
				// the attributes defined by that block.
				HashMap<String, String> attributes = parseAttributesBlock(loadFormat.getValidAttributes(), block);
				
				// At end of block, try to create new object with given attributes & values.
				customObjects = loadFormat.addCustomObject(customObjects, attributes);
			}
			// Increment line counter after processing any block.
			i++;
		}
		// Processing of all blocks done, return custom objects defined in file as Object array.
		return customObjects.toArray(new Object[customObjects.size()]);
	}
	
	// End Generic Static Methods
	
	// Loading Custom Methods
	public static Species[] loadCustomSpeciesFile(InputStream inputStream) {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new SpeciesLoadFormat());
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				Species[].class);
	}
	
	public static ToyType[] loadCustomToyTypesFile(InputStream inputStream) {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new ToyTypeLoadFormat());
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				ToyType[].class);
	}
	
	public static FoodType[] loadCustomFoodTypesFile(InputStream inputStream) {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new FoodTypeLoadFormat());
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				FoodType[].class);
	}
	
	// End Loading Custom Methods
	
	// Loading Save Files
	public static Player parsePlayer(String[] lines, Species[] validSpecies, FoodType[] validFoodTypes, ToyType[] validToyTypes) {
		String savedName = new String();
		Pet[] savedPets = new Pet[0];
		HashMap<FoodType, Integer> savedFood = new HashMap<FoodType, Integer>();
		ArrayList<Toy> matchingToys = new ArrayList<Toy>();
		int savedMoney = 0;
		int savedScore = 0;
		
		int i = 0;
		while (i < lines.length) {
			if (lines[i].length() >= 5 && lines[i].substring(0, 5).equals("$Pets")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Pets"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Pets' block while parsing file."));
				}

				// Creates a new array comprising the block found.
				String[] block = Arrays.copyOfRange(lines, blockStartIndex, i);
				Object[] savedObjects = parseCustomBlocks(block, new PetLoadFormat());
				savedPets = Arrays.copyOf(savedObjects,
						savedObjects.length,
						Pet[].class);
				
				// Change temporary species, favouriteToy and favouriteFood to matching valid saved versions if they exist, otherwise remove Pet
				ArrayList<Pet> matchingPets = new ArrayList<Pet>();
				for (Pet pet : savedPets) {
					boolean matchedSpecies = false;
					for (Species species : validSpecies) {
						if (pet.getSpecies().getName().equals(species.getName())) {
							pet.setSpecies(species);
							matchedSpecies = true;
						}
					}
					boolean matchedFavouriteToy = false;
					for (ToyType validToy : validToyTypes) {
						if (pet.getFavouriteToy().getName().equals(validToy.getName())) {
							pet.setFavouriteToy(validToy);
							matchedFavouriteToy = true;
						}
					}
					boolean matchedFavouriteFood = false;
					for (FoodType validFood : validFoodTypes) {
						if (pet.getFavouriteFood().getName().equals(validFood.getName())) {
							pet.setFavouriteFood(validFood);
							matchedFavouriteFood = true;
						}
					}
					if (matchedSpecies && matchedFavouriteToy && matchedFavouriteFood) {
						matchingPets.add(pet);
					}
				}
				savedPets = matchingPets.toArray(new Pet[matchingPets.size()]);
			}
			if (lines[i].length() >= 5 && lines[i].substring(0, 5).equals("$Food")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Food"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Food' block while parsing file."));
				}
				
				ArrayList<String> validFoodNamesList = new ArrayList<String>();
				for (FoodType food : validFoodTypes)
					validFoodNamesList.add(food.getName());
				String[] validFoodNames = validFoodNamesList.toArray(new String[validFoodNamesList.size()]);
				
				// Creates a list reference to the lines which comprise the block found.
				List<String> block = Arrays.asList(lines).subList(blockStartIndex, i);
				HashMap<String, String> foodAttributes = parseAttributesBlock(validFoodNames, block);
				
				savedFood = new HashMap<FoodType, Integer>();
				for (String foodName : foodAttributes.keySet()) {
					for (FoodType validFood : validFoodTypes)
						if (foodName.equals(validFood.getName()))
							savedFood.put(validFood, Integer.parseInt(foodAttributes.get(foodName)));
				
				}
			}
			if (lines[i].length() >= 5 && lines[i].substring(0, 5).equals("$Toys")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Toys"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Toys' block while parsing file."));
				}
				
				// Creates a new array comprising the block found.
				String[] block = Arrays.copyOfRange(lines, blockStartIndex, i);
				Object[] savedObjects = parseCustomBlocks(block, new ToyLoadFormat());
				Toy[] savedToys = Arrays.copyOf(savedObjects,
						savedObjects.length,
						Toy[].class);
				
				// Change temporary toyType to matching valid saved versions if it exists, otherwise remove Toy
				matchingToys = new ArrayList<Toy>();
				for (Toy toy : savedToys) {
					for (ToyType validToyType : validToyTypes)
						if (toy.getToyType().getName().equals(validToyType.getName())) {
							toy.setToyType(validToyType);
							matchingToys.add(toy);
						}
				}
			}
			if (lines[i].length() >= 11 && lines[i].substring(0, 11).equals("PlayerMisc")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/PlayerMisc"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'PlayerMisc' block while parsing file."));
				}
				
				String[] validPlayerMiscAttributes = {"name", "money", "score"};
				// Creates a list reference to the lines which comprise the block found.
				List<String> block = Arrays.asList(lines).subList(blockStartIndex, i);
				HashMap<String, String> playerMiscAttributes = parseAttributesBlock(validPlayerMiscAttributes, block);
				
				savedName = playerMiscAttributes.get("name").substring(1, playerMiscAttributes.get("name").length()-1);
				savedMoney = Integer.parseInt(playerMiscAttributes.get("money"));
				savedScore = Integer.parseInt(playerMiscAttributes.get("score"));
			}
			// Increment line counter after processing any block
			i++;
		}
		
		// Creating a new player object out of the data parsed
		Player savedPlayer = new Player(
				savedName,
				savedPets,
				savedFood,
				matchingToys,
				savedMoney,
				savedScore
				);
		return savedPlayer;
	}
	
	public static void loadSavedGameFile(InputStream inputStream, Game game) {
		final String[] lines = readAllLines(inputStream);
		Species[] savedSpecies = new Species[0];
		FoodType[] savedFoodTypes = new FoodType[0];
		ToyType[] savedToyTypes = new ToyType[0];
		ArrayList<Player> savedPlayersList = new ArrayList<Player>();
		int savedNumberOfDays = 0;
		int savedIncomePerTurn = 0;
		int savedCurrentDay = 0;
		int savedCurrentPlayerIndex = 0;
		int[] savedPreviousScores = {0, 0};
		
		int i = 0;
		while (i < lines.length) {
			if (lines[i].length() >= 8 && lines[i].substring(0, 8).equals("$Species")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Species"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Species' block while parsing file."));
				}

				// Creates a new array comprising the block found.
				String[] block = Arrays.copyOfRange(lines, blockStartIndex, i);
				Object[] savedObjects = parseCustomBlocks(block, new SpeciesLoadFormat());
				savedSpecies = Arrays.copyOf(savedObjects,
						savedObjects.length,
						Species[].class);
			}
			if (lines[i].length() >= 10 && lines[i].substring(0, 10).equals("$FoodTypes")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/FoodTypes"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'FoodTypes' block while parsing file."));
				}

				// Creates a new array comprising the block found.
				String[] block = Arrays.copyOfRange(lines, blockStartIndex, i);
				Object[] savedObjects = parseCustomBlocks(block, new FoodTypeLoadFormat());
				savedFoodTypes = Arrays.copyOf(savedObjects,
						savedObjects.length,
						FoodType[].class);
			}
			if (lines[i].length() >= 9 && lines[i].substring(0, 9).equals("$ToyTypes")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/ToyTypes"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'ToyTypes' block while parsing file."));
				}

				// Creates a new array comprising the block found.
				String[] block = Arrays.copyOfRange(lines, blockStartIndex, i);
				Object[] savedObjects = parseCustomBlocks(block, new ToyTypeLoadFormat());
				savedToyTypes = Arrays.copyOf(savedObjects,
						savedObjects.length,
						ToyType[].class);
			}
			if (lines[i].length() >= 8 && lines[i].substring(0, 8).equals("$Players")) {
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Players")) {
						if (lines[i].substring(0, 1).equals("$")) {
							// Record the index and label of the start of the player block.
							int playerBlockStartIndex = i + 1;
							String playerLabel = lines[i].substring(1);
							
							// Check through lines until end of player block found.
							try
							{
								while (!lines[i].equals("/" + playerLabel))
									i++;
							}
							catch (ArrayIndexOutOfBoundsException e)
							{
								System.err.println(String.format("Could not find end of '%s' block while parsing file.", playerLabel));
							}
							
							// Creates a new array comprising the player block found.
							String[] block = Arrays.copyOfRange(lines, playerBlockStartIndex, i);
							Player newSavedPlayer = parsePlayer(block, savedSpecies, savedFoodTypes, savedToyTypes);
							savedPlayersList.add(newSavedPlayer);
						}
						i++;
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Players' block while parsing file."));
				}
			}
			if (lines[i].length() >= 11 && lines[i].substring(0, 11).equals("Misc")) {
				// Record the index at the start of the block.
				int blockStartIndex = i + 1;
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/Misc"))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(String.format("Could not find end of 'Misc' block while parsing file."));
				}
				
				String[] validMiscAttributes = {"numberOfDays", "incomePerTurn", "currentDay", "currentPlayerIndex"};
				// Creates a list reference to the lines which comprise the block found.
				List<String> block = Arrays.asList(lines).subList(blockStartIndex, i);
				HashMap<String, String> miscAttributes = parseAttributesBlock(validMiscAttributes, block);
				
				savedNumberOfDays = Integer.parseInt(miscAttributes.get("numberOfDays"));
				savedIncomePerTurn = Integer.parseInt(miscAttributes.get("incomePerTurn"));
				savedCurrentDay = Integer.parseInt(miscAttributes.get("currentDay"));
				savedCurrentPlayerIndex = Integer.parseInt(miscAttributes.get("currentPlayerIndex"));
			}
				
			// Increment line counter after processing any block.
			i++;
		}
		
		// Now resume Game based on saveData
		Player[] savedPlayers = savedPlayersList.toArray(new Player[savedPlayersList.size()]);
		game.resume(savedFoodTypes, savedToyTypes, savedPlayers, savedCurrentDay, savedCurrentPlayerIndex, savedNumberOfDays, savedIncomePerTurn, savedPreviousScores);
	}
	
	// End Loading Save Files
}
