package application;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class PlayerSetup extends JPanel {
	private static final long serialVersionUID = -138517277103698226L;
	private JTextField playerNameField;
	private PetSetup[] petSetups;
	private int numberOfPets = 1;

	/**
	 * Create the panel.
	 */
	public PlayerSetup(Species[] species, String[] speciesNames, ToyType[] toyTypes, FoodType[] foodTypes, Font semiBoldFont, Font regularFont, int playerNumber) {
		setLayout(null);
		setOpaque(false);
		
		petSetups = new PetSetup[3];
		for (int i=0; i<3; i++) {
			petSetups[i] = new PetSetup(species, speciesNames, toyTypes, foodTypes, semiBoldFont, regularFont, i+1);
			petSetups[i].setBounds(0, 60 + 110*i, 240, 125);
			petSetups[i].setVisible(false);
			add(petSetups[i]);
		}
		petSetups[0].setVisible(true);
		
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
		
		JLabel petAmountLabel = new JLabel("Number of Pets");
		petAmountLabel.setFont(semiBoldFont);
		petAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		petAmountLabel.setBounds(134, 10, 94, 15);
		add(petAmountLabel);
		
		JComboBox<String> petAmountChooser = new JComboBox<String>();
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
		
		JLabel label = new JLabel(new ImageIcon(PlayerSetup.class.getResource("/images/backs/playerSetup.png")));
		label.setBounds(0, 0, 235, 58);
		add(label);
	}
	
	public Player generatePlayer(int startingMoney) {
		Pet[] pets = new Pet[numberOfPets];
		for (int i=0; i<numberOfPets; i++)
			pets[i] = petSetups[i].generatePet();
		return new Player(playerNameField.getText(), pets, startingMoney);
	}
}
