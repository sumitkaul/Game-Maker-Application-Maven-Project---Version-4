package JBox2d.Actions;

import java.io.IOException;

import org.jbox2d.dynamics.Body;

import JBox2d.main.PhysicsComponent;

public class RemoveAction implements JBoxGameAction {
	
	public RemoveAction()
	{
		
	}

	@Override
	public void doAction(Body body) {
		try {
			PhysicsComponent.getInstance().getWorld().destroyBody(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
