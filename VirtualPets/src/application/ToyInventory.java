package application;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JPanel;

public class ToyInventory extends JPanel {
	private static final long serialVersionUID = -752677781672398397L;
	private static final int[] itemHorizontalPositions = {0, 90, 180};
	private static final int itemVerticalDistance = 90;
	
	private ToyInventoryIcon[] toyIcons;

	/**
	 * Create the panel.
	 */
	public ToyInventory(ArrayList<Toy> playerToys, Font semiBoldFont) {
		setLayout(null);
		
		toyIcons = new ToyInventoryIcon[playerToys.size()];
		
		for (int i=0; i < playerToys.size(); i++) {
			toyIcons[i] = new ToyInventoryIcon(playerToys.get(i), semiBoldFont);
			toyIcons[i].setBounds(itemHorizontalPositions[i%3], 
										 i/3*itemVerticalDistance, 
										 85,85);
			add(toyIcons[i]);
		}
	}
	
	public ToyInventoryIcon[] getToyIcons() {
		return toyIcons;
	}
	
	public void setToyIconsEnabled(boolean enabled) {
		for (ToyInventoryIcon icon : toyIcons)
			icon.setButtonEnabled(enabled);
	}
}
