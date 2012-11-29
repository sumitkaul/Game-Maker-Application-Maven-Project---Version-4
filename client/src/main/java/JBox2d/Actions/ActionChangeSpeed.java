package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActionChangeSpeed implements JBoxGameAction{
	private double newSpeedX, newSpeedY;

	public ActionChangeSpeed(double newSpeedX, double newSpeedY) {
		super();
		this.newSpeedX = newSpeedX;
		this.newSpeedY = newSpeedY;
	}


	@Override
	public void doAction(Body body){
		Vec2 bodyPosition=body.getPosition();
		bodyPosition.x= (float) this.newSpeedX;
		bodyPosition.y= (float) this.newSpeedY;
		body.setLinearVelocity(bodyPosition);
	}
}
