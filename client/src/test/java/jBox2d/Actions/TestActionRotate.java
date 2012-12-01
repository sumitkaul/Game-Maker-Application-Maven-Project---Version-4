package jBox2d.Actions;
import static org.junit.Assert.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.Before;
import org.junit.Test;

import JBox2d.Actions.ActionRotate;

public class TestActionRotate {
	public static World world;
	private BodyDef bodyDef;
	private Body body;

	@Before
	public void setUp() throws Exception {
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(0.0f, 4.0f);

		bodyDef.bullet=true;

		world =new World(new Vec2(0,9.8f),false);
		body = world.createBody(bodyDef);
	}
	
	@Test
	public void testDoAction()
	{
		ActionRotate action = new ActionRotate(3.0f);
		action.doAction(body);
		assertEquals((int) 3.0, (int) body.getAngle());
	}
}
