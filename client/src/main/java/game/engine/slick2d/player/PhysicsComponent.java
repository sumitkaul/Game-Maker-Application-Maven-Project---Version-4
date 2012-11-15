package game.engine.slick2d.player;



import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;



import org.newdawn.slick.util.Log;

public class PhysicsComponent {

	
	public static World world;
	public static Set<Body> bodies;
	private float colorRed;
	private float colorBlue;
	private float colorGreen;
   public PhysicsComponent() throws IOException
   {
	//   this.world=new World(new Vec2(0,9.8f),false);
		//this.bodies=new HashSet<Body>();
		randomColors();
	//	setUpBox();
		//setUpGround();
   }
   /*
   public void setUpGround()
	{
		BodyDef bodyDef =new BodyDef();
		bodyDef.position.set(0,0);
		bodyDef.type=BodyType.STATIC;
		PolygonShape boxShape=new PolygonShape();
		boxShape.setAsBox(1000,0);
		Body box=world.createBody(bodyDef);
		FixtureDef boxFixture=new FixtureDef();
		boxFixture.density=0.5f;
	    boxFixture.shape=boxShape;
		box.createFixture(boxFixture);
		bodies.add(box); 
	}
	
	public void setUpBox() throws IOException
	{
		
		BodyDef box1Def =new BodyDef();
		box1Def.position.set(500,500);
		
		box1Def.type=BodyType.DYNAMIC;
	    PolygonShape groundShape=new PolygonShape();
		groundShape.setAsBox(0.75f,0.75f);
		
		Body ground=world.createBody(box1Def);
		FixtureDef groundFixture=new FixtureDef();
		groundFixture.restitution=0.3f;
		groundFixture.friction=0.3f;
		groundFixture.shape=groundShape;
		ground.createFixture(groundFixture);
		bodies.add(ground);
		
	}
	
*/	
	
	
	public void randomColors() {
		Random randomGenerator = new Random();
		colorRed = randomGenerator.nextFloat();
		colorBlue = randomGenerator.nextFloat();
		colorGreen = randomGenerator.nextFloat();

	}

	
	
}
