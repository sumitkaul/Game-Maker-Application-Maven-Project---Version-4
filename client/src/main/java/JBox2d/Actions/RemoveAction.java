package JBox2d.Actions;

import org.jbox2d.dynamics.Body;

import JBox2d.main.PhysicsComponent;

public class RemoveAction implements JBoxGameAction {
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(RemoveAction.class);
	
	public RemoveAction()
	{
		
	}

	@Override
	public void doAction(Body body) {
			PhysicsComponent.getWorld().destroyBody(body);
	}
}
