package view;

import javax.swing.JFrame;

import chat.ChatReceiver;
import chat.ChatSender;
import chat.StatusReceiver;
import chat.StatusSender;
import facade.Facade;

import utility.Constants;
import utility.Helper;

public class GamePlayerView {

	private JFrame baseFrame;
	private GamePanel gamePanel;
	private Facade facade;
	
	public GamePlayerView(int frameWidth, int frameHeight) {
		baseFrame = Helper.getsharedHelper().createBaseFrame(frameWidth,frameHeight);
		baseFrame.setJMenuBar(new MenuBarPanel().getMenuBar());
		
		gamePanel = new GamePanel(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
		baseFrame.add(gamePanel);
		facade = new Facade(gamePanel);
		
		PlayerButtonPanel playerButtonPanel = new PlayerButtonPanel();
		baseFrame.add(playerButtonPanel.getPlayerButtonPanel());
		
		baseFrame.setVisible(true);
		
		new ChatSender();
		new ChatReceiver();
		new StatusSender();
		new StatusReceiver();
	}

	/*
	 * Getter and Setter methods
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public Facade getFacade() {
		return facade;
	}

	public void setFacade(Facade facade) {
		this.facade = facade;
	}
	
}
