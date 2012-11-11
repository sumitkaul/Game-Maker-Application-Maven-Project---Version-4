package action;

import model.SpriteModel;

public class ActionBackToLastPosition implements GameAction {
    
    @Override
    public void doAction(SpriteModel object) {
        object.setPosX(object.getPreviousX());
        object.setPosY(object.getPreviousY());
    }
}
