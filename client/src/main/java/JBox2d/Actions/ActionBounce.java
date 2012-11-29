package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActionBounce implements JBoxGameAction{

	private boolean randomBounce;


	public ActionBounce(boolean randomBounce) {
		this.setRandomBounce(randomBounce);
	}
	
	@Override
	public void doAction(Body body) {
		 
		Vec2 bodyPosition=new Vec2();
		bodyPosition.x=0;
	    bodyPosition.y=50;
		body.applyForce(bodyPosition, body.getWorldCenter());
	}


	public boolean isRandomBounce() {
		return randomBounce;
	}
	public void setRandomBounce(boolean randomBounce) {
		this.randomBounce = randomBounce;
	}

}
