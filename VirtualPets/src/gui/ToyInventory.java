package gui;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;

import model.Toy;

/**
 * A panel of toy icons to display toys that are in the player's inventory.
 * @author Alex Tompkins (ato47)
 */
public class ToyInventory extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final int itemDistance = 90;
	private ToyInventoryIcon[] toyIcons;

	/**
	 * Create the panel of toy icons for toy that is in the player's inventory.
	 * The panel is 3 toy icons wide and the adjusts height to accomodate however
	 * many rows are necessary to display all toy icons. Gets placed in a JScrollPane
	 * for easy viewing.
	 * @param playertoys
	 * The ArrayList containing the Toys in the player's inventory.
	 * @param semiBoldFont
	 * The font to use for the quantity label on the icons.
	 */
	public ToyInventory(ArrayList<Toy> playerToys, Font semiBoldFont) {
		setLayout(null);
		
		toyIcons = new ToyInventoryIcon[playerToys.size()];
		
		for (int i=0; i < playerToys.size(); i++) {
			toyIcons[i] = new ToyInventoryIcon(playerToys.get(i), semiBoldFont);
			// Position each toy icon in its correct position by doing integer division/remainder by 3, 
			// which is the length of the row, then multiplying by the distance between their origins.
			toyIcons[i].setBounds((i%3)*itemDistance, i/3*itemDistance, 85,85);
			add(toyIcons[i]);
		}
	}
	
	public ToyInventoryIcon[] getToyIcons() {
		return toyIcons;
	}
	
	/**
	 * Sets all the toy icons in this inventory to either enabled or disabled.
	 * This will grey them out in order to show to the player that they cannot be used yet.
	 * @param enabled
	 * True for setting toy icons to be enabled, false for setting toy icons to be disabled
	 */
	public void setToyIconsEnabled(boolean enabled) {
		for (ToyInventoryIcon icon : toyIcons)
			icon.setButtonEnabled(enabled);
	}
}
