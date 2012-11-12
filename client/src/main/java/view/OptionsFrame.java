package view;


import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;

import team3.a9.lookandfeel.AnimationHandler;
import utility.ClockDisplay;

import net.miginfocom.swing.MigLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsFrame implements ActionListener{

	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(OptionsFrame.class);
	private JFrame optionFrame;
	private JButton makerButton, playerButton;
	
	public OptionsFrame(){
		optionFrame = new JFrame();
    	makerButton = new JButton("Make Game");
    	makerButton.addActionListener(this);
    	playerButton = new JButton("Play Game");
    	playerButton.addActionListener(this);

    	
    	optionFrame.setLayout(new MigLayout("center,center"));
    	optionFrame.add(makerButton,"wmin 100, hmin 150");
    	optionFrame.add(playerButton,"wmin 100, hmin 150");
    	
    	optionFrame.setSize(300, 300);
        optionFrame.setFocusable(true);
        optionFrame.setLocationRelativeTo(null);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setTitle("Option Page");
        optionFrame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		  if (e.getSource() == makerButton) {	
			  Design.getInstance().setOptionFrame(this);
			  Design.getInstance().reset();
			  Design.getInstance().clearAll();
			  Design.getInstance().getGamePanel().registerDrawable(ClockDisplay.getInstance());
			  Design.getInstance().getBaseFrame().setVisible(true);
			  AnimationHandler.FadeOut(Design.getInstance().getPlayerButtonPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
			  try {
				Thread.sleep(1000);
			  } catch (InterruptedException e1) {
				  LOG.debug(e1);
			}
			  AnimationHandler.FadeIn(Design.getInstance().getGameMakerPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
			  optionFrame.setVisible(false);
		  }
		  
		  if (e.getSource() == playerButton) {
			  Design.getInstance();
			  Design.getInstance().setOptionFrame(this);
			  Design.getInstance().reset();
			  Design.getInstance().clearAll();
			  Design.getInstance().getBaseFrame().setVisible(true);
			  AnimationHandler.FadeOut(Design.getInstance().getGameMakerPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
			  try {
					Thread.sleep(1000);
				  } catch (InterruptedException e1) {
					  LOG.debug(e1);
				}
			  AnimationHandler.FadeIn(Design.getInstance().getPlayerButtonPanel(), (JComponent) Design.getInstance().getBaseFrame().getContentPane(), 500);
			  
			  /*
			  GamePlayerWindow.getInstance();
			  GamePlayerWindow.getInstance().setOptionFrame(this);
			  GamePlayerWindow.getInstance().getBaseFrame().setVisible(true);
			  */
			  optionFrame.setVisible(false);
		  }
		
	}

	public JFrame getOptionFrame() {
		return optionFrame;
	}

	public void setOptionFrame(JFrame optionFrame) {
		this.optionFrame = optionFrame;
	}
    

}
