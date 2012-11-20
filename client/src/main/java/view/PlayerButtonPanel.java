package view;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import chat.ChatSender;

import net.miginfocom.swing.MigLayout;

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.Player;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Layers;
import utility.Score;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameProgressLoadPanel;
import view.companels.GameProgressSavePanel;
import view.companels.TopScoresPanel;

public class PlayerButtonPanel implements ActionListener{
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PlayerButtonPanel.class);
	private JPanel playerButtonPanel;
	private JLabel userName;
	private JButton startButton;
	private JButton pauseButton;
	private JButton newButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton topscoreButton;
	private JButton send;
	private JButton share;
	private JButton chooseUsername;
	private JButton chooseText;
	private JTextField textSend;
	private static JTextPane textPane;
	private static JScrollPane textScrollPane;
	private Color userColor;
	private Color textColor;
	private String playerAvatarUrl;
	private static HTMLEditorKit kit;
    private static HTMLDocument doc;
    
    
	public static void updateChatWindow(String msg) {

		try {
		kit.insertHTML(doc, doc.getLength(),msg, 0, 0, null);
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		textPane.setCaretPosition(textPane.getDocument().getLength());
		textScrollPane.getVerticalScrollBar().setValue(textScrollPane.getVerticalScrollBar().getMaximum());
		
	}
	public void sendChatMessage() {
		
		if(Player.getInstance().getUsername()!=null)
		{
			Integer userHashColorNumber=userColor.hashCode();
			String userColorHash=Integer.toHexString(userHashColorNumber);
			Integer textColorHashNumber=textColor.hashCode();
			String textColorHash=Integer.toHexString(textColorHashNumber);
			//ChatSender.sendMessage("<b style=\"color:#"+d.substring(2)+"\">"+Player.getInstance().getUsername()+"</b>: <a style=\"color:#"+d1.substring(2)+"\">"+textSend.getText()+"</a>");
			ChatSender.sendMessage("<img src=\""+playerAvatarUrl+"\" width=\"25\" height=\"25\"><b style=\"color:#"+userColorHash.substring(2)+"\">"+Player.getInstance().getUsername()+"</b>: <a style=\"color:#"+textColorHash.substring(2)+"\">"+textSend.getText()+"</a>");
			textSend.setText("");	
			send.setEnabled(false);
		}
		
		else
		{
			JFrame frame=new JFrame();
			if(Player.getInstance().getUsername()==null)
				JOptionPane.showMessageDialog(frame,"Please login");
			if(!checkText(textSend.getText()))
				JOptionPane.showMessageDialog(frame,"Please enter valid text.");
		}
		
	}
	
	public boolean checkText(String text){
		
		String[] temp= text.split(" ");
		boolean flag=false;
		
		for(int i=0;i < temp.length;i++){
		
			if(temp[i].matches("^[a-zA-Z0-9_]*$"))
				flag=true;
			else
				flag=false;
		}
		
		return flag;
	}
	
	public PlayerButtonPanel() {
		
		userColor=Color.BLUE;
		textColor=Color.BLACK;
		playerAvatarUrl="http://www.mayurmasrani.com/default_user.jpg"; //to be changed later according to users avatar
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameMakerView.getInstance().getFacade().startGame();
				GameMakerView.getInstance().getGamePanel().requestFocusInWindow();
			}
		});
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameMakerView.getInstance().getFacade().stopGame();
				// ScoreDialog.showScoreDialog();
				JOptionPane.showMessageDialog(GameMakerView.getInstance().getBaseFrame(), "Your Score is " + Score.getInstance().getScore());
			}
		});

		newButton = new JButton("New");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameMakerView.getInstance().reset();
				GameBaseLoadPanel p = new GameBaseLoadPanel(GameMakerView.getInstance().getGamePanel());

				String gameData = p.readGameDataFromRemoteList();
				if (gameData == null) {
					return;
				}

				GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

				LOG.debug("load done");

				List<SpriteModel> allSpriteModels = game.getSpriteList();
				List<String> layers = game.getLayers();
				ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
				// SpriteList.getInstance().setSpriteList(allSpriteModels);
				SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));

				GameMakerView.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
				GameMakerView.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				GameMakerView.getInstance().getFacade().createViewsForModels(game.getSpriteList());

				for (SpriteModel model : allSpriteModels) {
					SpriteList.getInstance().addSprite(model);
					SpriteList.getInstance().setSelectedSpriteModel(model);
				}
			}
		});

		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GameProgressLoadPanel p = new GameProgressLoadPanel(GameMakerView.getInstance().getGamePanel());

				String gameData = p.readGameDataFromRemoteList();

				if (gameData == null) {
					return;
				}

				GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

				LOG.debug("load done");

				List<SpriteModel> allSpriteModels = game.getSpriteList();
				List<String> layers = game.getLayers();
				ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
				// SpriteList.getInstance().setSpriteList(allSpriteModels);
				SpriteList.getInstance().setSelectedSpriteModel(allSpriteModels.get(0));

				GameMakerView.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
				GameMakerView.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				GameMakerView.getInstance().getFacade().createViewsForModels(game.getSpriteList());

				for (SpriteModel model : allSpriteModels) {
					SpriteList.getInstance().addSprite(model);
					SpriteList.getInstance().setSelectedSpriteModel(model);
				}
			}
		});
		
		
		share=new JButton("Share");
		share.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				
				BrowserFrame browserFrame=new BrowserFrame();
				browserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
			
			
		});
		
		
		
		
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), GameMakerView.getInstance().getFacade().getGameController().getEvents(), GameMakerView.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
				String gameData = GameDataPackageIO.convertGamePackageToString(game);
				GameProgressSavePanel p = new GameProgressSavePanel(GameMakerView.getInstance().getGamePanel());

				p.saveGameToRemoteServer(gameData);

			}
		});

		topscoreButton = new JButton("Top Scores");
		topscoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TopScoresPanel p = new TopScoresPanel(GameMakerView.getInstance().getGamePanel());
				p.readGameScoresFromRemoteList();
			}
		});


		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		kit = new HTMLEditorKit();
	    doc = new HTMLDocument();
	    textPane.setEditorKit(kit);
	    textPane.setDocument(doc);
	    
		JScrollPane textScrollPane = new JScrollPane(textPane);

		final JLabel textLabel = new JLabel("Enter Your Text Here:");

		textSend = new JTextField();
		textSend.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				if(textSend.getText().length()==0)
					send.setEnabled(false);
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if(textSend.getText().length()>0)
					send.setEnabled(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {				
			}
		});
		textSend.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
			     if (key == KeyEvent.VK_ENTER) {
			    	 if(textSend.getText().length()>0)
							sendChatMessage();
					}
			}
		});
		
		send = new JButton("Send");
		send.setEnabled(false);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(textSend.getText().length()>0)
					sendChatMessage();
			}
		});
		send.addActionListener(this);
		chooseUsername = new JButton("Username Color");
		chooseUsername.setForeground(userColor);
		chooseUsername.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color=JColorChooser.showDialog(textLabel, "Choose Username Color", userColor);
				if(color!=null) {
					userColor=color;
					chooseUsername.setForeground(userColor);
				}
			      //= JColorChooser.showDialog(this,"Choose Background Color",userColor);
			}
		});
		chooseText = new JButton("Text Color");
		chooseText.setForeground(textColor);
		chooseText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color=JColorChooser.showDialog(textLabel, "Choose text Color", textColor);
				if(color!=null) {
					textColor=color;
					chooseText.setForeground(textColor);
				}
			      //= JColorChooser.showDialog(this,"Choose Background Color",userColor);
			}
		});
		playerButtonPanel = new JPanel(new MigLayout("center,center"));
//		playerButtonPanel.add(loginButton, "wrap, wmin 200, hmin 30");
//		playerButtonPanel.add(registerButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(newButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(loadButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(saveButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(startButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(share, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(pauseButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(textScrollPane, "wrap,wmin 500, hmin 150");
		playerButtonPanel.add(textLabel,"wrap,wmin 100, hmin 10");
		playerButtonPanel.add(textSend,"wrap,wmin 500, hmin 50");
		playerButtonPanel.add(send,"wrap,wmin 200, hmin 30");
		playerButtonPanel.add(chooseUsername);
		playerButtonPanel.add(chooseText);
		
		
    }
	
	

	public JPanel getPlayerButtonPanel() {
		return playerButtonPanel;
	}



	public void setPlayerButtonPanel(JPanel playerButtonPanel) {
		this.playerButtonPanel = playerButtonPanel;
	}



	public JLabel getUserName() {
		return userName;
	}

	public void setUserName(JLabel userName) {
		this.userName = userName;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
	}
}
