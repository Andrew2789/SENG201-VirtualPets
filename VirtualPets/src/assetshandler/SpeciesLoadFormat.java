package assetshandler;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import model.Species;

/**
 * A format for loading Species from a file.
 * @author Alex Tompkins (ato47)
 */
public class SpeciesLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "optimumWeight", "hungerGain", 
			"energyLoss", "happinessLoss", "minToyDamage", "maxToyDamage"};
	private String path;
	private ImageIcon icon;
	
	/**
	 * Creates a Species loading format with a given path to its images folder.
	 * @param path
	 * The given path to its images folder.
	 */
	public SpeciesLoadFormat(String path) {
		this.path = path;
	}

	/**
	 * Refer to interface LoadFormat
	 */
	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

	/**
	 * Refer to interface LoadFormat
	 */
	@Override
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes) {
		try {
			// Parse each attribute's given value to check if valid 
			// and pass correct type to constructor.
			
			String name = attributes.get("name").substring(1, attributes.get("name").length() - 1);
			if (path == null)
				icon = new ImageIcon(this.getClass().getResource(attributes.get("icon").substring(1, attributes.get("icon").length() - 1)));
			else
				icon = new ImageIcon((path + attributes.get("icon").substring(1, attributes.get("icon").length() - 1)).replaceAll("\\\\", "/"));
			int optimumWeight = Integer.parseInt(attributes.get("optimumWeight"));
			int hungerGain = Integer.parseInt(attributes.get("hungerGain"));
			int energyLoss = Integer.parseInt(attributes.get("energyLoss"));
			int happinessLoss = Integer.parseInt(attributes.get("happinessLoss"));
			int minToyDamage = Integer.parseInt(attributes.get("minToyDamage"));
			int maxToyDamage = Integer.parseInt(attributes.get("maxToyDamage"));
			
			Species newSpecies = new Species(name, icon, optimumWeight, hungerGain, energyLoss, happinessLoss, minToyDamage, maxToyDamage);
			customObjects.add(newSpecies);
		}
		catch (NumberFormatException exc) {
			System.err.println("Incorrect number format when parsing custom file.");
		}
		catch (NullPointerException exc) {
			System.err.println("Species attribute value either missing or invalid.");
		}
		return customObjects;
	}

}
