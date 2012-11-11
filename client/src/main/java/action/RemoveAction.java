package action;

import utility.SpriteList;
import model.SpriteModel;

public class RemoveAction implements GameAction{

	@Override
	public void doAction(SpriteModel model) {
		 SpriteList.getInstance().getToBeRemovedSpriteModels().add(model);
		
	}

}
