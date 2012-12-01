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
import JBox2d.Actions.ActionStartOver;

public class TestActionStartOver {

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
		ActionStartOver action = new ActionStartOver();
		action.setStartX(20.0);
		action.setStartY(30.0);
		action.doAction(body);
		assertNotNull(body);
		assertEquals((int)20.0 ,(int) body.getPosition().x);
		assertEquals((int)30.0 ,(int)body.getPosition().y);
	}
}
