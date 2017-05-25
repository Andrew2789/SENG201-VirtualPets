package application;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class GameSetup extends JPanel {
	private static final long serialVersionUID = 1441493919925710008L;
	private int numberOfPlayers = 1;
	private int numberOfDays = 5;
	private int startingMoney = 150;
	private int incomePerTurn = 35;
	private PlayerSetup[] playerSetups;
	
	private JLabel errorLabel;
	private JButton buttonDone, buttonBack;
	private boolean fieldsEnabled;

	/**
	 * Create the panel - spinners for numeric inputs and a number of PlayerSetups depending on user input into a combo box and an information panel displaying all species info to the user.
	 * @param species
	 * All of the species loaded, passed to PlayerSetups so that their pets can initialise
	 * @param toyTypes
	 * All of the toy types loaded, passed to PlayerSetups so that their pets can initialise
	 * @param foodTypes
	 * All of the food types loaded, passed to PlayerSetups so that their pets can initialise
	 * @param titleFont
	 * The font for the title
	 * @param boldFont
	 * The font for headings
	 * @param semiBoldFont
	 * The font for subheadings
	 * @param regularFont
	 * The font for field and input text
	 */
	public GameSetup(Species[] species, ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		//Create species info panel
		JPanel speciesDisplays = new JPanel();
		speciesDisplays.setLayout(null);
		speciesDisplays.setBackground(new Color(240, 255, 255));
		speciesDisplays.setPreferredSize(new Dimension(598, (species.length+1)/2*118));
		SpeciesDisplayer speciesDisplayer;
		for (int i=0; i<species.length; i++) {
			speciesDisplayer = new SpeciesDisplayer(species[i], boldFont, semiBoldFont);
			if ((i & 1) == 0)
				speciesDisplayer.setBounds(0, i/2*118, 300, 120);
			else
				speciesDisplayer.setBounds(298, i/2*118, 300, 120);
			speciesDisplays.add(speciesDisplayer);
		}
		
		JScrollPane speciesViewer = new JScrollPane(speciesDisplays);
		speciesViewer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		speciesViewer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		speciesViewer.setBounds(92, 125, 616, 358);
		speciesViewer.setVisible(false);
		speciesViewer.getVerticalScrollBar().setUnitIncrement(16);
		add(speciesViewer);

		String[] speciesNames = new String[species.length];
		for (int i=0; i<species.length; i++)
			speciesNames[i] = species[i].getName();
		
		//Create 3 PlayerSetups, but only show 1 by default
		playerSetups = new PlayerSetup[3];
		for (int i=0; i<3; i++) {
			playerSetups[i] = new PlayerSetup(species, speciesNames, toyTypes, foodTypes, semiBoldFont, regularFont, i+1);
			playerSetups[i].setBounds(40 + 240*i, 150, 240, 450);
			playerSetups[i].setVisible(false);
			add(playerSetups[i]);
		}
		playerSetups[0].setVisible(true);
		
		//The window title
		JLabel title = new JLabel("Game Setup");
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setBounds(240, 20, 320, 50);
		add(title);
		
		//Label and combo box to set number of players, changing number of PlayerSetups shown
		JLabel numberOfPlayersLabel = new JLabel("Number of Players");
		numberOfPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfPlayersLabel.setFont(boldFont);
		numberOfPlayersLabel.setBounds(50, 85, 115, 17);
		add(numberOfPlayersLabel);
		
		JComboBox<String> numberOfPlayersChooser = new JComboBox<String>();
		numberOfPlayersChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numberOfPlayers = numberOfPlayersChooser.getSelectedIndex()+1;
				for (int i=0; i<3; i++) {
					if (i > numberOfPlayers-1)
						playerSetups[i].setVisible(false);
					else
						playerSetups[i].setVisible(true);
				}
			}
		});
		numberOfPlayersChooser.setFont(regularFont);
		numberOfPlayersChooser.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		numberOfPlayersChooser.setBounds(87, 103, 40, 22);
		add(numberOfPlayersChooser);
		
		//Spinners and labels to let user input number of days, starting money, and income per turn
		JLabel numberOfDaysLabel = new JLabel("Number of Days");
		numberOfDaysLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfDaysLabel.setFont(boldFont);
		numberOfDaysLabel.setBounds(180, 86, 107, 17);
		add(numberOfDaysLabel);
		
		JSpinner numberOfDaysChooser = new JSpinner();
		numberOfDaysChooser.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				numberOfDays = (int)numberOfDaysChooser.getValue();
			}
		});
		numberOfDaysChooser.setModel(new SpinnerNumberModel(5, 1, null, 1));
		numberOfDaysChooser.setFont(null);
		numberOfDaysChooser.setBounds(210, 103, 47, 22);
		add(numberOfDaysChooser);
		
		JLabel startingMoneyLabel = new JLabel("Starting Money");
		startingMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startingMoneyLabel.setFont(boldFont);
		startingMoneyLabel.setBounds(305, 85, 96, 17);
		add(startingMoneyLabel);
		
		JSpinner startingMoneyChooser = new JSpinner();
		startingMoneyChooser.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				startingMoney = (int)startingMoneyChooser.getValue();
			}
		});
		startingMoneyChooser.setFont(regularFont);
		startingMoneyChooser.setModel(new SpinnerNumberModel(150, 0, 500, 10));
		startingMoneyChooser.setBounds(330, 103, 47, 22);
		add(startingMoneyChooser);
		
		JLabel moneyPerTurnLabel = new JLabel("Money per Turn");
		moneyPerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyPerTurnLabel.setFont(boldFont);
		moneyPerTurnLabel.setBounds(420, 85, 98, 17);
		add(moneyPerTurnLabel);
		
		JSpinner moneyPerTurnChooser = new JSpinner();
		moneyPerTurnChooser.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				incomePerTurn = (int)moneyPerTurnChooser.getValue();
			}
		});
		moneyPerTurnChooser.setFont(regularFont);
		moneyPerTurnChooser.setModel(new SpinnerNumberModel(35, 0, 100, 1));
		moneyPerTurnChooser.setBounds(445, 103, 47, 22);
		add(moneyPerTurnChooser);
		
		//Buttons to let the user advance or go back, and an error label to notify the user if their inputs are invalid
		buttonDone = new JButton("Done");
		buttonDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!fieldsFilled()) {
					errorLabel.setVisible(true);
					errorLabel.setText("Please enter something in all fields before continuing.");
				}
			}
		});
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.WHITE);
		errorLabel.setFont(boldFont);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setBounds(50, 134, 468, 15);
		errorLabel.setVisible(false);
		add(errorLabel);
		
		buttonDone.setFont(boldFont);
		buttonDone.setBounds(558, 85, 70, 22);
		add(buttonDone);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(boldFont);
		buttonBack.setBounds(638, 85, 70, 22);
		add(buttonBack);
		
		//Buttons to open and close species info, and a generic ActionListener which they both use.
		JButton buttonCloseSpeciesViewer = new JButton("Close Species Viewer");
		JButton buttonSpeciesInfo = new JButton("Species Info");
		
		fieldsEnabled = true;
		ActionListener flipEnabled = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fieldsEnabled = !fieldsEnabled;
				speciesViewer.setVisible(!fieldsEnabled);
				buttonCloseSpeciesViewer.setVisible(!fieldsEnabled);
				buttonSpeciesInfo.setVisible(fieldsEnabled);
				buttonBack.setVisible(fieldsEnabled);
				buttonDone.setVisible(fieldsEnabled);
				numberOfPlayersChooser.setEnabled(fieldsEnabled);
				numberOfDaysChooser.setEnabled(fieldsEnabled);
				startingMoneyChooser.setEnabled(fieldsEnabled);
				moneyPerTurnChooser.setEnabled(fieldsEnabled);
				for (PlayerSetup playerSetup: playerSetups)
					playerSetup.setFieldsEnabled(fieldsEnabled);
			}
		};
		
		buttonCloseSpeciesViewer.addActionListener(flipEnabled);
		buttonCloseSpeciesViewer.setBounds(300, 535, 200, 45);
		buttonCloseSpeciesViewer.setVisible(false);
		add(buttonCloseSpeciesViewer);
		
		buttonSpeciesInfo.addActionListener(flipEnabled);
		buttonSpeciesInfo.setFont(null);
		buttonSpeciesInfo.setBounds(558, 110, 150, 22);
		add(buttonSpeciesInfo);
		
		//Semi-transparent settings background, and overall window background
		JLabel settingsBack = new JLabel(new ImageIcon(GameSetup.class.getResource("/images/backs/settings.png")));
		settingsBack.setBounds(40, 82, 493, 52);
		add(settingsBack);
		
		JLabel backgroundImage = new JLabel(new ImageIcon(GameSetup.class.getResource("/images/gameBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);
	}
	
	//Getters
	public JButton getDoneButton() {
		return buttonDone;
	}
	
	public JButton getBackButton() {
		return buttonBack;
	}
	//End getters
	
	/**
	 * Use user's input to either initialise a new game or notify them of invalid inputs.
	 * @param game
	 * The game to initialise if input is valid
	 * @return
	 * Whether the game was initilised or if there was invalid inputs
	 */
	public boolean setGamePlayers(Game game) {
		Player[] players = new Player[numberOfPlayers];
		for (int i=0; i<numberOfPlayers; i++)
			players[i] = playerSetups[i].generatePlayer(startingMoney);
		
		//Check that there are no duplicate names
		ArrayList<String> usedPlayerNames = new ArrayList<String>();
		ArrayList<String> usedPetNames = new ArrayList<String>();
		for (Player player: players) {
			if (usedPlayerNames.contains(player.getName())) {
				errorLabel.setText("Multiple players named "+player.getName()+". Please give all players and pets unique names.");
				errorLabel.setVisible(true);
				return false;
			}
			usedPlayerNames.add(player.getName());
			for (Pet pet: player.getPets()) {
				if (usedPetNames.contains(pet.getName())) {
					errorLabel.setText("Multiple pets named "+pet.getName()+". Please give all players and pets unique names.");
					errorLabel.setVisible(true);
					return false;
				}
				usedPetNames.add(pet.getName());
			}
		}
		game.initialise(players, numberOfDays, incomePerTurn);
		return true;
	}
	
	/**
	 * Check whether all shown fields have some input entered.
	 * @return
	 * Whether all fields have some input entered
	 */
	public boolean fieldsFilled() {
		boolean filled = true;
		for (int i=0; i<numberOfPlayers; i++) {
			if (!playerSetups[i].fieldsFilled()) {
				filled = false;
				break;
			}
		}
		return filled;
	}
}
