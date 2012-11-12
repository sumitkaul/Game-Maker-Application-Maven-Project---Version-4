package view;


import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat.ChatSender;

import net.miginfocom.swing.MigLayout;

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.GameBase;
import model.Player;
import model.Resource;
import model.SpriteModel;
import team3.a9.lookandfeel.AnimationHandler;
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
	private Design design;
	private JPanel playerButtonPanel;
	private JLabel userName;
	private JButton startButton;
	private JButton pauseButton;
	private JButton newButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton topscoreButton;
	private JButton send;
	private JTextField textSend;
	private static JTextArea textArea;
	private static JScrollPane textScrollPane;
	
	public static void updateChatWindow(String msg) {
    	textArea.setText(textArea.getText()+"\n"+msg);
    	textScrollPane.getVerticalScrollBar().setValue(200);
    }
	
	public PlayerButtonPanel(Design designArg) {
		this.design = designArg;
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Design.getInstance().getFacade().startGame();
				Design.getInstance().getGamePanel().requestFocusInWindow();
			}
		});
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Design.getInstance().getFacade().stopGame();
				// ScoreDialog.showScoreDialog();
				JOptionPane.showMessageDialog(Design.getInstance().getBaseFrame(), "Your Score is " + Score.getInstance().getScore());
			}
		});

		newButton = new JButton("New");
		newButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Design.getInstance().reset();
				GameBaseLoadPanel p = new GameBaseLoadPanel(Design.getInstance().getGamePanel());

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

				Design.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
				Design.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				Design.getInstance().getFacade().createViewsForModels(game.getSpriteList());

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
				GameProgressLoadPanel p = new GameProgressLoadPanel(Design.getInstance().getGamePanel());

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

				Design.getInstance().getFacade().getGameController().setEvents(game.getEventsForGameController());
				Design.getInstance().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				Design.getInstance().getFacade().createViewsForModels(game.getSpriteList());

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
				GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), Design.getInstance().getFacade().getGameController().getEvents(), Design.getInstance().getFacade().getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
				String gameData = GameDataPackageIO.convertGamePackageToString(game);
				GameProgressSavePanel p = new GameProgressSavePanel(Design.getInstance().getGamePanel());

				p.saveGameToRemoteServer(gameData);

			}
		});

		topscoreButton = new JButton("Top Scores");
		topscoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				TopScoresPanel p = new TopScoresPanel(Design.getInstance().getGamePanel());
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

		textArea = new JTextArea();
		textArea.setEditable(false);
		JScrollPane textScrollPane = new JScrollPane(textArea);

		JLabel textLabel = new JLabel("Enter Your Text Here:");

		textSend = new JTextField();
		send = new JButton("Send");
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(Player.getInstance().getUsername()!=null)
				{
					ChatSender.sendMessage(Player.getInstance().getUsername()+": "+textSend.getText());
				}
				else
				{
					JFrame frame=new JFrame();
					JOptionPane.showMessageDialog(frame,"Please login");
				}
				textSend.setText("");			
			}
		});
		send.addActionListener(this);

		
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
