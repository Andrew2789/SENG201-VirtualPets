package customFileLoader;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import application.Toy;
import application.ToyType;

public class ToyLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"toyType", "durability"};

	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

	@Override
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes) {
		try {
			// Parse each attribute's given value to check if valid 
			// and pass correct type to constructor.
			
			// Set temporary ToyType to use for matching with valid ToyTypes.
			ToyType toyType = new ToyType(
					attributes.get("toyType"),
					new ImageIcon(), 0, 0);
			
			Toy newToy = new Toy(toyType, Integer.parseInt(attributes.get("durability")));
			
			customObjects.add(newToy);
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
