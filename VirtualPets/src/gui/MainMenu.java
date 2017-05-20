package gui;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

public class MainMenu extends JPanel {
	private JButton btnNewGame;
	private JButton btnLoadGame;
	private JButton btnScoreboard;
	private JButton btnCreateNewAsset;
	private JButton btnQuit;
	
	/**
	 * Create the panel.
	 */
	public MainMenu() {
		setLayout(null);
		setSize(800,600);
		
		btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewGame.setEnabled(true);
		btnNewGame.setBounds(300, 150, 200, 50);
		add(btnNewGame);
		
		btnLoadGame = new JButton("Load Game");
		btnLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLoadGame.setEnabled(true);
		btnLoadGame.setBounds(300, 212, 200, 50);
		add(btnLoadGame);
		
		btnScoreboard = new JButton("Scoreboard");
		btnScoreboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnScoreboard.setEnabled(true);
		btnScoreboard.setBounds(300, 274, 200, 50);
		add(btnScoreboard);
		
		btnCreateNewAsset = new JButton("Create New Asset");
		btnCreateNewAsset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCreateNewAsset.setEnabled(true);
		btnCreateNewAsset.setBounds(300, 336, 200, 50);
		add(btnCreateNewAsset);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setEnabled(true);
		btnHelp.setBounds(300, 398, 200, 50);
		add(btnHelp);
		
		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnQuit.setEnabled(true);
		btnQuit.setBounds(300, 460, 200, 50);
		add(btnQuit);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon("images/menuBackground.png"));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);

	}
	
	public JButton getNewGameButton() {
		return btnNewGame;
	}
	
	public JButton getQuitButton() {
		return btnQuit;
	}
}
