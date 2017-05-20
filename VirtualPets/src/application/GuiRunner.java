package application;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GuiRunner {
	private Font poppins;
	private Font sourceSansPro;
	private Font sourceSansProSemiBold;
	private Font sourceSansProBold;
	
	private Species[] species;
	private ImageIcon[] speciesIcons;
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
		species = new Species[] {
				new Species("Cat", 60, 10, 15, 5, 40, 70),
				new Species("Dog", 90, 15, 25, 10, 30, 55),
				new Species("Crab", 10, 12, 30, 10, 25, 45),
				new Species("Fish", 35, 20, 20, 2, 10, 20),
				new Species("Sloth", 55, 5, 40, 5, 15, 25)
		};
		
		speciesIcons = new ImageIcon[species.length];
		for (int i=0; i<speciesIcons.length; i++)
			speciesIcons[i] = new ImageIcon("images/species/"+species[i].getName()+".png");
		
		toyTypes = new ToyType[] {
				new ToyType("Proleteriat", -100, 100),
				new ToyType("Bell", 10, 15),
				new ToyType("Mouse", 25, 50)
		};
		
		foodTypes = new FoodType[] {
				new FoodType("Lasagne", 20, 50, 25, 10),
				new FoodType("Berries", 5, -10, -10, 5),
				new FoodType("Mushrooms", 10, 20, 20, 5),
				new FoodType("$2-rice", 2, 5, 20, 5)
		};
		
		initialise();
		loadMainMenu();
		loadGameSetup();
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
		
		poppins = loadFont("fonts/Poppins/Poppins-Regular.ttf");
		sourceSansPro = loadFont("fonts/Source_Sans_Pro/SourceSansPro-Regular.ttf");
		sourceSansProSemiBold = loadFont("fonts/Source_Sans_Pro/SourceSansPro-SemiBold.ttf");
		sourceSansProBold = loadFont("fonts/Source_Sans_Pro/SourceSansPro-Bold.ttf");
	};
	
	public Font loadFont(String location) {
		File font_file = new File(location);
		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
			return font;
		}
		catch (FontFormatException e) {
			System.out.println("Font must be a truetype font.");
			return null;
		}
		catch (IOException e) {
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
		gameSetup = new GameSetup(species, speciesIcons, toyTypes, foodTypes, poppins.deriveFont(48f), sourceSansProBold.deriveFont(14f), sourceSansProSemiBold.deriveFont(14f), sourceSansPro.deriveFont(14f));

		gameSetup.getDoneButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				game = gameSetup.generateGame();
				gameSetup.setVisible(false);
				frame.getContentPane().add(game);
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
}
