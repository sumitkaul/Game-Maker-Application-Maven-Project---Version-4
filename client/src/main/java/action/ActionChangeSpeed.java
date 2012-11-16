package action;

import java.io.Serializable;

import model.SpriteModel;

public class ActionChangeSpeed implements GameAction,Serializable {

    private int newSpeedX, newSpeedY;

    public ActionChangeSpeed(int newSpeedX, int newSpeedY) {
        super();
        this.newSpeedX = newSpeedX;
        this.newSpeedY = newSpeedY;
    }

    @Override
    public void doAction(SpriteModel object) {
        object.setSpeedX(newSpeedX);
        object.setSpeedY(newSpeedY);
    }
}
