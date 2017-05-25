package application;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JTextArea;

/**
 * @author Andrew Davidson (ada130)
 * A panel that takes up the entire screen, showing a main menu. From the main menu, the user can start a new game, 
 * load a game, create new assets, read about how to play the game, and quit.
 */
public class MainMenu extends JPanel {
	private static final long serialVersionUID = 5563328853841424713L;
	private JButton buttonNewGame, buttonLoadGame, buttonCreateNewAsset, buttonHelp, buttonQuit;
	private JPanel helpPanel;

	/**
	 * Create the main menu panel: title label, menu buttons, help screen popup with close button.
	 * @param titleFont
	 * The font to use for the title
	 * @param buttonFont
	 * The font to use for the buttons
	 * @param boldFont
	 * The font to use for the help screen's example slider
	 * @param helpFont
	 * The font to use for the text in the help screen
	 */
	public MainMenu(Font titleFont, Font buttonFont, Font boldFont, Font helpFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		// Help panel and subcomponents
		helpPanel = new JPanel();
		helpPanel.setOpaque(false);
		helpPanel.setBounds(0, 0, 800, 600);
		helpPanel.setVisible(false);
		helpPanel.setLayout(null);
		add(helpPanel);

		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				helpPanel.setVisible(false);
				setButtonsVisible(true);
			}
		});
		buttonClose.setBounds(350, 563, 100, 25);
		helpPanel.add(buttonClose);
		
		PetStatDisplayer helpSlider = new PetStatDisplayer(boldFont, helpFont, "Example Slider", 
				new ImageIcon(MainMenu.class.getResource("/images/sliders/help.png")), "An example slider.",
				new Color(127, 127, 127), 0, 100, 3, 8);
		helpSlider.setBounds(560, 330, 250, 50);
		helpSlider.setStat(50);
		helpPanel.add(helpSlider);
		
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
		helpPanel.add(helpLabel);
		
		JLabel helpBackground = new JLabel("");
		helpBackground.setIcon(new ImageIcon(MainMenu.class.getResource("/images/backs/helpBack.png")));
		helpBackground.setBounds(0, 0, 800, 600);
		helpPanel.add(helpBackground);
		// End help panel
		
		//Main menu title, buttons, and background
		JLabel title = new JLabel("Virtual Pets");
		title.setFont(titleFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(150, 40, 500, 80);
		add(title);
		
		buttonNewGame = new JButton("New Game");
		buttonNewGame.setFont(buttonFont);
		buttonNewGame.setEnabled(true);
		buttonNewGame.setBounds(300, 150, 200, 60);
		add(buttonNewGame);
		
		buttonLoadGame = new JButton("Load Game");
		buttonLoadGame.setFont(buttonFont);
		buttonLoadGame.setEnabled(true);
		buttonLoadGame.setBounds(300, 221, 200, 60);
		add(buttonLoadGame);
		
		buttonCreateNewAsset = new JButton("Create New Asset");
		buttonCreateNewAsset.setFont(buttonFont);
		buttonCreateNewAsset.setEnabled(true);
		buttonCreateNewAsset.setBounds(300, 292, 200, 60);
		add(buttonCreateNewAsset);
		
		buttonHelp = new JButton("Help");
		buttonHelp.setFont(buttonFont);
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsVisible(false);
				helpPanel.setVisible(true);
			}
		});
		buttonHelp.setEnabled(true);
		buttonHelp.setBounds(300, 363, 200, 60);
		add(buttonHelp);
		
		buttonQuit = new JButton("Quit");
		buttonQuit.setFont(buttonFont);
		buttonQuit.setEnabled(true);
		buttonQuit.setBounds(300, 434, 200, 60);
		add(buttonQuit);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(MainMenu.class.getResource("/images/menuBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);
	}
	
	// Getters (GuiRunner must be able to access these buttons to add relevant ActionListeners to them)
	public JButton getNewGameButton() {
		return buttonNewGame;
	}
	
	public JButton getLoadGameButton() {
		return buttonLoadGame;
	}

	public JButton getCreateNewAssetButton() {
		return buttonCreateNewAsset;
	}
	
	public JButton getQuitButton() {
		return buttonQuit;
	}
	// End Getters	
	
	/**
	 * Sets whether the menu buttons are visible. Used when displaying the help screen so that the buttons do not draw in
	 * front of the help panel at all.
	 * @param enabled
	 * Whether the buttons will be set to be visible
	 */
	public void setButtonsVisible(boolean enabled) {
		buttonNewGame.setVisible(enabled);
		buttonLoadGame.setVisible(enabled);
		buttonCreateNewAsset.setVisible(enabled);
		buttonHelp.setVisible(enabled);
		buttonQuit.setVisible(enabled);
	}
}
