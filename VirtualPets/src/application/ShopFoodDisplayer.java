package application;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ShopFoodDisplayer extends JPanel {
	private static final long serialVersionUID = -506971370002352550L;
	private JButton buyButton;

	/**
	 * Create the panel.
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
		buyButton.setBounds(10, 85, 75, 20);
		add(buyButton);
	}
}
