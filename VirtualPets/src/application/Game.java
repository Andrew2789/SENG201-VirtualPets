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
	
	private int[][] tabLayouts = {{175, 0, 0},
								  {100, 250, 0},
								  {25, 175, 325}};
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
	private PetStatDisplayer hungerSlider;
	private PetStatDisplayer energySlider;
	private PetStatDisplayer happinessSlider;
	private PetStatDisplayer weightSlider;
	
	private JLabel inventoryMoney;
	
	private Player activePlayer;
	private Pet activePet;
	private JLabel speciesTitle;
	private JLabel buttonShopIcon;
	private JLabel inventoryLabelBackground;

	/**
	 * Create the panel.
	 */
	public Game(ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font subtitleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);
		
		for (int i=0; i<3; i++) {
			petTabs[i] = new PetTab(semiBoldFont);
			petTabs[i].setBounds(0, 100, 148, 155);
			petTabs[i].setVisible(false);
			
			final int finalI = i;
			petTabs[i].getClickDetector().addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for (int i=0; i<3; i++) {
						if (i == finalI)
							petTabs[i].setBorder(new MatteBorder(4, 4, 0, 4, (Color) new Color(255,255,255)));
						else
							petTabs[i].setBorder(null);
						setPet(finalI);
					}
				}
			});
			add(petTabs[i]);
		}
		petTabs[0].setBorder(new MatteBorder(4, 4, 0, 4, (Color) new Color(255,255,255)));
		
		dayLabel = new JLabel("");
		dayLabel.setFont(titleFont);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(250, 10, 300, 50);
		add(dayLabel);
		
		playerLabel = new JLabel("");
		playerLabel.setFont(subtitleFont);
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerLabel.setBounds(300, 70, 200, 30);
		add(playerLabel);
		
		JButton buttonShop = new JButton(new ImageIcon(Game.class.getResource("/images/shop.png")));
		buttonShop.setBounds(531, 123, 65, 65);
		buttonShop.setToolTipText("Shop for food and toys for your pets.");
		add(buttonShop);
		
		JButton buttonEndTurn = new JButton(new ImageIcon(Game.class.getResource("/images/endTurn.png")));
		buttonEndTurn.setBounds(608, 110, 90, 90);
		buttonEndTurn.setToolTipText("End your turn.");
		add(buttonEndTurn);
		
		JButton buttonMenu = new JButton(new ImageIcon(Game.class.getResource("/images/menu.png")));
		buttonMenu.setBounds(710, 123, 65, 65);
		buttonMenu.setToolTipText("Open the menu.");
		add(buttonMenu);
		
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setBounds(506, 212, 294, 20);
		inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabel.setFont(subtitleFont);
		add(inventoryLabel);
		
		inventoryMoney = new JLabel("");
		inventoryMoney.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryMoney.setFont(boldFont);
		inventoryMoney.setBounds(506, 233, 294, 20);
		add(inventoryMoney);
		
		JLabel inventoryBackground = new JLabel(new ImageIcon(Game.class.getResource("/images/inventoryBack.png")));
		inventoryBackground.setBounds(506, 255, 294, 345);
		add(inventoryBackground);
		
		inventoryLabelBackground = new JLabel("");
		inventoryLabelBackground.setIcon(new ImageIcon(Game.class.getResource("/images/inventoryTitleBack.png")));
		inventoryLabelBackground.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabelBackground.setFont(null);
		inventoryLabelBackground.setBounds(589, 210, 130, 45);
		add(inventoryLabelBackground);
		
		JPanel petInfoPanel = new JPanel();
		petInfoPanel.setBounds(0, 255, 500, 345);
		petInfoPanel.setOpaque(false);
		petInfoPanel.setLayout(null);
		add(petInfoPanel);
		
		speciesTitle = new JLabel("Species");
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
		buttonCure.setToolTipText("Cures sickness. Costs $50 and consumes one action point.");
		buttonCure.setBounds(82, 66, 100, 20);
		petInfoPanel.add(buttonCure);
		
		buttonDiscipline = new JButton("Discipline");
		buttonDiscipline.setEnabled(false);
		buttonDiscipline.setFont(boldFont);
		buttonDiscipline.setToolTipText("Will make your pet unhappy (-30 happiness) but stops them from misbehaving. Consumes one action point.");
		buttonDiscipline.setBounds(82, 110, 100, 20);
		petInfoPanel.add(buttonDiscipline);
		
		JLabel favouriteToyTitle = new JLabel("Favourite Toy");
		favouriteToyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyTitle.setFont(boldFont);
		favouriteToyTitle.setBounds(12, 142, 110, 28);
		petInfoPanel.add(favouriteToyTitle);
		
		favouriteToyLabel = new JLabel("");
		favouriteToyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteToyLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteToyLabel.setFont(semiBoldFont);
		favouriteToyLabel.setBounds(12, 158, 110, 16);
		petInfoPanel.add(favouriteToyLabel);
		
		favouriteToyIcon = new JLabel("");
		favouriteToyIcon.setIcon(new ImageIcon(Game.class.getResource("/images/species/Cat.png")));
		favouriteToyIcon.setBounds(20, 180, 94, 94);
		petInfoPanel.add(favouriteToyIcon);
		
		JLabel favouriteFoodTitle = new JLabel("Favourite Food");
		favouriteFoodTitle.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodTitle.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodTitle.setFont(boldFont);
		favouriteFoodTitle.setBounds(132, 142, 110, 28);
		petInfoPanel.add(favouriteFoodTitle);
		
		favouriteFoodLabel = new JLabel("");
		favouriteFoodLabel.setHorizontalAlignment(SwingConstants.CENTER);
		favouriteFoodLabel.setVerticalAlignment(SwingConstants.TOP);
		favouriteFoodLabel.setFont(semiBoldFont);
		favouriteFoodLabel.setBounds(134, 158, 110, 16);
		petInfoPanel.add(favouriteFoodLabel);
		
		favouriteFoodIcon = new JLabel("");
		favouriteFoodIcon.setIcon(new ImageIcon(Game.class.getResource("/images/species/Bunny.png")));
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
		
		hungerSlider = new PetStatDisplayer(boldFont, semiBoldFont, "Hunger", new ImageIcon(Game.class.getResource("/images/hungerSlider.png")), 
				"How hungry this pet is. Once hunger reaches the orange region, the pet will begin to starve.", new Color(227, 66, 52), 0, 100, 0, 8);
		hungerSlider.setBounds(257, 12, 232, 50);
		petInfoPanel.add(hungerSlider);
		
		energySlider = new PetStatDisplayer(boldFont, semiBoldFont, "Energy", new ImageIcon(Game.class.getResource("/images/energySlider.png")), 
				"How much energy this pet has. Once energy reaches the red region, the pet will have a chance to die at the end of each turn.", new Color(30, 224, 220), 0, 100, 6, 0);
		energySlider.setBounds(257, 72, 232, 50);
		petInfoPanel.add(energySlider);
		
		happinessSlider = new PetStatDisplayer(boldFont, semiBoldFont, "Happiness", new ImageIcon(Game.class.getResource("/images/happinessSlider.png")), 
				"How happy this pet is.", new Color(255, 180, 0), 0, 100, 3, 0);
		happinessSlider.setBounds(257, 132, 232, 50);
		petInfoPanel.add(happinessSlider);
		
		JLabel petInfoBackground = new JLabel(new ImageIcon(Game.class.getResource("/images/petInteractBack.png")));
		petInfoBackground.setBounds(0, 0, 500, 345);
		petInfoPanel.add(petInfoBackground);
		
		JLabel background = new JLabel(new ImageIcon(Game.class.getResource("/images/gameBackground.png")));
		background.setBounds(0, 0, 800, 600);
		add(background);

		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
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
		inventoryMoney.setText("Money: $"+player.getMoney());
		for (int i=0; i<3; i++) {
			if (i < player.getPets().length) {
				petTabs[i].setBounds(tabLayouts[player.getPets().length-1][i], 100, 148, 155);
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
		
		hungerSlider.setStat(activePet.getHunger());
		energySlider.setStat(activePet.getEnergy());
		happinessSlider.setStat(activePet.getHappiness());
		favouriteToyLabel.setText(activePet.getFavouriteToy().getName());
		favouriteFoodLabel.setText(activePet.getFavouriteFood().getName());
	}
}
