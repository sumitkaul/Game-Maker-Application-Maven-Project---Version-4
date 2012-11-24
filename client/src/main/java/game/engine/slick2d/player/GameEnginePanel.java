package game.engine.slick2d.player;

import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import utility.Constants;

public class GameEnginePanel {
    
    private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(GameEnginePanel.class);
    private CanvasGameContainer app;
    private JFrame jframe;
    

    
    public GameEnginePanel(CanvasGameContainer app){
        
        jframe = new JFrame("Game");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //jframe.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        LOG.debug("This is Game Engine Test");
        //System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/target/natives/");
        //System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));

        //GameEngineController game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, null);
        this.app = app;
        app.setPreferredSize(new Dimension(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT));
        app.requestFocusInWindow();
        jframe.getContentPane().add(app);
        jframe.pack();
        jframe.setVisible(true);

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
    }

    public void pauseGame(){
        app.getContainer().pause();
    }
    
    public void resumeGame(){
        app.getContainer().resume();
    }
    
    public void restartGame(){
        try {
            app.getContainer().reinit();
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
    
}
