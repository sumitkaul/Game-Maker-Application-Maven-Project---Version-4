/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import view.ChatPanel;
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

public class OneToOneReceiverTest {
    private OneToOneReceiver mock;
    private  OneToOneReceiver actual;
    private ChatPanel mockPanel;
    public OneToOneReceiverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        mock = mock(OneToOneReceiver.class);
        mockPanel=mock(ChatPanel.class);
        actual= new  OneToOneReceiver("test", mockPanel);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class OneToOneReceiver.
     */
    @Test
    public void testRun() {
        
        verify(mock,never()).run();
    }
}
