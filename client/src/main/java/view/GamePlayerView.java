package view;

import javax.swing.JFrame;
import chat.StatusReceiver;
import chat.StatusSender;
import facade.Facade;
import game.engine.slick2d.player.GameEnginePanel;
import java.awt.Dimension;
import javax.swing.JPanel;

import utility.Constants;
import utility.Helper;

public class GamePlayerView {

    private JFrame baseFrame;
    private GameEnginePanel gameenginePanel;
    //private Facade facade;

    public GamePlayerView(int frameWidth, int frameHeight) {
        //Create  a base frame to hold all the other panels
        baseFrame = Helper.getsharedHelper().createBaseFrame(frameWidth, frameHeight);
        baseFrame.setJMenuBar(new MenuBarPanel().getMenuBar());

        //Create a game engine panel. This is where all the game objects are rendered and the game is played.
        gameenginePanel = new GameEnginePanel();

        //Create a button panel where the controls to play/pause etc the game and chat window is present.
        PlayerButtonPanel playerButtonPanel = new PlayerButtonPanel();
        baseFrame.add(playerButtonPanel.getPlayerButtonPanel());
        baseFrame.add(gameenginePanel);
        baseFrame.setVisible(true);

        new StatusSender();
        new StatusReceiver();
    }

    /*
     * Getter and Setter methods
     */
    public GameEnginePanel getGameEnginePanel() {
        return gameenginePanel;
    }

    public void setGameEnginePanel(GameEnginePanel gamePanel) {
        this.gameenginePanel = gamePanel;
    }
}
