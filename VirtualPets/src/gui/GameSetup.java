package gui;
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
import playerData.Player;
import playerData.Species;

public class GameSetup extends JPanel {
	private static final long serialVersionUID = 1441493919925710008L;
	private int numberOfPlayers;
	private int numberOfDays = 5;
	private int startingMoney = 150;
	private int incomePerTurn = 35;
	private PlayerSetup[] playerSetups;

	/**
	 * Create the panel.
	 */
	public GameSetup(Species[] species, Font titleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);
		
		playerSetups = new PlayerSetup[3];
		for (int i=0; i<3; i++) {
			playerSetups[i] = new PlayerSetup(semiBoldFont, regularFont, i+1);
			playerSetups[i].setBounds(40 + 240*i, 150, 240, 450);
			playerSetups[i].setVisible(false);
			add(playerSetups[i]);
		}
		playerSetups[0].setVisible(true);
		
		JLabel title = new JLabel("Game Setup");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(titleFont);
		title.setBounds(240, 30, 320, 50);
		add(title);
		
		JLabel numberOfPlayersLabel = new JLabel("Number of Players");
		numberOfPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfPlayersLabel.setFont(boldFont);
		numberOfPlayersLabel.setBounds(100, 85, 115, 17);
		add(numberOfPlayersLabel);
		
		JComboBox<String> numberOfPlayersChooser = new JComboBox<String>();
		numberOfPlayersChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numberOfPlayers = numberOfPlayersChooser.getSelectedIndex();
				for (int i=0; i<3; i++) {
					if (i > numberOfPlayers)
						playerSetups[i].setVisible(false);
					else
						playerSetups[i].setVisible(true);
				}
			}
		});
		numberOfPlayersChooser.setFont(regularFont);
		numberOfPlayersChooser.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		numberOfPlayersChooser.setBounds(137, 103, 40, 22);
		add(numberOfPlayersChooser);
		
		JLabel numberOfDaysLabel = new JLabel("Number of Days");
		numberOfDaysLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numberOfDaysLabel.setFont(boldFont);
		numberOfDaysLabel.setBounds(223, 86, 107, 17);
		add(numberOfDaysLabel);
		
		JSpinner numberOfDaysChooser = new JSpinner();
		numberOfDaysChooser.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		numberOfDaysChooser.setFont(null);
		numberOfDaysChooser.setBounds(252, 103, 47, 22);
		add(numberOfDaysChooser);
		
		JLabel startingMoneyLabel = new JLabel("Starting Money");
		startingMoneyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startingMoneyLabel.setFont(boldFont);
		startingMoneyLabel.setBounds(340, 85, 96, 17);
		add(startingMoneyLabel);
		
		JSpinner startingMoneyChooser = new JSpinner();
		startingMoneyChooser.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				startingMoney = (int)startingMoneyChooser.getValue();
			}
		});
		startingMoneyChooser.setFont(regularFont);
		startingMoneyChooser.setModel(new SpinnerNumberModel(250, 0, 500, 10));
		startingMoneyChooser.setBounds(365, 103, 47, 22);
		add(startingMoneyChooser);
		
		JLabel moneyPerTurnLabel = new JLabel("Money per Turn");
		moneyPerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		moneyPerTurnLabel.setFont(boldFont);
		moneyPerTurnLabel.setBounds(450, 85, 98, 17);
		add(moneyPerTurnLabel);
		
		JSpinner moneyPerTurnChooser = new JSpinner();
		moneyPerTurnChooser.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				incomePerTurn = (int)moneyPerTurnChooser.getValue();
			}
		});
		moneyPerTurnChooser.setFont(regularFont);
		moneyPerTurnChooser.setModel(new SpinnerNumberModel(35, 0, 100, 1));
		moneyPerTurnChooser.setBounds(475, 103, 47, 22);
		add(moneyPerTurnChooser);
		
		JButton buttonDone = new JButton("Done");
		buttonDone.setFont(boldFont);
		buttonDone.setBounds(561, 101, 89, 25);
		add(buttonDone);
		
		JButton buttonBack = new JButton("Back");
		buttonBack.setFont(boldFont);
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonBack.setBounds(651, 101, 89, 25);
		add(buttonBack);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setBounds(0, 0, 800, 600);
		backgroundImage.setIcon(new ImageIcon("images/setupBackground.png"));
		add(backgroundImage);
	}
	
	public Game generateGame() {
		Player[] players = new Player[numberOfPlayers];
		for (int i=0; i<numberOfPlayers; i++)
			players[i] = new Player(playerSetups[i].getPlayerName(), playerSetups[i].getPets(), startingMoney);
		return new Game(players, numberOfDays, incomePerTurn);
	}
}
