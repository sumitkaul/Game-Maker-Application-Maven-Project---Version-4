package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.html.ImageView;

import utility.Constants;

public class InfoPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InfoPanel() {
		//setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
		
//		URL url = getClass().getResource("speech-bubble-md.png");
//		ImageIcon imgIcon = new ImageIcon(url);
//		
//		Rectangle imageRect = new Rectangle(0,0,imgIcon.getIconWidth(),imgIcon.getIconHeight());
//		setBounds(0,0,imgIcon.getIconWidth(),imgIcon.getIconHeight()+40);
//		
//		JLayeredPane centerPane = new JLayeredPane();
//		
//		JLabel imageLabel = new JLabel();
//		imageLabel.setBounds(imageRect);
//		centerPane.add(imageLabel);
//		
//		JLabel textLabel = new JLabel();
//		Rectangle textRect = new Rectangle(imageRect.x+10,imageRect.y, imageRect.width-20, imageRect.height-20);
//		textLabel.setBounds(textRect);
//		textLabel.setText(Constants.spriteAddedText1);
//		
//		centerPane.add(textLabel);
//		
//		add(centerPane,BorderLayout.CENTER);
		
		JPanel controlPanel = new JPanel(new FlowLayout());
		controlPanel.setOpaque(false);
		
		JButton backButton = new JButton("<");
		backButton.addActionListener(this);
		controlPanel.add(backButton);
		
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		controlPanel.add(closeButton);
		
		JButton nextButton = new JButton(">");
		nextButton.addActionListener(this);
		controlPanel.add(nextButton);
		
		add(controlPanel,BorderLayout.NORTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton button = (JButton) arg0.getSource();
		if(button.getText().equalsIgnoreCase("<")){
			
		}
		else if(button.getText().equalsIgnoreCase(">")){
			
		}
		else if(button.getText().equalsIgnoreCase("Close")){
			removeAll();
		}
	}
		
}
