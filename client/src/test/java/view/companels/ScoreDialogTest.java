package view.companels;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.companels.ScoreDialog;


public class ScoreDialogTest {

	
    ScoreDialog  scoreDialog;
    int status=1;
    int expectedsStatus;
    int actualStatus;
    java.awt.Frame parent;
    boolean modal;
	@Before
	public void setUp() throws Exception {
		
		scoreDialog= new ScoreDialog(parent, modal);
	}

	@After
	public void tearDown() throws Exception {
		
		scoreDialog=null;
	}

	@Test
	public void testGetReturnStatus() {
		  
		scoreDialog.doClose(status);
		
		int actualStatus=scoreDialog.getReturnStatus();
		expectedsStatus=status;
		assertEquals(expectedsStatus,actualStatus);
	}


}
