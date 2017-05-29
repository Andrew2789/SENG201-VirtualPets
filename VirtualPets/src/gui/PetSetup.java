package gui;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import java.util.concurrent.ThreadLocalRandom;

import model.FoodType;
import model.Pet;
import model.Species;
import model.ToyType;

/**
 * Allows the user to set the name and species of a single pet, and generate the pet. PlayerSetup should create multiple PetSetups.
 * @author Andrew Davidson (ada130)
 */
public class PetSetup extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JTextField petNameField;
	private JComboBox<String> speciesChooser;
	private JLabel speciesIcon;
	private Species[] species;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;

	/**
	 * Create the panel - a labelled text field for the name and a labelled combo box to choose a species from.
	 * @param species
	 * All of the possible species, used to generate the pet after a species is chosen
	 * @param speciesNames
	 * The names of all of the species, used for the combo box
	 * @param toyTypes
	 * All of the toy types, used to assign a random favourite toy on pet generation
	 * @param foodTypes
	 * All of the food types, used to assign a random favourite food on pet generation
	 * @param semiBoldFont
	 * The font to be used for headings
	 * @param regularFont
	 * The font to be used for fields
	 * @param petNumber
	 * The number of the pet to be chosen, only used for display purposes
	 */
	public PetSetup(Species[] species, String[] speciesNames, ToyType[] toyTypes, FoodType[] foodTypes, Font semiBoldFont, Font regularFont, int petNumber) {
		setLayout(null);
		setOpaque(false);
		
		//The species label and combo box
		JLabel speciesLabel = new JLabel("Species");
		speciesLabel.setFont(semiBoldFont);
		speciesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		speciesLabel.setBounds(12, 55, 88, 15);
		add(speciesLabel);

		speciesChooser = new JComboBox<String>();
		speciesChooser.setModel(new DefaultComboBoxModel<String>(speciesNames));
		speciesChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				speciesIcon.setIcon(species[speciesChooser.getSelectedIndex()].getIcon());
			}
		});
		speciesChooser.setFont(regularFont);
		speciesChooser.setBounds(12, 72, 114, 26);
		add(speciesChooser);
		
		//The pet name label and field
		JLabel petNameLabel = new JLabel("Pet "+petNumber+"'s Name");
		petNameLabel.setFont(semiBoldFont);
		petNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		petNameLabel.setBounds(12, 8, 114, 15);
		add(petNameLabel);
		
		petNameField = new JTextField();
		petNameField.setFont(regularFont);
		petNameField.setBounds(12, 25, 114, 22);
		add(petNameField);
		petNameField.setColumns(10);
		
		speciesIcon = new JLabel(species[0].getIcon());
		speciesIcon.setBounds(130, 4, 100, 100);
		add(speciesIcon);
		
		//A semi-transparent background
		JLabel background = new JLabel(new ImageIcon(PetSetup.class.getResource("/images/backs/petSetup.png")));
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setBounds(0, 0, 235, 108);
		add(background);
		
		//Store references to the species, toyTypes and foodTypes arrays to use in pet generation
		this.species = species;
		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
	}
	
	/**
	 * Generates a new pet based off the user's inputs.
	 * @return
	 * The generated pet
	 */
	public Pet generatePet() {
		ToyType favouriteToy = toyTypes[ThreadLocalRandom.current().nextInt(0, toyTypes.length)];
		FoodType favouriteFood = foodTypes[ThreadLocalRandom.current().nextInt(0, foodTypes.length)];
		return new Pet(petNameField.getText(), species[speciesChooser.getSelectedIndex()], favouriteToy, favouriteFood); 
	}
	
	/**
	 * Check that all fields are filled.
	 * @return
	 * Whether all fields are filled
	 */
	public boolean fieldsFilled() {
		return !petNameField.getText().isEmpty();
	}
	
	/**
	 * Sets whether the fields and combo box are active.
	 * @param enabled
	 * Whether the fields and combo box will be set to active or inactive
	 */
	public void setFieldsEnabled(boolean enabled) {
		petNameField.setEnabled(enabled);
		speciesChooser.setEnabled(enabled);
	}
}
