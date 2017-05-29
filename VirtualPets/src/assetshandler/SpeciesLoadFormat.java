package assetshandler;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import model.Species;

public class SpeciesLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "optimumWeight", "hungerGain", 
			"energyLoss", "happinessLoss", "minToyDamage", "maxToyDamage"};
	private String path;
	private ImageIcon icon;
	
	public SpeciesLoadFormat(String path) {
		this.path = path;
	}

	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

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
