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
	private int min;
	private int max;

	/**
	 * Create the panel.
	 */
	public PetStatDisplayer(Font boldFont, Font semiBoldFont, String title, ImageIcon slider, String tooltip, int min, int max, int minLabelOffset, int maxLabelOffset) {
		setLayout(null);
		setOpaque(false);
		
		JLabel sliderTitle = new JLabel(title);
		sliderTitle.setBounds(0, 0, 232, 18);
		add(sliderTitle);
		sliderTitle.setHorizontalAlignment(SwingConstants.CENTER);
		sliderTitle.setFont(boldFont);
		
		JLabel sliderIcon = new JLabel(slider);
		sliderIcon.setBounds(0, 19, 232, 19);
		add(sliderIcon);
		sliderIcon.setToolTipText(tooltip);
		
		sliderRect = new JLabel("");
		sliderRect.setBounds(22, 19, 0, 8);
		sliderRect.setBackground(new Color(80, 240, 100));
		sliderRect.setOpaque(true);
		add(sliderRect);
		
		JLabel minLabel = new JLabel(Integer.toString(min));
		minLabel.setHorizontalAlignment(SwingConstants.CENTER);
		minLabel.setBounds(13, 25+minLabelOffset, 18, 22);
		minLabel.setFont(semiBoldFont);
		add(minLabel);
		
		JLabel maxLabel = new JLabel(Integer.toString(max));
		maxLabel.setHorizontalAlignment(SwingConstants.CENTER);
		maxLabel.setBounds(195, 25+maxLabelOffset, 27, 22);
		maxLabel.setFont(semiBoldFont);
		add(maxLabel);
		
		this.min = min;
		this.max = max;
	}
	
	public void setStat(int amount) {
		sliderRect.setBounds(22, 19, amount*188/(max-min), 8);
	}
	
	public void setMinMax(int min, int max) {
		this.min = min;
		this.max = max;
	}
}
