package view;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import utility.ClockDisplay;
import utility.Helper;
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
	private JPanel chatViewPanel;
	private JButton share;
	

	
	public PlayerButtonPanel() {
		
		
//		userColor=Color.BLUE;
//		textColor=Color.BLACK;
//		playerAvatarUrl="http://www.mayurmasrani.com/default_user.jpg"; //to be changed later according to users avatar
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
				gamePlayerView.getFacade().startGame();
				gamePlayerView.getGamePanel().requestFocusInWindow();
			}
		});
		pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
				gamePlayerView.getFacade().stopGame();
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

				Helper.getsharedHelper().getGamePlayerView().getFacade().getGameController().setEvents(game.getEventsForGameController());
				Helper.getsharedHelper().getGamePlayerView().getFacade().getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

				Helper.getsharedHelper().getGamePlayerView().getFacade().createViewsForModels(game.getSpriteList());

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


		chatViewPanel = new ChatViewPanel(this).getChatViewPanel();
		
		playerButtonPanel = new JPanel(new MigLayout("center,center"));
//		playerButtonPanel.add(loginButton, "wrap, wmin 200, hmin 30");
//		playerButtonPanel.add(registerButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(newButton, "wrap, wmin 200, hmin 30");
		playerButtonPanel.add(loadButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(saveButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(startButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(share, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(pauseButton, "wrap,wmin 200, hmin 30");
		playerButtonPanel.add(chatViewPanel,"wrap,wmin 500, hmin 250");

		
		
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
