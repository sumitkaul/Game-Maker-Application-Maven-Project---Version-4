package view;

import game.engine.slick2d.player.GameEngineController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

import loader.GameDataPackageIO;
import loader.GamePackage;
import model.SpriteModel;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import utility.ClockDisplay;
import utility.Helper;
import utility.Layers;
import utility.Score;
import utility.SpriteList;
import view.companels.GameBaseLoadPanel;
import view.companels.GameProgressLoadPanel;
import view.companels.GameProgressSavePanel;
import view.companels.TopScoresPanel;

public class PlayerButtonPanel implements ActionListener {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PlayerButtonPanel.class);
    private JPanel playerButtonPanel, buttonPanel;
    private JLabel userName;
    private JButton startButton;
    private JButton newButton;
    private JButton loadButton;
    private JButton saveButton;
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
//				gamePlayerView.getFacade().startGame();
//				gamePlayerView.getGamePanel().requestFocusInWindow();
            }
        });

        newButton = new JButton("New");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
                gamePlayerView.getGameEnginePanel().newGame();
            }
        });

        loadButton = new JButton("Load");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameProgressLoadPanel p = new GameProgressLoadPanel(GameMakerView.getInstance().getGamePanel());
                GamePlayerView gamePlayerView = (GamePlayerView) Helper.getsharedHelper().getGamePlayerView();
                String gamename[] = new String[1];
                String gameData = p.readGameDataFromRemoteList(gamename);

                if (gameData == null) {
                    return;
                }

                GamePackage game = GameDataPackageIO.loadGamePackageFromFile(gameData);

                LOG.debug("load done");

                Collection<SpriteModel> allSpriteModels = game.getSpriteList();
                List<String> layers = game.getLayers();
                ClockDisplay.getInstance().setVisible(game.isClockDisplayable());
                // SpriteList.getInstance().setSpriteList(allSpriteModels);
                SpriteModel m = (SpriteModel) ((Queue) allSpriteModels).peek();
                SpriteList.getInstance().setSelectedSpriteModel(m);
                gamePlayerView.getGameEnginePanel().removeGame();
                
                GameEngineController gameEngine = new GameEngineController(gamename[0], game);
                gameEngine.setEventsForGameController(game.getEventsForGameController());
                gameEngine.setKeyEvents(game.getEventsForKeyController());
                try {
                    CanvasGameContainer app = new CanvasGameContainer(gameEngine);
                    gamePlayerView.getGameEnginePanel().addGame(app);
                    gamePlayerView.getGameEnginePanel().startGame();
                } catch (SlickException ex) {
                    Logger.getLogger(PlayerButtonPanel.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (SpriteModel model : allSpriteModels) {
                    SpriteList.getInstance().addSprite(model);
                    SpriteList.getInstance().setSelectedSpriteModel(model);
                }
            }
        });


        share = new JButton("Share");
        share.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                BrowserFrame browserFrame = new BrowserFrame();
                browserFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });



        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 GamePlayerView gamePlayerView = (GamePlayerView) Helper.getsharedHelper().getGamePlayerView();
                GamePackage game = new GamePackage(SpriteList.getInstance().getSpriteList(), gamePlayerView.getGameEnginePanel().getGame().getEventsForGameController(), gamePlayerView.getGameEnginePanel().getGame().getKeyEvents(), null, false);
                String gameData = GameDataPackageIO.convertGamePackageToString(game);
                GameProgressSavePanel p = new GameProgressSavePanel(GameMakerView.getInstance().getGamePanel());

                p.saveGameToRemoteServer(gameData);

            }
        });

        chatViewPanel = new ChatViewPanel(this).getChatViewPanel();

        playerButtonPanel = new JPanel(new MigLayout("center,center"));
        buttonPanel = new JPanel();
        
        buttonPanel.add(newButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(share);

        playerButtonPanel.add(buttonPanel, "wrap,wmin 400, gapy 10px 80px");
        playerButtonPanel.add(chatViewPanel, "wrap,wmin 400, hmin 300");



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
