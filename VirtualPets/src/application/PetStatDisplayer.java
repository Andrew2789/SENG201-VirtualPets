package application;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * A view only slider that displays information about a pet's attribute.
 * @author Andrew Davidson (ada130)
 */
public class PetStatDisplayer extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel sliderRect, currentValue, currentValueRect, minLabel, maxLabel;
	private int min, max;

	/**
	 * Create the panel - a view only slider that displays information about a pet's attribute to a player.
	 * @param boldFont
	 * The font to use for headings
	 * @param semiBoldFont
	 * The font to use for less important labels
	 * @param title
	 * The name of the attribute to display
	 * @param slider
	 * The slider image to use
	 * @param tooltip
	 * The tooltip to display on hover
	 * @param sliderColour
	 * The colour of the slider rectangle to generate
	 * @param min
	 * The minimum value for this attribute
	 * @param max
	 * The maximum value for this attribute
	 * @param minLabelOffset
	 * The vertical offset of the minimum value label
	 * @param maxLabelOffset
	 * The vertical offset of the maximum value label
	 */
	public PetStatDisplayer(Font boldFont, Font semiBoldFont, String title, ImageIcon slider, 
			String tooltip, Color sliderColour, int min, int max, int minLabelOffset, int maxLabelOffset) {
		setLayout(null);
		setOpaque(false);
		
		//Title label
		JLabel sliderTitle = new JLabel(title);
		sliderTitle.setBounds(0, 0, 232, 18);
		add(sliderTitle);
		sliderTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sliderTitle.setFont(boldFont);
		
		//Current value label and marker rectangle
		currentValue = new JLabel("");
		currentValue.setHorizontalAlignment(SwingConstants.CENTER);
		currentValue.setFont(boldFont);
		currentValue.setBounds(0, 31, 28, 22);
		add(currentValue);
		
		currentValueRect = new JLabel("");
		currentValueRect.setBounds(0, 15, 2, 6);
		currentValueRect.setBackground(Color.BLACK);
		currentValueRect.setOpaque(true);
		add(currentValueRect);
		
		//The slider image
		JLabel sliderIcon = new JLabel(slider);
		sliderIcon.setBounds(0, 19, 232, 19);
		add(sliderIcon);
		sliderIcon.setToolTipText(tooltip);
		
		//The rectangle showing the current value of the attribute
		sliderRect = new JLabel("");
		sliderRect.setBounds(22, 19, 0, 8);
		sliderRect.setBackground(sliderColour);
		sliderRect.setOpaque(true);
		add(sliderRect);
		
		//Labels for the minimum and maximum values reachable
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
	
	/**
	 * Update the slider with a new value for the displayed attribute.
	 * @param amount
	 * The new value
	 */
	public void setStat(int amount) {
		int scaledAmount = (amount-min)*188/(max-min);
		sliderRect.setBounds(22, 19, scaledAmount, 8);
		currentValueRect.setBounds(scaledAmount+20, 29, 2, 6);
		if (amount == min || amount == max) {
			currentValue.setVisible(false);
		}
		else {
			currentValue.setVisible(true);
			currentValue.setBounds((amount-min)*188/(max-min)+8, 31, 28, 22);
			currentValue.setText(Integer.toString(amount));
		}
	}
	
	/**
	 * Set the minimum and maximum values for the slider.
	 * @param min
	 * The new minimum value
	 * @param max
	 * The new maximum value
	 */
	public void setMinMax(int min, int max) {
		this.min = min;
		this.max = max;
		minLabel.setText(Integer.toString(min));
		maxLabel.setText(Integer.toString(max));
	}
}
