/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jBox2d.main;
import org.mockito.invocation.InvocationOnMock;
import java.util.LinkedHashMap;
import org.jbox2d.dynamics.Body;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doCallRealMethod;

import org.mockito.InOrder;
import org.mockito.stubbing.Answer;
import org.mockito.Mockito;
import JBox2d.main.JBoxObjectList;
import JBox2d.main.JBoxSpriteModel;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class JBoxObjectListTest {
    private static JBoxObjectList mock ;
    public JBoxObjectListTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
       
    }
    
    @Before
    public void setUp() {
         mock = Mockito.mock(JBoxObjectList.class);
    }
    
    @After
    public void tearDown() {
     mock=null;
    }

    /**
     * Test of registerSpriteModel method, of class JBoxObjectList.
     */
    @Test
    public void testRegisterSpriteModel() {
        System.out.println("registerSpriteModel");
        JBoxObjectList mock2=Mockito.mock(JBoxObjectList.class);
        JBoxSpriteModel sprite1 = Mockito.mock(JBoxSpriteModel.class);
        JBoxSpriteModel sprite2=Mockito.mock(JBoxSpriteModel.class);
        mock.registerSpriteModel(sprite1);
       mock2.registerSpriteModel(sprite2);
       InOrder inOrder= inOrder(mock,mock2);
        inOrder.verify(mock).registerSpriteModel(sprite1);
        inOrder.verify(mock2).registerSpriteModel(sprite2);
        
      
    }

    /**
     * Test of getListJBoxSpriteModel method, of class JBoxObjectList.
     */
    @Test
    public void testSetListJBoxSpriteModel() throws Exception{
        Map mocker=Mockito.mock(LinkedHashMap.class); 
       doThrow(new RuntimeException()).when(mock).setListJBoxSpriteModel(mocker);
       doCallRealMethod().when(mock).setListJBoxSpriteModel(mocker);
       
    }
    

   
}
