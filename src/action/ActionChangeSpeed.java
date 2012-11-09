package action;

import model.SpriteModel;

public class ActionChangeSpeed implements GameAction {

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
