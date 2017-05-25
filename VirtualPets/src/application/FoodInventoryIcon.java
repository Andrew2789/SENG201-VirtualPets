package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FoodInventoryIcon extends JPanel {
	private static final long serialVersionUID = 2475277303480636218L;
	private JButton clickDetector;
	private JLabel foodIcon;
	private JLabel quantityLabel;
	
	/**
	 * Create the panel.
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
		clickDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		clickDetector.setContentAreaFilled(false);
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
	
	public void setButtonEnabled(boolean enabled) {
		clickDetector.setEnabled(enabled);
		foodIcon.setEnabled(enabled);
		quantityLabel.setEnabled(enabled);
	}
}
