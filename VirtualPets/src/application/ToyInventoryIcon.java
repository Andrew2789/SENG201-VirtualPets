package application;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToyInventoryIcon extends JPanel {
	private static final long serialVersionUID = -6546718780959454045L;
	private JButton clickDetector;
	private JLabel toyIcon;
	private JLabel durabilityLabel;
	private Toy specificToy;
	
	/**
	 * Create the panel.
	 */
	public ToyInventoryIcon(Toy toy, Font semiBoldFont) {
		setLayout(null);
		setOpaque(false);
		
		durabilityLabel = new JLabel("");
		durabilityLabel.setFont(semiBoldFont);
		durabilityLabel.setBounds(10, 10, 65, 15);
		add(durabilityLabel);
		
		toyIcon = new JLabel("");
		toyIcon.setBounds(5, 5, 75, 75);
		add(toyIcon);
		
		clickDetector = new JButton();
		clickDetector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		clickDetector.setContentAreaFilled(false);
		clickDetector.setBorderPainted(false);
		clickDetector.setBounds(0, 0, 85, 85);
		add(clickDetector);
		
		setToyIcon(toy.getToyType());
		setdurability(toy.getDurability());
		this.specificToy = toy;
	}
	
	public void setToyIcon(ToyType toyType) {
		this.toyIcon.setIcon(toyType.getIcon());
	}
	
	public void setdurability(int durability) {
		this.durabilityLabel.setText(String.format("%d%%", durability));
	}
	
	public JButton getClickDetector() {
		return clickDetector;
	}
	
	public Toy getSpecificToy() {
		return specificToy;
	}
	
	public void setButtonEnabled(boolean enabled) {
		this.clickDetector.setEnabled(enabled);
	}
}
