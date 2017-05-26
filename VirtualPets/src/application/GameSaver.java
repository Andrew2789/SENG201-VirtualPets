package application;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameSaver {
	public static void saveGame(File saveFile, Species[] species, FoodType[] foodTypes, ToyType[] toyTypes, Player[] players, 
			int numberOfDays, int incomePerTurn, int currentday, int currentPlayerIndex, int[] previousScores) {
		BufferedWriter writer = null;
		try {
			FileWriter fw = new FileWriter(saveFile);
			writer = new BufferedWriter(fw);
			
			ArrayList<String> lines = new ArrayList<String>();
			lines = generateSpeciesBlock(lines, species);
			lines = generateFoodTypesBlock(lines, foodTypes);
			lines = generateToyTypesBlock(lines, toyTypes);
			lines = generatePlayersBlock(lines, players);
			lines = generateMiscBlock(lines, numberOfDays, incomePerTurn, currentday, currentPlayerIndex, previousScores);
			
			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
			
			writer.close();
		}
		catch (IOException e) {
			System.err.println("Error when saving game.");
		}
	}
	
	private static ArrayList<String> generateSpeciesBlock(ArrayList<String> text, Species[] speciesArray) {
		text.add("$Species");
		for (Species species : speciesArray) {
			text.add(String.format("$%s", species.getName()));
			text.add(String.format("\t" + "name=\"%s\"", species.getName()));
			text.add(String.format("\t" + "icon=\"/images/species/%s.png\"", species.getName()));
			text.add(String.format("\t" + "optimumWeight=%d", species.getOptimumWeight()));
			text.add(String.format("\t" + "hungerGain=%d", species.getHungerGain()));
			text.add(String.format("\t" + "energyLoss=%d", species.getEnergyLoss()));
			text.add(String.format("\t" + "happinessLoss=%d", species.getHappinessLoss()));
			text.add(String.format("\t" + "minToyDamage=%d", species.getMinToyDamage()));
			text.add(String.format("\t" + "maxToyDamage=%d", species.getMaxToyDamage()));
			text.add(String.format("/%s", species.getName()));
		}
		text.add("/Species");
		return text;
	}
	
	private static ArrayList<String> generateFoodTypesBlock(ArrayList<String> text, FoodType[] foodTypes) {
		text.add("$FoodTypes");
		for (FoodType food : foodTypes) {
			text.add(String.format("$%s", food.getName()));
			text.add(String.format("\t" + "name=\"%s\"", food.getName()));
			text.add(String.format("\t" + "icon=\"/images/food/%s.png\"", food.getName()));
			text.add(String.format("\t" + "price=%d", food.getPrice()));
			text.add(String.format("\t" + "nutrition=%d", food.getNutrition()));
			text.add(String.format("\t" + "tastiness=%d", food.getTastiness()));
			text.add(String.format("\t" + "weight=%d", food.getWeight()));
			text.add(String.format("/%s", food.getName()));
		}
		text.add("/FoodTypes");
		return text;
	}
	
	private static ArrayList<String> generateToyTypesBlock(ArrayList<String> text, ToyType[] toyTypes) {
		text.add("$ToyTypes");
		for (ToyType toy : toyTypes) {
			text.add(String.format("$%s", toy.getName()));
			text.add(String.format("\t" + "name=\"%s\"", toy.getName()));
			text.add(String.format("\t" + "icon=\"/images/toys/%s.png\"", toy.getName()));
			text.add(String.format("\t" + "nutrition=%d", toy.getPrice()));
			text.add(String.format("\t" + "tastiness=%d", toy.getHappinessGain()));
			text.add(String.format("/%s", toy.getName()));
		}
		text.add("/ToyTypes");
		return text;
	}
	
	private static ArrayList<String> generatePlayersBlock(ArrayList<String> text, Player[] players) {
		text.add("$Players");
		for (Player player : players) {
			text.add(String.format("$%s", player.getName()));
			
			text.add("$Pets");
			for (Pet pet : player.getPets()) {
				text.add(String.format("$%s", pet.getName()));
				text.add(String.format("\t" + "name=\"%s\"", pet.getName()));
				text.add(String.format("\t" + "species=%s", pet.getSpecies().getName()));
				text.add(String.format("\t" + "healthy=%s", pet.isHealthy()));
				text.add(String.format("\t" + "behaving=%s", pet.isBehaving()));
				text.add(String.format("\t" + "alive=%s", pet.isAlive()));
				text.add(String.format("\t" + "revivable=%s", pet.isRevivable()));
				text.add(String.format("\t" + "hunger=%d", pet.getHunger()));
				text.add(String.format("\t" + "energy=%d", pet.getEnergy()));
				text.add(String.format("\t" + "happiness=%d", pet.getHappiness()));
				text.add(String.format("\t" + "weight=%d", pet.getWeight()));
				text.add(String.format("\t" + "actionPoints=%d", pet.getActionPoints()));
				text.add(String.format("\t" + "favouriteToy=%s", pet.getFavouriteToy().getName()));
				text.add(String.format("\t" + "favouriteFood=%s", pet.getFavouriteFood().getName()));
				text.add(String.format("/%s", pet.getName()));
			}
			text.add("/Pets");
			
			text.add("$Food");
			for (FoodType food : player.getFood().keySet()) {
				text.add(String.format("\t" + "%s=%d", food.getName(), player.getFood().get(food)));
			}
			text.add("/Food");
			
			text.add("$Toys");
			for (Toy toy : player.getToys()) {
				text.add(String.format("$%s", toy.getToyType().getName()));
				text.add(String.format("\t" + "toyType=%s", toy.getToyType().getName()));
				text.add(String.format("\t" + "durability=%d", toy.getDurability()));
				text.add(String.format("/%s", toy.getToyType().getName()));
			}
			text.add("/Toys");
			
			text.add("$PlayerMisc");
			text.add(String.format("\t" + "name=\"%s\"", player.getName()));
			text.add(String.format("\t" + "money=%d", player.getMoney()));
			text.add(String.format("\t" + "score=%d", player.getScore()));
			text.add("/PlayerMisc");
			
			text.add(String.format("/%s", player.getName()));
		}
		text.add("/Players");
		return text;
	}
	
	private static ArrayList<String> generateMiscBlock(ArrayList<String> text, int numberOfDays, 
			int incomePerTurn, int currentDay, int currentPlayerIndex, int[] previousScores) {
		text.add("$Misc");
		text.add(String.format("\t" + "numberOfDays=%d", numberOfDays));
		text.add(String.format("\t" + "incomePerTurn=%d", incomePerTurn));
		text.add(String.format("\t" + "currentday=%d", currentDay));
		text.add(String.format("\t" + "currentPlayerIndex=%d", currentPlayerIndex));
		
		text.add("$PreviousScores");
		for (int i=0; i<previousScores.length; i++)
			text.add(String.format("\t" + "%d=%d", i, previousScores[i]));
		text.add("/PreviousScores");
		
		text.add("/Misc");
		return text;
	}
}
