package view;

import game.engine.slick2d.player.GameEngineController;
import game.engine.slick2d.player.GameEnginePanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.newdawn.slick.SlickException;
import utility.Constants;
import utility.Helper;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.GameContainer;


public class OptionsFrame implements ActionListener {

    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(OptionsFrame.class);
    private JFrame optionFrame;
    private JButton makerButton, playerButton, gamestartButton, gamestopButton, gamepauseButton, gameresumeButton,gamerestartButton;
    private GameEnginePanel te;
 

    public OptionsFrame() {
        optionFrame = new JFrame();
        makerButton = new JButton("Make Game");
        makerButton.addActionListener(this);
        playerButton = new JButton("Play Game");
        playerButton.addActionListener(this);
        gamestartButton = new JButton("Start GE Testing");
        gamestartButton.addActionListener(this);
        gamestopButton = new JButton("Stop GE Testing");
        gamestopButton.addActionListener(this);
        gamepauseButton = new JButton("Pause GE Testing");
        gamepauseButton.addActionListener(this);
        gameresumeButton = new JButton("resume GE Testing");
        gameresumeButton.addActionListener(this);
        gamerestartButton = new JButton("restart GE Testing");
        gamerestartButton.addActionListener(this);


        optionFrame.setLayout(new MigLayout("center,center"));
        optionFrame.add(makerButton, "wmin 100, hmin 150");
        optionFrame.add(playerButton, "wmin 100, hmin 150");
        optionFrame.add(gamestartButton, "wmin 100, hmin 150");
        optionFrame.add(gamestopButton, "wmin 100, hmin 150");
        optionFrame.add(gamepauseButton, "wmin 100, hmin 150");
        optionFrame.add(gameresumeButton, "wmin 100, hmin 150");
        optionFrame.add(gamerestartButton, "wmin 100, hmin 150");
        

        optionFrame.setSize(800, 500);
        optionFrame.setFocusable(true);
        optionFrame.setLocationRelativeTo(null);
        optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        optionFrame.setTitle("Option Page");
        optionFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == makerButton) {
        	Constants.isGameMaker=true;
            GameMakerView.getInstance();
            GameMakerView.getInstance().getBaseFrame().setVisible(true);

            optionFrame.setVisible(false);
            Helper.getsharedHelper().setPlayerMode(false);
        }

        if (e.getSource() == playerButton) {
        	Constants.isGamePlayer = true;

        	GamePlayerView gamePlayerView = new GamePlayerView(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
        	Helper.getsharedHelper().setGamePlayerView(gamePlayerView);
        	Helper.getsharedHelper().setPlayerMode(true);
            optionFrame.setVisible(false);
        }

        if (e.getSource() == gamestartButton) {
           new Thread(){public void run(){
             try {
                GameEngineController game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, new String[]{"/game/engine/slick2d/player/testing_game.xml"});
                CanvasGameContainer app = new CanvasGameContainer(game);
                te = new GameEnginePanel(app); 
                te.startGame();
            } catch (Exception ex) {
                LOG.error(ex);
            }
           }}.start();
                         
        }
        
        if (e.getSource() == gamestopButton){
            te.exitGame();
        }
        
        if (e.getSource() == gamepauseButton){
            te.pauseGame();
        }
        
        if (e.getSource() == gameresumeButton){
            te.resumeGame();
        }
        if (e.getSource() == gamerestartButton){
            te.restartGame();
        }
        
        

    }

    public JFrame getOptionFrame() {
        return optionFrame;
    }

    public void setOptionFrame(JFrame optionFrame) {
        this.optionFrame = optionFrame;
    }
}
