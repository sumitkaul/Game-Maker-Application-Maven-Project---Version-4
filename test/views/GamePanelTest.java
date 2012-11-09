package views;


import gameMaker.gameMaker;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import utility.Constants;
import view.GamePanel;

public class GamePanelTest extends UISpecTestCase{

		private GamePanel gamePanel;
	
	 	protected void setUp() throws Exception {
	 	    setAdapter(new MainClassAdapter(gameMaker.class, new String[0]));
	 	   gamePanel = new GamePanel(Constants.BOARD_WIDTH,Constants.BOARD_HEIGHT);
	 	  }
	 	
	 	 public void testAbout(){
	 		
	 		int width = gamePanel.getWidth();
	 		int height = gamePanel.getHeight();
	 		if(height == Constants.BOARD_HEIGHT && width == Constants.BOARD_WIDTH)
	 			assertTrue(true);
	 		else
	 			assertTrue(false);

	 	 }
}
