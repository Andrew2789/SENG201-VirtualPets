package application;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class RoundOverview extends JPanel {
	private static final long serialVersionUID = -3308469502655225114L;
	private JLabel dayLabel;
	private JLabel[][] playerLabels;
	private JButton buttonContinue;
	private int[] previousScores;

	/**
	 * Create the panel.
	 */
	public RoundOverview(Font titleFont, Font subtitleFont, Font boldFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		playerLabels = new JLabel[3][2];
		previousScores = new int[3];
		
		dayLabel = new JLabel("");
		dayLabel.setForeground(Color.WHITE);
		dayLabel.setFont(titleFont);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(150, 10, 500, 50);
		add(dayLabel);
		
		playerLabels[0][0] = new JLabel("");
		playerLabels[0][0].setForeground(Color.WHITE);
		playerLabels[0][0].setFont(subtitleFont);
		playerLabels[0][0].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[0][0].setBounds(200, 100, 400, 30);
		add(playerLabels[0][0]);
		
		playerLabels[0][1] = new JLabel("");
		playerLabels[0][1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[0][1].setForeground(Color.WHITE);
		playerLabels[0][1].setFont(boldFont);
		playerLabels[0][1].setBounds(200, 131, 400, 30);
		add(playerLabels[0][1]);
		
		playerLabels[1][0] = new JLabel("");
		playerLabels[1][0].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[1][0].setForeground(Color.WHITE);
		playerLabels[1][0].setFont(subtitleFont);
		playerLabels[1][0].setBounds(200, 205, 400, 30);
		add(playerLabels[1][0]);
		
		playerLabels[1][1] = new JLabel("");
		playerLabels[1][1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[1][1].setForeground(Color.WHITE);
		playerLabels[1][1].setFont(boldFont);
		playerLabels[1][1].setBounds(200, 236, 400, 30);
		add(playerLabels[1][1]);
		
		playerLabels[2][0] = new JLabel("");
		playerLabels[2][0].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[2][0].setForeground(Color.WHITE);
		playerLabels[2][0].setFont(subtitleFont);
		playerLabels[2][0].setBounds(200, 311, 400, 30);
		add(playerLabels[2][0]);
		
		playerLabels[2][1] = new JLabel("");
		playerLabels[2][1].setHorizontalAlignment(SwingConstants.CENTER);
		playerLabels[2][1].setForeground(Color.WHITE);
		playerLabels[2][1].setFont(boldFont);
		playerLabels[2][1].setBounds(200, 342, 400, 30);
		add(playerLabels[2][1]);
		
		buttonContinue = new JButton("Continue");
		buttonContinue.setBounds(300, 450, 200, 50);
		buttonContinue.setFont(boldFont);
		add(buttonContinue);
		
		JLabel background = new JLabel(new ImageIcon(Game.class.getResource("/images/gameBackground.png")));
		background.setBounds(0, 0, 800, 600);
		add(background);
	}

	public void displayEndOfRound(int dayNumber, Player[] players) {
		dayLabel.setText("Score Summary for Day "+dayNumber);
		for (int i=0; i<3; i++) {
			if (i < players.length) {
				playerLabels[i][0].setText(players[i].getName());
				playerLabels[i][1].setText(String.format("Score: %d + %d from this round = %d", previousScores[i], players[i].getScore()-previousScores[i], players[i].getScore()));
				previousScores[i] = players[i].getScore();
			}
			else {
				playerLabels[i][0].setText("");
				playerLabels[i][1].setText("");
			}
		}
	}
	
	public JButton getButtonContinue() {
		return buttonContinue;
	}
}
