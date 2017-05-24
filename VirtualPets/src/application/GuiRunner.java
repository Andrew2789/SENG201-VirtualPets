package application;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;

public class GuiRunner {
	private Font poppins;
	private Font sourceSansPro;
	private Font sourceSansProSemiBold;
	private Font sourceSansProBold;
	
	private Species[] species;
	private ToyType[] toyTypes;
	private FoodType[] foodTypes;
	
	private JFrame frame;
	private MainMenu mainMenu;
	private GameSetup gameSetup;
	private Game game;

	/**
	 * Launch the application.
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
	 * Create the application.
	 */
	public GuiRunner() {
		species = SettingsLoader.loadCustomSpeciesFile("resources/default_species.txt");
		
		toyTypes = new ToyType[] {
				new ToyType("Proleteriat", -100, 100),
				new ToyType("Bell", 10, 15),
				new ToyType("Mouse", 25, 50)
		};
		
		foodTypes = new FoodType[] {
				new FoodType("Lasagne", 20, 50, 25, 10),
				new FoodType("Berries", 5, -10, -10, 5),
				new FoodType("Mushrooms", 10, 20, 20, 5),
				new FoodType("$2-Rice", 2, 5, 20, 5)
		};
		
		initialise();
		loadMainMenu();
		mainMenu.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame and load fonts.
	 */
	private void initialise() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(new Dimension(800, 600));
		frame.pack();
		
		poppins = loadFont(GuiRunner.class.getResource("/fonts/Poppins/Poppins-Regular.ttf"));
		sourceSansPro = loadFont(GuiRunner.class.getResource("/fonts/Source_Sans_Pro/SourceSansPro-Regular.ttf"));
		sourceSansProSemiBold = loadFont(GuiRunner.class.getResource("/fonts/Source_Sans_Pro/SourceSansPro-Semibold.ttf"));
		sourceSansProBold = loadFont(GuiRunner.class.getResource("/fonts/Source_Sans_Pro/SourceSansPro-Bold.ttf"));
	};
	
	public Font loadFont(URL location) {
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, location.openStream());
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
		mainMenu = new MainMenu(poppins.deriveFont(84f), sourceSansProSemiBold.deriveFont(16f));
		
		mainMenu.getNewGameButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadGameSetup();
				mainMenu.setVisible(false);
				gameSetup.setVisible(true);
			}
		});
		
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
		gameSetup = new GameSetup(species, toyTypes, foodTypes, poppins.deriveFont(48f), sourceSansProBold.deriveFont(14f), sourceSansProSemiBold.deriveFont(14f), sourceSansPro.deriveFont(14f));

		gameSetup.getDoneButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loadGame();
				gameSetup.setGamePlayers(game);
				gameSetup.setVisible(false);
				game.setVisible(true);
			}
		});
		
		gameSetup.getBackButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				gameSetup.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		
		frame.getContentPane().add(gameSetup);
	}
	
	private void loadGame() {
		game = new Game(toyTypes, foodTypes, poppins.deriveFont(48f), poppins.deriveFont(18f), sourceSansProBold.deriveFont(14f), sourceSansProSemiBold.deriveFont(14f), sourceSansPro.deriveFont(14f));
		
		frame.getContentPane().add(game);
	}
}
