package application;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.Toy;
import model.ToyType;

/**
 * An icon to represent a toy in the player's inventory. Shows the durability on the icon as a label.
 * @author Alex Tompkins (ato47)
 */
public class ToyInventoryIcon extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton clickDetector;
	private JLabel toyIcon;
	private JLabel durabilityLabel;
	private Toy specificToy;
	
	/**
	 * Create the panel - a simple icon to represent one of the Toys in the player's inventory.
	 * @param food
	 * The Toy that this icon represents.
	 * @param semiBoldFont
	 * The font to use for the quantity label.
	 */
	public ToyInventoryIcon(Toy toy, Font semiBoldFont) {
		setLayout(null);
		setOpaque(false);
		
		durabilityLabel = new JLabel("");
		durabilityLabel.setFont(semiBoldFont);
		durabilityLabel.setBounds(10, 10, 65, 15);
		add(durabilityLabel);
		
		toyIcon = new JLabel("");
		toyIcon.setBounds(5, 5, 75, 75);
		add(toyIcon);
		
		clickDetector = new JButton();
		clickDetector.setContentAreaFilled(false);
		clickDetector.setToolTipText(String.format("<html>%s<br>Happiness Gain: %d<br>Durability: %d%%</html>", toy.getToyType().getName(), toy.getToyType().getHappinessGain(), toy.getDurability()));
		clickDetector.setBorderPainted(false);
		clickDetector.setBounds(0, 0, 85, 85);
		add(clickDetector);
		
		setToyIcon(toy.getToyType());
		setdurability(toy.getDurability());
		this.specificToy = toy;
		
		durabilityLabel.setEnabled(false);
		toyIcon.setEnabled(false);
		clickDetector.setEnabled(false);
	}
	
	//Getters
	public void setToyIcon(ToyType toyType) {
		this.toyIcon.setIcon(toyType.getIcon());
	}
	
	public void setdurability(int durability) {
		this.durabilityLabel.setText(String.format("%d%%", durability));
	}
	
	public JButton getClickDetector() {
		return clickDetector;
	}
	
	public Toy getSpecificToy() {
		return specificToy;
	}
	//End getters
	
	/**
	 * Sets this icon to be either enabled or disabled. Being disabled will grey it out to show to the
	 * player that it cannot be used.
	 * @param enabled
	 * True to enable this icon, false to disable it.
	 */
	public void setButtonEnabled(boolean enabled) {
		clickDetector.setEnabled(enabled);
		toyIcon.setEnabled(enabled);
		durabilityLabel.setEnabled(enabled);
	}
}
