package gui;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class PetSetup extends JPanel {
	private static final long serialVersionUID = -4986198431577503898L;
	private JTextField petNameField;

	/**
	 * Create the panel.
	 */
	public PetSetup(Font semiBoldFont, Font regularFont, int petNumber) {
		setLayout(null);
		setOpaque(false);
		
		JComboBox<String> speciesChooser = new JComboBox<String>();
		speciesChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		speciesChooser.setFont(regularFont);
		speciesChooser.setBounds(12, 72, 114, 26);
		add(speciesChooser);
		
		JLabel speciesLabel = new JLabel("Species");
		speciesLabel.setFont(semiBoldFont);
		speciesLabel.setHorizontalAlignment(SwingConstants.LEFT);
		speciesLabel.setBounds(12, 55, 88, 15);
		add(speciesLabel);
		
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
		
		JLabel speciesIcon = new JLabel("");
		speciesIcon.setBounds(130, 10, 100, 100);
		add(speciesIcon);
		
		JLabel background = new JLabel("");
		background.setVerticalAlignment(SwingConstants.TOP);
		background.setIcon(new ImageIcon("images/petSetupBack.png"));
		background.setBounds(0, 0, 235, 108);
		add(background);
	}
}
