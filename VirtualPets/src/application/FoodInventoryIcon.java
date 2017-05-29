package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

import model.FoodType;

/**
 * An icon to represent the number of a specific FoodType in the player's inventory.
 * @author Alex Tompkins (ato47)
 */
public class FoodInventoryIcon extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton clickDetector;
	private JLabel foodIcon;
	private JLabel quantityLabel;
	
	/**
	 * Create the panel - a simple icon to represent one of the FoodTypes in the player's inventory.
	 * @param food
	 * The FoodType that this icon represents.
	 * @param quantity
	 * The number of this FoodType in the player's inventory.
	 * @param semiBoldFont
	 * The font to use for the quantity label.
	 */
	public FoodInventoryIcon(FoodType food, int quantity, Font semiBoldFont) {
		setLayout(null);
		setOpaque(false);
		
		quantityLabel = new JLabel("");
		quantityLabel.setFont(semiBoldFont);
		quantityLabel.setBounds(10, 10, 65, 15);
		add(quantityLabel);
		
		foodIcon = new JLabel("");
		foodIcon.setBounds(5, 5, 75, 75);
		add(foodIcon);
		
		clickDetector = new JButton();
		clickDetector.setContentAreaFilled(false);
		clickDetector.setToolTipText(String.format("<html>%s<br>Nutrition: %d<br>Tastiness: %d<br>Weight: %s<br></html>", food.getName(), food.getNutrition(), food.getTastiness(), food.getWeight()));
		clickDetector.setBorderPainted(false);
		clickDetector.setBounds(0, 0, 85, 85);
		add(clickDetector);
		
		this.foodIcon.setIcon(food.getIcon());
		this.quantityLabel.setText(String.format("%dx", quantity));
		
		quantityLabel.setEnabled(false);
		foodIcon.setEnabled(false);
		clickDetector.setEnabled(false);
	}
	
	public JButton getClickDetector() {
		return clickDetector;
	}
	
	/**
	 * Sets this icon to be either enabled or disabled. When disabled the button will be greyed out 
	 * to show to the player that it cannot be used.
	 * @param enabled
	 * True to enable this icon, false to disable it.
	 */
	public void setButtonEnabled(boolean enabled) {
		clickDetector.setEnabled(enabled);
		foodIcon.setEnabled(enabled);
		quantityLabel.setEnabled(enabled);
	}
}
