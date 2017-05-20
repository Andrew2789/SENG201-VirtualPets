package gui;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class GameSetup extends JPanel {
	private int startingMoney = 150;
	private int incomePerTurn = 35;
	private PlayerSetup[] playerSetups;

	/**
	 * Create the panel.
	 */
	public GameSetup() {
		setLayout(null);
		
		playerSetups = new PlayerSetup[3];
		for (int i=0; i<3; i++) {
			playerSetups[i] = new PlayerSetup();
			playerSetups[i].setBounds(350, 75 + 175*i, 450, 175);
			add(playerSetups[i]);
		}
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1);
		slider.setMinimum(1);
		slider.setMaximum(3);
		slider.setBounds(92, 137, 200, 31);
		add(slider);
		
		JLabel lblSetup = new JLabel("Setup");
		lblSetup.setBounds(378, 39, 42, 15);
		add(lblSetup);
		
		JLabel lblNumberOfPlayers = new JLabel("Number of Players");
		lblNumberOfPlayers.setBounds(130, 120, 132, 15);
		add(lblNumberOfPlayers);
		
		JLabel label = new JLabel("");
		label.setBounds(0, 0, 800, 600);
		label.setIcon(new ImageIcon("images/menuBackground.png"));
		add(label);
		
		
	}
}
