package game.engine.slick2d.player;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class TestEngine {

    public static void main(String[] args) throws SlickException {
        GameEngineController game = new GameEngineController("test");


        AppGameContainer app = new AppGameContainer(game);
        app.setTargetFrameRate(100);
        app.setDisplayMode(800, 600, false);
        app.setAlwaysRender(true);
        //app.setClearEachFrame(true);


        app.start();
    }
}
