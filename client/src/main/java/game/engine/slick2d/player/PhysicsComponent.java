package game.engine.slick2d.player;



import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashMap;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.util.Log;

public class PhysicsComponent {


	public static World world;
	public static LinkedHashMap<String,Body> bodies;
	

	public PhysicsComponent() throws IOException
	{
		this.world=new World(new Vec2(0,9.8f),false);
		this.bodies=new LinkedHashMap<String,Body>();

	}
      public void moveLogic() 
      {
          
	world.step(1 / 60f, 8, 3);

      }

      public Body createBody(String spriteName,String shape,String bodyType,
              float x,float y,float width,float height,float radius) throws IOException
	{
		Body body=null;
		if(bodyType.equalsIgnoreCase("Dynamic"))
			body=world.createBody(createBodyDefDynamic(x,y));
		if (bodyType.equalsIgnoreCase("Static"))
			body=world.createBody(createBodyDefStatic(x,y));
		if(body!=null)
		{
			FixtureDef bodyFixture=createFixtureDefinition(0.3f, 0.7f);
                        
			if (shape.equalsIgnoreCase("circle"))
			{
				bodyFixture.shape=createShapeCircle(radius);
			}
			if (shape.equalsIgnoreCase("polygon"))
			{
				bodyFixture.shape=createShapePolygon(width,height);
			}
			body.createFixture(bodyFixture);
                        bodies.put(spriteName, body);
		}
                return body;
	}


	public BodyDef createBodyDefDynamic(float x,float y)
	{
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(new Vec2(x/30,y/30));
		bodyDef.type=BodyType.DYNAMIC;
		return bodyDef;
	}

	public BodyDef createBodyDefStatic(float x,float y)
	{
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(new Vec2(x/30,y/30));
		bodyDef.type=BodyType.STATIC;
                
		return bodyDef;
	}

	public PolygonShape createShapePolygon(float x,float y)
	{
		PolygonShape polygonShape=new PolygonShape();
		polygonShape.setAsBox(x/30,y/30);
		return polygonShape;
	}

	public CircleShape createShapeCircle(float x)
	{
		CircleShape circleShape=new CircleShape();
		circleShape.m_radius=x/30;
		return circleShape;
	}

	public FixtureDef createFixtureDefinition(float friction,float restitution)
	{
		FixtureDef fixture=new FixtureDef();
		fixture.restitution=restitution;
		fixture.friction=friction;
                
		return fixture;
	}

}


