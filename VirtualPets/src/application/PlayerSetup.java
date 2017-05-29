package application;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

import model.FoodType;
import model.Pet;
import model.Player;
import model.Species;
import model.ToyType;

/**
 * Allows the user to set up a player, giving it a name and pets. Can generate a player. GameSetup should use multiple PlayerSetups.
 * @author Andrew Davidson (ada130)
 */
public class PlayerSetup extends JPanel {
	private static final long serialVersionUID = -138517277103698226L;
	private JTextField playerNameField;
	private JComboBox<String> petAmountChooser;
	private PetSetup[] petSetups;
	private int numberOfPets = 1;
	
	/**
	 * Create the panel - a name entry field, a number of pets spinner, and PetSetups.
	 * @param species
	 * All of the species currently loaded in the game, passed to PetSetups
	 * @param speciesNames
	 * The names of all of the species currently loaded in the game, passed to PetSetups
	 * @param toyTypes
	 * All of the toy types currently laoded in the game, passed to PetSetups
	 * @param foodTypes
	 * All of the food types currently loaded in the game, passed to PetSetups
	 * @param semiBoldFont
	 * The font to use for headings
	 * @param regularFont
	 * The font to use for fields and combo boxes
	 * @param playerNumber
	 * The number of this player, used for display purposes only
	 */
	public PlayerSetup(Species[] species, String[] speciesNames, ToyType[] toyTypes, FoodType[] foodTypes, Font semiBoldFont, Font regularFont, int playerNumber) {
		setLayout(null);
		setOpaque(false);
		
		//Create 3 pet setups, but only show 1 by default
		petSetups = new PetSetup[3];
		for (int i=0; i<3; i++) {
			petSetups[i] = new PetSetup(species, speciesNames, toyTypes, foodTypes, semiBoldFont, regularFont, i+1);
			petSetups[i].setBounds(0, 60 + 110*i, 240, 125);
			petSetups[i].setVisible(false);
			add(petSetups[i]);
		}
		petSetups[0].setVisible(true);
		
		//Player name input field and label
		JLabel playerNameLabel = new JLabel("Player "+playerNumber+"'s Name");
		playerNameLabel.setFont(semiBoldFont);
		playerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		playerNameLabel.setBounds(10, 10, 114, 15);
		add(playerNameLabel);
		
		playerNameField = new JTextField();
		playerNameField.setFont(regularFont);
		playerNameField.setBounds(10, 27, 114, 22);
		add(playerNameField);
		playerNameField.setColumns(10);
		
		//Pet amount combo box and label
		JLabel petAmountLabel = new JLabel("Number of Pets");
		petAmountLabel.setFont(semiBoldFont);
		petAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		petAmountLabel.setBounds(134, 10, 94, 15);
		add(petAmountLabel);
		
		petAmountChooser = new JComboBox<String>();
		petAmountChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numberOfPets = petAmountChooser.getSelectedIndex()+1;
				for (int i=0; i<3; i++) {
					if (i > numberOfPets-1)
						petSetups[i].setVisible(false);
					else
						petSetups[i].setVisible(true);
				}
			}
		});
		petAmountChooser.setFont(regularFont);
		petAmountChooser.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		petAmountChooser.setBounds(134, 27, 40, 21);
		add(petAmountChooser);
		
		//A semi-transparent background
		JLabel label = new JLabel(new ImageIcon(PlayerSetup.class.getResource("/images/backs/playerSetup.png")));
		label.setBounds(0, 0, 235, 58);
		add(label);
	}
	
	/**
	 * Generates a new player based on user inputs.
	 * @param startingMoney
	 * The amount of money this player should be initialised with
	 * @return
	 * The new player
	 */
	public Player generatePlayer(int startingMoney) {
		Pet[] pets = new Pet[numberOfPets];
		for (int i=0; i<numberOfPets; i++)
			pets[i] = petSetups[i].generatePet();
		return new Player(playerNameField.getText(), pets, startingMoney);
	}
	
	/**
	 * Check if all fields have some input, and check the same for all shown PetSetups.
	 * @return
	 * Whether all fields have some input in them
	 */
	public boolean fieldsFilled() {
		if (playerNameField.getText().isEmpty())
			return false;
		boolean filled = true;
		for (int i=0; i<numberOfPets; i++) {
			if (!petSetups[i].fieldsFilled()) {
				filled = false;
				break;
			}
		}
		return filled;
	}
	
	/**
	 * Set whether all fields and combo boxes are enabled, and the same for all PetSetups.
	 * @param enabled
	 * Whether to set fields and combo boxes active or inactive
	 */
	public void setFieldsEnabled(boolean enabled) {
		playerNameField.setEnabled(enabled);
		petAmountChooser.setEnabled(enabled);
		for (PetSetup petSetup: petSetups)
			petSetup.setFieldsEnabled(enabled);
	}
}
