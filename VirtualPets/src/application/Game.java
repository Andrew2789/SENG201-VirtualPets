package application;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

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
	
	private int currentDay;
	private Player activePlayer;
	private Pet activePet;
	private RoundOverview roundOverview;
	
	private JLabel dayLabel;
	private JLabel playerLabel;
	private JButton buttonShop;
	private JButton buttonEndTurn;
	private JButton buttonMenu;
	private InternalDialog currentDialog;
	
	private JPanel menu;
	private JButton buttonSaveGame;
	private JButton buttonExitToMainMenu;
	private JButton buttonExitToDesktop;
	private JButton buttonCloseMenu;
	
	private PetTab[] petTabs = new PetTab[3];
	private int[][] tabLayouts = {{175, 0, 0}, {100, 250, 0}, {25, 175, 325}};
	private PetInteract petInteract;
	
	private JScrollPane foodInventoryScrollPane;
	private JScrollPane toyInventoryScrollPane;
	private FoodInventory foodInventory;
	private ToyInventory toyInventory;
	
	private JPanel shopBase;
	private ShopPanel shopPanel;
	
	private JLabel inventoryMoney;
	private boolean selectingToy = false;
	private boolean selectingFood = false;
	
	private Font semiBoldFont;
	private Font boldFont;
	private Font regularFont;

	/**
	 * Create the panel.
	 */
	public Game(ToyType[] toyTypes, FoodType[] foodTypes, Font titleFont, Font subtitleFont, Font boldFont, Font semiBoldFont, Font regularFont, 
			RoundOverview roundOverview, ActionListener exitToMainMenu, ActionListener exitToDesktop) {
		setLayout(null);
		setSize(800, 600);
		setVisible(false);

		currentDialog = new InternalDialog(boldFont);
		currentDialog.setBounds(275, 165, 250, 100);
		add(currentDialog);
		
		menu = new JPanel();
		menu.setBackground(Color.GRAY);
		menu.setBounds(250, 180, 300, 310);
		menu.setVisible(false);
		menu.setLayout(null);
		add(menu);
		
		buttonSaveGame = new JButton("Save Game");
		buttonSaveGame.setBounds(50, 36, 200, 50);
		menu.add(buttonSaveGame);
		
		buttonExitToMainMenu = new JButton("Exit to Main Menu");
		buttonExitToMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenuButtonsEnabled(false);
				currentDialog.setOptions("Are you sure? All unsaved", "progress will be lost.", true, true);

				currentDialog.getButtonOk().addActionListener(exitToMainMenu);
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setMenuButtonsEnabled(true);
					}
				});
				
				currentDialog.setVisible(true);
			}
		});
		buttonExitToMainMenu.setBounds(50, 98, 200, 50);
		menu.add(buttonExitToMainMenu);
		
		buttonExitToDesktop = new JButton("Exit to Desktop");
		buttonExitToDesktop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMenuButtonsEnabled(false);
				currentDialog.setOptions("Are you sure? All unsaved", "progress will be lost.", true, true);

				currentDialog.getButtonOk().addActionListener(exitToDesktop);
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setMenuButtonsEnabled(true);
					}
				});
				
				currentDialog.setVisible(true);
			}
		});
		buttonExitToDesktop.setBounds(50, 160, 200, 50);
		menu.add(buttonExitToDesktop);
		
		buttonCloseMenu = new JButton("Close Menu");
		buttonCloseMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(false);
				setButtonsEnabled(true);
			}
		});
		buttonCloseMenu.setBounds(50, 222, 200, 50);
		menu.add(buttonCloseMenu);

		shopBase = new JPanel();
		shopBase.setLayout(null);
		shopBase.setVisible(false);
		shopBase.setBounds(5, 145, 500, 450);
		add(shopBase);
		
		foodInventoryScrollPane = new JScrollPane();
		foodInventoryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		foodInventoryScrollPane.setBounds(510, 264, 286, 156);
		foodInventoryScrollPane.setOpaque(false);
		add(foodInventoryScrollPane);
		
		toyInventoryScrollPane = new JScrollPane();
		toyInventoryScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		toyInventoryScrollPane.setBounds(510, 438, 286, 156);
		toyInventoryScrollPane.setOpaque(false);
		add(toyInventoryScrollPane);
		
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
		playerLabel.setBounds(200, 60, 400, 30);
		add(playerLabel);
		
		petInteract = new PetInteract(boldFont, semiBoldFont);
		petInteract.setBounds(0, 255, 500, 345);
		add(petInteract);

		petInteract.getButtonPlay().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsEnabled(false);
				currentDialog.setOptions("Click a toy from your inventory", "to use to play with your pet", false, true);
				selectingToy = true;
				toyInventory.setToyIconsEnabled(true);
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonsEnabled(true);
						selectingToy = false;
						toyInventory.setToyIconsEnabled(false);
					}
				});
				
				currentDialog.setVisible(true);
			}
		});

		petInteract.getButtonFeed().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setButtonsEnabled(false);
				currentDialog.setOptions("Click a food from your inventory", "to feed to your pet", false, true);
				selectingFood = true;
				foodInventory.setFoodIconsEnabled(true);
				
				currentDialog.getButtonCancel().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setButtonsEnabled(true);
						selectingFood = false;
						foodInventory.setFoodIconsEnabled(false);
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
					currentDialog.setOptions("You do not have enough money", "to cure your pet", true, false);
					
					currentDialog.getButtonOk().addActionListener(new ActionListener() {
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
		
		petInteract.getButtonRevive().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activePlayer.getMoney() >= 100) {
					activePet.revive();
					activePlayer.changeMoney(-100);
					inventoryMoney.setText("Money: $"+activePlayer.getMoney());
					refreshPetInfo();
				}
				else {
					setButtonsEnabled(false);
					currentDialog.setOptions("You do not have enough money", "to revive your pet", true, false);
					
					currentDialog.getButtonOk().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							setButtonsEnabled(true);
						}
					});
					
					currentDialog.setVisible(true);
				}
			}
		});
		
		buttonShop = new JButton(new ImageIcon(Game.class.getResource("/images/shop.png")));
		buttonShop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayShop();
			}
		});
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
					currentDialog.setOptions("Some of your pets still have AP.", "Are you sure you want to end turn?", true, true);
					
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

		roundOverview.initialise();
		roundOverview.getButtonContinue().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				roundOverview.setVisible(false);
			}
		});
		
		this.toyTypes = toyTypes;
		this.foodTypes = foodTypes;
		this.semiBoldFont = semiBoldFont;
		this.boldFont = boldFont;
		this.regularFont = regularFont;
		this.roundOverview = roundOverview;
	}
	
	public void initialise(Player[] players, int numberOfDays, int incomePerTurn) {
		this.players = players;
		this.numberOfDays = numberOfDays;
		this.incomePerTurn = incomePerTurn;
		currentDay = 1;
		dayLabel.setText("Day "+currentDay+" of "+numberOfDays);
		setTurn(0);
	}
	
	public void resume(Player[] players, int currentDay, int currentPlayerIndex, int numberOfDays, int incomePerTurn, int[] previousScores) {
		this.players = players;
		this.numberOfDays = numberOfDays;
		this.incomePerTurn = incomePerTurn;
		this.currentDay = currentDay;
		dayLabel.setText("Day "+currentDay+" of "+numberOfDays);
		roundOverview.setPreviousRoundScores(previousScores);
		setTurn(currentPlayerIndex);
	}
	
	public void setTurn(int playerIndex) {
		activePlayer = players[playerIndex];
		playerLabel.setText(activePlayer.getName()+"'s turn. Score: "+activePlayer.getScore());
		inventoryMoney.setText("Money: $"+activePlayer.getMoney());
		
		refreshFoodInventory();
		refreshToyInventory();
		
		for (int i=0; i<3; i++) {
			if (i < activePlayer.getPets().length) {
				petTabs[i].setBounds(tabLayouts[activePlayer.getPets().length-1][i], 100, 148, 155);
				petTabs[i].setBorder(null);
				petTabs[i].setPet(activePlayer.getPets()[i]);
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
			petTabs[i].setPet(activePlayer.getPets()[i]);
		petInteract.setPet(activePet);
		if (activePet.getActionPoints() == 0)
			petInteract.setButtonsEnabled(false);
	}
	
	public void refreshFoodInventory() {
		foodInventory = new FoodInventory(activePlayer.getFood(), semiBoldFont);
		foodInventory.setPreferredSize(new Dimension(269, ((activePlayer.getFood().size()+2)/3)*90));
		foodInventoryScrollPane.setViewportView(foodInventory);
		
		HashMap<FoodType, FoodInventoryIcon> foodIcons = foodInventory.getFoodIcons();
		for (FoodType food : foodIcons.keySet()) {
			foodIcons.get(food).getClickDetector().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selectingFood) {
						activePlayer.feed(activePet, food);
						selectingFood = false;
						setButtonsEnabled(true);
						currentDialog.setVisible(false);
						
						refreshFoodInventory();
						refreshPetInfo();
					}
				}
			});
		}
	}
		
	public void refreshToyInventory() {
		toyInventory = new ToyInventory(activePlayer.getToys(), semiBoldFont);
		toyInventory.setPreferredSize(new Dimension(269, ((activePlayer.getToys().size()+2)/3)*90));
		toyInventoryScrollPane.setViewportView(toyInventory);
		
		ToyInventoryIcon[] toyIcons = toyInventory.getToyIcons();
		for (ToyInventoryIcon icon : toyIcons) {
			icon.getClickDetector().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selectingToy) {
						activePlayer.playWith(activePet, icon.getSpecificToy());
						selectingToy = false;
						setButtonsEnabled(true);
						currentDialog.setVisible(false);
						
						refreshToyInventory();
						refreshPetInfo();
					}
				}
			});
		}
	}
	
	public void displayShop() {
		for (Component comp : shopBase.getComponents()) {
			shopBase.remove(comp);
		}
		shopPanel = new ShopPanel(foodTypes, toyTypes, activePlayer.getMoney(), semiBoldFont, boldFont, regularFont);
		shopBase.add(shopPanel);
		shopBase.setVisible(true);
		setButtonsEnabled(false);
		shopPanel.enablePossibleBuyButtons(activePlayer.getMoney());
		shopPanel.getLeaveButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shopBase.setVisible(false);
				setButtonsEnabled(true);
			}
		});
		
		for (ShopFoodDisplayer foodDisplay : shopPanel.getFoodsForSale()) {
			foodDisplay.getBuyButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (activePlayer.getMoney() >= foodDisplay.getFoodType().getPrice()) {
						activePlayer.changeMoney(-foodDisplay.getFoodType().getPrice());
						activePlayer.addFood(foodDisplay.getFoodType());
						inventoryMoney.setText("Money: $"+activePlayer.getMoney());
						shopBase.setVisible(false);
						displayShop();
						refreshFoodInventory();
					}
				}
			});
		}
		
		for (ShopToyDisplayer toyDisplay : shopPanel.getToysForSale()) {
			toyDisplay.getBuyButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (activePlayer.getMoney() >= toyDisplay.getToyType().getPrice()) {
						activePlayer.changeMoney(-toyDisplay.getToyType().getPrice());
						activePlayer.addToy(new Toy(toyDisplay.getToyType()));
						inventoryMoney.setText("Money: $"+activePlayer.getMoney());
						shopBase.setVisible(false);
						displayShop();
						refreshToyInventory();
					}
				}
			});
		}
	}
	
	public void setPet(int petIndex) {
		activePet = activePlayer.getPets()[petIndex];
		petInteract.setPet(activePet);
		petInteract.setButtonsEnabled(activePet.getActionPoints() > 0);
	}
	
	public void endTurn() {
		activePlayer.changeMoney(incomePerTurn);
		for (Pet pet: activePlayer.getPets())
			activePlayer.changeScore(pet.finishTurn());
		
		int currentPlayerIndex = 0;
		for (int i=0; i<players.length; i++)
			if (players[i] == activePlayer)
				currentPlayerIndex = i;
		currentPlayerIndex = (currentPlayerIndex+1)%players.length;
		if (currentPlayerIndex == 0) {
			if (currentDay == numberOfDays) {
				roundOverview.displayEndOfGame(currentDay, players);
				setVisible(false);
				roundOverview.setVisible(true);
			}
			else {
				roundOverview.displayEndOfRound(currentDay, players);
				setVisible(false);
				roundOverview.setVisible(true);
				currentDay += 1;
				dayLabel.setText("Day "+currentDay+" of "+numberOfDays);
			}
		}
		
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
	
	public void setMenuButtonsEnabled(boolean enabled) {
		buttonSaveGame.setEnabled(enabled);
		buttonExitToMainMenu.setEnabled(enabled);
		buttonExitToDesktop.setEnabled(enabled);
		buttonCloseMenu.setEnabled(enabled);
	}
	
	public JButton getExitToMainMenu() {
		return buttonExitToMainMenu;
	}
	
	public JButton getExitToDesktop() {
		return buttonExitToDesktop;
	}
	
	public InternalDialog getCurrentDialog() {
		return currentDialog;
	}
}
