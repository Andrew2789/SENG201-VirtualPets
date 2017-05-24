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
	private JLabel promptLabel;
	private JLabel promptLabel2;
	private JButton buttonOk;
	private JButton buttonCancel;

	/**
	 * Create the panel.
	 */
	public InternalDialog(Font boldFont) {
		setBackground(Color.GRAY);
		setVisible(false);
		setLayout(null);
		
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
	
	public void setOptions(String line1, String line2, boolean okShown) {
		promptLabel.setText(line1);
		promptLabel2.setText(line2);
		buttonOk.setVisible(okShown);
		if (okShown)
			buttonCancel.setBounds(12, 62, 85, 26);
		else
			buttonCancel.setBounds(82, 62, 85, 26);
		
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

	public JButton getButtonOk() {
		return buttonOk;
	}
	
	public JButton getButtonCancel() {
		return buttonCancel;
	}

}
