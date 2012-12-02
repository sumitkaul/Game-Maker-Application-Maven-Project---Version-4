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
import static org.mockito.Mockito.*;

/**
 *
 * @author Pranav
 */
public class StatusReceiverTest {
    private StatusReceiver actual;
    private StatusReceiver mock;
    public StatusReceiverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        actual=new StatusReceiver();
        mock=mock(StatusReceiver.class);
    }
    
    @After
    public void tearDown() {
        
       actual=null;
       mock=null;
    }

    /**
     * Test of run method, of class StatusReceiver.
     */
    @Test
    public void testRun() throws InterruptedException {
        long time=10;
      Thread t= new Thread(mock);
     t.start();
     t.sleep(time);
    verify(mock,times(1)).run();
       //  verifyNoMoreInteractions(mock);

    }
}
