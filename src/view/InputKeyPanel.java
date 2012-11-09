package view;

import gameMaker.gameMaker;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import utility.Helper;

public class InputKeyPanel implements MouseListener,KeyListener{

	private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(gameMaker.class);	
	private JPanel inputPanel;
	
	private JLabel infoLbl;
	
	public InputKeyPanel(JPanel inputPanel){
		this.inputPanel=inputPanel;
		inputPanel.setFocusable(true);
		
		infoLbl = new JLabel();
		infoLbl.setText("Press here and type any key");
		infoLbl.setForeground(Color.BLACK);
		inputPanel.add(infoLbl);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		inputPanel.requestFocus(true);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	LOG.debug(KeyEvent.getKeyText(e.getKeyCode()));
		String charTyped = KeyEvent.getKeyText(e.getKeyCode());
		infoLbl.setText("You have selected :"+charTyped);
		Helper.getsharedHelper().setCurrentKeyCode(e.getKeyCode());
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
	
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	public JPanel getInputPanel() {
		return inputPanel;
	}

	public void setInputPanel(JPanel inputPanel) {
		this.inputPanel = inputPanel;
	}

}
