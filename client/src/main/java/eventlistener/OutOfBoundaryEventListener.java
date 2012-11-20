package eventlistener;


import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import model.SpriteModel;
import utility.SpriteList;
import view.GameMakerView;

import action.GameAction;

public class OutOfBoundaryEventListener implements EventListener,Serializable {

	private String registeredObjectId;
	private String registeredGroupId;
	private GameAction action;

	@Override
	public void checkEvent(HashMap<String, Object> map) {
		List<SpriteModel> allSpriteModel = SpriteList.getInstance()
				.getSpriteList();
		for (int i = 0; i < allSpriteModel.size(); i++) {
			SpriteModel model = allSpriteModel.get(i);
			if ((allSpriteModel.get(i).getId()
					.equalsIgnoreCase(registeredObjectId))
					|| (allSpriteModel.get(i).getGroupId()
							.equalsIgnoreCase(registeredGroupId))) {
				double posX = model.getPosX();
				double speedX = model.getSpeedX();
				double posY = model.getPosY();
				double speedY = model.getSpeedY();
				if (speedX > 0) {
					if ((posX - model.getWidth()) > GameMakerView.getInstance().getGamePanel().getWidth()){
						action.doAction(model);
					}
				} else {
					if (posX + model.getWidth() < 0) {
						action.doAction(allSpriteModel.get(i));
					}
				}
				if (speedY > 0) {
					if ((posY - model.getHeight()) >  GameMakerView.getInstance().getGamePanel().getWidth()) {
						action.doAction(allSpriteModel.get(i));
					}
				} else {
					if (posY + model.getHeight() < 0) {
						action.doAction(allSpriteModel.get(i));
					}
				}

				
			}
		}

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

}
