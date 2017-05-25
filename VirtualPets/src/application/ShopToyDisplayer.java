package application;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ShopToyDisplayer extends JPanel {
	private static final long serialVersionUID = -7801211551735059571L;
	private JButton buyButton;

	/**
	 * Create the panel.
	 */
	public ShopToyDisplayer(ToyType toy, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		
		JLabel icon = new JLabel(toy.getIcon());
		icon.setBounds(10, 10, 75, 75);
		add(icon);
		
		JLabel nameLabel = new JLabel(toy.getName());
		nameLabel.setBounds(95, 10, 130, 15);
		nameLabel.setFont(semiBoldFont);
		add(nameLabel);
		
		JLabel priceLabel = new JLabel("Price: $" + toy.getPrice());
		priceLabel.setBounds(95, 30, 130, 15);
		priceLabel.setFont(regularFont);
		add(priceLabel);
		
		JLabel nutritionLabel = new JLabel("Happiness Gain: " + toy.getHappinessGain());
		nutritionLabel.setBounds(95, 50, 130, 15);
		nutritionLabel.setFont(regularFont);
		add(nutritionLabel);
		
		buyButton = new JButton("Buy");
		buyButton.setBounds(10, 85, 75, 20);
		add(buyButton);
	}
}
