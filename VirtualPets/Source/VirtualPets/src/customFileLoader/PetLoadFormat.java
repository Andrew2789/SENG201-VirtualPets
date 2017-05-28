package customFileLoader;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import application.FoodType;
import application.Pet;
import application.Species;
import application.ToyType;

public class PetLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "species", "healthy", "behaving", "alive", "revivable", 
			"hunger", "energy", "happiness", "weight", "actionPoints", "favouriteToy", "favouriteFood"};
	
	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

	@Override
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes) {
		try {
			// Parse each attribute's given value to check if valid 
			// and pass correct type to constructor.
			
			// Set temporary species to use for matching with valid species.
			Species species = new Species(
					attributes.get("species"),
					new ImageIcon(), 0, 0, 0, 0, 0, 0);
			
			// Set temporary FoodType to use for matching with valid FoodTypes.
			FoodType favouriteFood = new FoodType(
					attributes.get("favouriteFood"),
					new ImageIcon(), 0, 0, 0, 0);
			
			// Set temporary ToyType to use for matching with valid ToyTypes.
			ToyType favouriteToy = new ToyType(
					attributes.get("favouriteToy"),
					new ImageIcon(), 0, 0);
			
			Pet newPet = new Pet(
					attributes.get("name").substring(1, attributes.get("name").length() - 1),
					species, 
					Boolean.parseBoolean(attributes.get("healthy")),
					Boolean.parseBoolean(attributes.get("behaving")),
					Boolean.parseBoolean(attributes.get("alive")),
					Boolean.parseBoolean(attributes.get("revivable")),
					Integer.parseInt(attributes.get("hunger")),
					Integer.parseInt(attributes.get("energy")),
					Integer.parseInt(attributes.get("happiness")),
					Integer.parseInt(attributes.get("weight")),
					Integer.parseInt(attributes.get("actionPoints")),
					favouriteToy,
					favouriteFood);
			
			customObjects.add(newPet);
		}
		catch (NumberFormatException e) {
			System.err.println("Incorrect number format when parsing custom file.");
		}
		catch (NullPointerException e) {
			System.err.println("Attribute value either missing or invalid.");
		}
		return customObjects;
	}

}
