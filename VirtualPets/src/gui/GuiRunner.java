package gui;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GuiRunner {
	private Dimension windowDimensions = new Dimension(800, 600);

	private JFrame frame;

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
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialise() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.getContentPane().setPreferredSize(windowDimensions);
		frame.pack();
	}
	
	private MainMenu loadMainMenu() {
		MainMenu mainMenu = new MainMenu();

		mainMenu.getNewGameButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mainMenu.setVisible(false);
				loadGameSetup();
			}
		});
		
		mainMenu.getQuitButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
			}
		});
		
		frame.getContentPane().add(mainMenu);
		return mainMenu;
	}
	
	private GameSetup loadGameSetup() {
		GameSetup gameSetup = new GameSetup();
		gameSetup.setSize(800, 600);
		frame.getContentPane().add(gameSetup);
		return gameSetup;
	}
}
