package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameSetup extends JPanel {
	private int startingMoney = 150;
	private int incomePerTurn = 35;
	private PlayerSetup[] playerSetups;

	/**
	 * Create the panel.
	 */
	public GameSetup(Font titleFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);
		
		playerSetups = new PlayerSetup[3];
		for (int i=0; i<3; i++) {
			playerSetups[i] = new PlayerSetup();
			playerSetups[i].setBounds(40 + 240*i, 150, 240, 450);
			add(playerSetups[i]);
		}
		
		JLabel gameSetupTitle = new JLabel("Game Setup");
		gameSetupTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gameSetupTitle.setFont(titleFont);
		gameSetupTitle.setBounds(240, 30, 320, 50);
		add(gameSetupTitle);
		
		JLabel lblNumberOfPlayers = new JLabel("Number of Players");
		lblNumberOfPlayers.setFont(regularFont);
		lblNumberOfPlayers.setBounds(130, 85, 100, 17);
		add(lblNumberOfPlayers);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		comboBox.setBounds(130, 103, 40, 20);
		add(comboBox);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(250, 0, 500, 10));
		spinner.setBounds(260, 103, 47, 20);
		add(spinner);
		
		JLabel lblStartingMoney = new JLabel("Starting Money");
		lblStartingMoney.setFont(regularFont);
		lblStartingMoney.setBounds(260, 85, 82, 17);
		add(lblStartingMoney);
		
		JLabel lblIncomePerTurn = new JLabel("Money per Turn");
		lblIncomePerTurn.setFont(regularFont);
		lblIncomePerTurn.setBounds(380, 85, 100, 17);
		add(lblIncomePerTurn);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(35, 0, 100, 1));
		spinner_1.setBounds(380, 103, 47, 20);
		add(spinner_1);
		
		JButton btnDone = new JButton("Done");
		btnDone.setBounds(490, 91, 89, 23);
		add(btnDone);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBack.setBounds(590, 91, 89, 23);
		add(btnBack);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 800, 600);
		label.setIcon(new ImageIcon("images/setupBackground.png"));
		add(label);
		
		
	}
}
