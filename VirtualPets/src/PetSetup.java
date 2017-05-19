import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PetSetup extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PetSetup() {
		setOpaque(false);
		setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(12, 64, 126, 24);
		add(comboBox);
		
		JLabel lblSpecies = new JLabel("Species");
		lblSpecies.setBounds(12, 47, 70, 15);
		add(lblSpecies);
		
		JLabel lblPetName = new JLabel("Pet Name");
		lblPetName.setBounds(12, 12, 70, 15);
		add(lblPetName);
		
		textField = new JTextField();
		textField.setBounds(12, 28, 114, 19);
		add(textField);
		textField.setColumns(10);
	}
}
