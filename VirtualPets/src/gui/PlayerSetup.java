import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.Font;

public class PlayerSetup extends JPanel {
	private JTextField textField;
	private PetSetup[] petSetups;

	/**
	 * Create the panel.
	 */
	public PlayerSetup() {
		setOpaque(false);
		setLayout(null);
		
		JLabel lblPlayerName = new JLabel("Player Name");
		lblPlayerName.setBounds(12, 12, 99, 15);
		add(lblPlayerName);
		
		textField = new JTextField();
		textField.setBounds(12, 28, 114, 19);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNumberOfPets = new JLabel("Number of Pets");
		lblNumberOfPets.setBounds(155, 12, 130, 15);
		add(lblNumberOfPets);
		
		JSlider slider = new JSlider();
		slider.setFont(new Font("Dialog", Font.BOLD, 10));
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(1);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(3);
		slider.setBounds(150, 25, 200, 50);
		add(slider);
		
		petSetups = new PetSetup[3];
		for (int i=0; i<3; i++) {
			petSetups[i] = new PetSetup();
			petSetups[i].setBounds(150*i, 70, 150, 100);
			add(petSetups[i]);
		}
	}
}
