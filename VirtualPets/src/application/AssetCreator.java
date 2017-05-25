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

public class AssetCreator extends JPanel {
	private static final long serialVersionUID = 5455584120587343044L;
	private JTextField nameField;

	/**
	 * Create the panel.
	 */
	public AssetCreator(Font titleFont) {
		setLayout(null);
		setSize(800,600);
		
		JLabel title = new JLabel("Virtual Pets");
		title.setFont(titleFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(150, 40, 500, 80);
		add(title);
		
		JButton buttonSpecies = new JButton("Create Species");
		buttonSpecies.setBounds(150, 132, 155, 25);
		add(buttonSpecies);
		
		JButton buttonToy = new JButton("Create Toy");
		buttonToy.setBounds(323, 132, 155, 25);
		add(buttonToy);
		
		JButton buttonFood = new JButton("Create Food");
		buttonFood.setBounds(495, 132, 155, 25);
		add(buttonFood);
		
		JPanel speciesPanel = new JPanel();
		speciesPanel.setBackground(Color.DARK_GRAY);
		speciesPanel.setBounds(150, 160, 500, 350);
		add(speciesPanel);
		speciesPanel.setLayout(null);
		
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.setBounds(239, 313, 117, 25);
		speciesPanel.add(buttonCancel);
		
		JButton buttonDone = new JButton("Done");
		buttonDone.setBounds(371, 313, 117, 25);
		speciesPanel.add(buttonDone);
		
		JLabel nameLabel = new JLabel("Species Name");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setBounds(12, 12, 135, 25);
		speciesPanel.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(12, 34, 135, 25);
		speciesPanel.add(nameField);
		nameField.setColumns(10);
		
		JLabel optimumWeightLabel = new JLabel("Optimum Weight");
		optimumWeightLabel.setForeground(Color.WHITE);
		optimumWeightLabel.setBounds(12, 71, 135, 25);
		speciesPanel.add(optimumWeightLabel);
		
		JSpinner optimumWeightChooser = new JSpinner();
		optimumWeightChooser.setBounds(12, 98, 39, 20);
		speciesPanel.add(optimumWeightChooser);
		
		JLabel hungerGainLabel = new JLabel("Hunger Gain per Turn");
		hungerGainLabel.setForeground(Color.WHITE);
		hungerGainLabel.setBounds(12, 130, 192, 25);
		speciesPanel.add(hungerGainLabel);
		
		JSpinner hungerGainChooser = new JSpinner();
		hungerGainChooser.setBounds(12, 157, 39, 20);
		speciesPanel.add(hungerGainChooser);
		
		JLabel energyLossPerLabel = new JLabel("Energy Loss per Turn");
		energyLossPerLabel.setForeground(Color.WHITE);
		energyLossPerLabel.setBounds(12, 189, 192, 25);
		speciesPanel.add(energyLossPerLabel);
		
		JSpinner energyLossPerChooser = new JSpinner();
		energyLossPerChooser.setBounds(12, 216, 39, 20);
		speciesPanel.add(energyLossPerChooser);
		
		JLabel happinessLossPerLabel = new JLabel("Happiness Loss per Turn");
		happinessLossPerLabel.setForeground(Color.WHITE);
		happinessLossPerLabel.setBounds(12, 248, 192, 25);
		speciesPanel.add(happinessLossPerLabel);
		
		JSpinner happinessLossPerChooser = new JSpinner();
		happinessLossPerChooser.setBounds(12, 274, 39, 20);
		speciesPanel.add(happinessLossPerChooser);
		
		JLabel minToyDamageLabel = new JLabel("Min Toy Damage");
		minToyDamageLabel.setForeground(Color.WHITE);
		minToyDamageLabel.setBounds(221, 12, 135, 25);
		speciesPanel.add(minToyDamageLabel);
		
		JSpinner minToyDamageChooser = new JSpinner();
		minToyDamageChooser.setBounds(221, 37, 39, 20);
		speciesPanel.add(minToyDamageChooser);
		
		JLabel maxToyDamageLabel = new JLabel("Max Toy Damage");
		maxToyDamageLabel.setForeground(Color.WHITE);
		maxToyDamageLabel.setBounds(221, 71, 135, 25);
		speciesPanel.add(maxToyDamageLabel);
		
		JSpinner maxToyDamageChooser = new JSpinner();
		maxToyDamageChooser.setBounds(221, 98, 39, 20);
		speciesPanel.add(maxToyDamageChooser);
		
		JButton buttonSetIcon = new JButton("Set Icon");
		buttonSetIcon.setBounds(222, 130, 117, 25);
		speciesPanel.add(buttonSetIcon);
		
		JLabel iconPreview = new JLabel("");
		iconPreview.setBounds(232, 157, 100, 100);
		speciesPanel.add(iconPreview);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AssetCreator.class.getResource("/images/gameBackground.png")));
		label.setBounds(0, 0, 800, 600);
		add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(128, 348, 32, 24);
		add(comboBox);
		
	}
}
