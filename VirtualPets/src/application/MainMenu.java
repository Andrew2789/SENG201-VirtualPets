package application;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainMenu extends JPanel {
	private static final long serialVersionUID = 5563328853841424713L;
	private JButton buttonNewGame;
	private JButton buttonLoadGame;
	private JButton buttonScoreboard;
	private JButton buttonCreateNewAsset;
	private JButton buttonQuit;
	private JLabel title;
	
	/**
	 * Create the main menu panel.
	 */
	public MainMenu(Font titleFont, Font buttonFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		title = new JLabel("Virtual Pets");
		title.setFont(titleFont);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds(150, 40, 500, 80);
		add(title);
		
		buttonNewGame = new JButton("New Game");
		buttonNewGame.setFont(buttonFont);
		buttonNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonNewGame.setEnabled(true);
		buttonNewGame.setBounds(300, 150, 200, 50);
		add(buttonNewGame);
		
		buttonLoadGame = new JButton("Load Game");
		buttonLoadGame.setFont(buttonFont);
		buttonLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonLoadGame.setEnabled(true);
		buttonLoadGame.setBounds(300, 212, 200, 50);
		add(buttonLoadGame);
		
		buttonScoreboard = new JButton("Scoreboard");
		buttonScoreboard.setFont(buttonFont);
		buttonScoreboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonScoreboard.setEnabled(true);
		buttonScoreboard.setBounds(300, 274, 200, 50);
		add(buttonScoreboard);
		
		buttonCreateNewAsset = new JButton("Create New Asset");
		buttonCreateNewAsset.setFont(buttonFont);
		buttonCreateNewAsset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonCreateNewAsset.setEnabled(true);
		buttonCreateNewAsset.setBounds(300, 336, 200, 50);
		add(buttonCreateNewAsset);
		
		JButton buttonHelp = new JButton("Help");
		buttonHelp.setFont(buttonFont);
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonHelp.setEnabled(true);
		buttonHelp.setBounds(300, 398, 200, 50);
		add(buttonHelp);
		
		buttonQuit = new JButton("Quit");
		buttonQuit.setFont(buttonFont);
		buttonQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonQuit.setEnabled(true);
		buttonQuit.setBounds(300, 460, 200, 50);
		add(buttonQuit);
		
		JLabel backgroundImage = new JLabel("");
		backgroundImage.setIcon(new ImageIcon(MainMenu.class.getResource("/images/menuBackground.png")));
		backgroundImage.setBounds(0, 0, 800, 600);
		add(backgroundImage);

	}
	
	public JButton getNewGameButton() {
		return buttonNewGame;
	}
	
	public JButton getQuitButton() {
		return buttonQuit;
	}
}
