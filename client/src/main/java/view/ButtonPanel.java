package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utility.Score;

public class ButtonPanel {
//	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ButtonPanel.class);
	private GameMakerView design;
	private JPanel buttonPanel;
	private JLabel userName;
	
	public ButtonPanel(GameMakerView designArg) {
		this.design = designArg;
        buttonPanel = new JPanel();
        JButton newGame = new JButton("Clear");
        newGame.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	design.reset();
            }
        });
        
        
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	design.getFacade().startGame();
            	design.getGamePanel().requestFocusInWindow();
            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                design.getFacade().stopGame();
                // ScoreDialog.showScoreDialog();
                if (GameMakerView.getInstance().isShouldDisplayScore()) {
		    JOptionPane.showMessageDialog(design.getBaseFrame(), "Your Score is " + Score.getInstance().getScore());
		}
            }
        });       
        userName= new JLabel();  
        buttonPanel.add(start);
        buttonPanel.add(stop);
        buttonPanel.add(newGame);
        buttonPanel.add(userName);
        

    }
	
	public JPanel getPanel()
	{
		return buttonPanel;
	}

	public JLabel getUserName() {
		return userName;
	}

	public void setUserName(JLabel userName) {
		this.userName = userName;
	}


}
