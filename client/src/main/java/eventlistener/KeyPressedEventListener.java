package eventlistener;

import action.GameAction;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import utility.Constants;
import utility.SpriteList;
import utility.enums.playerModes;

import model.SpriteModel;
import multiplayer.Sender;

public class KeyPressedEventListener implements EventListener,Serializable {
	private static final long serialVersionUID = 1L;
	
	private int keyRegistered;
    private double xSpeed;
    private double ySpeed;
    private String registeredObjectId;
    private String registeredGroupId;
    private GameAction action;

    @Override
    public void checkEvent(HashMap<String,Object> map) {
    	
    	Integer keyPressed = (Integer) map.get("keypressed");
    	if(keyRegistered != keyPressed.intValue())
			return;
    	
    	List<SpriteModel> allSpriteModel =  SpriteList.getInstance().getSpriteList();
    	for(int i=0;i<allSpriteModel.size();i++){
    		
    		if((allSpriteModel.get(i).getId().equalsIgnoreCase(registeredObjectId)) ||
    				(allSpriteModel.get(i).getGroupId().equalsIgnoreCase(registeredGroupId))){
    					allSpriteModel.get(i).setSpeedX(getxSpeed());
    					allSpriteModel.get(i).setSpeedY(getySpeed());
    			if (!Constants.isMultiplayer || (Constants.isMultiplayer && Constants.isHost && allSpriteModel.get(i).getMode().equals(playerModes.PLAYER1) || (Constants.isMultiplayer && !Constants.isHost && allSpriteModel.get(i).getMode().equals(playerModes.PLAYER2))))
    				{
    				action.doAction(allSpriteModel.get(i));
    				// This next line seems to do nothing
    				Sender sender = new Sender();
    			}
    		}	
    	}
    }


    public int getKeyRegistered() {
        return keyRegistered;
    }

    public void setKeyRegistered(int keyRegistered) {
        this.keyRegistered = keyRegistered;
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

	public GameAction getAction() {
		return action;
	}

	public void setAction(GameAction action) {
		this.action = action;
	}


	public double getySpeed() {
		return ySpeed;
	}


	public void setySpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}


	public double getxSpeed() {
		return xSpeed;
	}


	public void setxSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
}
