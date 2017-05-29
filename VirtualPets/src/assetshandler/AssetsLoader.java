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

public class AssetsLoader
{
	// Generic Static Methods
	private static String[] readAllLines(InputStream inputStream) throws FileNotFoundException, IOException
	{
		BufferedReader buffer = null;
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
			throw new IOException("Custom asset file not found.");
		}
		catch (IOException e)
		{
			throw new IOException("An error occured while reading the custom asset file.");
		}
		finally {
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
			if (lines[i].length() > 0 && lines[i].startsWith("$"))
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
	public static Object[][] loadCustomAssetsFile(File customAssetFolder) throws FileNotFoundException, IOException {
		InputStream configStream = new FileInputStream(new File(customAssetFolder.getPath() + "/config.txt"));
		
		final String[] lines = readAllLines(configStream);
		String[] selectedBlocks = null;
		Object[] customObjects = null;
		Species[] customSpecies = {};
		FoodType[] customFoodTypes = {};
		ToyType[] customToyTypes = {};
		
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
		
		Object[][] customAssets = {customSpecies, customFoodTypes, customToyTypes};
		return customAssets;
	}
	
	public static Species[] loadCustomSpeciesFile(InputStream inputStream) throws FileNotFoundException, IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new SpeciesLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				Species[].class);
	}
	
	public static ToyType[] loadCustomToyTypesFile(InputStream inputStream) throws FileNotFoundException, IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new ToyTypeLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				ToyType[].class);
	}
	
	public static FoodType[] loadCustomFoodTypesFile(InputStream inputStream) throws FileNotFoundException, IOException {
		final String[] lines = readAllLines(inputStream);
		Object[] customObjects = parseCustomBlocks(lines, new FoodTypeLoadFormat(null));
		return Arrays.copyOf(
				customObjects, 
				customObjects.length, 
				FoodType[].class);
	}
	// End Loading Custom Methods
}
