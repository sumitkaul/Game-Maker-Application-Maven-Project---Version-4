/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jBox2d.Actions;

import java.util.LinkedHashMap;
import org.jbox2d.dynamics.World;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.BodyDef;
import java.util.logging.Logger;
import JBox2d.Actions.ActionMoveUpwards;
import org.jbox2d.dynamics.Body;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ActionMoveUpwardsTest {
        private static final Logger log = Logger.getLogger(ActionMoveUpwards.class.getName());
        public static World world;
	public static LinkedHashMap<String,Body> bodies;
	private BodyDef bodyDef;
	private Body body;
    public ActionMoveUpwardsTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
       
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
         bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(0.0f, 4.0f);

		bodyDef.bullet=true;

		world =new World(new Vec2(0,9.8f),false);
		body = world.createBody(bodyDef);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doAction method, of class ActionMoveUpwards.
     */
    @Test
    public void testDoAction() {
       
       
        ActionMoveUpwards action = new ActionMoveUpwards();
		action.doAction(body);
		assertNotNull(body);
		assertEquals((int)0 ,(int) body.getPosition().x);
		assertEquals((int)-1 ,(int)body.getPosition().y);
    }
}
