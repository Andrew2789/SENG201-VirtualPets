package customFileLoader;

import java.util.ArrayList;
import java.util.HashMap;

public interface LoadFormat {
	public String[] getValidAttributes();
	public ArrayList<Object> addCustomObject(ArrayList<Object> customObjects, HashMap<String, String> attributes);
}
