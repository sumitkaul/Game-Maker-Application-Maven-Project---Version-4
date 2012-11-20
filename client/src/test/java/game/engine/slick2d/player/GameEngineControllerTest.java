package game.engine.slick2d.player;

import org.apache.log4j.Logger;
import org.junit.Test;

public class GameEngineControllerTest {

    private static final Logger LOG = Logger.getLogger(GameEngineControllerTest.class.getName());

    /**
     * Test of constructor, of class GameEngineController.
     */
    @Test
    public void testGameEngineController() throws Exception {
        LOG.info("GameEngineController");

        GameEngineController instance = new GameEngineController("test", GameEngineController.LOAD_MODE_REMOTE_NO_UI,
                new String[]{"test_game_engine_delta",
                    "localhost:8097",
                    "/finalproject/loadGameBase"});
    }
}
