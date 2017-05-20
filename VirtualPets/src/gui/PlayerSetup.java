package gui;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class PlayerSetup extends JPanel {
	private JTextField textField;
	private PetSetup[] petSetups;

	/**
	 * Create the panel.
	 */
	public PlayerSetup() {
		setLayout(null);
		setOpaque(false);
		
		petSetups = new PetSetup[3];
		for (int i=0; i<3; i++) {
			petSetups[i] = new PetSetup();
			petSetups[i].setBounds(0, 75 + 125*i, 240, 125);
			add(petSetups[i]);
		}
		
		JLabel lblPlayerName = new JLabel("Player %d's Name");
		lblPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPlayerName.setHorizontalAlignment(SwingConstants.LEFT);
		lblPlayerName.setBounds(12, 12, 114, 15);
		add(lblPlayerName);
		
		textField = new JTextField();
		textField.setBounds(12, 28, 114, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNumberOfPets = new JLabel("Number of Pets");
		lblNumberOfPets.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNumberOfPets.setHorizontalAlignment(SwingConstants.CENTER);
		lblNumberOfPets.setBounds(136, 12, 94, 15);
		add(lblNumberOfPets);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3"}));
		comboBox.setBounds(163, 27, 40, 20);
		add(comboBox);
		
		
	}
}
