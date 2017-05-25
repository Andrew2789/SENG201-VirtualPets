package application;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InternalDialog extends JPanel {
	private static final long serialVersionUID = 8843581086428980769L;
	private JLabel promptLabel, promptLabel2;
	private JButton buttonOk, buttonCancel;

	/**
	 * Create the panel - a small internal dialog box which can have instantiators add actionListeners to its buttons.
	 * @param boldFont
	 * The font to use for all text in the dialog box
	 */
	public InternalDialog(Font boldFont) {
		setBackground(Color.GRAY);
		setVisible(false);
		setLayout(null);
		
		//Two lines to display a notification on
		promptLabel = new JLabel("");
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
		promptLabel.setFont(boldFont);
		promptLabel.setForeground(Color.WHITE);
		promptLabel.setBounds(12, 5, 228, 21);
		add(promptLabel);
		
		promptLabel2 = new JLabel("");
		promptLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		promptLabel2.setFont(boldFont);
		promptLabel2.setForeground(Color.WHITE);
		promptLabel2.setBounds(12, 25, 228, 21);
		add(promptLabel2);
		
		//Ok and Cancel buttons, can be set to shown or false and can have actionListeners added
		buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonOk.setBounds(155, 62, 85, 26);
		buttonOk.setFont(boldFont);
		add(buttonOk);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		buttonCancel.setBounds(12, 62, 85, 26);
		buttonCancel.setFont(boldFont);
		add(buttonCancel);
	}

	//Getters
	public JButton getButtonOk() {
		return buttonOk;
	}
	
	public JButton getButtonCancel() {
		return buttonCancel;
	}
	//End getters
	
	/**
	 * Set the dialog box contents. Clean out old actionListeners and make it hide on either button click.
	 * @param line1
	 * The first notification liine
	 * @param line2
	 * The second notification line
	 * @param okShown
	 * Whether the Ok button should be shown
	 * @param cancelShown
	 * Whether the Cancel button should be shown
	 */
	public void setOptions(String line1, String line2, boolean okShown, boolean cancelShown) {
		promptLabel.setText(line1);
		promptLabel2.setText(line2);
		buttonOk.setVisible(okShown);
		buttonCancel.setVisible(cancelShown);
		if (okShown)
			buttonCancel.setBounds(12, 62, 85, 26);
		else
			buttonCancel.setBounds(82, 62, 85, 26);
		
		if (cancelShown)
			buttonOk.setBounds(155, 62, 85, 26);
		else
			buttonOk.setBounds(82, 62, 85, 26);
		
		for (ActionListener al: buttonOk.getActionListeners())
			buttonOk.removeActionListener(al);
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		for (ActionListener al: buttonCancel.getActionListeners())
			buttonCancel.removeActionListener(al);
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

}
