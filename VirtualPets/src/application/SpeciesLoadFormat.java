package application;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class SpeciesLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "optimumWeight", "hungerGain", 
			"energyLoss", "happinessLoss", "minToyDamage", "maxToyDamage"};

	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

	@Override
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes) {
		try {
			// Parse each attribute's given value to check if valid 
			// and pass correct type to constructor.
			Species newSpecies = new Species(
					attributes.get("name").substring(1, attributes.get("name").length() - 1),
					new ImageIcon(SettingsLoader.class.getResource(
							attributes.get("icon").substring(1, attributes.get("icon").length() - 1)
							)),
					Integer.parseInt(attributes.get("optimumWeight")), 
					Integer.parseInt(attributes.get("hungerGain")),
					Integer.parseInt(attributes.get("energyLoss")), 
					Integer.parseInt(attributes.get("happinessLoss")),
					Integer.parseInt(attributes.get("minToyDamage")),
					Integer.parseInt(attributes.get("maxToyDamage")));
			customObjects.add(newSpecies);
		}
		catch (NumberFormatException e) {
			System.err.println("Incorrect number format when parsing custom species file.");
		}
		catch (NullPointerException e) {
			System.err.println("Could not find corresponding image for custom species.");
		}
		return customObjects;
	}

}
