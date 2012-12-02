package view.companels;

import org.junit.*;
import javax.swing.JComponent;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * Test cases for public constructor
 */
public class GameBaseLoadPanelTest {
    
    private static JComponent mockJComponent;
    
    public GameBaseLoadPanelTest() {
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
    public void testReadGameDataFromRemoteList() {       
        
        if(mockJComponent == null){            
            fail("Unable to create mock " + mockJComponent.getClass().toString());
        }
        
        GameBaseLoadPanel instance = null;
        instance = new GameBaseLoadPanel(mockJComponent);                
        
        Logger log = Logger.getLogger(instance.getClass());
        
        //String expResult = "";
        try{
           String result = instance.readGameDataFromRemoteList();
           assertNotNull(result);
        }catch(Exception e){
            log.info("Exception occured while testing: public void readGameDataFromRemoteList()");
//          log.error("Exception occured while testing: public void testReadGameDataFromRemoteList()", e);
        }        
    }
}
