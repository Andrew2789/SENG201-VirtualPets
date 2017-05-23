package application;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PetStatDisplayer extends JPanel {
	private static final long serialVersionUID = 8536607353336710123L;
	private JLabel sliderRect;
	private JLabel currentValue;
	private JLabel currentValueRect;
	private JLabel minLabel;
	private JLabel maxLabel;
	private int min;
	private int max;

	/**
	 * Create the panel.
	 */
	public PetStatDisplayer(Font boldFont, Font semiBoldFont, String title, ImageIcon slider, 
			String tooltip, Color sliderColour, int min, int max, int minLabelOffset, int maxLabelOffset) {
		setLayout(null);
		setOpaque(false);
		
		JLabel sliderTitle = new JLabel(title);
		sliderTitle.setBounds(0, 0, 232, 18);
		add(sliderTitle);
		sliderTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sliderTitle.setFont(boldFont);
		
		currentValue = new JLabel("");
		currentValue.setHorizontalAlignment(SwingConstants.CENTER);
		currentValue.setFont(boldFont);
		currentValue.setBounds(0, 31, 18, 22);
		add(currentValue);
		
		currentValueRect = new JLabel("");
		currentValueRect.setBounds(0, 15, 2, 6);
		currentValueRect.setBackground(Color.BLACK);
		currentValueRect.setOpaque(true);
		add(currentValueRect);
		
		JLabel sliderIcon = new JLabel(slider);
		sliderIcon.setBounds(0, 19, 232, 19);
		add(sliderIcon);
		sliderIcon.setToolTipText(tooltip);
		
		sliderRect = new JLabel("");
		sliderRect.setBounds(22, 19, 0, 8);
		sliderRect.setBackground(sliderColour);
		sliderRect.setOpaque(true);
		add(sliderRect);
		
		minLabel = new JLabel(Integer.toString(min));
		minLabel.setHorizontalAlignment(SwingConstants.CENTER);
		minLabel.setBounds(13, 25+minLabelOffset, 18, 22);
		minLabel.setFont(semiBoldFont);
		add(minLabel);
		
		maxLabel = new JLabel(Integer.toString(max));
		maxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		maxLabel.setBounds(195, 25+maxLabelOffset, 27, 22);
		maxLabel.setFont(semiBoldFont);
		add(maxLabel);
		
		this.min = min;
		this.max = max;
	}
	
	public void setStat(int amount) {
		int scaledAmount = (amount-min)*188/(max-min);
		sliderRect.setBounds(22, 19, scaledAmount, 8);
		currentValueRect.setBounds(scaledAmount+20, 29, 2, 6);
		currentValue.setBounds((amount-min)*188/(max-min)+12, 31, 18, 22);
		currentValue.setText(Integer.toString(amount));
	}
	
	public void setMinMax(int min, int max) {
		this.min = min;
		this.max = max;
		minLabel.setText(Integer.toString(min));
		maxLabel.setText(Integer.toString(max));
	}
}
