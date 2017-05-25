package application;

import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.Color;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AssetCreator extends JPanel {
	private static final long serialVersionUID = 5455584120587343044L;
	private JButton buttonSpeciesSetIcon, buttonFoodSetIcon, buttonToySetIcon;
	private JButton buttonSpecies, buttonToy, buttonFood;

	/**
	 * Create the panel.
	 */
	public AssetCreator(Font titleFont) {
		setLayout(null);
		setSize(800,600);
		setVisible(false);
		
		JLabel title = new JLabel("Asset Creator");
		title.setFont(titleFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(150, 40, 500, 80);
		add(title);
		
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
		
		JButton buttonSpeciesDone = new JButton("Done");
		buttonSpeciesDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonSpeciesDone.setBounds(371, 313, 117, 25);
		speciesPanel.add(buttonSpeciesDone);
		
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
		
		JButton buttonSpeciesSetIcon = new JButton("Set Icon (100x100)");
		buttonSpeciesSetIcon.setBounds(222, 130, 155, 25);
		speciesPanel.add(buttonSpeciesSetIcon);
		
		JLabel speciesIconPreview = new JLabel("");
		speciesIconPreview.setBounds(222, 173, 100, 100);
		speciesPanel.add(speciesIconPreview);
		
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
		
		JButton buttonToyDone = new JButton("Done");
		buttonToyDone.setBounds(371, 313, 117, 25);
		toyPanel.add(buttonToyDone);
		
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
		
		JButton buttonFoodDone = new JButton("Done");
		buttonFoodDone.setBounds(371, 313, 117, 25);
		foodPanel.add(buttonFoodDone);
		
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
		
		JButton buttonFoodSetIcon = new JButton("Set Icon (75x75)");
		buttonFoodSetIcon.setBounds(202, 74, 135, 25);
		foodPanel.add(buttonFoodSetIcon);
		
		JLabel foodIconPreview = new JLabel("");
		foodIconPreview.setBounds(202, 113, 75, 75);
		foodPanel.add(foodIconPreview);
		
		//Buttons to select type of asset to create
		
		buttonSpecies = new JButton("Create Species");
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
				
				speciesPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});

		buttonToy = new JButton("Create Toy");
		buttonToy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toyNameField.setText("");
				toyPriceChooser.setValue(50);
				happinessGainChooser.setValue(35);
				toyIconPreview.setIcon(null);
				
				toyPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});
		buttonToy.setBounds(323, 132, 155, 25);
		add(buttonToy);
		
		buttonFood = new JButton("Create Food");
		buttonFood.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foodNameField.setText("");
				foodPriceChooser.setValue(35);
				nutritionChooser.setValue(40);
				tastinessChooser.setValue(20);
				weightChooser.setValue(8);
				foodIconPreview.setIcon(null);
				
				foodPanel.setVisible(true);
				setButtonsEnabled(false);
			}
		});
		buttonFood.setBounds(495, 132, 155, 25);
		add(buttonFood);		
		buttonSpecies.setBounds(150, 132, 155, 25);
		add(buttonSpecies);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(AssetCreator.class.getResource("/images/gameBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);
	}
	
	private void setButtonsEnabled(boolean enabled) {
		buttonSpecies.setEnabled(enabled);
		buttonToy.setEnabled(enabled);
		buttonFood.setEnabled(enabled);
	}
}
