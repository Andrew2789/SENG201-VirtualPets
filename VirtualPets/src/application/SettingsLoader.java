package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SettingsLoader
{
	private static String[] readAllLines(String file_path)
	{
		BufferedReader buffer;
		ArrayList<String> lines = new ArrayList<String>();

		try
		{
			buffer = new BufferedReader(new FileReader(file_path));
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

	public static Object[] loadCustomFile(String file_path, LoadFormat loadFormat)
	{
		ArrayList<Object> customObjects = new ArrayList<Object>();
		final String[] lines = readAllLines(file_path);

		int i = 0;
		while (i < lines.length)
		{
			if (lines[i].substring(0, 1).equals("$"))
			{
				// Record the index and label at the start of the block.
				int block_start_index = i + 1;
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
				List<String> block = Arrays.asList(lines).subList(block_start_index, i);
				
				// Then pass that list reference to the parseAttributesBlock method which will return
				// the attributes defined by that block.
				HashMap<String, String> attributes = parseAttributesBlock(loadFormat.getValidAttributes(), block);
				
				// At end of block, try to create new object with given attributes & values.
				customObjects = loadFormat.addCustomObject(customObjects, attributes);
			}
			// Increment line counter after processing any block.
			i++;
		}
		// Processing of all blocks done, return custom species defined in file as Species array.
		return customObjects.toArray(new Object[customObjects.size()]);
	}
	
	// DEBUG MAIN
	
	public static void main(String[] args) {
		Object[] custom_object_array = loadCustomFile("sample_species.txt", new SpeciesLoadFormat());
		
		Species[] custom_species_array = Arrays.copyOf(
				custom_object_array, 
				custom_object_array.length, 
				Species[].class);
		for (Species species : custom_species_array) {
			System.out.println(species.getName());
			System.out.println(species.getOptimumWeight());
			System.out.println(species.getEnergyLoss());
			System.out.println(species.getHungerGain());
			System.out.println(species.getHappinessLoss());
			System.out.println(species.getMinToyDamage());
			System.out.println(species.getMaxToyDamage());
			System.out.println();
		}
	}
	
}
