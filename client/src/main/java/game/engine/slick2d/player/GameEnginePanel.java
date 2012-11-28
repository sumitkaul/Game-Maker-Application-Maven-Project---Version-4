package game.engine.slick2d.player;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import utility.Constants;
import utility.Helper;
import view.GamePlayerView;

public class GameEnginePanel extends JPanel {

    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(GameEnginePanel.class);
    private CanvasGameContainer app;
    private GameEngineController game;

    public GameEnginePanel() {
        setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        setVisible(true);
    }

    public void addGame(CanvasGameContainer app) {
        this.app = app;
        this.app.getContainer().setTargetFrameRate(60);
        this.app.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        add(this.app);
        this.app.requestFocusInWindow();
    }

    public void removeGame() {
        if (app != null) {
            app.getContainer().exit();
            app.dispose();
            remove(app);
            removeAll();
            game = null;
        }
    }

    public void newGame() {
        try {
            removeGame();
            game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, new String[]{"/game/engine/slick2d/player/testing_game.xml"});
            CanvasGameContainer app = new CanvasGameContainer(game);
            app.getContainer().setTargetFrameRate(60);
            app.getContainer().setForceExit(true);
            GamePlayerView gamePlayerView = Helper.getsharedHelper().getGamePlayerView();
            gamePlayerView.getGameEnginePanel().addGame(app);
            gamePlayerView.getBaseFrame().pack();
            gamePlayerView.getGameEnginePanel().startGame();
        } catch (Exception ex) {
            LOG.error(ex);
        }
    }

    public void startGame() {
        try {
            app.start();
        } catch (SlickException ex) {
            LOG.error(ex);
        }
    }

    public void exitGame() {
        app.getContainer().exit();
        app.requestFocusInWindow();
    }

    public void restartGame() {
        try {
            app.getContainer().reinit();
            app.requestFocusInWindow();
        } catch (SlickException ex) {
            LOG.error(ex);
        }
    }

    public CanvasGameContainer getApp() {
        return app;
    }

    public void setApp(CanvasGameContainer app) {
        this.app = app;
    }

    public GameEngineController getGame() {
        return game;
    }

    public void setGame(GameEngineController game) {
        this.game = game;
    }

}
