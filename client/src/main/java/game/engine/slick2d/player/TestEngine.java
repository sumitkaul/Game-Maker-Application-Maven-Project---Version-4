package game.engine.slick2d.player;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class TestEngine {

    public static void main(String[] args) throws SlickException, IOException {
        System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/target/natives/");
        System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
        
        GameEngineController game = new GameEngineController("test");


        AppGameContainer app = new AppGameContainer(game);
        app.setTargetFrameRate(100);
        app.setDisplayMode(800, 600, false);
        app.setAlwaysRender(true);
        //app.setClearEachFrame(true);


        app.start();
    }
}
