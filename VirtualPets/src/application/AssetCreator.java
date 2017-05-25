package application;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import java.awt.Color;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Andrew Davidson (ada130)
 * @author Alex Tompkins (ato47)
 * Allows the user to create and save custom species, toyTypes, and foodTypes.
 */
public class AssetCreator extends JPanel {
	private static final long serialVersionUID = 5455584120587343044L;
	private JButton buttonSpeciesSetIcon, buttonFoodSetIcon, buttonToySetIcon;
	private JButton buttonSpecies, buttonToy, buttonFood;
	private JButton buttonBack;

	/**
	 * Create the panel - has a setup box openable for new species, toyType, or foodType.
	 * @param titleFont
	 * The font to use for the title
	 * @param labelFont
	 * The font to use for other labels
	 */
	public AssetCreator(Font titleFont, Font labelFont) {
		setLayout(null);
		setSize(800,600);
		setVisible(false);
		
		JLabel title = new JLabel("Asset Creator");
		title.setFont(titleFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(150, 40, 500, 80);
		add(title);
		
		//A panel to allow creation of a new species, with all the relevant fields and spinners. Also allows the user to select an icon from their device.
		JPanel speciesPanel = new JPanel();
		speciesPanel.setBackground(Color.DARK_GRAY);
		speciesPanel.setBounds(150, 160, 500, 350);
		speciesPanel.setVisible(false);
		add(speciesPanel);
		speciesPanel.setLayout(null);
		
		JButton buttonSpeciesCancel = new JButton("Cancel");
		buttonSpeciesCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setButtonsEnabled(true);
				speciesPanel.setVisible(false);
			}
		});
		buttonSpeciesCancel.setBounds(239, 313, 117, 25);
		speciesPanel.add(buttonSpeciesCancel);
		
		JLabel speciesNameLabel = new JLabel("Species Name");
		speciesNameLabel.setForeground(Color.WHITE);
		speciesNameLabel.setBounds(12, 12, 135, 25);
		speciesPanel.add(speciesNameLabel);
		
		JTextField speciesNameField = new JTextField();
		speciesNameField.setBounds(12, 34, 135, 25);
		speciesPanel.add(speciesNameField);
		speciesNameField.setColumns(10);
		
		JLabel optimumWeightLabel = new JLabel("Optimum Weight");
		optimumWeightLabel.setForeground(Color.WHITE);
		optimumWeightLabel.setBounds(12, 71, 135, 25);
		speciesPanel.add(optimumWeightLabel);
		
		JSpinner optimumWeightChooser = new JSpinner();
		optimumWeightChooser.setModel(new SpinnerNumberModel(50, 10, 200, 5));
		optimumWeightChooser.setBounds(12, 98, 39, 20);
		speciesPanel.add(optimumWeightChooser);
		
		JLabel hungerGainLabel = new JLabel("Hunger Gain per Turn");
		hungerGainLabel.setForeground(Color.WHITE);
		hungerGainLabel.setBounds(12, 130, 192, 25);
		speciesPanel.add(hungerGainLabel);
		
		JSpinner hungerGainChooser = new JSpinner();
		hungerGainChooser.setModel(new SpinnerNumberModel(30, 0, 65, 5));
		hungerGainChooser.setBounds(12, 157, 39, 20);
		speciesPanel.add(hungerGainChooser);
		
		JLabel energyLossLabel = new JLabel("Energy Loss per Turn");
		energyLossLabel.setForeground(Color.WHITE);
		energyLossLabel.setBounds(12, 189, 192, 25);
		speciesPanel.add(energyLossLabel);
		
		JSpinner energyLossChooser = new JSpinner();
		energyLossChooser.setModel(new SpinnerNumberModel(30, 0, 65, 1));
		energyLossChooser.setBounds(12, 216, 39, 20);
		speciesPanel.add(energyLossChooser);
		
		JLabel happinessLossLabel = new JLabel("Happiness Loss per Turn");
		happinessLossLabel.setForeground(Color.WHITE);
		happinessLossLabel.setBounds(12, 248, 192, 25);
		speciesPanel.add(happinessLossLabel);
		
		JSpinner happinessLossChooser = new JSpinner();
		happinessLossChooser.setModel(new SpinnerNumberModel(30, 0, 65, 1));
		happinessLossChooser.setBounds(12, 274, 39, 20);
		speciesPanel.add(happinessLossChooser);
		
		JLabel minToyDamageLabel = new JLabel("Min Toy Damage");
		minToyDamageLabel.setForeground(Color.WHITE);
		minToyDamageLabel.setBounds(221, 12, 135, 25);
		speciesPanel.add(minToyDamageLabel);
		
		JSpinner minToyDamageChooser = new JSpinner();
		minToyDamageChooser.setModel(new SpinnerNumberModel(30, 0, 100, 5));
		minToyDamageChooser.setBounds(221, 37, 39, 20);
		speciesPanel.add(minToyDamageChooser);
		
		JLabel maxToyDamageLabel = new JLabel("Max Toy Damage");
		maxToyDamageLabel.setForeground(Color.WHITE);
		maxToyDamageLabel.setBounds(221, 71, 135, 25);
		speciesPanel.add(maxToyDamageLabel);
		
		JSpinner maxToyDamageChooser = new JSpinner();
		maxToyDamageChooser.setModel(new SpinnerNumberModel(60, 0, 100, 5));
		maxToyDamageChooser.setBounds(221, 98, 39, 20);
		speciesPanel.add(maxToyDamageChooser);
		
		buttonSpeciesSetIcon = new JButton("Set Icon (100x100)");
		buttonSpeciesSetIcon.setBounds(222, 130, 155, 25);
		speciesPanel.add(buttonSpeciesSetIcon);
		
		JLabel speciesIconPreview = new JLabel("");
		speciesIconPreview.setBounds(222, 173, 100, 100);
		speciesPanel.add(speciesIconPreview);

		//Error label if the user tries to create the species with missing inputs
		JLabel speciesErrorLabel = new JLabel("Please enter a name and choose an icon");
		speciesErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speciesErrorLabel.setForeground(Color.WHITE);
		speciesErrorLabel.setBounds(239, 277, 249, 25);
		speciesErrorLabel.setVisible(false);
		speciesPanel.add(speciesErrorLabel);
		
		//Create and save the new species if inputs are valid, display error otherwise
		JButton buttonSpeciesDone = new JButton("Done");
		buttonSpeciesDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!speciesNameField.getText().isEmpty() && (true)) {
					Species species = new Species(
							speciesNameField.getName(), 
							new ImageIcon(), 
							(int)optimumWeightChooser.getValue(), 
							(int)hungerGainChooser.getValue(), 
							(int)energyLossChooser.getValue(), 
							(int)happinessLossChooser.getValue(), 
							(int)minToyDamageChooser.getValue(), 
							(int)maxToyDamageChooser.getValue());
				}
				else
					speciesErrorLabel.setVisible(true);
			}
		});
		buttonSpeciesDone.setBounds(371, 313, 117, 25);
		speciesPanel.add(buttonSpeciesDone);
		
		//Panel to allow the user to create new toyTypes
		
		JPanel toyPanel = new JPanel();
		toyPanel.setBounds(150, 160, 500, 350);
		add(toyPanel);
		toyPanel.setLayout(null);
		toyPanel.setVisible(false);
		toyPanel.setBackground(Color.DARK_GRAY);
		
		JButton buttonToyCancel = new JButton("Cancel");
		buttonToyCancel.setBounds(239, 313, 117, 25);
		buttonToyCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setButtonsEnabled(true);
				toyPanel.setVisible(false);
			}
		});
		toyPanel.add(buttonToyCancel);
		
		JLabel toyNameLabel = new JLabel("Toy Name");
		toyNameLabel.setForeground(Color.WHITE);
		toyNameLabel.setBounds(12, 12, 135, 25);
		toyPanel.add(toyNameLabel);
		
		JTextField toyNameField = new JTextField();
		toyNameField.setColumns(10);
		toyNameField.setBounds(12, 34, 135, 25);
		toyPanel.add(toyNameField);
		
		JLabel toyPriceLabel = new JLabel("Price");
		toyPriceLabel.setForeground(Color.WHITE);
		toyPriceLabel.setBounds(12, 71, 135, 25);
		toyPanel.add(toyPriceLabel);
		
		JSpinner toyPriceChooser = new JSpinner();
		toyPriceChooser.setModel(new SpinnerNumberModel(50, 0, null, 5));
		toyPriceChooser.setBounds(12, 98, 39, 20);
		toyPanel.add(toyPriceChooser);
		
		JLabel happinessGainLabel = new JLabel("Happiness Gain");
		happinessGainLabel.setForeground(Color.WHITE);
		happinessGainLabel.setBounds(12, 130, 192, 25);
		toyPanel.add(happinessGainLabel);
		
		JSpinner happinessGainChooser = new JSpinner();
		happinessGainChooser.setModel(new SpinnerNumberModel(35, 0, 100, 5));
		happinessGainChooser.setBounds(12, 157, 39, 20);
		toyPanel.add(happinessGainChooser);
		
		buttonToySetIcon = new JButton("Set Icon (75x75)");
		buttonToySetIcon.setBounds(202, 34, 135, 25);
		toyPanel.add(buttonToySetIcon);
		
		JLabel toyIconPreview = new JLabel("");
		toyIconPreview.setBounds(202, 71, 75, 75);
		toyPanel.add(toyIconPreview);

		//Error label if the user tries to create the toyType with missing inputs
		JLabel toyErrorLabel = new JLabel("Please enter a name and choose an icon");
		toyErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		toyErrorLabel.setForeground(Color.WHITE);
		toyErrorLabel.setBounds(239, 277, 249, 25);
		toyErrorLabel.setVisible(false);
		toyPanel.add(toyErrorLabel);
		
		//Create and save the new toyType if inputs are valid, display error otherwise
		JButton buttonToyDone = new JButton("Done");
		buttonToyDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!toyNameField.getText().isEmpty() && (true)) {
					ToyType toyType = new ToyType(
							toyNameField.getName(), 
							new ImageIcon(), 
							(int)toyPriceChooser.getValue(), 
							(int)happinessGainChooser.getValue());
				}
				else
					toyErrorLabel.setVisible(true);
			}
		});
		buttonToyDone.setBounds(371, 313, 117, 25);
		toyPanel.add(buttonToyDone);
		
		//Panel to allow the user to create new foodTypes
		
		JPanel foodPanel = new JPanel();
		foodPanel.setBounds(150, 160, 500, 350);
		add(foodPanel);
		foodPanel.setLayout(null);
		foodPanel.setVisible(false);
		foodPanel.setBackground(Color.DARK_GRAY);
		
		JButton buttonFoodCancel = new JButton("Cancel");
		buttonFoodCancel.setBounds(239, 313, 117, 25);
		buttonFoodCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setButtonsEnabled(true);
				foodPanel.setVisible(false);
			}
		});
		foodPanel.add(buttonFoodCancel);
		
		JLabel foodNameLabel = new JLabel("Food Name");
		foodNameLabel.setForeground(Color.WHITE);
		foodNameLabel.setBounds(12, 12, 135, 25);
		foodPanel.add(foodNameLabel);
		
		JTextField foodNameField = new JTextField();
		foodNameField.setColumns(10);
		foodNameField.setBounds(12, 34, 135, 25);
		foodPanel.add(foodNameField);
		
		JLabel foodPriceLabel = new JLabel("Price");
		foodPriceLabel.setForeground(Color.WHITE);
		foodPriceLabel.setBounds(12, 71, 135, 25);
		foodPanel.add(foodPriceLabel);
		
		JSpinner foodPriceChooser = new JSpinner();
		foodPriceChooser.setModel(new SpinnerNumberModel(35, 0, null, 5));
		foodPriceChooser.setBounds(12, 98, 39, 20);
		foodPanel.add(foodPriceChooser);
		
		JLabel nutritionLabel = new JLabel("Nutrition");
		nutritionLabel.setForeground(Color.WHITE);
		nutritionLabel.setBounds(12, 130, 192, 25);
		foodPanel.add(nutritionLabel);
		
		JSpinner nutritionChooser = new JSpinner();
		nutritionChooser.setModel(new SpinnerNumberModel(40, -100, 100, 5));
		nutritionChooser.setBounds(12, 157, 39, 20);
		foodPanel.add(nutritionChooser);
		
		JLabel tastinessLabel = new JLabel("Tastiness");
		tastinessLabel.setForeground(Color.WHITE);
		tastinessLabel.setBounds(12, 189, 192, 25);
		foodPanel.add(tastinessLabel);
		
		JSpinner tastinessChooser = new JSpinner();
		tastinessChooser.setModel(new SpinnerNumberModel(20, -100, 100, 5));
		tastinessChooser.setBounds(12, 216, 39, 20);
		foodPanel.add(tastinessChooser);
		
		JLabel weightLabel = new JLabel("Weight");
		weightLabel.setForeground(Color.WHITE);
		weightLabel.setBounds(202, 12, 135, 25);
		foodPanel.add(weightLabel);
		
		JSpinner weightChooser = new JSpinner();
		weightChooser.setModel(new SpinnerNumberModel(8, 1, null, 1));
		weightChooser.setBounds(202, 36, 39, 20);
		foodPanel.add(weightChooser);
		
		buttonFoodSetIcon = new JButton("Set Icon (75x75)");
		buttonFoodSetIcon.setBounds(202, 74, 135, 25);
		foodPanel.add(buttonFoodSetIcon);
		
		JLabel foodIconPreview = new JLabel("");
		foodIconPreview.setBounds(202, 113, 75, 75);
		foodPanel.add(foodIconPreview);

		//Error label if the user tries to create the foodType with missing inputs
		JLabel foodErrorLabel = new JLabel("Please enter a name and choose an icon");
		foodErrorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		foodErrorLabel.setForeground(Color.WHITE);
		foodErrorLabel.setBounds(239, 277, 249, 25);
		foodErrorLabel.setVisible(false);
		foodPanel.add(foodErrorLabel);
		
		//Create and save the new foodType if inputs are valid, display error otherwise
		JButton buttonFoodDone = new JButton("Done");
		buttonFoodDone.setBounds(371, 313, 117, 25);
		buttonFoodDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!foodNameField.getText().isEmpty() && (true)) {
					FoodType foodType = new FoodType(
							foodNameField.getName(), 
							new ImageIcon(), 
							(int)foodPriceChooser.getValue(), 
							(int)nutritionChooser.getValue(), 
							(int)tastinessChooser.getValue(), 
							(int)weightChooser.getValue());
				}
				else
					foodErrorLabel.setVisible(true);
			}
		});
		foodPanel.add(buttonFoodDone);
		
		//Buttons to select type of asset to create
		
		buttonSpecies = new JButton("Create Species");
		buttonSpecies.setFont(labelFont);
		buttonSpecies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speciesNameField.setText("");
				optimumWeightChooser.setValue(50);
				hungerGainChooser.setValue(30);
				energyLossChooser.setValue(30);
				happinessLossChooser.setValue(30);
				minToyDamageChooser.setValue(30);
				maxToyDamageChooser.setValue(60);
				speciesIconPreview.setIcon(null);
				speciesErrorLabel.setVisible(false);
				
				speciesPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});

		buttonToy = new JButton("Create Toy");
		buttonToy.setFont(labelFont);
		buttonToy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toyNameField.setText("");
				toyPriceChooser.setValue(50);
				happinessGainChooser.setValue(35);
				toyIconPreview.setIcon(null);
				toyErrorLabel.setVisible(false);
				
				toyPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});
		buttonToy.setBounds(323, 132, 155, 25);
		add(buttonToy);
		
		buttonFood = new JButton("Create Food");
		buttonFood.setFont(labelFont);
		buttonFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foodNameField.setText("");
				foodPriceChooser.setValue(35);
				nutritionChooser.setValue(40);
				tastinessChooser.setValue(20);
				weightChooser.setValue(8);
				foodIconPreview.setIcon(null);
				foodErrorLabel.setVisible(false);
				
				foodPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});
		buttonFood.setBounds(495, 132, 155, 25);
		add(buttonFood);		
		buttonSpecies.setBounds(150, 132, 155, 25);
		add(buttonSpecies);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(labelFont);
		buttonBack.setBounds(325, 540, 150, 25);
		add(buttonBack);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(AssetCreator.class.getResource("/images/gameBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);
		
		//Set font of all labels/spinners/fields
		for (java.awt.Component comp: speciesPanel.getComponents())
			comp.setFont(labelFont);
		
		for (java.awt.Component comp: toyPanel.getComponents())
			comp.setFont(labelFont);
		
		for (java.awt.Component comp: foodPanel.getComponents())
			comp.setFont(labelFont);
	}
	
	//Getter
	public JButton getBackButton() {
		return buttonBack;
	}
	
	/**
	 * Set whether non-asset-creator buttons are enabled
	 * @param enabled
	 * Whether these buttons are enabled
	 */
	private void setButtonsEnabled(boolean enabled) {
		buttonSpecies.setEnabled(enabled);
		buttonToy.setEnabled(enabled);
		buttonFood.setEnabled(enabled);
		buttonBack.setEnabled(enabled);
	}
}
