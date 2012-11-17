package view;

import javax.swing.JFrame;

import utility.Constants;
import utility.Helper;

public class GamePlayerView {

	private JFrame baseFrame;
	
	public GamePlayerView(int frameWidth, int frameHeight) {
		baseFrame = Helper.getsharedHelper().createBaseFrame(frameWidth,frameHeight);
		baseFrame.setJMenuBar(new MenuBarPanel().getMenuBar());
		
		GamePanel gamePanel = new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
		baseFrame.add(gamePanel);
		
		PlayerButtonPanel playerButtonPanel = new PlayerButtonPanel();
		baseFrame.add(playerButtonPanel.getPlayerButtonPanel());
		
		baseFrame.setVisible(true);
	}
	
}
