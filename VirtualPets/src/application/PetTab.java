package application;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

/**
 * @author Andrew Davidson (ada130)
 * A clickable tab which will switch the selected pet to the pet represented in this tab, the tab also displays
 * status effects and action points of the pet.
 */
public class PetTab extends JPanel {
	private static final long serialVersionUID = -4840106067673959643L;
	private JButton clickDetector;
	private JLabel deadOverlay, sickIcon, misbehavingIcon, starvingIcon;
	private JLabel petNameLabel, petIcon, actionPointsLabel;

	/**
	 * Create the pet tab panel - the button, the status overlays, the name/action points labels, and the pet's icon.
	 */
	public PetTab(Font semiBoldFont) {
		setLayout(null);
		setOpaque(false);
		
		//Status effect icons
		deadOverlay = new JLabel("");
		deadOverlay.setIcon(new ImageIcon(PetTab.class.getResource("/images/Dead.png")));
		deadOverlay.setBounds(0, 0, 150, 149);
		deadOverlay.setVisible(false);
		add(deadOverlay);
		
		sickIcon = new JLabel("");
		sickIcon.setToolTipText("This pet is sick.");
		sickIcon.setIcon(new ImageIcon(PetTab.class.getResource("/images/statuses/Sick.png")));
		sickIcon.setBounds(120, 36, 20, 20);
		add(sickIcon);
		
		misbehavingIcon = new JLabel("");
		misbehavingIcon.setToolTipText("This pet is misbehaving.");
		misbehavingIcon.setIcon(new ImageIcon(PetTab.class.getResource("/images/statuses/Misbehaving.png")));
		misbehavingIcon.setBounds(120, 60, 20, 20);
		add(misbehavingIcon);
		
		starvingIcon = new JLabel("");
		starvingIcon.setToolTipText("This pet is starving!");
		starvingIcon.setIcon(new ImageIcon(PetTab.class.getResource("/images/statuses/Starving.png")));
		starvingIcon.setBounds(120, 84, 20, 20);
		add(starvingIcon);
		//End status effect icons
		
		//Pet name, icon, action points
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
		//End pet info
		
		//Button to detect clicks on this tab
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
	
	//Getters and setters
	public JButton getClickDetector() {
		return clickDetector;
	}
	
	public void setButtonEnabled(boolean enabled) {
		clickDetector.setEnabled(enabled);
	}
	//End getters and setters
	
	/**
	 * Set this tab to display information about a specified pet.
	 * @param pet
	 * The pet to display info about
	 */
	public void setPet(Pet pet) {
		petNameLabel.setText(pet.getName());
		petIcon.setIcon(pet.getSpecies().getIcon());
		deadOverlay.setVisible(!pet.isAlive());
		sickIcon.setVisible(!pet.isHealthy());
		misbehavingIcon.setVisible(!pet.isBehaving());
		starvingIcon.setVisible((pet.getHunger() >= 90));
		actionPointsLabel.setText("Action Points: "+pet.getActionPoints());
	}
}
