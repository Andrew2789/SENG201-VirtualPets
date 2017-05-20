package gui;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;

public class GuiRunner {
	private Font poppins;
	private Font sourceSansPro;
	private Font sourceSansProHeavy;
	
	private JFrame frame;
	private MainMenu mainMenu;
	private GameSetup gameSetup;

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
		
		poppins = FontLoader.loadFont("fonts/Poppins/Poppins-Regular.ttf");
		sourceSansPro = FontLoader.loadFont("fonts/Source_Sans_Pro/SourceSansPro-Regular.ttf");
		sourceSansProHeavy = FontLoader.loadFont("fonts/Source_Sans_Pro/SourceSansPro-SemiBold.ttf");
	};
	
	/**
	 * Load the main menu screen and store it.
	 */
	private void loadMainMenu() {
		mainMenu = new MainMenu(poppins.deriveFont(84f), sourceSansProHeavy.deriveFont(16f));
		
		mainMenu.getNewGameButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
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
		gameSetup = new GameSetup(poppins.deriveFont(48f), sourceSansPro.deriveFont(14f));
		
		frame.getContentPane().add(gameSetup);
	}
}
