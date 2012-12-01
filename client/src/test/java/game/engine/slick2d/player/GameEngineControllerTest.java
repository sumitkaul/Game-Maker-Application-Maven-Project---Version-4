package game.engine.slick2d.player;

import eventlistener.KeyPressedEventListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.doAnswer;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;

import static org.mockito.Mockito.mock;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert.*;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

public class GameEngineControllerTest {

    private static final Logger LOG = Logger.getLogger(GameEngineControllerTest.class.getName());
      private HashMap<Integer, KeyPressedEventListener> keyReg;
    private HashMap<Integer, Integer> key2key;
    
    //this is not a proper unit test... unit tests should not require network access
    /**
     * Test of constructor, of class GameEngineController.
     */
    
     GameEngineController instance = mock(GameEngineController.class);
     List result;
    @Test
    public void testGameEngineController() throws Exception {
//        LOG.info("GameEngineController");
            Mockito.when(instance.getKeyEvents()).thenReturn(result);
           assertEquals(null, instance.getKeyEvents());
      
    }
    
   @Test
   public void testgetImageFromBytes() throws Exception{
       byte [] imageData=null;
       Image image=null;
       Mockito.when(instance.getImageFromBytes(imageData, "test")).thenReturn(image);
       assertEquals(image, instance.getImageFromBytes(imageData, "test"));
   }
   @Test
   public void testGetEventsForGameController() throws Exception{
      List test=null;
        Mockito.when(instance.getEventsForGameController()).thenReturn(test);
           assertEquals(null, instance.getEventsForGameController());
   }
   @Test
   public void testCheckEvents() throws Exception
   {
       GameEngineController first= mock(GameEngineController.class);
       GameEngineController second= mock(GameEngineController.class);
       GameContainer firstGC=mock(GameContainer.class);
       GameContainer secondGC=mock(GameContainer.class);
       first.checkEvents(firstGC);
       second.checkEvents(secondGC);
       InOrder inOrder= inOrder(first,second);
        inOrder.verify(first).checkEvents(firstGC);
        inOrder.verify(second).checkEvents(secondGC);
   }
   
   @Test
   public void testrenderSpriteImageDraw() throws Exception
   {
                 GameEngineController mock= Mockito.mock(GameEngineController.class);
       doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          return null;
      }})
  .when(mock).renderSpriteImageDraw();
   }
}

