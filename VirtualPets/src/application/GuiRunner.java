package application;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import customFileLoader.SettingsLoader;
import saveFileHandling.GameLoader;

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
	private HelpPanel helpPanel;

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
		species = SettingsLoader.loadCustomSpeciesFile(GuiRunner.class.getResourceAsStream("/default_species.txt"));
		toyTypes = SettingsLoader.loadCustomToyTypesFile(GuiRunner.class.getResourceAsStream("/default_toytypes.txt"));
		foodTypes = SettingsLoader.loadCustomFoodTypesFile(GuiRunner.class.getResourceAsStream("/default_foodtypes.txt"));
		
		poppins = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Poppins/Poppins-Regular.ttf"));
		sourceSansPro = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Regular.ttf"));
		sourceSansProSemibold = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Semibold.ttf"));
		sourceSansProBold = loadFont(GuiRunner.class.getResourceAsStream("/fonts/Source_Sans_Pro/SourceSansPro-Bold.ttf"));
		
		initialise();
		loadMainMenu();
		helpPanel = new HelpPanel(sourceSansProBold.deriveFont(15f), sourceSansPro.deriveFont(15f));
		frame.add(helpPanel);
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
		finally {
			try {
				file.close();
			}
			catch (IOException e) {
			}
		}
	}
	
	/**
	 * Load the main menu screen and store it.
	 */
	private void loadMainMenu() {
		mainMenu = new MainMenu(poppins.deriveFont(84f), sourceSansProSemibold.deriveFont(16f));

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
				JFileChooser openFileDialog = new JFileChooser();
				if (openFileDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						File saveFile = openFileDialog.getSelectedFile();
						loadGame();
						Game savedGame = GameLoader.readGameFromFile(saveFile);
						game.setVisible(true);
						mainMenu.setVisible(false);
						game.resume(savedGame);
					}
					catch (NullPointerException exc) {
						System.err.println("Loading game file failed due to an invalid/missing file being provided.");
					}
				}
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
		
		//Open help panel if help pressed
		mainMenu.getHelpButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				helpPanel.setActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						helpPanel.setVisible(false);
						mainMenu.setVisible(true);
					}
				});
				helpPanel.setVisible(true);
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
	
	/**
	 * Load the asset creation screen and store it.
	 */
	private void loadAssetCreator() {
		assetCreator = new AssetCreator(poppins.deriveFont(72f), sourceSansProSemibold.deriveFont(14f));
		
		//Go back to the main menu when back is pressed
		assetCreator.getBackButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				assetCreator.setVisible(false);
				
				//Load newly created species, toy types, and food types
				
				ArrayList<Species> newSpecies = assetCreator.getNewSpecies();
				if (newSpecies.size() > 0) {
					Species[] updatedSpecies = new Species[species.length+newSpecies.size()];
					for (int i=0; i<species.length; i++)
						updatedSpecies[i] = species[i];
					for (int i=0; i<newSpecies.size(); i++)
						updatedSpecies[species.length+i] = newSpecies.get(i);
					species = updatedSpecies;
				}
				
				ArrayList<ToyType> newToyTypes = assetCreator.getNewToyTypes();
				if (newToyTypes.size() > 0) {
					ToyType[] updatedToyTypes = new ToyType[toyTypes.length+newToyTypes.size()];
					for (int i=0; i<toyTypes.length; i++)
						updatedToyTypes[i] = toyTypes[i];
					for (int i=0; i<newToyTypes.size(); i++)
						updatedToyTypes[toyTypes.length+i] = newToyTypes.get(i);
					toyTypes = updatedToyTypes;
				}
				
				ArrayList<FoodType> newFoodTypes = assetCreator.getNewFoodTypes();
				if (newFoodTypes.size() > 0) {
					FoodType[] updatedFoodTypes = new FoodType[foodTypes.length+newFoodTypes.size()];
					for (int i=0; i<foodTypes.length; i++)
						updatedFoodTypes[i] = foodTypes[i];
					for (int i=0; i<newFoodTypes.size(); i++)
						updatedFoodTypes[foodTypes.length+i] = newFoodTypes.get(i);
					foodTypes = updatedFoodTypes;
				}
				
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
		
		//Open help panel if help pressed
		game.getHelpButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				helpPanel.setActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						helpPanel.setVisible(false);
						game.setVisible(true);
					}
				});
				helpPanel.setVisible(true);
				game.setVisible(false);
			}
		});
		
		frame.getContentPane().add(game);
	}
}
