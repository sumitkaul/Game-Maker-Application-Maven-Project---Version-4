package action;

import java.io.Serializable;

import view.PlayerButtonPanel;

import model.SpriteModel;

@SuppressWarnings("serial")
public class ActionMove implements GameAction,Serializable {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ActionMove.class);
    
    @Override
    public void doAction(SpriteModel object) {
    	LOG.info("In move action ==========================");
        object.setPosX(object.getPosX() + object.getSpeedX());
        object.setPosY(object.getPosY() + object.getSpeedY());
        LOG.info("all params here" +object.getPosX()+"===" + object.getPosY() );
    }
}
