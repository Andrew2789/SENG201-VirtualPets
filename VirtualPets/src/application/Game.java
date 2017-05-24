package application;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class Game extends JPanel {
	private static final long serialVersionUID = 1557753595413288282L;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;
	private Player[] players;
	private int numberOfDays;
	private int incomePerTurn;
	
	private int currentDay = 0;
	private Player activePlayer;
	private Pet activePet;
	
	private JLabel dayLabel;
	private JLabel playerLabel;
	private JButton buttonShop;
	private JButton buttonEndTurn;
	private JButton buttonMenu;
	private InternalDialog currentDialog;
	
	private JPanel menu;
	private JButton saveGame;
	private JButton exitToMainMenu;
	private JButton exitToDesktop;
	
	private PetTab[] petTabs = new PetTab[3];
	private int[][] tabLayouts = {{175, 0, 0}, {100, 250, 0}, {25, 175, 325}};
	private PetInteract petInteract;
	
	private JScrollPane foodInventoryScrollPane;
	private JPanel foodInventory;
	
	private JLabel inventoryMoney;
	private boolean selectingToy = false;
	private boolean selectingFood = false;
	
	private Font semiBoldFont;

	/**
	 * Create the panel.
	 */
	public Game(ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font subtitleFont, Font boldFont, Font semiBoldFont, Font regularFont) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		menu = new JPanel();
		menu.setBackground(Color.GRAY);
		menu.setBounds(245, 180, 310, 310);
		menu.setVisible(false);
		menu.setLayout(null);
		add(menu);
		
		saveGame = new JButton("Save Game");
		saveGame.setBounds(50, 36, 210, 50);
		menu.add(saveGame);
		
		exitToMainMenu = new JButton("Exit to Main Menu");
		exitToMainMenu.setBounds(50, 98, 210, 50);
		menu.add(exitToMainMenu);
		
		exitToDesktop = new JButton("Exit to Desktop");
		exitToDesktop.setBounds(50, 160, 210, 50);
		menu.add(exitToDesktop);
		
		JButton closeMenu = new JButton("Close Menu");
		closeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(false);
				setButtonsEnabled(true);
			}
		});
		closeMenu.setBounds(50, 222, 210, 50);
		menu.add(closeMenu);
		
		currentDialog = new InternalDialog(boldFont);
		currentDialog.setBounds(275, 200, 250, 100);
		add(currentDialog);

		foodInventoryScrollPane = new JScrollPane();
		foodInventoryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		foodInventoryScrollPane.setBounds(510, 264, 286, 156);
		add(foodInventoryScrollPane);
		
		for (int i=0; i<3; i++) {
			petTabs[i] = new PetTab(semiBoldFont);
			petTabs[i].setBounds(0, 100, 148, 155);
			petTabs[i].setVisible(false);
			
			final int finalI = i;
			petTabs[i].getClickDetector().addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for (int i=0; i<3; i++) {
						if (i == finalI)
							petTabs[i].setBorder(new MatteBorder(4, 4, 0, 4, Color.WHITE));
						else
							petTabs[i].setBorder(null);
						setPet(finalI);
					}
				}
			});
			add(petTabs[i]);
		}
		petTabs[0].setBorder(new MatteBorder(4, 4, 0, 4, Color.WHITE));
		
		dayLabel = new JLabel("");
		dayLabel.setForeground(Color.WHITE);
		dayLabel.setFont(titleFont);
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(250, 10, 300, 50);
		add(dayLabel);
		
		playerLabel = new JLabel("");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setFont(subtitleFont);
		playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		playerLabel.setBounds(300, 60, 200, 30);
		add(playerLabel);
		
		petInteract = new PetInteract(boldFont, semiBoldFont);
		petInteract.setBounds(0, 255, 500, 345);
		add(petInteract);

		petInteract.getButtonPlay().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsEnabled(false);
				currentDialog.setOptions("Click a toy from your inventory", "to use to play with your pet", false);
				selectingToy = true;
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonsEnabled(true);
						selectingToy = false;
					}
				});
				
				currentDialog.setVisible(true);
			}
		});

		petInteract.getButtonFeed().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsEnabled(false);
				currentDialog.setOptions("Click a food from your inventory", "to feed to your pet", false);
				selectingFood = true;
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonsEnabled(true);
						selectingFood = false;
					}
				});
				
				currentDialog.setVisible(true);
			}
		});

		petInteract.getButtonRest().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activePet.sleep();
				refreshPetInfo();
			}
		});
		
		petInteract.getButtonToilet().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activePet.goToToilet();
				refreshPetInfo();
			}
		});
		
		petInteract.getButtonCure().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activePlayer.getMoney() >= 50) {
					activePet.cure();
					activePlayer.changeMoney(-50);
					inventoryMoney.setText("Money: $"+activePlayer.getMoney());
					refreshPetInfo();
				}
				else {
					setButtonsEnabled(false);
					currentDialog.setOptions("You do not have enough money", "to cure your pet", false);
					
					currentDialog.getButtonCancel().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setButtonsEnabled(true);
						}
					});
					
					currentDialog.setVisible(true);
				}
			}
		});
		
		petInteract.getButtonDiscipline().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activePet.discipline();
				refreshPetInfo();
			}
		});
		
		buttonShop = new JButton(new ImageIcon(Game.class.getResource("/images/shop.png")));
		buttonShop.setBounds(531, 123, 65, 65);
		buttonShop.setToolTipText("Shop for food and toys for your pets.");
		add(buttonShop);
		
		buttonEndTurn = new JButton(new ImageIcon(Game.class.getResource("/images/endTurn.png")));
		buttonEndTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean requiresPrompt = false;
				for (Pet pet: activePlayer.getPets()) {
					if (pet.getActionPoints() > 0) {
						requiresPrompt = true;
						break;
					}
				}
				if (requiresPrompt) {
					setButtonsEnabled(false);
					currentDialog.setOptions("Some of your pets still have AP.", "Are you sure you want to end turn?", true);
					
					currentDialog.getButtonOk().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							endTurn();
							setButtonsEnabled(true);
						}
					});
					
					currentDialog.getButtonCancel().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setButtonsEnabled(true);
						}
					});
					
					currentDialog.setVisible(true);
				}
				else
					endTurn();
			}
		});
		buttonEndTurn.setBounds(608, 110, 90, 90);
		buttonEndTurn.setToolTipText("End your turn.");
		add(buttonEndTurn);
		buttonMenu = new JButton(new ImageIcon(Game.class.getResource("/images/menu.png")));
		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsEnabled(false);
				menu.setVisible(true);
			}
		});
		buttonMenu.setBounds(710, 123, 65, 65);
		buttonMenu.setToolTipText("Open the menu.");
		add(buttonMenu);
		
		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setForeground(Color.BLACK);
		inventoryLabel.setBounds(506, 212, 294, 20);
		inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabel.setFont(subtitleFont);
		add(inventoryLabel);
		
		inventoryMoney = new JLabel("");
		inventoryMoney.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryMoney.setFont(boldFont);
		inventoryMoney.setBounds(506, 233, 294, 20);
		add(inventoryMoney);
		
		JLabel inventoryBackground = new JLabel(new ImageIcon(Game.class.getResource("/images/backs/inventory.png")));
		inventoryBackground.setBounds(506, 255, 294, 345);
		add(inventoryBackground);
		
		JLabel inventoryLabelBackground = new JLabel("");
		inventoryLabelBackground.setIcon(new ImageIcon(Game.class.getResource("/images/backs/inventoryTitle.png")));
		inventoryLabelBackground.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabelBackground.setFont(null);
		inventoryLabelBackground.setBounds(589, 210, 130, 45);
		add(inventoryLabelBackground);
		
		JLabel background = new JLabel(new ImageIcon(Game.class.getResource("/images/gameBackground.png")));
		background.setBounds(0, 0, 800, 600);
		add(background);

		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
		this.semiBoldFont = semiBoldFont;
	}
	
	public void initialise(Player[] players, int numberOfDays, int incomePerTurn) {
		this.players = players;
		this.numberOfDays = numberOfDays;
		this.incomePerTurn = incomePerTurn;
		setTurn(0);
	}
	
	public void setTurn(int playerIndex) {
		if (playerIndex == 0) {
			currentDay += 1;
			dayLabel.setText("Day "+currentDay+" of "+numberOfDays);
		}
		activePlayer = players[playerIndex];
		playerLabel.setText(activePlayer.getName()+"'s turn");
		inventoryMoney.setText("Money: $"+activePlayer.getMoney());
		
		// DEBUG ONLY
		activePlayer.addFood(foodTypes[0]);
		activePlayer.addFood(foodTypes[1]);
		activePlayer.addFood(foodTypes[2]);
		activePlayer.addFood(foodTypes[3]);
		// END DEBUG
		foodInventory = new FoodInventory(activePlayer.getFood(), semiBoldFont);
		foodInventory.setPreferredSize(new Dimension(269, activePlayer.getFood().size()/3*90));
		
		foodInventoryScrollPane.setViewportView(foodInventory);
		for (int i=0; i<3; i++) {
			if (i < activePlayer.getPets().length) {
				petTabs[i].setBounds(tabLayouts[activePlayer.getPets().length-1][i], 100, 148, 155);
				petTabs[i].setBorder(null);
				petTabs[i].setPet(activePlayer.getPets()[i]);
				if (activePlayer.getPets()[i].isAlive())
					petTabs[i].setActionPoints(2);
				else
					petTabs[i].setActionPoints(0);
				petTabs[i].setVisible(true);
			}
			else
				petTabs[i].setVisible(false);
		}
		setPet(0);
		petTabs[0].setBorder(new MatteBorder(4, 4, 0, 4, Color.WHITE));
	}
	
	public void refreshPetInfo() {
		for (int i=0; i<activePlayer.getPets().length; i++)
			petTabs[i].setActionPoints(activePlayer.getPets()[i].getActionPoints());
		petInteract.setPet(activePet);
		if (activePet.getActionPoints() == 0)
			petInteract.setButtonsEnabled(false);
	}
	
	public void setPet(int petIndex) {
		activePet = activePlayer.getPets()[petIndex];
		petInteract.setPet(activePet);
		petInteract.setButtonsEnabled(activePet.getActionPoints() > 0);
	}
	
	public void endTurn() {
		activePlayer.changeMoney(incomePerTurn);
		for (Pet pet: activePlayer.getPets()) {
			activePlayer.changeScore(pet.finishTurn());
			pet.genRandomEvents();
		}
		
		int currentPlayerIndex = 0;
		for (int i=0; i<players.length; i++)
			if (players[i] == activePlayer)
				currentPlayerIndex = i;
		currentPlayerIndex = (currentPlayerIndex+1)%players.length;
		
		setTurn(currentPlayerIndex);
	}
	
	public void setButtonsEnabled(boolean enabled) {
		buttonShop.setEnabled(enabled);
		buttonEndTurn.setEnabled(enabled);
		buttonMenu.setEnabled(enabled);
		if (activePet.getActionPoints() > 0)
			petInteract.setButtonsEnabled(enabled);
		for (PetTab petTab: petTabs)
			petTab.setButtonEnabled(enabled);
	}
	
	public JButton getExitToMainMenu() {
		return exitToMainMenu;
	}
	
	public JButton getExitToDesktop() {
		return exitToDesktop;
	}
}
