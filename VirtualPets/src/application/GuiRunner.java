package application;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;

import customFileLoader.Loader;

/**
 * A controller and viewer for the application. Loads and shows the various game panels.
 * @author Andrew Davidson (ada130)
 */
public class GuiRunner {
	private Font poppins, sourceSansPro, sourceSansProSemibold, sourceSansProBold;
	private Species[] species;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;
	
	private JFrame frame;
	private MainMenu mainMenu;
	private GameSetup gameSetup;
	private AssetCreator assetCreator;
	private Game game;

	/**
	 * Launch the application.
	 * @param args
	 * Arguments to run the application with. Not used
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiRunner window = new GuiRunner();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Load fonts, species, toy types, and food types. Initialises the gui and loads the main menu.
	 */
	public GuiRunner() {
		species = Loader.loadCustomSpeciesFile(GuiRunner.class.getResourceAsStream("/default_species.txt"));
		toyTypes = Loader.loadCustomToyTypesFile(GuiRunner.class.getResourceAsStream("/default_toytypes.txt"));
		foodTypes = Loader.loadCustomFoodTypesFile(GuiRunner.class.getResourceAsStream("/default_foodtypes.txt"));
		
		poppins = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Poppins/Poppins-Regular.ttf"));
		sourceSansPro = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Regular.ttf"));
		sourceSansProSemibold = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Semibold.ttf"));
		sourceSansProBold = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Bold.ttf"));
		
		initialise();
		loadMainMenu();
		mainMenu.setVisible(true);
	}
	
	/**
	 * Initialises the frame at the correct size with nothing in it.
	 */
	private void initialise() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		frame.pack();
	};
	
	/**
	 * Load a font.
	 * @param file
	 * An InputStream to the desired font
	 * @return
	 * The loaded font
	 */
	public Font loadFont(InputStream file) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, file);
			return font;
		}
		catch (FontFormatException e) {
			System.out.println("Font must be a truetype font.");
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to load font.");
			return null;
		}
	}
	
	/**
	 * Load the main menu screen and store it.
	 */
	private void loadMainMenu() {
		mainMenu = new MainMenu(poppins.deriveFont(84f), sourceSansProSemibold.deriveFont(16f), sourceSansProBold.deriveFont(15f), sourceSansPro.deriveFont(15f));

		//Switch to gameSetup if new game is clicked
		mainMenu.getNewGameButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadGameSetup();
				gameSetup.setVisible(true);
				mainMenu.setVisible(false);
			}
		});	
		
		//Load a game if load game is clicked
		mainMenu.getLoadGameButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Get some things
				loadGame();
				game.resume(players, currentDay, currentPlayerIndex, numberOfDays, incomePerTurn, previousScores);
				game.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		
		//Switch to AssetCreator if create new asset is clicked
		mainMenu.getCreateNewAssetButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadAssetCreator();
				assetCreator.setVisible(true);
				mainMenu.setVisible(false);
			}
		});
		
		//Quit if quit is pressed
		mainMenu.getQuitButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(mainMenu);
	}
	
	/**
	 * Load the game setup screen and store it.
	 */
	private void loadGameSetup() {
		gameSetup = new GameSetup(species, toyTypes, foodTypes, poppins.deriveFont(48f), sourceSansProBold.deriveFont(14f), sourceSansProSemibold.deriveFont(14f), sourceSansPro.deriveFont(14f));

		//If all fields are filled, use the inputs to create and start a new game
		gameSetup.getDoneButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (gameSetup.fieldsFilled()) {
					loadGame();
					if (gameSetup.setGamePlayers(game)) {
						gameSetup.setVisible(false);
						game.setVisible(true);
					}
				}
			}
		});
		
		//Go back to the main menu if back is clicked
		gameSetup.getBackButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gameSetup.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		
		frame.getContentPane().add(gameSetup);
	}
	
	private void loadAssetCreator() {
		assetCreator = new AssetCreator(poppins.deriveFont(72f), sourceSansProSemibold.deriveFont(14f));
		
		//Go back to the main menu when back is pressed
		assetCreator.getBackButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				assetCreator.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		
		frame.getContentPane().add(assetCreator);
	}
	
	/**
	 * Load the game screen and store it.
	 */
	private void loadGame() {
		RoundOverview roundOverview = new RoundOverview(poppins.deriveFont(36f), poppins.deriveFont(22f), sourceSansProBold.deriveFont(16f));
		
		//When the game ends, show the main menu
		roundOverview.getButtonEndGame().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				roundOverview.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		
		frame.getContentPane().add(roundOverview);
		
		//When exit to main menu is pressed, switch to the main menu
		ActionListener exitToMainMenu = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainMenu.setVisible(true);
				game.setVisible(false);
			}
		};
		
		//When exit to desktop is pressed, quit
		ActionListener exitToDesktop = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		};
		
		game = new Game(toyTypes, foodTypes, poppins.deriveFont(48f), poppins.deriveFont(18f), sourceSansProBold.deriveFont(14f), 
				sourceSansProSemibold.deriveFont(14f), sourceSansPro.deriveFont(14f), roundOverview, exitToMainMenu, exitToDesktop);
		
		frame.getContentPane().add(game);
	}
}
