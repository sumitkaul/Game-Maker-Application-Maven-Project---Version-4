package newui;

import action.ActionStartOver;
import action.GameAction;
import controller.GameController;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import eventlistener.OutOfBoundaryEventListener;
import facade.Facade;
import gameMaker.gameMaker;
import interfaces.Resizable;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import net.miginfocom.swing.MigLayout;
import newui.OptionsFrame;
import team3.a9.lookandfeel.AnimationHandler;
import utility.*;
import view.ActionEventPanel;
import view.ButtonPanel;
import view.Design;
import view.GamePanel;
import view.ThumbView;
import view.companels.GameBaseLoadPanel;
import view.companels.GameBaseSavePanel;
import view.companels.GameProgressLoadPanel;
import view.companels.GameProgressSavePanel;
import view.companels.ScoreDialog;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import view.companels.*;

public class GamePlayerWindow implements Resizable, ActionListener {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(GamePlayerWindow.class);
    private static GamePlayerWindow sharedGamePlayerWindow = null;

    private GamePanel gamePanel; 
    private final JFrame baseFrame;
    private String userName ="";
    private OptionsFrame optionFrame;
	private Facade facade;
	private JPanel buttonPanel;
	private JButton startButton, pauseButton, saveButton, loadButton, newButton, topscoreButton;

 
    public static GamePlayerWindow getInstance() {
        if (sharedGamePlayerWindow == null) {
            sharedGamePlayerWindow = new GamePlayerWindow(600, 800);
        }
        return sharedGamePlayerWindow;
    }

    protected GamePlayerWindow(int frameWidth, int frameHeight) {

         
        baseFrame = new JFrame();
        baseFrame.addWindowListener(new WindowAdapter() {
    		@Override
    		public void windowClosing(WindowEvent arg0) {
        			GamePlayerWindow.getInstance().getOptionFrame().getOptionFrame().setVisible(true);
        			baseFrame.setVisible(false);  			
    		}
    	});
        baseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        baseFrame.setTitle("Game Maker");
        baseFrame.setSize(frameWidth, frameHeight);
        baseFrame.setLayout(new BorderLayout());
        
        gamePanel = new GamePanel(600, 700);
        gamePanel.setCurrentLayer(Constants.ALL_LAYERS);
        facade = new Facade(gamePanel);

        baseFrame.getRootPane().addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                int originalFrameWidth = Constants.FRAME_WIDTH;
                int originalFrameHeight = Constants.FRAME_HEIGHT;

                Component rootPane = e.getComponent();
                Rectangle r = rootPane.getBounds();

                double xScale = (double) r.width / originalFrameWidth;
                double yScale = (double) r.height / originalFrameHeight;

                ResizeHelper.getInstance().setxFactor(xScale);
                ResizeHelper.getInstance().setyFactor(yScale);

                gamePanel.repaint();

            }
        });

        

        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	facade.startGame();
            	gamePanel.requestFocusInWindow();
            }
        });
        pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                facade.stopGame();
                // ScoreDialog.showScoreDialog();
                JOptionPane.showMessageDialog(baseFrame, "Your Score is " + Score.getInstance().getScore());
            }
        });
        
        newButton = new JButton("New");      
        newButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	GamePlayerWindow.getInstance().reset();
                GameBaseLoadPanel p = new GameBaseLoadPanel(gamePanel);

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

                facade.getGameController().setEvents(game.getEventsForGameController());
                facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

                facade.createViewsForModels(game.getSpriteList());

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
                GameProgressLoadPanel p = new GameProgressLoadPanel(gamePanel);

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

                facade.getGameController().setEvents(game.getEventsForGameController());
                facade.getKeyListenerController().setKeyEvents(game.getEventsForKeyController());

                facade.createViewsForModels(game.getSpriteList());

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
                GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), facade.getGameController().getEvents(), facade.getKeyListenerController().getKeyEvents(), Layers.getInstance().getLayers(), ClockDisplay.getInstance().isVisible());
                String gameData = GameDataPackageIO.convertGamePackageToString(game);
                GameProgressSavePanel p = new GameProgressSavePanel(gamePanel);

                p.saveGameToRemoteServer(gameData);

            }
        });

        topscoreButton = new JButton("Top Scores");
        topscoreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                TopScoresPanel p = new TopScoresPanel(gamePanel);
                p.readGameScoresFromRemoteList();
            }
        });

        
        
        buttonPanel = new JPanel();
        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(startButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(topscoreButton);
        baseFrame.getContentPane().add(buttonPanel,BorderLayout.NORTH);
        baseFrame.getContentPane().add(gamePanel,BorderLayout.CENTER);
        baseFrame.setVisible(true);
        baseFrame.setResizable(true);
    }

       @Override
    public void Resize(int framewidth, int frameheight) {
        int widthdiff = framewidth - Constants.FRAME_WIDTH;
        int heightdiff = frameheight - Constants.FRAME_HEIGHT;

        gamePanel.setSize(Constants.CONTROL_PANEL_WIDTH + (int) (widthdiff * 0.6), Constants.CONTROL_PANEL_LENGTH + (int) ((heightdiff * 2) / 7));

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }


    public void reset() {
        List<SpriteModel> allSpriteModels = SpriteList.getInstance().getSpriteList();
        for (SpriteModel model : allSpriteModels) {
            gamePanel.unregisterModel(model);
        }
        
        SpriteList.getInstance().getSpriteList().clear();

        facade.getGameController().getEvents().clear();
        facade.getKeyListenerController().getKeyEvents().clear();
    }

    public void createDuplicateSpriteModel(SpriteModel model) {
        SpriteModel spriteModel = new SpriteModel(model.getPosX() + model.getWidth() / 2, model.getPosY() + model.getHeight() / 2, model.getSpeedX(), model.getSpeedY(), model.getWidth(), model.getHeight(), model.getImageUrlString(), model.getLayer());

        facade.addSpriteModelToView(spriteModel);
        gamePanel.repaint();
    }
    

    public Facade getFacade() {
        return facade;
    }



    public JFrame getBaseFrame() {
		return baseFrame;
	}

	
	 public GamePanel getGamePanel() {
	        return gamePanel;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public OptionsFrame getOptionFrame() {
		return optionFrame;
	}

	public void setOptionFrame(OptionsFrame optionFrame) {
		this.optionFrame = optionFrame;
	}

}
