package action;

import java.io.Serializable;

import model.SpriteModel;

@SuppressWarnings("serial")
public class ActionMove implements GameAction,Serializable {
    
    @Override
    public void doAction(SpriteModel object) {
        object.setPosX(object.getPosX() + object.getSpeedX());
        object.setPosY(object.getPosY() + object.getSpeedY());
    }
}
