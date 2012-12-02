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
import view.ChatPanel;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
public class ChatReceiverTest {
    private ChatReceiver actual;
    private ChatReceiver mock;
    private ChatPanel panelMock;
    public ChatReceiverTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        panelMock=mock(ChatPanel.class);
                
        actual= new ChatReceiver(panelMock);
        mock =mock(ChatReceiver.class);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class ChatReceiver.
     */
    @Test
    public void testRun() {
        //System.out.println("run");
       
       Thread t= new Thread(mock);
     t.start();
    
     //verify(mock,times(1)).run();
       
    }
}
