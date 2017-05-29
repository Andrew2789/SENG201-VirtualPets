package assetshandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A format for loading assets. Defines a list of final attributes (strings) for a type
 * and must implement a way to add a custom object of that type to a given ArrayList.
 * @author Alex Tompkins (ato47)
 */
public interface LoadFormat {
	/**
	 * Simply returns the array of valid attributes for a given type.
	 * @return
	 * The array of valid attributes for this type.
	 */
	public String[] getValidAttributes();
	
	/**
	 * A method for adding a custom object of this type to the ArrayList given. Must generate
	 * the custom object itself from attribute definitions given.
	 * @param customObjects
	 * The ArrayList of previous custom objects given to this method.
	 * @param attributes
	 * The attribute definitions given (all should be valid).
	 * @return
	 * The same ArrayList but with an extra custom object added on.
	 */
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes);
}
