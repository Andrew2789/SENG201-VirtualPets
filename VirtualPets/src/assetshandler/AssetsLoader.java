package assetshandler;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.FoodType;
import model.Species;
import model.ToyType;

/**
 * A class with purely static methods for the purposes of loading saved assets (Species, FoodTypes and ToyTypes) from a file.
 * @author Alex Tompkins (ato47)
 */
public class AssetsLoader {
	
	// Generic Static Methods
	
	/**
	 * Reads all the lines of a given InputStream (generally made from a file) into a String array.
	 * @param inputStream
	 * The InputStream that provides the content of the file.
	 * @return
	 * An array made up of String items where each String is a line of the file.
	 * @throws IOException
	 * Throws an IOException if an error occurs while reading/closing the InputStream.
	 */
	private static String[] readAllLines(InputStream inputStream) throws IOException {
		BufferedReader buffer = null;
		ArrayList<String> lines = new ArrayList<String>();

		// Firstly, try to open a BufferedReader on the InputStream, allowing us to read line-by-line
		// Then continue to read the file line-by-line into the ArrayList, trimming whitespace
		try {
			buffer = new BufferedReader(new InputStreamReader(inputStream));
			String line = buffer.readLine();
			while (line != null) {
				lines.add(line.trim());
				line = buffer.readLine();
			}
		}
		catch (IOException e) {
			throw new IOException("An error occured while reading the custom asset file.");
		}
		finally {
			// Whether an exception was raised during reading or not, now try to close both the stream and the reader.
			try {
				if (inputStream != null)
					inputStream.close();
				if (buffer != null)
					buffer.close();
			}
			catch (IOException exc) {
				System.err.println("Error while attempting to close custom asset file.");
			}
		}
		// Convert the ArrayList into an Array, and return
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Parses an 'attributes block', which is essentially just a list of properties belonging to a particular object.
	 * Each valid line of the block is of the form: "attribute=value"
	 * @param validAttributes
	 * This defines the valid properties of the object being parsed.
	 * @param block
	 * The lines of the file corresponding to this block, as a String array.
	 * @return
	 * A HashMap where the key is the *valid* attribute name, and its value is as defined in the block.
	 */
	private static HashMap<String, String> parseAttributesBlock(String[] validAttributes, List<String> block) {
		// Initialise new attributes HashMap for this object
		HashMap<String, String> attributes = new HashMap<String, String>();

		for (String line : block)
			// Check if line is proper attribute assignment
			if (line.contains("=")) {
				String[] lineParts = line.split("=");
				// Check through valid attributes to see if valid assignment line
				for (String validAttribute : validAttributes)
					if (lineParts[0].equals(validAttribute))
						attributes.put(lineParts[0], lineParts[1]);
			}

		return attributes;
	}

	/**
	 * Parses a number of custom 'blocks'. Each 'block' is a series of lines defining a single object.
	 * Blocks begin with a line "$label" and end with a line "/label", and in between are attribute defines.
	 * @param lines
	 * The lines of the file corresponding to the custom blocks to be parsed.
	 * @param loadFormat
	 * The interface with which to create an object out of each block.
	 * @return
	 * Returns an array of objects defined by the custom blocks given.
	 */
	private static Object[] parseCustomBlocks(String[] lines, LoadFormat loadFormat) {
		ArrayList<Object> customObjects = new ArrayList<Object>();

		int i = 0;
		while (i < lines.length) {
			if (lines[i].length() > 0 && lines[i].startsWith("$")) {
				// Record the index and label at the start of the block.
				int blockStartIndex = i + 1;
				String label = lines[i].substring(1);
				
				// Check through lines until end of block found.
				try {
					while (!lines[i].equals("/" + label))
						i++;
				}
				catch (ArrayIndexOutOfBoundsException e) {
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
	
	// Custom Loading Methods
	/**
	 * Loads a file wherein are defined a number of different types of different objects.
	 * Each type, for example FoodTypes, are enclosed by markers such as "@FoodTypes" on either side.
	 * @param customAssetFolder
	 * File object representing the folder in which the assets are contained. Contains a config.txt file
	 * and an images folder, containing subdirectories for each type to store their icons.
	 * @return
	 * Returns an Object array with 3 arrays within it: a Species array, a FoodType array and a ToyType array.
	 * @throws FileNotFoundException
	 * Will throw FileNotFoundException if the File given is invalid (e.g. its parent directory does not exist).
	 * @throws IOException
	 * Will throw IOException if an error occurs during reading of the File given.
	 */
	public static Object[][] loadCustomAssetsFile(File customAssetFolder) throws FileNotFoundException, IOException {
		// Create an InputStream to read the config file.
		InputStream configStream = new FileInputStream(new File(customAssetFolder.getPath() + "/config.txt"));
		final String[] lines = readAllLines(configStream);
		
		// Initialise all local variables to null values.
		String[] selectedBlocks = null;
		Object[] customObjects = null;
		Species[] customSpecies = {};
		FoodType[] customFoodTypes = {};
		ToyType[] customToyTypes = {};
		
		// Search through lines of the config file to find markers of each type
		HashMap<String, int[]> markers = new HashMap<String, int[]>();
		int lineNum = 0;
		while (lineNum < lines.length) {
			if (lines[lineNum].length() > 0 && lines[lineNum].startsWith("@")) {
				String key = lines[lineNum].substring(1);
				if (!markers.containsKey(key)) {
					markers.put(key, new int[]{lineNum+1, -1});
				}
				else if (markers.get(key)[1] == -1) {
					markers.get(key)[1] = lineNum;
				}
			}
			lineNum++;
		}
		
		// If file contains Species types, parse those blocks between the markers "@Species"
		if (markers.containsKey("Species") && 
			 markers.get("Species")[0] != -1 &&
			 markers.get("Species")[1] != -1) {
			selectedBlocks = Arrays.copyOfRange(lines, markers.get("Species")[0], markers.get("Species")[1]);
			customObjects = parseCustomBlocks(selectedBlocks, new SpeciesLoadFormat(customAssetFolder.getPath()));
			customSpecies = Arrays.copyOf(
					customObjects, 
					customObjects.length, 
					Species[].class);
		}
		// If file contains FoodTypes, parse those blocks between the markers "@FoodTypes"
		if (markers.containsKey("FoodTypes") && 
			 markers.get("FoodTypes")[0] != -1 &&
			 markers.get("FoodTypes")[1] != -1) {
			selectedBlocks = Arrays.copyOfRange(lines, markers.get("FoodTypes")[0], markers.get("FoodTypes")[1]);
			customObjects = parseCustomBlocks(selectedBlocks, new FoodTypeLoadFormat(customAssetFolder.getPath()));
			customFoodTypes = Arrays.copyOf(
					customObjects, 
					customObjects.length, 
					FoodType[].class);
		}
		// If file contains ToyTypes, parse those blocks between the markers "@ToyTypes"
		if (markers.containsKey("ToyTypes") && 
			 markers.get("ToyTypes")[0] != -1 &&
			 markers.get("ToyTypes")[1] != -1) {
			selectedBlocks = Arrays.copyOfRange(lines, markers.get("ToyTypes")[0], markers.get("ToyTypes")[1]);
			customObjects = parseCustomBlocks(selectedBlocks, new ToyTypeLoadFormat(customAssetFolder.getPath()));
			customToyTypes = Arrays.copyOf(
					customObjects, 
					customObjects.length, 
					ToyType[].class);
		}
		
		// Finally, return all the custom assets defined in the array of their respective type.
		Object[][] customAssets = {customSpecies, customFoodTypes, customToyTypes};
		return customAssets;
	}
	
	/**
	 * Loads all Species assets from a custom file containing only Species blocks.
	 * @param inputStream
	 * The InputStream of the custom species file.
	 * @return
	 * Returns an array of species.
	 * @throws IOException
	 * Throws IOException if an error occurs while reading the InputStream, or when closing it.
	 */
	public static Species[] loadCustomSpeciesFile(InputStream inputStream) throws IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new SpeciesLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				Species[].class);
	}
	
	/**
	 * Loads all ToyType assets from a custom file containing only ToyType blocks.
	 * @param inputStream
	 * The InputStream of the custom species file.
	 * @return
	 * Returns an array of ToyTypes.
	 * @throws IOException
	 * Throws IOException if an error occurs while reading the InputStream, or when closing it.
	 */
	public static ToyType[] loadCustomToyTypesFile(InputStream inputStream) throws IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new ToyTypeLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				ToyType[].class);
	}
	
	/**
	 * Loads all FoodType assets from a custom file containing only FoodType blocks.
	 * @param inputStream
	 * The InputStream of the custom species file.
	 * @return
	 * Returns an array of FoodTypes.
	 * @throws IOException
	 * Throws IOException if an error occurs while reading the InputStream, or when closing it.
	 */
	public static FoodType[] loadCustomFoodTypesFile(InputStream inputStream) throws IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new FoodTypeLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				FoodType[].class);
	}
	// End Loading Custom Methods
}
