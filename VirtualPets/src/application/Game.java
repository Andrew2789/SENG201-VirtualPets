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
import javax.swing.JTextArea;
import javax.swing.JTextPane;

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
	
	private JLabel speciesLabel;
	private JLabel favouriteToyLabel;
	private JLabel favouriteToyIcon;
	private JLabel favouriteFoodLabel;
	private JLabel favouriteFoodIcon;
	private JLabel healthyLabel;
	private JButton buttonCure;
	private JLabel behavingLabel;
	private JButton buttonDiscipline;
	
	private Player activePlayer;
	private Pet activePet;
	private JLabel speciesTitle;

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
						setPet(finalI);
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
		
		speciesTitle = new JLabel("Species:");
		speciesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		speciesTitle.setFont(boldFont);
		speciesTitle.setBounds(12, 12, 232, 16);
		petInfoPanel.add(speciesTitle);
		
		speciesLabel = new JLabel("");
		speciesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speciesLabel.setFont(semiBoldFont);
		speciesLabel.setBounds(12, 28, 232, 16);
		petInfoPanel.add(speciesLabel);
		
		healthyLabel = new JLabel("");
		healthyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		healthyLabel.setFont(boldFont);
		healthyLabel.setBounds(12, 48, 232, 16);
		petInfoPanel.add(healthyLabel);
		
		behavingLabel = new JLabel("");
		behavingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		behavingLabel.setFont(boldFont);
		behavingLabel.setBounds(12, 92, 232, 16);
		petInfoPanel.add(behavingLabel);
		
		buttonCure = new JButton("Cure");
		buttonCure.setEnabled(false);
		buttonCure.setFont(boldFont);
		buttonCure.setToolTipText("ADD");
		buttonCure.setBounds(82, 66, 100, 20);
		petInfoPanel.add(buttonCure);
		
		buttonDiscipline = new JButton("Discipline");
		buttonDiscipline.setEnabled(false);
		buttonDiscipline.setFont(boldFont);
		buttonDiscipline.setToolTipText("ADD");
		buttonDiscipline.setBounds(82, 110, 100, 20);
		petInfoPanel.add(buttonDiscipline);
		
		JLabel favouriteToyTitle = new JLabel("Favourite Toy:");
		favouriteToyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyTitle.setFont(boldFont);
		favouriteToyTitle.setBounds(12, 142, 110, 26);
		petInfoPanel.add(favouriteToyTitle);
		
		favouriteToyLabel = new JLabel("");
		favouriteToyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyLabel.setFont(semiBoldFont);
		favouriteToyLabel.setBounds(12, 158, 110, 16);
		petInfoPanel.add(favouriteToyLabel);
		
		favouriteToyIcon = new JLabel("");
		favouriteToyIcon.setIcon(new ImageIcon("images/species/Cat.png"));
		favouriteToyIcon.setBounds(20, 180, 94, 94);
		petInfoPanel.add(favouriteToyIcon);
		
		JLabel favouriteFoodTitle = new JLabel("Favourite Food:");
		favouriteFoodTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodTitle.setFont(boldFont);
		favouriteFoodTitle.setBounds(132, 142, 110, 26);
		petInfoPanel.add(favouriteFoodTitle);
		
		favouriteFoodLabel = new JLabel("");
		favouriteFoodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodLabel.setFont(semiBoldFont);
		favouriteFoodLabel.setBounds(134, 158, 110, 16);
		petInfoPanel.add(favouriteFoodLabel);
		
		favouriteFoodIcon = new JLabel("");
		favouriteFoodIcon.setIcon(new ImageIcon("images/species/Dog.png"));
		favouriteFoodIcon.setBounds(142, 180, 94, 94);
		petInfoPanel.add(favouriteFoodIcon);
		
		JButton buttonPlay = new JButton("Play");
		buttonPlay.setToolTipText("Play with the selected pet. You will need to select a toy. This will use 1 action point.");
		buttonPlay.setBounds(14, 298, 110, 35);
		petInfoPanel.add(buttonPlay);
		
		JButton buttonFeed = new JButton("Feed");
		buttonFeed.setToolTipText("Feed the selected pet. You will need to select a food. This will use 1 action point.");
		buttonFeed.setBounds(134, 298, 110, 35);
		petInfoPanel.add(buttonFeed);
		
		JButton buttonRest = new JButton("Rest");
		buttonRest.setToolTipText("Have the selected pet rest. This will use 1 action point.");
		buttonRest.setBounds(256, 298, 110, 35);
		petInfoPanel.add(buttonRest);
		
		JButton buttonToilet = new JButton("Toilet");
		buttonToilet.setToolTipText("Send the selected pet to the toilet. This will use 1 action point.");
		buttonToilet.setBounds(378, 298, 110, 35);
		petInfoPanel.add(buttonToilet);
		
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
		activePlayer = player;
		for (int i=0; i<3; i++) {
			if (i < player.getPets().length) {
				petTabs[i].setPet(player.getPets()[i]);
				petTabs[i].setActionPoints(2);
				petTabs[i].setVisible(true);
			}
			else
				petTabs[i].setVisible(false);
		}
		setPet(0);
	}
	
	public void setPet(int petIndex) {
		activePet = activePlayer.getPets()[petIndex];
		speciesLabel.setText(activePet.getSpecies().getName());
		if (activePet.isHealthy())
			healthyLabel.setText("This pet is healthy");
		else
			healthyLabel.setText("This pet is sick");
		buttonCure.setEnabled(!activePet.isHealthy());
		
		if (activePet.isBehaving())
			behavingLabel.setText("This pet is behaving");
		else
			behavingLabel.setText("This pet is misbehaving");
		buttonDiscipline.setEnabled(!activePet.isBehaving());
		
		favouriteToyLabel.setText(activePet.getFavouriteToy().getName());
		favouriteFoodLabel.setText(activePet.getFavouriteFood().getName());
	}
}
