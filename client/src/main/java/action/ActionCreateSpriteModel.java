package action;


import java.io.Serializable;
import java.util.List;


import eventlistener.EventListener;
import eventlistener.NewFrameEventListener;
import utility.Helper;
import utility.SpriteList;
import view.Design;
import model.SpriteModel;

public class ActionCreateSpriteModel implements GameAction,Serializable {
	
	public void doAction(SpriteModel spriteModel){
		

		double heading = spriteModel.getHeading();
		double cosValue = Math.cos(heading*Math.PI/180)*10;
		double sinValue = Math.sin(heading*Math.PI/180)*10;
		
		double x = sinValue / 10;
		double y = -cosValue / 10;
	
		SpriteModel newSpriteModel=new SpriteModel((spriteModel.getPosX()+(spriteModel.getWidth()/2)),(spriteModel.getPosY()+5),x,y,7,12,spriteModel.getImageUrlString(),spriteModel.getLayer(),spriteModel.getImageId());

		SpriteList.getInstance().addSprite(newSpriteModel);
		Design.getInstance().getFacade().addSpriteModelToView(newSpriteModel);
		newSpriteModel.setGroupId("Bomb");
		
		
		boolean foundBombMoveListener = false;
		List<EventListener> listenerList = Design.getInstance().getFacade().getGameController().getEvents();
		for(EventListener listener : listenerList){
			if(listener instanceof NewFrameEventListener){
				NewFrameEventListener newFrameListerner = (NewFrameEventListener)listener;
				String groupId = newFrameListerner.getRegisteredGroupId();
				if(groupId.equalsIgnoreCase("Bomb"))
					foundBombMoveListener = true;
			}
			
		}
		
		if(!foundBombMoveListener){
			EventListener listener = Helper.getsharedHelper().getEventListenerForString("New Frame", "Move", newSpriteModel,null);
			Design.getInstance().getFacade().getGameController().registerListener(listener);
		}
	
	

		for(int i=0;i<(SpriteList.getInstance().getSpriteList().size());i++){
		if((!SpriteList.getInstance().getSpriteList().get(i).getGroupId().equalsIgnoreCase("Bomb")) && SpriteList.getInstance().getSpriteList().get(i)!=spriteModel){
		EventListener listener1 = Helper.getsharedHelper().getEventListenerForString("Collision", "remove", newSpriteModel,SpriteList.getInstance().getSpriteList().get(i));
		Design.getInstance().getFacade().getGameController().registerListener(listener1);}
		}
	
		
		List<SpriteModel> allSpriteList = SpriteList.getInstance().getSpriteList();
		for(int i=0;i<allSpriteList.size();i++){
			
			if(!allSpriteList.get(i).getGroupId().equalsIgnoreCase("Bomb")){
				if(!allSpriteList.get(i).equals(spriteModel)){
					EventListener listener1 = Helper.getsharedHelper().getEventListenerForString("Collision", "remove", allSpriteList.get(i),newSpriteModel);
					Design.getInstance().getFacade().getGameController().registerListener(listener1);
				}
	}
		
			
		}
		}	
	}