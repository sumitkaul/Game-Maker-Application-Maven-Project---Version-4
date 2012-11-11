package action;

import model.SpriteModel;

public class ActionChangeVisibility implements GameAction {

    private boolean visible;

    public ActionChangeVisibility(boolean visible) {
        super();
        this.visible = visible;
    }

    @Override
    public void doAction(SpriteModel model) {
        model.setVisible(visible);
    }
}
