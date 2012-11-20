package action;

import java.io.Serializable;

import utility.SpriteList;
import model.SpriteModel;

public class RemoveAction implements GameAction,Serializable{

	@Override
	public void doAction(SpriteModel model) {
		 SpriteList.getInstance().getToBeRemovedSpriteModels().add(model);
		
	}

}
