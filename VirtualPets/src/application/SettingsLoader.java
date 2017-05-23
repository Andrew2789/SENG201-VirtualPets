package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;

public class SettingsLoader
{
	private String[] readFile(String file_path)
	{
		BufferedReader buffer;
		ArrayList<String> lines = new ArrayList<String>();

		try
		{
			buffer = new BufferedReader(new FileReader(file_path));
			String line = buffer.readLine();
			while (line != null)
			{
				lines.add(line);
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

	private HashMap<String, String> parseAttributesBlock(String[] validAttributes, List<String> block) 
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
					if (lineParts[0].toLowerCase() == validAttribute.toLowerCase())
						attributes.put(lineParts[0], lineParts[1]);
			}

		return attributes;
	}

	public Species[] loadSpeciesFile(String file_path)
	{
		final String[] validSpeciesAttributes = { "name", "icon", "optimumWeight", "hungerGain", "energyLoss",
				"happinessLoss", "minToyDamage", "maxToyDamage" };
		ArrayList<Species> customSpecies = new ArrayList<Species>();
		final String[] lines = readFile(file_path);

		int i = 0;
		while (i < lines.length)
		{
			if (lines[i].substring(0, 1) == "$")
			{
				// Record the index and label at the start of the block.
				int block_start_index = i + 1;
				String label = lines[i].substring(1);

				// Check through lines until end of block found.
				while (lines[i] != "/" + label)
					i++;

				// Creates a list reference to the lines which comprise the block found.
				List<String> block = Arrays.asList(lines).subList(block_start_index, i);
				
				// Then pass that list reference to the parseAttributesBlock method which will return
				// the attributes defined by that block.
				HashMap<String, String> attributes = parseAttributesBlock(validSpeciesAttributes, block);

				// At end of block, try to create new object with given attributes & values.
				try
				{
					// Parse each attribute's given value to check if valid and return correct type to
					// constructor.
					Species newSpecies = new Species(
							attributes.get("name").substring(1, attributes.get("name").length() - 1),
							new ImageIcon(this.getClass().getResource(
									attributes.get("icon").substring(1, attributes.get("icon").length() - 1)
									)),
							Integer.parseInt(attributes.get("optimumWeight")), 
							Integer.parseInt(attributes.get("hungerGain")),
							Integer.parseInt(attributes.get("energyLoss")), 
							Integer.parseInt(attributes.get("happinessLoss")),
							Integer.parseInt(attributes.get("minToyDamage")),
							Integer.parseInt(attributes.get("maxToyDamage")));
					customSpecies.add(newSpecies);
				}
				catch (NumberFormatException e)
				{

				}
				catch (NullPointerException e)
				{

				}
			}
			// Increment line counter after processing any block.
			i++;
		}
		// Processing of all blocks done, return custom species defined in file as Species array.
		return customSpecies.toArray(new Species[customSpecies.size()]);
	}
}
