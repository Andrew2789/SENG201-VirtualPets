package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * A panel that displays information explaining the game mechanics and controls.
 * @author Andrew Davidson (ada130)
 */
public class HelpPanel extends JPanel {
	private static final long serialVersionUID = -1616396715859775492L;
	private JButton buttonClose;
	
	/**
	 * Shows help about the game.
	 * @param boldFont
	 * The font to use for the slider label
	 * @param helpFont
	 * The font to use for the help paragraphs
	 */
	public HelpPanel(Font boldFont, Font helpFont) {
		setSize(800, 600);
		setVisible(false);
		setLayout(null);

		buttonClose = new JButton("Close");
		buttonClose.setBounds(350, 563, 100, 25);
		add(buttonClose);
		
		PetStatDisplayer helpSlider = new PetStatDisplayer(boldFont, helpFont, "Example Slider", 
				new ImageIcon(MainMenu.class.getResource("/images/sliders/help.png")), "An example slider.",
				new Color(127, 127, 127), 0, 100, 3, 8);
		helpSlider.setBounds(560, 330, 250, 50);
		helpSlider.setStat(50);
		add(helpSlider);
		
		JTextArea helpLabel = new JTextArea();
		helpLabel.setFont(helpFont);
		helpLabel.setToolTipText("");
		helpLabel.setOpaque(false);
		helpLabel.setWrapStyleWord(true);
		helpLabel.setLineWrap(true);
		helpLabel.setForeground(Color.BLACK);
		helpLabel.setBackground(Color.GRAY);
		helpLabel.setEditable(false);
		helpLabel.setText("The game is played for a user specified number of days. Each player can choose up to 3 pets in the game setup, giving them a name and a species. The pets will have a favourite food and toy that are randomly generated.\n\nDuring each day, every player may complete up to 2 actions with each of their pets (feeding, playing, resting, going to the toilet, curing them if they are sick, or disciplining them if they are misbehaving). These actions all change the pet's status - pets can vary in hunger, energy, happiness, weight, whether they are healthy, and whether they are behaving. \nWhen playing with a pet, they gain happiness dependent on the toy's happiness gain stat, but lose some energy and gain some hunger. If it is their favourite toy, they gain 1.5x as much happiness. \nWhen feeding a pet, they gain happiness dependent on the food's tastiness, lose hunger based on the food's nutrition, and gain weight based on the food's weight. If it is their favourite food, they gain 1.5x as much happiness. \n\nPlayers may visit the shop to buy food and toys for their pets - players start with a user specified amount of money, and have a user specified income per turn.\n\nPet's status bars also show the values at which they start having chances of gaining detrimental effects at the end of each turn:\n- In green regions, pets have a chance to get sick. While sick, they will lose an additional\n10 energy and 10 happiness per turn.\n- In blue regions, pets have a chance to start misbehaving. While misbehaving, they will \nlose an additional 20 happiness per turn.\n- In red regions, pets have a chance to die.\n- In the orange region of hunger, pets will start to start to starve, quickly losing weight, happiness and energy until they are fed.\n\nPets may be revived once for $100, but they will stay permanently dead if they die again. When a pet is revived, they will have 0 action points for the remainder of that day, but they will be set to reasonably healthy stats.\nPlayers gain score for each pet depending on their status at the end of each round. The player with the highest score at the end of the game wins!");
		helpLabel.setBounds(8, 12, 784, 540);
		add(helpLabel);

		JLabel helpBack = new JLabel("");
		helpBack.setIcon(new ImageIcon(HelpPanel.class.getResource("/images/backs/helpBack.png")));
		helpBack.setBounds(0, 0, 800, 600);
		add(helpBack);

		JLabel helpBackground = new JLabel("");
		helpBackground.setIcon(new ImageIcon(HelpPanel.class.getResource("/images/menuBackground.png")));
		helpBackground.setBounds(0, 0, 800, 600);
		add(helpBackground);
	}
	
	/**
	 * Sets the action on close.
	 * @param newAl
	 * The new action listener for when close is pressed
	 */
	public void setActionListener(ActionListener newAl) {
		for (ActionListener al: buttonClose.getActionListeners())
			buttonClose.removeActionListener(al);
		buttonClose.addActionListener(newAl);
	}
}
