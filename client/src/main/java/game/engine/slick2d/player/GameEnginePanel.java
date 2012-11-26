package game.engine.slick2d.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import utility.Constants;

public class GameEnginePanel implements ActionListener{
    
    private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(GameEnginePanel.class);
    private CanvasGameContainer app;
    private JFrame jframe;
    private JButton gamestopButton, gamepauseButton, gameresumeButton,gamerestartButton;
    private JPanel buttonPanel;
    
    
    
    public GameEnginePanel(CanvasGameContainer app){
        gamestopButton = new JButton("Stop");
        gamestopButton.addActionListener(this);
        gamepauseButton = new JButton("Pause");
        gamepauseButton.addActionListener(this);
        gameresumeButton = new JButton("resume");
        gameresumeButton.addActionListener(this);
        gamerestartButton = new JButton("restart");
        gamerestartButton.addActionListener(this);
        
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(gamestopButton);
        buttonPanel.add(gamepauseButton);
        buttonPanel.add(gameresumeButton);
        buttonPanel.add(gamerestartButton);
        
        jframe = new JFrame("Game");
        jframe.setLayout(new BorderLayout());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        LOG.debug("This is Game Engine Test");
        //System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/target/natives/");
        //System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));

        //GameEngineController game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, null);
        this.app = app;
        app.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        jframe.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        jframe.getContentPane().add(app, BorderLayout.CENTER);
        jframe.pack();
        jframe.setVisible(true);
        app.requestFocusInWindow();

//        try {
//            //app = new AppGameContainer(game);
////            app.setTargetFrameRate(100);
////            app.setDisplayMode(800, 600, false);
////            app.setAlwaysRender(true);
////            app.setForceExit(false);
//            //app.setClearEachFrame(true);
//        
//
//            app.start();
//        } catch (SlickException ex) {
//            Logger.getLogger(GameEnginePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
 
    }
    
    public void startGame(){
        try {
            app.start();
        } catch (SlickException ex) {
            LOG.error(ex);
        }
    }
    
    public void exitGame(){
        app.getContainer().exit();
        app.requestFocusInWindow();
    }

    public void pauseGame(){
        app.getContainer().setPaused(!app.getContainer().isPaused());
        //app.getContainer().pause();
        app.requestFocusInWindow();
    }
    
    public void resumeGame(){
        app.getContainer().resume();
        app.requestFocusInWindow();
    }
    
    public void restartGame(){
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

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == gamestopButton) {
           exitGame();
       }
       
       if (e.getSource() == gamepauseButton) {
           pauseGame();
       }
       if (e.getSource() == gameresumeButton) {
           resumeGame();
       }
       if (e.getSource() == gamerestartButton) {
           restartGame();
       }
    }
    
}
