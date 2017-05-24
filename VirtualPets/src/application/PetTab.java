package application;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class PetTab extends JPanel {
	private static final long serialVersionUID = -4840106067673959643L;
	private JButton clickDetector;
	private JLabel deadOverlay;
	private JLabel petIcon;
	private JLabel petNameLabel;
	private JLabel actionPointsLabel;

	/**
	 * Create the panel.
	 */
	public PetTab(Font semiBoldFont) {
		setLayout(null);
		setOpaque(false);
		
		deadOverlay = new JLabel("");
		deadOverlay.setIcon(new ImageIcon(PetTab.class.getResource("/images/Dead.png")));
		deadOverlay.setBounds(0, 0, 150, 149);
		deadOverlay.setVisible(false);
		add(deadOverlay);
		
		petIcon = new JLabel("");
		petIcon.setBounds(24, 28, 100, 100);
		add(petIcon);
		
		petNameLabel = new JLabel("");
		petNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		petNameLabel.setFont(semiBoldFont);
		petNameLabel.setBounds(10, 7, 130, 18);
		add(petNameLabel);
		
		actionPointsLabel = new JLabel("");
		actionPointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actionPointsLabel.setFont(semiBoldFont);
		actionPointsLabel.setBounds(10, 131, 130, 18);
		add(actionPointsLabel);
		
		
		clickDetector = new JButton(new ImageIcon(PetTab.class.getResource("/images/backs/petTab.png")));
		clickDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		clickDetector.setContentAreaFilled(false);
		clickDetector.setBorderPainted(false);
		clickDetector.setBounds(0, 0, 150, 155);
		add(clickDetector);
	}
	
	public void setPet(Pet pet) {
		petNameLabel.setText(pet.getName());
		petIcon.setIcon(pet.getSpecies().getIcon());
		deadOverlay.setVisible(!pet.isAlive());
	}
	
	public void setActionPoints(int amount) {
		actionPointsLabel.setText("Action Points: "+amount);
	}
	
	public JButton getClickDetector() {
		return clickDetector;
	}
	
	public void setButtonEnabled(boolean enabled) {
		clickDetector.setEnabled(enabled);
	}
}
