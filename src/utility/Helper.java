package utility;

import java.util.ArrayList;
import java.util.List;

import view.Design;

import model.SpriteModel;
import eventlistener.CollisionEventListener;
import eventlistener.EventListener;
import eventlistener.KeyPressedEventListener;
import eventlistener.NewFrameEventListener;
import eventlistener.OutOfBoundaryEventListener;
import action.ActionBackToLastPosition;
import action.ActionCreateSpriteModel;
import action.ActionBounce;
import action.ActionChangeSpeed;
import action.ActionChangeVisibility;
import action.ActionChangeGameStatus;
import action.ActionIncreaseScore;
import action.ActionMove;
import action.ActionPlaySound;
import action.ActionRandomChangeDirection;
import action.ActionRotate;
import action.ActionStartOver;
import action.GameAction;
import action.RemoveAction;

public class Helper {
	public static Helper sharedHelper;
	private int currentKeyCode; 
	
	public static Helper getsharedHelper(){
		if(sharedHelper == null)
			sharedHelper = new Helper();
		return sharedHelper;
	}
	
	public GameAction getActionForString(String actionString, SpriteModel model){
		GameAction gameAction = null;
		if(actionString.equalsIgnoreCase("move")){
			gameAction = new ActionMove();
		}
		else if(actionString.equalsIgnoreCase("create bomb")){
			gameAction = new ActionCreateSpriteModel();
		}
		else if(actionString.equalsIgnoreCase("change visibility")){
			gameAction = new ActionChangeVisibility(!model.isVisible());
		}
		else if(actionString.equalsIgnoreCase("play sound")){
			gameAction = new ActionPlaySound(model.getSoundFile());
		}
		else if(actionString.equalsIgnoreCase("Random Movement")){
			gameAction = new ActionRandomChangeDirection(model);
		}
		else if(actionString.equalsIgnoreCase("Change Speed")){
			gameAction = new ActionChangeSpeed((int)model.getSpeedX(),(int)model.getSpeedY());
		}
		else if(actionString.equalsIgnoreCase("Bounce")){
			gameAction = new ActionBounce(false);
		}
		else if(actionString.equalsIgnoreCase("Remove")){
			gameAction = new RemoveAction();
		}
		else if(actionString.equalsIgnoreCase("Rotate Clockwise")){
			gameAction = new ActionRotate("Clockwise");
		}
		else if(actionString.equalsIgnoreCase("Rotate Anticlockwise")){
			gameAction = new ActionRotate("Anticlockwise");
		}
		else if(actionString.equalsIgnoreCase("Reposition")){
			gameAction = new ActionBackToLastPosition();
		}
		else if(actionString.equalsIgnoreCase("Start over")){
			gameAction = new ActionStartOver();
		}
		else if(actionString.equalsIgnoreCase("Game Win")){
			gameAction = new ActionChangeGameStatus(true);
		}
		else if(actionString.equalsIgnoreCase("Game Lose")){
			gameAction = new ActionChangeGameStatus(false);
		}
		
		else if(actionString.equalsIgnoreCase("Increase Score")){
		    gameAction = new ActionIncreaseScore(model.getScoreModificationValue());
		    Design.getInstance().setShouldDisplayScore(true);
		}
		
		return gameAction;
	}
	


	public EventListener getEventListenerForString(String eventName,String actionName, 
			SpriteModel selectedSpriteModel, SpriteModel secondarySpriteModel) {
		
		if(eventName.equalsIgnoreCase("New Frame")){
			NewFrameEventListener newFrameEventListener = new NewFrameEventListener();
			newFrameEventListener.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			newFrameEventListener.setRegisteredObjectId(selectedSpriteModel.getId());
			GameAction action = getActionForString(actionName,selectedSpriteModel);
			newFrameEventListener.setAction(action);
			return newFrameEventListener;
		}
		else if(eventName.equalsIgnoreCase("Collision")){
			CollisionEventListener collisionListener = new CollisionEventListener();
			if(selectedSpriteModel != null && selectedSpriteModel.getGroupId() != null)
				collisionListener.setRegisteredGroupId1(selectedSpriteModel.getGroupId());
			if(secondarySpriteModel!=null && secondarySpriteModel.getId()!=null)
				collisionListener.setRegisteredGroupId2(secondarySpriteModel.getGroupId());
			GameAction action = getActionForString(actionName, selectedSpriteModel);
			
			List<GameAction> list = new ArrayList<GameAction>();
			if(action!=null)
				list.add(action);
			
			collisionListener.setAction(action);
	
			return collisionListener;
			
		}
		else if(eventName.equalsIgnoreCase("Input")){
			KeyPressedEventListener keyListener = new KeyPressedEventListener();
			keyListener.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			keyListener.setRegisteredObjectId(selectedSpriteModel.getId()); 
			
			keyListener.setxSpeed(selectedSpriteModel.getSpeedX());
			keyListener.setySpeed(selectedSpriteModel.getSpeedY());
			
			keyListener.setKeyRegistered(this.currentKeyCode);
			
			GameAction action = getActionForString(actionName, selectedSpriteModel);
			keyListener.setAction(action);
			
			return keyListener;
		}
		else if(eventName.equalsIgnoreCase("Out of Boundary")){
			OutOfBoundaryEventListener outOfBoundary = new OutOfBoundaryEventListener();
			outOfBoundary.setRegisteredGroupId(selectedSpriteModel.getGroupId());
			outOfBoundary.setRegisteredObjectId(selectedSpriteModel.getId()); 
			GameAction action = getActionForString(actionName,selectedSpriteModel);
			List<GameAction> list = new ArrayList<GameAction>();
			if(action!=null)
				list.add(action);
			outOfBoundary.setAction(action);
			return outOfBoundary;
		}
		return null;
		
	}
	
	/*
	 * GETTER AND SETTER
	 */
	
	public int getCurrentKeyCode() {
		return currentKeyCode;
	}

	public void setCurrentKeyCode(int currentKeyCode) {
		this.currentKeyCode = currentKeyCode;
	}
}
