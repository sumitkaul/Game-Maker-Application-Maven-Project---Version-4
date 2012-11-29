package JBox2d.Actions;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;
import org.jbox2d.common.Vec2;
import utility.Constants;
import model.SpriteModel;

@SuppressWarnings("serial")
public class ActionRotate implements JBoxGameAction,Serializable{

	private float angle;
	
	public ActionRotate(float angle) {
		this.angle = angle;
	}
	
	@Override
	public void doAction(Body body) {
		Vec2 pos = body.getPosition();
		body.setTransform(pos, angle);
	}	



}
