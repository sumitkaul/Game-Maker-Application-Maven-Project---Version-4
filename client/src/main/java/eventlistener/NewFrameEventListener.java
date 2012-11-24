package eventlistener;

import action.GameAction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import utility.SpriteList;

import model.SpriteModel;

public class NewFrameEventListener implements EventListener,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String registeredObjectId;
    private String registeredGroupId;
    private GameAction action;
    
    @Override
    public void checkEvent(HashMap<String,Object> map) {
    	
      
    	List<SpriteModel> allSpriteModel =  SpriteList.getInstance().getSpriteList();
    	
    	for(SpriteModel model : allSpriteModel){
    		if((model.getId().equalsIgnoreCase(registeredObjectId)) ||
    				(model.getGroupId().equalsIgnoreCase(registeredGroupId))){
    			action.doAction(model);
    		}
    	}
    }
  
    public GameAction getAction() {
		return action;
	}

	public void setAction(GameAction action) {
		this.action = action;
	}

	public String getRegisteredGroupId() {
        return registeredGroupId;
    }
    
    public void setRegisteredGroupId(String registeredGroupId) {
        this.registeredGroupId = registeredGroupId;
    }
    
    public String getRegisteredObjectId() {
        return registeredObjectId;
    }
    
    public void setRegisteredObjectId(String registeredObjectId) {
        this.registeredObjectId = registeredObjectId;
    }
    
	@Override
	public int getEventId() {
		return this.hashCode();
	}
}
