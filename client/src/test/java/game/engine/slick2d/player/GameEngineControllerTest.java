package game.engine.slick2d.player;

import eventlistener.KeyPressedEventListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.doAnswer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;


import static org.mockito.Mockito.inOrder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;
import org.junit.Assert.*;
import org.junit.Before;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import utility.SpriteList;

public class GameEngineControllerTest {

    private static final Logger LOG = Logger.getLogger(GameEngineControllerTest.class.getName());
      private HashMap<Integer, KeyPressedEventListener> keyReg;
    private HashMap<Integer, Integer> key2key;
    
    //this is not a proper unit test... unit tests should not require network access
    /**
     * Test of constructor, of class GameEngineController.
     */
    
     GameEngineController instance ;
     List result;
     
     @Before
     public void setUp(){
         instance=Mockito.mock(GameEngineController.class);
     }
    @After
    public void tearDown(){
        instance=null;
    }
    
    
   @Test
   public void testgetImageFromBytes() throws Exception{
       byte [] imageData=null;
       Image image=Mockito.mock(Image.class);
       Mockito.when(instance.getImageFromBytes(imageData, "test")).thenReturn(image);
       assertEquals(image, instance.getImageFromBytes(imageData, "test"));
   }
   @Test
   public void testGetEventsForGameController() throws Exception{
      List <EventListener>tester= new ArrayList<EventListener>();
       List test=spy(tester);
        Mockito.when(instance.getEventsForGameController()).thenReturn(test);
           assertNotSame(tester, instance.getEventsForGameController());
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
      assertNotNull(SpriteList.getInstance().getSpriteList());
     
              
   }
   
   @Test 
   public void testinitSpriteImageMapping() throws Exception{
        GameEngineController first= mock(GameEngineController.class);
       GameEngineController second= mock(GameEngineController.class);
      
       first.initSpriteImageMapping();
       second.initSpriteImageMapping();
       InOrder inOrder= inOrder(first,second);
        inOrder.verify(first).initSpriteImageMapping();
        inOrder.verify(second).initSpriteImageMapping();
        assertNotSame(first,second );
   }
   @Test 
   public void testInit()throws Exception{
        GameContainer result=mock(GameContainer.class);
        GameEngineController mock= Mockito.mock(GameEngineController.class);
       doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          return null;
      }})
  .when(mock).init(result);
      
   }
   
   
   @Test
   public void testSetPauseReporter() throws Exception{
        GameEngineController first= mock(GameEngineController.class);
       GameEngineController second= mock(GameEngineController.class);
       GamePauseReporter firstPara=mock(GamePauseReporter.class);
       GamePauseReporter secondPara=mock(GamePauseReporter.class);
       first.setPauseReporter(firstPara);
       second.setPauseReporter(secondPara);
       InOrder inOrder= inOrder(first,second);
        inOrder.verify(first).setPauseReporter(firstPara);
        inOrder.verify(second).setPauseReporter(secondPara);
        
   }
   
   @Test
   public void testUpdate() throws Exception{
      GameContainer result=mock(GameContainer.class);
        GameEngineController mock= Mockito.mock(GameEngineController.class);
       doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          return null;
      }})
  .when(mock).update(result, 0);
   
   }
   @Test
   public void testInitActionEvents(){
       GameContainer result=mock(GameContainer.class);
        GameEngineController mock= Mockito.mock(GameEngineController.class);
       doAnswer(new Answer() {
      public Object answer(InvocationOnMock invocation) {
          Object[] args = invocation.getArguments();
          return null;
      }})
  .when(mock).initActionEvents();
   }
             
           
   
}

