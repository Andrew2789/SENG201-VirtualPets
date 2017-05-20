import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class GuiRunner {

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
		frame.setBounds(100, 100, 800, 800);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	private MainMenu loadMainMenu() {
		MainMenu mainMenu = new MainMenu();
		mainMenu.setSize(800, 600);

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
