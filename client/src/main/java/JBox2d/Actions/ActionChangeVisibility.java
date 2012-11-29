package JBox2d.Actions;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;

import model.SpriteModel;

public class ActionChangeVisibility implements JBoxGameAction,Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Since JBox2D does not support changing visibility, We are destroying the body.
	 * If we unbind the image from, body it will be invisible, but still it will exist and other object can collide against it.
	 * But for changeover from actions to jbox2dActions the name is kept same.
	 **/

	private boolean visible;

	public ActionChangeVisibility(boolean visible) {
		super();
		this.visible = visible;
	}

	@Override
	public void doAction(Body body) {
		if(this.visible == false)
			body.getWorld().destroyBody(body);

	}
}
