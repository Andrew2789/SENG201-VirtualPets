package application;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

public class ShopPanel extends JPanel {
	private static final long serialVersionUID = -3713119219367796726L;
	private JScrollPane buyFoodScrollPane;
	private JScrollPane buyToysScrollPane;
	private JPanel buyFoodPanel;
	private JPanel buyToysPanel;
	private JButton leaveButton;
	
	private ShopFoodDisplayer[] foodsForSale;
	private ShopToyDisplayer[] toysForSale;
	
	/**
	 * Create the panel.
	 */
	public ShopPanel(FoodType[] foodTypes, ToyType[] toyTypes, int money, Font semiBoldFont, Font boldFont, Font regularFont) {
		setLayout(null);
		setSize(500, 450);
		
		JLabel titleLabel = new JLabel("Welcome to the Shop!");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(boldFont);
		titleLabel.setBounds(10, 15, 235, 25);
		add(titleLabel);
		
		JLabel moneyLabel = new JLabel(String.format("You have: $%d", money));
		moneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyLabel.setFont(semiBoldFont);
		moneyLabel.setBounds(10, 55, 480, 25);
		add(moneyLabel);
		
		JLabel foodLabel = new JLabel("Food for Sale", SwingConstants.CENTER);
		foodLabel.setFont(semiBoldFont);
		foodLabel.setBounds(10, 95, 235, 25);
		add(foodLabel);
		
		JLabel toysLabel = new JLabel("Toys for Sale", SwingConstants.CENTER);
		toysLabel.setFont(semiBoldFont);
		toysLabel.setBounds(255, 95, 235, 25);
		add(toysLabel);
		
		buyFoodScrollPane = new JScrollPane();
		buyFoodScrollPane.setLayout(null);
		buyFoodScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buyFoodScrollPane.setBounds(10, 130, 235, 310);
		add(buyFoodScrollPane);
		
		buyFoodPanel = new JPanel();
		buyFoodPanel.setLayout(null);
		buyFoodPanel.setPreferredSize(new Dimension(235, (foodTypes.length+1)/2*115));
		buyFoodScrollPane.setViewportView(buyFoodPanel);
		
		buyToysScrollPane = new JScrollPane();
		buyToysScrollPane.setLayout(null);
		buyToysScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buyToysScrollPane.setBounds(255, 130, 235, 310);
		add(buyToysScrollPane);
		
		buyToysPanel = new JPanel();
		buyToysPanel.setLayout(null);
		buyToysPanel.setPreferredSize(new Dimension(235, (toyTypes.length+1)/2*115));
		buyToysScrollPane.setViewportView(buyToysPanel);
		
		leaveButton = new JButton("Leave Shop");
		leaveButton.setBounds(255, 15, 235, 25);
		add(leaveButton);
		
		foodsForSale = new ShopFoodDisplayer[foodTypes.length];
		for (int i=0; i<foodTypes.length; i++) {
			foodsForSale[i] = new ShopFoodDisplayer(foodTypes[i], semiBoldFont, regularFont);
			foodsForSale[i].setBounds(10, i*115, 235, 115);
			// DEBUG
			System.out.println(foodsForSale[i]);
			// END DEBUG
			buyFoodPanel.add(foodsForSale[i]);
		}
		
		toysForSale = new ShopToyDisplayer[toyTypes.length];
		for (int i=0; i<toyTypes.length; i++) {
			toysForSale[i] = new ShopToyDisplayer(toyTypes[i], semiBoldFont, regularFont);
			toysForSale[i].setBounds(255, i*115, 235, 115);
			// DEBUG
			System.out.println(toysForSale[i]);
			// END DEBUG
			buyToysPanel.add(toysForSale[i]);
		}
		
		enablePossibleBuyButtons(money);
	}
	
	public JButton getLeaveButton() {
		return leaveButton;
	}
	
	public ShopFoodDisplayer[] getFoodsForSale() {
		return foodsForSale;
	}
	
	public ShopToyDisplayer[] getToysForSale() {
		return toysForSale;
	}
	
	public void enablePossibleBuyButtons(int money) {
		for (ShopFoodDisplayer foodDisplay : foodsForSale) {
			if (foodDisplay.getFoodType().getPrice() <= money)
				foodDisplay.enableBuyButton(true);
		}
	}
}
