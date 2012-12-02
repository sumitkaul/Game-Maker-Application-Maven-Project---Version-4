/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.companels;

import org.junit.*;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Nikhil
 */
public class GameBaseSavePanelTest {
    
    private static JComponent mockJComponent;
    
    public GameBaseSavePanelTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
        mockJComponent = mock(JComponent.class);
    }
    
    @After
    public void tearDown() {
        mockJComponent = null;
    }

    @Test
    public void testSaveGameToRemoteServerWithoutUI() throws Exception {    
        
        if(mockJComponent == null){            
            fail("mockJComponent was not created. "+mockJComponent.getClass().toString());
        }
        
        String gameData = "";
        String gameName = "";
        String authorName = "";
        Boolean isMultiPlayer = null;
        GameBaseSavePanel instance = null;
        boolean expResult = false;
        
        instance = new GameBaseSavePanel(mockJComponent);        
        boolean result = instance.saveGameToRemoteServerWithoutUI(gameData, gameName, authorName, isMultiPlayer);
        
        assertEquals(expResult, result);               
    }
}
