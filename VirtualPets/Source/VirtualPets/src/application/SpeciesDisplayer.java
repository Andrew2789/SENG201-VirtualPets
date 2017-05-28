package application;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;

/**
 * A box that displays all information about a certain species.
 * @author Andrew Davidson (ada130)
 */
public class SpeciesDisplayer extends JPanel {
	private static final long serialVersionUID = 2326573558020329538L;

	/**
	 * Create the species displayer for a specified species, displaying all of its attributes and its icon.
	 * @param species
	 * The species to display
	 * @param boldFont
	 * The font to use for the name heading
	 * @param semiBoldFont
	 * The font to use for attributes
	 */
	public SpeciesDisplayer(Species species, Font boldFont, Font semiBoldFont) {
		setBorder(new MatteBorder(2, 2, 2, 2, (Color) new Color(0, 0, 0)));
		setLayout(null);
		setOpaque(false);
		
		JLabel icon = new JLabel(species.getIcon());
		icon.setBounds(10, 10, 100, 100);
		add(icon);
		
		JLabel name = new JLabel(species.getName());
		name.setBounds(120, 10, 170, 16);
		name.setFont(boldFont);
		add(name);
		
		JLabel optimumWeight = new JLabel("Optimum Weight: "+species.getOptimumWeight());
		optimumWeight.setBounds(120, 27, 170, 16);
		optimumWeight.setFont(semiBoldFont);
		add(optimumWeight);
		
		JLabel hungerGain = new JLabel("Hunger Gain per Turn: "+species.getHungerGain());
		hungerGain.setBounds(120, 44, 170, 16);
		hungerGain.setFont(semiBoldFont);
		add(hungerGain);
		
		JLabel energyLoss = new JLabel("Energy Loss per Turn: "+species.getEnergyLoss());
		energyLoss.setBounds(120, 61, 170, 16);
		energyLoss.setFont(semiBoldFont);
		add(energyLoss);
		
		JLabel happinessLoss = new JLabel("Happiness Loss per Turn: "+species.getHappinessLoss());
		happinessLoss.setBounds(120, 78, 170, 16);
		happinessLoss.setFont(semiBoldFont);
		add(happinessLoss);
		
		JLabel toyDamage = new JLabel("Toy Damage: "+species.getMinToyDamage()+"% - "+species.getMaxToyDamage()+"%");
		toyDamage.setBounds(120, 95, 170, 16);
		toyDamage.setFont(semiBoldFont);
		add(toyDamage);
	}
}
