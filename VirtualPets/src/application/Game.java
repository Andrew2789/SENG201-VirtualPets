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
import javax.swing.JButton;

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
			petTabs[i].setBounds(0 + 150*i, 100, 148, 155);
			petTabs[i].setVisible(false);
			
			final int finalI = i;
			petTabs[i].getClickDetector().addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for (int i=0; i<3; i++) {
						if (i == finalI)
							petTabs[i].setBorder(new MatteBorder(2, 2, 0, 2, (Color) new Color(255,255,255)));
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
		
		JLabel speciesLabel = new JLabel("Species: %s");
		speciesLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		speciesLabel.setBounds(12, 12, 232, 16);
		petInfoPanel.add(speciesLabel);
		
		JLabel healthyLabel = new JLabel("This pet is healthy");
		healthyLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		healthyLabel.setBounds(12, 36, 232, 16);
		petInfoPanel.add(healthyLabel);
		
		JLabel behavingLabel = new JLabel("This pet is behaving");
		behavingLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		behavingLabel.setBounds(12, 80, 232, 16);
		petInfoPanel.add(behavingLabel);
		
		JButton btnCure = new JButton("Cure");
		btnCure.setEnabled(false);
		btnCure.setToolTipText("ADD");
		btnCure.setBounds(12, 54, 102, 20);
		petInfoPanel.add(btnCure);
		
		JButton btnDiscipline = new JButton("Discipline");
		btnDiscipline.setEnabled(false);
		btnDiscipline.setToolTipText("ADD");
		btnDiscipline.setBounds(12, 98, 102, 20);
		petInfoPanel.add(btnDiscipline);
		
		JLabel favouriteToyLabel = new JLabel("Favourite Toy:\n%s");
		favouriteToyLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		favouriteToyLabel.setBounds(12, 130, 179, 47);
		petInfoPanel.add(favouriteToyLabel);
		
		JLabel favouriteToyIcon = new JLabel("");
		favouriteToyIcon.setBounds(162, 122, 80, 80);
		petInfoPanel.add(favouriteToyIcon);
		
		JLabel favouriteFoodLabel = new JLabel("Favourite Food:\n%s");
		favouriteFoodLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		favouriteFoodLabel.setBounds(12, 203, 179, 47);
		petInfoPanel.add(favouriteFoodLabel);
		
		JLabel favouriteFoodIcon = new JLabel("");
		favouriteFoodIcon.setBounds(162, 205, 80, 80);
		petInfoPanel.add(favouriteFoodIcon);
		
		JButton btnPlayer = new JButton("Play");
		btnPlayer.setToolTipText("Play with the selected pet. You will need to select a toy. This will use 1 action point.");
		btnPlayer.setBounds(12, 298, 110, 35);
		petInfoPanel.add(btnPlayer);
		
		JButton btnEat = new JButton("Feed");
		btnEat.setToolTipText("Feed the selected pet. You will need to select a food. This will use 1 action point.");
		btnEat.setBounds(132, 298, 110, 35);
		petInfoPanel.add(btnEat);
		
		JButton btnRest = new JButton("Rest");
		btnRest.setToolTipText("Have the selected pet rest. This will use 1 action point.");
		btnRest.setBounds(254, 298, 110, 35);
		petInfoPanel.add(btnRest);
		
		JButton btnToilet = new JButton("Toilet");
		btnToilet.setToolTipText("Send the selected pet to the toilet. This will use 1 action point.");
		btnToilet.setBounds(376, 298, 110, 35);
		petInfoPanel.add(btnToilet);
		
		JLabel petInfoBackground = new JLabel(new ImageIcon("images/petInteractBack.png"));
		petInfoBackground.setBounds(0, 0, 500, 345);
		petInfoPanel.add(petInfoBackground);
		
		JLabel background = new JLabel(new ImageIcon("images/setupBackground.png"));
		background.setBounds(0, 0, 800, 600);
		add(background);
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
