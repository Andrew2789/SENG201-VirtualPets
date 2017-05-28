package application;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Displays pet statistics and allows the player to interact with the pet.
 * @author Andrew Davidson (ada130)
 */
public class PetInteract extends JPanel {
	private static final long serialVersionUID = 571759901412319640L;
	private Pet activePet;
	private JLabel speciesLabel, favouriteToyLabel, favouriteToyIcon, favouriteFoodLabel, favouriteFoodIcon;
	private JLabel behavingLabel, healthyLabel;
	private JButton buttonCure, buttonDiscipline;
	private PetStatDisplayer hungerSlider, energySlider, happinessSlider, weightSlider;
	private JButton buttonPlay, buttonFeed, buttonRest, buttonToilet;
	
	private JPanel revivePrompt;
	private JButton buttonRevive;
	private JLabel reviveLabel, reviveLabel2;
	
	/**
	 * Create the panel - initialise all components but do not set values until setPet is called. Players must be able to view all pet statistics.
	 * @param boldFont
	 * The font to use for headings
	 * @param semiBoldFont
	 * The font to use for inputs and less significant labels
	 */
	public PetInteract(Font boldFont, Font semiBoldFont) {
		setOpaque(false);
		setLayout(null);
		
		//JPanel to prompt user to revive the pet if they are dead
		revivePrompt = new JPanel();
		revivePrompt.setBackground(Color.GRAY);
		revivePrompt.setBounds(150, 75, 200, 150);
		revivePrompt.setVisible(false);
		add(revivePrompt);
		revivePrompt.setLayout(null);
		
		reviveLabel = new JLabel("");
		reviveLabel.setForeground(Color.WHITE);
		reviveLabel.setHorizontalAlignment(SwingConstants.CENTER);
		reviveLabel.setBounds(10, 11, 180, 25);
		reviveLabel.setFont(boldFont);
		revivePrompt.add(reviveLabel);
		
		reviveLabel2 = new JLabel("");
		reviveLabel2.setForeground(Color.WHITE);
		reviveLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		reviveLabel2.setBounds(10, 34, 180, 25);
		reviveLabel2.setFont(boldFont);
		revivePrompt.add(reviveLabel2);
		
		buttonRevive = new JButton("Revive");
		buttonRevive.setBounds(38, 75, 124, 43);
		buttonRevive.setFont(boldFont);
		revivePrompt.add(buttonRevive);
		
		//Pet attribute displays
		JLabel speciesTitle = new JLabel("Species");
		speciesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		speciesTitle.setFont(boldFont);
		speciesTitle.setBounds(12, 12, 232, 16);
		add(speciesTitle);
		
		speciesLabel = new JLabel("");
		speciesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speciesLabel.setFont(semiBoldFont);
		speciesLabel.setBounds(12, 28, 232, 16);
		add(speciesLabel);
		
		healthyLabel = new JLabel("");
		healthyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		healthyLabel.setFont(boldFont);
		healthyLabel.setBounds(12, 48, 232, 16);
		add(healthyLabel);
		
		behavingLabel = new JLabel("");
		behavingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		behavingLabel.setFont(boldFont);
		behavingLabel.setBounds(12, 97, 232, 16);
		add(behavingLabel);
		
		buttonCure = new JButton("Cure");
		buttonCure.setEnabled(false);
		buttonCure.setFont(boldFont);
		buttonCure.setToolTipText("Cures sickness and makes the pet happy (+20 happiness). Costs $50 and consumes one action point.");
		buttonCure.setBounds(82, 66, 100, 20);
		add(buttonCure);
		
		buttonDiscipline = new JButton("Discipline");
		buttonDiscipline.setEnabled(false);
		buttonDiscipline.setFont(boldFont);
		buttonDiscipline.setToolTipText("Will make your pet unhappy (-30 happiness) but stops them from misbehaving. Consumes one action point.");
		buttonDiscipline.setBounds(82, 115, 100, 20);
		add(buttonDiscipline);
		
		JLabel favouriteToyTitle = new JLabel("Favourite Toy");
		favouriteToyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyTitle.setFont(boldFont);
		favouriteToyTitle.setBounds(12, 155, 110, 28);
		add(favouriteToyTitle);
		
		favouriteToyLabel = new JLabel("");
		favouriteToyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyLabel.setFont(semiBoldFont);
		favouriteToyLabel.setBounds(12, 172, 110, 16);
		add(favouriteToyLabel);
		
		favouriteToyIcon = new JLabel("");
		favouriteToyIcon.setBounds(30, 185, 75, 75);
		add(favouriteToyIcon);
		
		JLabel favouriteFoodTitle = new JLabel("Favourite Food");
		favouriteFoodTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodTitle.setFont(boldFont);
		favouriteFoodTitle.setBounds(132, 155, 110, 28);
		add(favouriteFoodTitle);
		
		favouriteFoodLabel = new JLabel("");
		favouriteFoodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodLabel.setFont(semiBoldFont);
		favouriteFoodLabel.setBounds(134, 172, 110, 16);
		add(favouriteFoodLabel);
		
		favouriteFoodIcon = new JLabel("");
		favouriteFoodIcon.setBounds(151, 185, 75, 75);
		add(favouriteFoodIcon);
		
		//Pet action buttons
		buttonPlay = new JButton("Play");
		buttonPlay.setToolTipText("Play with the selected pet. You will need to select a toy. This will use 1 action point.");
		buttonPlay.setBounds(14, 298, 110, 35);
		add(buttonPlay);
		
		buttonFeed = new JButton("Feed");
		buttonFeed.setToolTipText("Feed the selected pet. You will need to select a food. This will use 1 action point.");
		buttonFeed.setBounds(134, 298, 110, 35);
		add(buttonFeed);
		
		buttonRest = new JButton("Rest");
		buttonRest.setToolTipText("Have the selected pet rest, giving +30 energy. This will use 1 action point.");
		buttonRest.setBounds(256, 298, 110, 35);
		add(buttonRest);
		
		buttonToilet = new JButton("Toilet");
		buttonToilet.setToolTipText("<html>Send the selected pet to the toilet, decreasing their weight by 1/6 of their optimum weight.<br>Pets cannot go below their optimum weight by going to the toilet. This will use 1 action point.</html>");
		buttonToilet.setBounds(378, 298, 110, 35);
		add(buttonToilet);
		
		//Pet status sliders
		hungerSlider = new PetStatDisplayer(boldFont, semiBoldFont, "Hunger", new ImageIcon(Game.class.getResource("/images/sliders/hunger.png")), 
				"How hungry this pet is. Once hunger reaches the orange region, the pet will begin to starve.", new Color(227, 66, 52), 0, 100, 0, 8);
		hungerSlider.setBounds(257, 12, 232, 50);
		add(hungerSlider);
		
		energySlider = new PetStatDisplayer(boldFont, semiBoldFont, "Energy", new ImageIcon(Game.class.getResource("/images/sliders/energy.png")), 
				"How much energy this pet has. Once energy reaches the red region, the pet will have a chance to die at the end of each turn.", new Color(30, 224, 220), 0, 100, 6, 0);
		energySlider.setBounds(257, 77, 232, 50);
		add(energySlider);
		
		happinessSlider = new PetStatDisplayer(boldFont, semiBoldFont, "Happiness", new ImageIcon(Game.class.getResource("/images/sliders/happiness.png")), 
				"How happy this pet is.", new Color(255, 180, 0), 0, 100, 3, 0);
		happinessSlider.setBounds(257, 142, 232, 50);
		add(happinessSlider);
		
		weightSlider = new PetStatDisplayer(boldFont, semiBoldFont, "Weight", new ImageIcon(Game.class.getResource("/images/sliders/weight.png")), 
				"How much this pet weighs.", new Color(127, 127, 127), 0, 100, 6, 6);
		weightSlider.setBounds(257, 207, 232, 50);
		add(weightSlider);
		
		//Semi-transparent background
		JLabel petInfoBackground = new JLabel(new ImageIcon(Game.class.getResource("/images/backs/petInteract.png")));
		petInfoBackground.setBounds(0, 0, 500, 345);
		add(petInfoBackground);
	}

	//Getters
	public JButton getButtonPlay() {
		return buttonPlay;
	}
	
	public JButton getButtonFeed() {
		return buttonFeed;
	}

	public JButton getButtonRest() {
		return buttonRest;
	}
	
	public JButton getButtonToilet() {
		return buttonToilet;
	}
	
	public JButton getButtonCure() {
		return buttonCure;
	}
	
	public JButton getButtonDiscipline() {
		return buttonDiscipline;
	}
	
	public JButton getButtonRevive() {
		return buttonRevive;
	}
	//End Getters
	
	/**
	 * Sets the pet to display information about
	 * @param activePet
	 * The pet to show info about
	 */
	public void setPet(Pet activePet) {
		this.activePet = activePet;
		speciesLabel.setText(activePet.getSpecies().getName());
		if (activePet.isHealthy())
			healthyLabel.setText("This pet is healthy");
		else
			healthyLabel.setText("This pet is sick");
		buttonCure.setEnabled(!activePet.isHealthy());
		
		if (activePet.isBehaving())
			behavingLabel.setText("This pet is behaving");
		else
			behavingLabel.setText("This pet is misbehaving");
		buttonDiscipline.setEnabled(!activePet.isBehaving());
		
		hungerSlider.setStat(activePet.getHunger());
		energySlider.setStat(activePet.getEnergy());
		happinessSlider.setStat(activePet.getHappiness());
		int optWeight = activePet.getSpecies().getOptimumWeight();
		weightSlider.setMinMax(optWeight*1/3, optWeight*5/3);
		weightSlider.setStat(activePet.getWeight());
		
		favouriteToyLabel.setText(activePet.getFavouriteToy().getName());
		favouriteFoodLabel.setText(activePet.getFavouriteFood().getName());

		favouriteToyIcon.setIcon(activePet.getFavouriteToy().getIcon());
		favouriteFoodIcon.setIcon(activePet.getFavouriteFood().getIcon());
		
		revivePrompt.setVisible(!activePet.isAlive());
		if (activePet.isRevivable()) {
			reviveLabel.setText("This pet is dead but may be");
			reviveLabel2.setText("revived once. Costs $100.");
			buttonRevive.setEnabled(true);
		}
		else {
			reviveLabel.setText("This pet has died again and");
			reviveLabel2.setText("cannot be revived.");
			buttonRevive.setEnabled(false);
		}
	}
	
	/**
	 * Sets whether buttons can be pressed or not.
	 * @param enabled
	 * Whether the buttons may be pressed
	 */
	public void setButtonsEnabled(boolean enabled) {
		buttonPlay.setEnabled(enabled);
		buttonFeed.setEnabled(enabled);
		buttonRest.setEnabled(enabled);
		buttonToilet.setEnabled(enabled);
		if (enabled) {
			if (!activePet.isHealthy())
				buttonCure.setEnabled(true);
			if (!activePet.isBehaving())
				buttonDiscipline.setEnabled(true);
		}
		else {
			buttonCure.setEnabled(false);
			buttonDiscipline.setEnabled(false);
		}
	}
	
}
