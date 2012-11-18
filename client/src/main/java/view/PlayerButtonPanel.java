package view;


import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;

import chat.ChatSender;

import net.miginfocom.swing.MigLayout;

import loader.GameDataPackageIO;
import loader.GamePackage;
import lookandfeel.AnimationHandler;
import model.GameBase;
import model.Player;
import model.Resource;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Constants;
import utility.Layers;
import utility.Score;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;
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
	private JButton choose;
	private JButton chooseText;
	private JTextField textSend;
	private static JTextPane textPane;
	private static JScrollPane textScrollPane;
	private Color userColor;
	private Color textColor;
	
	public static void updateChatWindow(String msg) {
		textPane.setContentType("text/html");
		int colonIndex=msg.indexOf(":");
		int chatStart=textPane.getText().indexOf("<body>");
		int chatEnd=textPane.getText().indexOf("</body>");
    	textPane.setText("<html>"+textPane.getText().substring(chatStart+"<body>".length(), chatEnd)+"<br/>"+msg+"</html>");
    	textPane.setCaretPosition(textPane.getDocument().getLength());
    	textScrollPane.getVerticalScrollBar().setValue(textScrollPane.getVerticalScrollBar().getMaximum());
    }
	public void sendChatMessage() {
		
		if(Player.getInstance().getUsername()!=null)
		{
			Integer hash=userColor.hashCode();
			String d=Integer.toHexString(hash);
			Integer hash2=textColor.hashCode();
			String d1=Integer.toHexString(hash2);
			ChatSender.sendMessage("<b style=\"color:#"+d.substring(2)+"\">"+Player.getInstance().getUsername()+"</b>: <a style=\"color:#"+d1.substring(2)+"\">"+textSend.getText()+"</a>");
		}
		
		else
		{
			JFrame frame=new JFrame();
			if(Player.getInstance().getUsername()==null)
				JOptionPane.showMessageDialog(frame,"Please login");
			if(!checkText(textSend.getText()))
				JOptionPane.showMessageDialog(frame,"Please enter valid text.");
		}
		textSend.setText("");	
		send.setEnabled(false);
		
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
		textColor=Color.RED;
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
				// TODO Auto-generated method stub
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
//		JButton loginButton = new JButton("Login");
//		loginButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				new LoginFrame();
//
//			}
//		});
//
//		JButton registerButton = new JButton("Register");
//		registerButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				new RegisterFrame();
//			}
//		});

		textPane = new JTextPane();
		textPane.setEditable(false);
		
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
		choose = new JButton("Username Color");
		choose.setForeground(userColor);
		choose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color bgColor=JColorChooser.showDialog(textLabel, "Choose Username Color", userColor);
				userColor=bgColor;
				choose.setForeground(userColor);
			      //= JColorChooser.showDialog(this,"Choose Background Color",userColor);
			}
		});
		chooseText = new JButton("Text Color");
		chooseText.setForeground(textColor);
		chooseText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Color bgColor=JColorChooser.showDialog(textLabel, "Choose text Color", textColor);
				textColor=bgColor;
				chooseText.setForeground(textColor);
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
		playerButtonPanel.add(pauseButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(textScrollPane, "wrap,wmin 500, hmin 150");
		playerButtonPanel.add(textLabel,"wrap,wmin 100, hmin 10");
		playerButtonPanel.add(textSend,"wrap,wmin 500, hmin 50");
		playerButtonPanel.add(send,"wrap,wmin 200, hmin 30");
		playerButtonPanel.add(choose);
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
