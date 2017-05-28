package application;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * A panel for displaying a single food item in the shop.
 * @author Alex Tompkins (ato47)
 */
public class ShopFoodDisplayer extends JPanel {
	private static final long serialVersionUID = -506971370002352550L;
	private FoodType foodType;
	private JButton buyButton;

	/**
	 * Create the panel - a single food item in the shop.
	 * @param food
	 * The food to be displayed.
	 */
	public ShopFoodDisplayer(FoodType food, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		
		JLabel icon = new JLabel(food.getIcon());
		icon.setBounds(10, 10, 75, 75);
		add(icon);
		
		JLabel nameLabel = new JLabel(food.getName());
		nameLabel.setBounds(95, 10, 130, 15);
		nameLabel.setFont(semiBoldFont);
		add(nameLabel);
		
		JLabel priceLabel = new JLabel("Price: $" + food.getPrice());
		priceLabel.setBounds(95, 30, 130, 15);
		priceLabel.setFont(regularFont);
		add(priceLabel);
		
		JLabel nutritionLabel = new JLabel("Nutrition: " + food.getNutrition());
		nutritionLabel.setBounds(95, 50, 130, 15);
		nutritionLabel.setFont(regularFont);
		add(nutritionLabel);
		
		JLabel tastinessLabel = new JLabel("Tastiness: " + food.getTastiness());
		tastinessLabel.setBounds(95, 70, 130, 15);
		tastinessLabel.setFont(regularFont);
		add(tastinessLabel);
		
		JLabel weightLabel = new JLabel("Weight: " + food.getWeight());
		weightLabel.setBounds(95, 90, 130, 15);
		weightLabel.setFont(regularFont);
		add(weightLabel);
		
		buyButton = new JButton("Buy");
		buyButton.setEnabled(false);
		buyButton.setBounds(10, 85, 75, 20);
		add(buyButton);
		
		this.foodType = food;
	}
	
	//Getters
	public FoodType getFoodType() {
		return foodType;
	}
	
	public JButton getBuyButton() {
		return buyButton;
	}
	//End getters
	
	/**
	 * Enables the food's buy button.
	 * @param enabled
	 */
	public void enableBuyButton(boolean enabled) {
		buyButton.setEnabled(enabled);
	}
}
