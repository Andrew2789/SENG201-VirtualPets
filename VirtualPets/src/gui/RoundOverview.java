package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import model.Player;

/**
 * Displays statistics about the player's scores at the end of each round.
 * @author Andrew Davidson (ada130)
 */
public class RoundOverview extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel dayLabel, winnerLabel;
	private JLabel[][] playerLabels;
	private JButton buttonContinue, buttonEndGame;
	private int[] previousScores;

	/**
	 * Create the panel - a panel to display statistics about player scores at the end of rounds and end of game.
	 * @param titleFont
	 * The font to use for the title
	 * @param subtitleFont
	 * The font to use for player names
	 * @param boldFont
	 * The font to use for score information
	 */
	public RoundOverview(Font titleFont, Font subtitleFont, Font boldFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		previousScores = new int[3];
		
		//Create a title label
		dayLabel = new JLabel("");
		dayLabel.setForeground(Color.WHITE);
		dayLabel.setFont(titleFont);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(100, 10, 600, 50);
		add(dayLabel);

		//Create two JLabels for each possible player, one for their name, and another for their score
		playerLabels = new JLabel[3][2];
		for (int i=0; i<3; i++) {
			for (int j=0; j<2; j++) {
				playerLabels[i][j] = new JLabel("");
				playerLabels[i][j].setForeground(Color.WHITE);
				if (j == 0)
					playerLabels[i][j].setFont(subtitleFont);
				else
					playerLabels[i][j].setFont(boldFont);
				playerLabels[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				playerLabels[i][j].setBounds(200, 100 + 105*i + j*31, 400, 30);
				add(playerLabels[i][j]);
			}
		}
		
		//A label to display the winner at the end of the game
		winnerLabel = new JLabel("");
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setForeground(Color.WHITE);
		winnerLabel.setFont(subtitleFont);
		winnerLabel.setBounds(200, 380, 400, 40);
		add(winnerLabel);
		
		//Continue to next round
		buttonContinue = new JButton("Continue");
		buttonContinue.setBounds(300, 450, 200, 50);
		buttonContinue.setFont(boldFont);
		add(buttonContinue);
		
		//Finish the game
		buttonEndGame = new JButton("End Game");
		buttonEndGame.setBounds(300, 450, 200, 50);
		buttonEndGame.setFont(boldFont);
		add(buttonEndGame);
		
		//An image background
		JLabel background = new JLabel(new ImageIcon(Game.class.getResource("/images/gameBackground.png")));
		background.setBounds(0, 0, 800, 600);
		add(background);
	}

	//Getters and setters
	public JButton getButtonContinue() {
		return buttonContinue;
	}
	
	public JButton getButtonEndGame() {
		return buttonEndGame;
	}
	
	public int[] getPreviousScores() {
		return this.previousScores;
	}
	
	public void setPreviousRoundScores(int[] previousScores) {
		this.previousScores = previousScores;
	}
	//End getters and setters
	
	/**
	 * Initialise the RoundOverview for a game by showing only relevant buttons.
	 */
	public void initialise() {
		winnerLabel.setText("");
		buttonContinue.setVisible(true);
		buttonEndGame.setVisible(false);
	}
	
	/**
	 * Display statistics about the score at the end of a round.
	 * @param dayNumber
	 * The number of the day to display score information about
	 * @param players
	 * The players to display information about
	 */
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
	
	/**
	 * Display score statistics for the end of a game, and the winner.
	 * @param dayNumber
	 * The number of days played for
	 * @param players
	 * The players to display score for and decide a winner from
	 */
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
}
