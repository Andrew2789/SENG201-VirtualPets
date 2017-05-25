package application;

import java.awt.Font;
import java.util.HashMap;
import java.util.TreeSet;
import javax.swing.JPanel;

public class FoodInventory extends JPanel {
	private static final long serialVersionUID = -581342435839614651L;
	private static final int[] itemHorizontalPositions = {0, 90, 180};
	private static final int itemVerticalDistance = 90;
	
	private HashMap<FoodType, FoodInventoryIcon> foodIcons;

	/**
	 * Create the panel.
	 */
	public FoodInventory(HashMap<FoodType, Integer> playerFoods, Font semiBoldFont) {
		setLayout(null);
		
		foodIcons = new HashMap<FoodType, FoodInventoryIcon>();
		
		int i = 0;
		for (FoodType food : new TreeSet<FoodType>(playerFoods.keySet())) {
			foodIcons.put(food, new FoodInventoryIcon(food, playerFoods.get(food), semiBoldFont));
			foodIcons.get(food).setBounds(itemHorizontalPositions[i%3], 
													i/3*itemVerticalDistance, 
													85,85);
			add(foodIcons.get(food));
			i++;
		}
		
	}
	
	public HashMap<FoodType, FoodInventoryIcon> getFoodIcons() {
		return foodIcons;
	}
	
	public void setFoodIconsEnabled(boolean enabled) {
		for (FoodInventoryIcon icon : foodIcons.values())
			icon.setButtonEnabled(enabled);
	}
}
