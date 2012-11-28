package JBox2d.main;;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class TestEngine {
    
    private static final org.apache.log4j.Logger LOG = 
			org.apache.log4j.Logger.getLogger(TestEngine.class);
    private AppGameContainer app;
    

    
    public TestEngine(AppGameContainer app){
        
        LOG.debug("This is Game Engine Test");
        //System.setProperty("org.lwjgl.librarypath", System.getProperty("user.dir") + "/target/natives/");
        //System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));

        //GameEngineController game = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE, null);
        this.app = app;

        try {
            //app = new AppGameContainer(game);
            app.setTargetFrameRate(100);
            app.setDisplayMode(800, 600, false);
            app.setAlwaysRender(true);
            app.setForceExit(false);
            //app.setClearEachFrame(true);
        

            //app.start();
        } catch (SlickException ex) {
            Logger.getLogger(TestEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    public AppGameContainer getApp() {
        return app;
    }

    public void setApp(AppGameContainer app) {
        this.app = app;
    }
    
}
