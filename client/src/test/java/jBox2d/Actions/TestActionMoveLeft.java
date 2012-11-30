package jBox2d.Actions;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;

import org.junit.Before;
import org.junit.Test;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import JBox2d.Actions.ActionMoveLeft;

public class TestActionMoveLeft {

	public static World world;
	public static LinkedHashMap<String,Body> bodies;
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
		ActionMoveLeft action = new ActionMoveLeft();
		action.doAction(body);
		assertNotNull(body);
		assertEquals((int)-1.0 ,(int) body.getPosition().x);
		assertEquals((int)4.0 ,(int)body.getPosition().y);
	}
}
