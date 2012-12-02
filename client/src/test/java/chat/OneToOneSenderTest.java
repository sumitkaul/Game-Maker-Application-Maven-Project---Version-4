/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;


public class OneToOneSenderTest {
    private OneToOneSender mock;
    private  OneToOneSender actual;
    
    
    public OneToOneSenderTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        actual=new  OneToOneSender("test");
        mock = mock(OneToOneSender.class);
        
    }
    
    @After
    public void tearDown() {
        actual=null;
    }

   
    @Test
    public void testRun() throws Exception{
             doCallRealMethod().when(mock).run();
//       reset(mock);
       verify(mock,never()).run();
       
       verifyZeroInteractions(mock);
       verifyNoMoreInteractions(mock);

    }
}
