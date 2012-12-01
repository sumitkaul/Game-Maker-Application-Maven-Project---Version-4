package game.engine.slick2d.player;

import java.util.EventListener;
import java.util.List;
import static org.mockito.Mockito.doAnswer;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert.*;

public class GameEngineControllerTest {

    private static final Logger LOG = Logger.getLogger(GameEngineControllerTest.class.getName());

    
    //this is not a proper unit test... unit tests should not require network access
    /**
     * Test of constructor, of class GameEngineController.
     */
    
     GameEngineController instance = mock(GameEngineController.class);
     List<EventListener> result;
    @Test
    public void testGameEngineController() throws Exception {
//        LOG.info("GameEngineController");
            Mockito.when(instance.getKeyEvents()).thenReturn(null);
           assertEquals(null, instance.getKeyEvents());
      
    }
    
   
}
