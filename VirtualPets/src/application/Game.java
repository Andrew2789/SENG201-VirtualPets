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
	private Player activePlayer;
	private Pet activePet;
	
	private JLabel dayLabel;
	private JLabel playerLabel;
	private JButton buttonShop;
	private JButton buttonEndTurn;
	private JButton buttonMenu;
	
	private JPanel menu;
	private JButton saveGame;
	private JButton exitToMainMenu;
	private JButton exitToDesktop;
	
	private PetTab[] petTabs = new PetTab[3];
	private int[][] tabLayouts = {{175, 0, 0}, {100, 250, 0}, {25, 175, 325}};
	private PetInteract petInteract;
	
	private JLabel inventoryMoney;
	

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
		
		buttonShop = new JButton(new ImageIcon(Game.class.getResource("/images/shop.png")));
		buttonShop.setBounds(531, 123, 65, 65);
		buttonShop.setToolTipText("Shop for food and toys for your pets.");
		add(buttonShop);
		
		buttonEndTurn = new JButton(new ImageIcon(Game.class.getResource("/images/endTurn.png")));
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
	}
	
	public void initialise(Player[] players, int numberOfDays, int incomePerTurn) {
		this.players = players;
		this.numberOfDays = numberOfDays;
		this.incomePerTurn = incomePerTurn;
		dayLabel.setText("Day 1 of "+numberOfDays);
		setTurn(0);
	}
	
	public void setTurn(int playerIndex) {
		activePlayer = players[playerIndex];
		playerLabel.setText(activePlayer.getName()+"'s turn");
		inventoryMoney.setText("Money: $"+activePlayer.getMoney());
		for (int i=0; i<3; i++) {
			if (i < activePlayer.getPets().length) {
				petTabs[i].setBounds(tabLayouts[activePlayer.getPets().length-1][i], 100, 148, 155);
				petTabs[i].setPet(activePlayer.getPets()[i]);
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
		petInteract.setPet(activePet);
	}
	
	public void setButtonsEnabled(boolean enabled) {
		buttonShop.setEnabled(enabled);
		buttonEndTurn.setEnabled(enabled);
		buttonMenu.setEnabled(enabled);
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
