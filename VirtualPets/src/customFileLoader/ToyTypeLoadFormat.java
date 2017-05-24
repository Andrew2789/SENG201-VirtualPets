package customFileLoader;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import application.ToyType;

public class ToyTypeLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "price", "happinessGain"};

	@Override
	public String[] getValidAttributes() {
		return validAttributes;
	}

	@Override
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes) {
		try {
			// Parse each attribute's given value to check if valid 
			// and pass correct type to constructor.
			ToyType newToyType = new ToyType(
					attributes.get("name").substring(1, attributes.get("name").length() - 1),
					new ImageIcon(SettingsLoader.class.getResource(
							attributes.get("icon").substring(1, attributes.get("icon").length() - 1)
							)), 
					Integer.parseInt(attributes.get("price")), 
					Integer.parseInt(attributes.get("happinessGain")));
			customObjects.add(newToyType);
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