package application;

import java.awt.Font;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.JPanel;

import model.FoodType;

/**
 * A panel of food icons to display food that is in the player's inventory.
 * @author Alex Tompkins (ato47)
 */
public class FoodInventory extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int itemDistance = 90;
	private HashMap<FoodType, FoodInventoryIcon> foodIcons;

	/**
	 * Create the panel of food icons for food that is in the player's inventory.
	 * The panel is 3 food icons wide and adjusts height to accommodate however
	 * many rows are necessary to display all food icons. Gets placed in a JScrollPane
	 * for easy viewing.
	 * @param playerFoods
	 * The HashMap containing the food types mapped to their quantities in the player's inventory.
	 * @param semiBoldFont
	 * The font to use for the quantity label on the icons.
	 */
	public FoodInventory(HashMap<FoodType, Integer> playerFoods, Font semiBoldFont) {
		setLayout(null);
		
		foodIcons = new HashMap<FoodType, FoodInventoryIcon>();
		
		int i = 0;
		for (FoodType food : new TreeSet<FoodType>(playerFoods.keySet())) {
			foodIcons.put(food, new FoodInventoryIcon(food, playerFoods.get(food), semiBoldFont));
			// Position each food icon in its correct position by doing integer division/remainder by 3, 
			// which is the length of the row, then multiplying by the distance between their origins.
			foodIcons.get(food).setBounds((i%3)*itemDistance, i/3*itemDistance, 85,85);
			add(foodIcons.get(food));
			i++;
		}
		
	}
	
	public HashMap<FoodType, FoodInventoryIcon> getFoodIcons() {
		return foodIcons;
	}
	
	/**
	 * Sets all the food icons in this inventory to either enabled or disabled.
	 * Disabling them will grey them out in order to show to the player that they cannot be used yet.
	 * @param enabled
	 * True for setting food icons to be enabled, false for setting food icons to be disabled
	 */
	public void setFoodIconsEnabled(boolean enabled) {
		for (FoodInventoryIcon icon : foodIcons.values())
			icon.setButtonEnabled(enabled);
	}
}
