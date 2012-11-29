package JBox2d.Actions;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;
import org.jbox2d.common.Vec2;


@SuppressWarnings("serial")
public class ActionStartOver implements JBoxGameAction,Serializable {

	private double startX, startY;

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	@Override
	public void doAction(Body body) {

		body.getPosition().x = (float) startX;
		body.getPosition().y = (float) startY;
	}


}
