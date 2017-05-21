package application;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;

public class Game extends JPanel {
	private static final long serialVersionUID = 1557753595413288282L;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;
	private Player[] players;
	private int numberOfDays;
	private int incomePerTurn;
	
	private JLabel dayLabel;
	private JLabel playerLabel;
	private PetTab[] petTabs = new PetTab[3];
	private int selectedPetIndex = 0;

	/**
	 * Create the panel.
	 */
	public Game(ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);
		
		for (int i=0; i<3; i++) {
			petTabs[i] = new PetTab(semiBoldFont);
			petTabs[i].setBounds(0 + 150*i, 100, 149, 155);
			petTabs[i].setVisible(false);
			
			petTabs[i].getClickDetector().addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for (int i=0; i<3; i++) {
						if (petTabs[i].checkClicked()) {
							petTabs[i].setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(255,255,255)));
							selectedPetIndex = i;
						}
						else
							petTabs[i].setBorder(null);
					}
				}
			});
			
			add(petTabs[i]);
		}
		petTabs[0].setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(255,255,255)));
		
		dayLabel = new JLabel("");
		dayLabel.setFont(titleFont);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(250, 10, 300, 50);
		add(dayLabel);
		
		playerLabel = new JLabel("");
		playerLabel.setFont(boldFont);
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerLabel.setBounds(300, 70, 200, 30);
		add(playerLabel);

		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
		
		JPanel petInfoPanel = new JPanel();
		petInfoPanel.setBounds(0, 255, 500, 345);
		petInfoPanel.setOpaque(false);
		add(petInfoPanel);
		petInfoPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("images/petInteractBack.png"));
		lblNewLabel.setBounds(0, 0, 500, 345);
		petInfoPanel.add(lblNewLabel);
		
		JLabel background = new JLabel("");
		background.setBounds(0, 0, 800, 600);
		add(background);
		background.setIcon(new ImageIcon("images/setupBackground.png"));
	}
	
	public void initialise(Player[] players, int numberOfDays, int incomePerTurn) {
		this.players = players;
		this.numberOfDays = numberOfDays;
		this.incomePerTurn = incomePerTurn;
		dayLabel.setText("Day 1 of "+numberOfDays);
		setTurn(players[0]);
	}
	
	public void setTurn(Player player) {
		playerLabel.setText(player.getName()+"'s turn");
		for (int i=0; i<3; i++) {
			if (i < player.getPets().length) {
				petTabs[i].setPet(player.getPets()[i]);
				petTabs[i].setActionPoints(2);
				petTabs[i].setVisible(true);
			}
			else
				petTabs[i].setVisible(false);
		}
	}
}
