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



/**
 *
 * @author Pranav
 */
public class AuthReceiverTest {
    private AuthReceiver actual;
    private AuthReceiver mock;
    public AuthReceiverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        actual = new AuthReceiver("test");
        mock= mock(AuthReceiver.class);
    }
    
    @After
    public void tearDown() {
        actual=null;    
        mock =null;
    }

    /**
     * Test of run method, of class AuthReceiver.
     */
    @Test
    public void testRun() {
     
     Thread t= new Thread(mock);
     t.start();
    
     verify(mock,times(1)).run();
    }
}
