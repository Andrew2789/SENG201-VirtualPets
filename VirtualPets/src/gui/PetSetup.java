package gui;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PetSetup extends JPanel {
	private JTextField petNameField;

	/**
	 * Create the panel.
	 */
	public PetSetup() {
		setLayout(null);
		setOpaque(false);
		
		JComboBox<String> speciesChooser = new JComboBox<String>();
		speciesChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		speciesChooser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		speciesChooser.setBounds(12, 75, 114, 26);
		add(speciesChooser);
		
		JLabel speciesChooserLabel = new JLabel("Species");
		speciesChooserLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		speciesChooserLabel.setHorizontalAlignment(SwingConstants.LEFT);
		speciesChooserLabel.setBounds(12, 58, 178, 15);
		add(speciesChooserLabel);
		
		JLabel petNameFieldLabel = new JLabel("Pet Name");
		petNameFieldLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		petNameFieldLabel.setHorizontalAlignment(SwingConstants.LEFT);
		petNameFieldLabel.setBounds(12, 11, 114, 15);
		add(petNameFieldLabel);
		
		petNameField = new JTextField();
		petNameField.setBounds(12, 28, 114, 19);
		add(petNameField);
		petNameField.setColumns(10);
		
		JLabel speciesIcon = new JLabel("");
		speciesIcon.setBounds(130, 11, 100, 100);
		add(speciesIcon);
	}
}
