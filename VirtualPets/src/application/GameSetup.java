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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GameSetup extends JPanel {
	private static final long serialVersionUID = 1441493919925710008L;
	private int numberOfPlayers = 1;
	private int numberOfDays = 5;
	private int startingMoney = 150;
	private int incomePerTurn = 35;
	private PlayerSetup[] playerSetups;
	
	private JLabel fieldsEmptyLabel;
	private JButton buttonDone;
	private JButton buttonBack;

	/**
	 * Create the panel.
	 */
	public GameSetup(Species[] species, ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);
		
		String[] speciesNames = new String[species.length];
		for (int i=0; i<species.length; i++)
			speciesNames[i] = species[i].getName();
		
		playerSetups = new PlayerSetup[3];
		for (int i=0; i<3; i++) {
			playerSetups[i] = new PlayerSetup(species, speciesNames, toyTypes, foodTypes, semiBoldFont, regularFont, i+1);
			playerSetups[i].setBounds(40 + 240*i, 150, 240, 450);
			playerSetups[i].setVisible(false);
			add(playerSetups[i]);
		}
		playerSetups[0].setVisible(true);
		
		JLabel title = new JLabel("Game Setup");
		title.setForeground(Color.WHITE);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setBounds(240, 20, 320, 50);
		add(title);
		
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
		startingMoneyChooser.setModel(new SpinnerNumberModel(250, 0, 500, 10));
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
		
		buttonDone = new JButton("Done");
		buttonDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fieldsEmptyLabel.setVisible(true);
			}
		});
		
		fieldsEmptyLabel = new JLabel("Please enter something in all fields before continuing.");
		fieldsEmptyLabel.setForeground(Color.WHITE);
		fieldsEmptyLabel.setFont(boldFont);
		fieldsEmptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fieldsEmptyLabel.setBounds(50, 134, 468, 15);
		fieldsEmptyLabel.setVisible(false);
		add(fieldsEmptyLabel);
		
		buttonDone.setFont(boldFont);
		buttonDone.setBounds(558, 85, 70, 22);
		add(buttonDone);
		
		buttonBack = new JButton("Back");
		buttonBack.setFont(boldFont);
		buttonBack.setBounds(638, 85, 70, 22);
		add(buttonBack);
		
		JButton buttonSpeciesInfo = new JButton("Species Info");
		buttonSpeciesInfo.setFont(null);
		buttonSpeciesInfo.setBounds(558, 110, 150, 22);
		add(buttonSpeciesInfo);
		
		JLabel settingsBack = new JLabel(new ImageIcon(GameSetup.class.getResource("/images/backs/settings.png")));
		settingsBack.setBounds(40, 82, 493, 52);
		add(settingsBack);
		
		JLabel backgroundImage = new JLabel(new ImageIcon(GameSetup.class.getResource("/images/gameBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);
	}
	
	public JButton getDoneButton() {
		return buttonDone;
	}
	
	public JButton getBackButton() {
		return buttonBack;
	}
	
	public void setGamePlayers(Game game) {
		Player[] players = new Player[numberOfPlayers];
		for (int i=0; i<numberOfPlayers; i++)
			players[i] = playerSetups[i].generatePlayer(startingMoney);
		game.initialise(players, numberOfDays, incomePerTurn);
	}
	
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
