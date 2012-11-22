package view;

import game.engine.slick2d.player.GameEngineController;
import game.engine.slick2d.player.TestEngine;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.newdawn.slick.SlickException;
import utility.Constants;
import utility.Helper;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import org.newdawn.slick.AppGameContainer;


public class OptionsFrame implements ActionListener {

    private static final org.apache.log4j.Logger LOG =
            org.apache.log4j.Logger.getLogger(OptionsFrame.class);
    private JFrame optionFrame;
    private JButton makerButton, playerButton, gamestartButton, gamestopButton;
    private TestEngine te;
 

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


        optionFrame.setLayout(new MigLayout("center,center"));
        optionFrame.add(makerButton, "wmin 100, hmin 150");
        optionFrame.add(playerButton, "wmin 100, hmin 150");
        optionFrame.add(gamestartButton, "wmin 100, hmin 150");
        optionFrame.add(gamestopButton, "wmin 100, hmin 150");

        optionFrame.setSize(500, 500);
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
                AppGameContainer app = new AppGameContainer(game);
                te = new TestEngine(app); 
                te.getApp().start();
            } catch (SlickException ex) {
                LOG.error(ex);
            }
           }}.start();
                         
        }
        
        if (e.getSource() == gamestopButton){
            te.getApp().exit();
        }

    }

    public JFrame getOptionFrame() {
        return optionFrame;
    }

    public void setOptionFrame(JFrame optionFrame) {
        this.optionFrame = optionFrame;
    }
}
