package assetshandler;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;

import model.FoodType;

/**
 * A format for loading FoodTypes from a file.
 * @author Alex Tompkins (ato47)
 */
public class FoodTypeLoadFormat implements LoadFormat {
	private static String[] validAttributes = {"name", "icon", "price", "nutrition", "tastiness", "weight"};
	private String path;
	private ImageIcon icon;
	
	/**
	 * Creates a FoodType loading format with a given path to its images folder.
	 * @param path
	 * The given path to its images folder.
	 */
	public FoodTypeLoadFormat (String path) {
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
			int price = Integer.parseInt(attributes.get("price"));
			int nutrition = Integer.parseInt(attributes.get("nutrition"));
			int tastiness = Integer.parseInt(attributes.get("tastiness"));
			int weight = Integer.parseInt(attributes.get("weight"));
			
			FoodType newFoodType = new FoodType(name, icon, price, nutrition, tastiness, weight);
			customObjects.add(newFoodType);
		}
		catch (NumberFormatException e) {
			System.err.println("Incorrect number format when parsing custom file.");
		}
		catch (NullPointerException e) {
			System.err.println("FoodType attribute value either missing or invalid.");
			e.printStackTrace();
		}
		return customObjects;
	}

}