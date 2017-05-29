package application;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;

import model.FoodType;
import model.ToyType;

/**
 * A panel that displays the shop. Includes two scroll panes for buying toys and food,
 * and displays your money at the top.
 * @author Alex Tompkins
 */
public class ShopPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JScrollPane buyFoodScrollPane, buyToysScrollPane;
	private JPanel buyFoodPanel, buyToysPanel;
	private JButton leaveButton;
	
	private ShopFoodDisplayer[] foodsForSale;
	private ShopToyDisplayer[] toysForSale;
	
	/**
	 * Create the panel for displaying the shop.
	 * @param foodTypes
	 * The foods available for purchase.
	 * @param toyTypes
	 * The toys available for purchase.
	 * @param money
	 * The player's money.
	 * @param semiBoldFont
	 * The font used for subtitles.
	 * @param boldFont
	 * The font used for the title.
	 * @param regularFont
	 * The font used for regular text.
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
		
		buyFoodPanel = new JPanel();
		buyFoodPanel.setLayout(null);
		buyFoodPanel.setPreferredSize(new Dimension(235, (foodTypes.length)*115));
		
		foodsForSale = new ShopFoodDisplayer[foodTypes.length];
		for (int i=0; i<foodTypes.length; i++) {
			foodsForSale[i] = new ShopFoodDisplayer(foodTypes[i], semiBoldFont, regularFont);
			foodsForSale[i].setBounds(0, i*115, 235, 115);
			buyFoodPanel.add(foodsForSale[i]);
		}
		
		buyFoodScrollPane = new JScrollPane(buyFoodPanel);
		buyFoodScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buyFoodScrollPane.setBounds(10, 130, 235, 310);
		buyFoodScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(buyFoodScrollPane);
		
		buyToysPanel = new JPanel();
		buyToysPanel.setLayout(null);
		buyToysPanel.setPreferredSize(new Dimension(235, (toyTypes.length)*115));
		
		toysForSale = new ShopToyDisplayer[toyTypes.length];
		for (int i=0; i<toyTypes.length; i++) {
			toysForSale[i] = new ShopToyDisplayer(toyTypes[i], semiBoldFont, regularFont);
			toysForSale[i].setBounds(0, i*115, 235, 115);
			buyToysPanel.add(toysForSale[i]);
		}
		
		buyToysScrollPane = new JScrollPane(buyToysPanel);
		buyToysScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		buyToysScrollPane.setBounds(255, 130, 235, 310);
		buyToysScrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(buyToysScrollPane);
		
		leaveButton = new JButton("Leave Shop");
		leaveButton.setBounds(255, 15, 235, 25);
		add(leaveButton);
		
		enablePossibleBuyButtons(money);
	}
	
	//Getters
	public JScrollPane getBuyFoodScrollPane() {
		return buyFoodScrollPane;
	}
	
	public JScrollPane getBuyToysScrollPane() {
		return buyToysScrollPane;
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
	//End getters
	
	/**
	 * Enables buy buttons on the shop panel selectively based on whether or not the player
	 * can buy them.
	 * @param money
	 * The player's money.
	 */
	public void enablePossibleBuyButtons(int money) {
		for (ShopFoodDisplayer foodDisplay : foodsForSale)
			if (foodDisplay.getFoodType().getPrice() <= money)
				foodDisplay.enableBuyButton(true);
		for (ShopToyDisplayer toyDisplay: toysForSale)
			if (toyDisplay.getToyType().getPrice() <= money)
				toyDisplay.enableBuyButton(true);
	}
}
