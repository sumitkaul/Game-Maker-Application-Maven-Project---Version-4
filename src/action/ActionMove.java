package action;

import model.SpriteModel;

public class ActionMove implements GameAction {
    
    @Override
    public void doAction(SpriteModel object) {
        object.setPosX(object.getPosX() + object.getSpeedX());
        object.setPosY(object.getPosY() + object.getSpeedY());
    }
}
