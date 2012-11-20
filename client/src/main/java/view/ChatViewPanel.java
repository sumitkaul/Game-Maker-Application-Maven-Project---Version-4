package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.util.Log;



import model.Player;
import chat.ChatReceiver;
import chat.ChatSender;
import chat.OneToOneReceiver;
import chat.OneToOneSender;
	
public class ChatViewPanel {
	
	PlayerButtonPanel playerButtonPanel;
	private JPanel chatViewPanel;
	private JTabbedPane tab;
	private JPanel commonChat;
	private JPanel gameChat;
	private JPanel chatPanel;
	private JPanel commonChatPanel;
	private JPanel gameChatPannel;
	
	
	private JList buddyList;
	private JList gameBuddyList;
	
	private JScrollPane buddyScroll;
	private JScrollPane gameBuddyScroll;
	private String budList[] = {"Mayur", "Shruthi", "Pranav"};
	private String gameBudList[] ={"Rohan","Charan","Ridhima"};
	
	public ChatViewPanel(PlayerButtonPanel playerButtonPanelArg){
		this.playerButtonPanel = playerButtonPanelArg;
		chatViewPanel = new JPanel();
		commonChat= new JPanel();
		gameChat = new JPanel();
		
		commonChatPanel = new JPanel();
		gameChatPannel = new JPanel();
		//chatViewPanel.setBackground(new Color(50, 150, 50));
		
		tab = new JTabbedPane();
		commonChat = new JPanel();
		gameChat = new JPanel();
		
		buddyList = new JList();
		buddyList.setListData(budList);
		buddyScroll = new JScrollPane(buddyList);
		
		gameBuddyList = new JList();
		gameBuddyList.setListData(gameBudList);
		gameBuddyScroll = new JScrollPane(gameBuddyList);
		ChatSender chatSender = new ChatSender();
		ChatPanel commChatPanel=new ChatPanel(chatSender);
		commonChatPanel = commChatPanel.getChatPanel();
		new ChatReceiver(commChatPanel);
		
		commonChat.setLayout(new GridLayout(1,2));
		commonChat.add(buddyScroll);
		commonChat.add(commonChatPanel);
	
		OneToOneSender oneSender=new OneToOneSender("topicname");
		ChatPanel gameChatPanel=new ChatPanel(oneSender);
		gameChatPannel = gameChatPanel.getChatPanel();
		//new OneToOneReceiver(topicName, gameChatPanel)
		
		gameChat.setLayout(new GridLayout(1,2));
		gameChat.add(gameBuddyScroll);
		gameChat.add(gameChatPannel);
		
		tab.addTab("Common Chat", commonChat);
		tab.addTab("Game Chat", gameChat);
		
		chatViewPanel.setLayout(new BorderLayout());
		chatViewPanel.add(tab, BorderLayout.CENTER);

		
	}
	

	public void setChatViewPanel(JPanel chatViewPanel){
		this.chatViewPanel = chatViewPanel;
	}
	
	public JPanel getChatViewPanel(){
		return chatViewPanel;
	}

}
