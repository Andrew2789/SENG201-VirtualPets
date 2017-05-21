package application;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;

public class PetSetup extends JPanel {
	private static final long serialVersionUID = -4986198431577503898L;
	private JTextField petNameField;
	private JComboBox<String> speciesChooser;
	private JLabel speciesIcon;
	private Species[] species;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;

	/**
	 * Create the panel.
	 */
	public PetSetup(Species[] species, String[] speciesNames, ToyType[] toyTypes, FoodType[] foodTypes, Font semiBoldFont, Font regularFont, int petNumber) {
		setLayout(null);
		setOpaque(false);
				
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
		
		JLabel background = new JLabel(new ImageIcon("images/petSetupBack.png"));
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setBounds(0, 0, 235, 108);
		add(background);
		
		this.species = species;
		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
	}
	
	public Pet generatePet() {
		ToyType favouriteToy = toyTypes[ThreadLocalRandom.current().nextInt(0, toyTypes.length)];
		FoodType favouriteFood = foodTypes[ThreadLocalRandom.current().nextInt(0, foodTypes.length)];
		return new Pet(petNameField.getText(), species[speciesChooser.getSelectedIndex()], favouriteToy, favouriteFood); 
	}
}
