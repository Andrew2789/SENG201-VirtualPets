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
	private JButton buttonEndGame;
	private int[] previousScores;
	private JLabel winnerLabel;

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
		dayLabel.setBounds(100, 10, 600, 50);
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
		
		winnerLabel = new JLabel("");
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setForeground(Color.WHITE);
		winnerLabel.setFont(subtitleFont);
		winnerLabel.setBounds(200, 380, 400, 40);
		add(winnerLabel);
		
		buttonContinue = new JButton("Continue");
		buttonContinue.setBounds(300, 450, 200, 50);
		buttonContinue.setFont(boldFont);
		add(buttonContinue);
		
		buttonEndGame = new JButton("End Game");
		buttonEndGame.setBounds(300, 450, 200, 50);
		buttonEndGame.setFont(boldFont);
		add(buttonEndGame);
		
		JLabel background = new JLabel(new ImageIcon(Game.class.getResource("/images/gameBackground.png")));
		background.setBounds(0, 0, 800, 600);
		add(background);
	}

	public void initialise() {
		winnerLabel.setText("");
		buttonContinue.setVisible(true);
		buttonEndGame.setVisible(false);
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
	
	public void displayEndOfGame(int dayNumber, Player[] players) {
		dayLabel.setText("Game Finished After "+dayNumber+" Days");
		buttonContinue.setVisible(false);
		buttonEndGame.setVisible(true);
		for (int i=0; i<3; i++) {
			if (i < players.length) {
				playerLabels[i][0].setText(players[i].getName());
				playerLabels[i][1].setText(String.format("Final Score: %d", players[i].getScore()));
			}
			else {
				playerLabels[i][0].setText("");
				playerLabels[i][1].setText("");
			}
		}
		int winningScore = players[0].getScore();
		Player winner = players[0];
		for (Player player: players)
			if (player.getScore() >= winningScore) {
				winningScore = player.getScore();
				winner = player;
			}
		
		winnerLabel.setText(winner.getName()+" wins!");
	}

	public JButton getButtonContinue() {
		return buttonContinue;
	}
	
	public JButton getButtonEndGame() {
		return buttonEndGame;
	}
}
