package assetshandler;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.util.ArrayList;

import model.Species;
import model.FoodType;
import model.ToyType;

public class AssetsSaver {
	public static void writeAssetsToFile(File folder, Species[] species, FoodType[] foodTypes, ToyType[] toyTypes) {
		File file = new File(folder.getPath() + "/config.txt");
		FileWriter fileOut = null;
		BufferedWriter bufferOut = null;
		
		try {
			fileOut = new FileWriter(file);
			bufferOut = new BufferedWriter(fileOut);
			
			ArrayList<String> linesToWrite = new ArrayList<String>();
			linesToWrite.addAll(generateSpeciesBlocks(species));
			linesToWrite.addAll(generateFoodTypeBlocks(foodTypes));
			linesToWrite.addAll(generateToyTypeBlocks(toyTypes));
			
			for (String line : linesToWrite) {
				bufferOut.write(line);
				bufferOut.newLine();
			}
			
			File imagesFolder = new File(folder.getPath() + "/images");
			if (imagesFolder.exists() || imagesFolder.mkdir())
				saveIcons(imagesFolder, species, foodTypes, toyTypes);
			else
				throw new IOException("Asset images folder could not be created.");
		}
		catch (IOException exc) {
			System.err.println("An error occurred when writing the asset files.");
		}
		finally {
			try {
				if (bufferOut != null)
					bufferOut.close();
				if (fileOut != null)
					fileOut.close();
			}
			catch (IOException exc) {
				System.err.println("An error occurred when closing the assets config file.");
			}
		}
	}
	
	private static ArrayList<String> generateSpeciesBlocks(Species[] speciesArray) {
		ArrayList<String> text = new ArrayList<String>();
		
		text.add("@Species");
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
		text.add("@Species");
		
		return text;
	}

	private static ArrayList<String> generateFoodTypeBlocks(FoodType[] foodTypes) {
		ArrayList<String> text = new ArrayList<String>();
		
		text.add("@FoodTypes");
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
		text.add("@FoodTypes");
		
		return text;
	}
	
	private static ArrayList<String> generateToyTypeBlocks(ToyType[] toyTypes) {
		ArrayList<String> text = new ArrayList<String>();
		
		text.add("@ToyTypes");
		for (ToyType toy : toyTypes) {
			text.add(String.format("$%s", toy.getName()));
			text.add(String.format("\t" + "name=\"%s\"", toy.getName()));
			text.add(String.format("\t" + "icon=\"/images/toys/%s.png\"", toy.getName()));
			text.add(String.format("\t" + "price=%d", toy.getPrice()));
			text.add(String.format("\t" + "happinessGain=%d", toy.getHappinessGain()));
			text.add(String.format("/%s", toy.getName()));
		}
		text.add("@ToyTypes");
		
		return text;
	}
	
	private static void saveIcons(File imagesFolder, Species[] speciesArray, FoodType[] foodTypes, ToyType[] toyTypes) throws IOException {
		File speciesFolder = new File(imagesFolder.getPath() + "/species");
		if (speciesFolder.exists() || speciesFolder.mkdir()) {
			for (Species species : speciesArray) {
				File imageFile = new File(speciesFolder.getPath() + String.format("/%s.png", species.getName()));
				writeIcon(imageFile, species.getIcon().getImage());
			}
		}
		else
			throw new IOException("Folder for species icons could not be created.");
		
		File foodFolder = new File(imagesFolder.getPath() + "/food");
		if (foodFolder.exists() || foodFolder.mkdir()) {
			for (FoodType food : foodTypes) {
				File imageFile = new File(foodFolder.getPath() + String.format("/%s.png", food.getName()));
				writeIcon(imageFile, food.getIcon().getImage());
			}
		}
		else
			throw new IOException("Folder for toy icons could not be created.");
		
		File toysFolder = new File(imagesFolder.getPath() + "/toys");
		if (toysFolder.exists() || toysFolder.mkdir()) {
			for (ToyType toy : toyTypes) {
				File imageFile = new File(toysFolder.getPath() + String.format("/%s.png", toy.getName()));
				writeIcon(imageFile, toy.getIcon().getImage());
			}
		}
		else
			throw new IOException("Folder for toy icons could not be created.");
	}
	
	private static void writeIcon(File file, Image icon) throws IOException {
		BufferedImage buffered = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = buffered.createGraphics();
		graphics.drawImage(icon, 0, 0, null);
		graphics.dispose();
		
		ImageIO.write(buffered, "png", file);
	}
}
