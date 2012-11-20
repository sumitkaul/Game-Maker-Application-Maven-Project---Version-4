package view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InfoPanel() {
		
		
		JLabel textLabel = new JLabel();
		
		textLabel.setText("Here is the sprite object which you created. saufhasu a" +
				"fgsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahgdg" +
				"sdggsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahgsdg" +
				"sgsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahgg" +
				"sgsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahgdg" +
				"sgsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahgdg" +
				"sgsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahglayeredPane" +
				"gsgsdgsdgsdgsdgsgsgsgsgsiuhaeiughdoghoahg");
		
		add(textLabel);
		
		
		JButton nextButton = new JButton("Next");
		add(nextButton);
		
	}
		
}
