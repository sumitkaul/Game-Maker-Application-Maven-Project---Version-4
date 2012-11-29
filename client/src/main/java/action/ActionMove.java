package action;

import java.io.Serializable;

import view.PlayerButtonPanel;

import model.SpriteModel;
import multiplayer.Protocol;

@SuppressWarnings("serial")
public class ActionMove implements GameAction,Serializable {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ActionMove.class);
    
	  @Override
    public void doAction(SpriteModel object) {

    	LOG.info("In action move[[[[[[[[[[[[[[[[[[[[[[[");

        object.setPosX(object.getPosX() + object.getSpeedX());
        object.setPosY(object.getPosY() + object.getSpeedY());

        LOG.info("Object x pos is 999999 "+object.getPosX()+"Onject y pos is 9999999999 "+object.getPosY());
}
}
