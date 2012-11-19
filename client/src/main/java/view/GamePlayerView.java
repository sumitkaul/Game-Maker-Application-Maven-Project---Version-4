package view;

import javax.swing.JFrame;

import chat.ChatReceiver;
import chat.ChatSender;
import chat.StatusReceiver;
import chat.StatusSender;

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
		ChatSender chatSender=new ChatSender();
		ChatReceiver chatReceiver=new ChatReceiver();
		StatusSender statusSender=new StatusSender();
		StatusReceiver statusReceiver=new StatusReceiver();
	}
	
}
