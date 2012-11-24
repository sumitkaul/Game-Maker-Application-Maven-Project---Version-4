package action;

import static org.junit.Assert.*;

import java.util.List;

import model.SpriteModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eventlistener.EventListener;
import eventlistener.NewFrameEventListener;
import java.util.Collection;

import utility.ResizeHelper;
import utility.SpriteList;
import view.GameMakerView;

public class ActionCreateSpriteModelTest {
	
	@Before
	public void setUp() throws Exception {
		GameMakerView.getInstance().reset();
	}

	@After
	public void tearDown() throws Exception {
		GameMakerView.getInstance().reset();
	}

	@Test
	public void testDoAction() {
		
	    SpriteModel	selectedSpriteModel = new SpriteModel(190, 190, 10, 10, 100, 100, "","");
      	selectedSpriteModel.setGroupId("Group1");
      	GameAction action = new ActionCreateSpriteModel();
        action.doAction(selectedSpriteModel);
		Collection<SpriteModel> allSpriteList = SpriteList.getInstance().getSpriteList();
		SpriteModel newModel =null;
		
		for(SpriteModel sm : allSpriteList){
			if(sm.getGroupId().equalsIgnoreCase("Bomb"))
			{
				newModel =  sm;
				break;
			}	
       }
	 if (newModel == null)
			assertTrue(false);
	 
	 boolean foundBombMoveListener = false;
	 List<EventListener> listenerList = GameMakerView.getInstance().getFacade().getGameController().getEvents();
		for(EventListener listener : listenerList){
			if(listener instanceof NewFrameEventListener){
				NewFrameEventListener newFrameListerner = (NewFrameEventListener)listener;
				String groupId = newFrameListerner.getRegisteredGroupId();
				if(groupId.equalsIgnoreCase("Bomb"))
					foundBombMoveListener = true;
			   } 

        }
		assertTrue(foundBombMoveListener);			
		
	}	
}