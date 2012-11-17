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
	private static LinkedHashMap<String,Body> bodies;
	private float colorRed;
	private float colorBlue;
	private float colorGreen;

	public PhysicsComponent() throws IOException
	{
		this.world=new World(new Vec2(0,9.8f),false);
		this.bodies=new LinkedHashMap<String,Body>();

	}

	public void createBody(String shape,String bodyType) throws IOException
	{
		Body body=null;
		if(bodyType.equalsIgnoreCase("Dynamic"))
			body=world.createBody(createBodyDefDynamic(500, 500));
		if (bodyType.equalsIgnoreCase("Static"))
			body=world.createBody(createBodyDefStatic(500, 500));
		if(body!=null)
		{
			FixtureDef bodyFixture=createFixtureDefinition(0.3f, 0.7f);

			if (shape.equalsIgnoreCase("circle"))
			{
				bodyFixture.shape=createShapeCircle(30);
			}
			if (shape.equalsIgnoreCase("ploygon"))
			{
				bodyFixture.shape=createShapePolygon(100,100);
			}
			body.createFixture(bodyFixture);
		}
	}


	public BodyDef createBodyDefDynamic(float x,float y)
	{
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(new Vec2(x,y));
		bodyDef.type=BodyType.DYNAMIC;
		return bodyDef;
	}

	public BodyDef createBodyDefStatic(float x,float y)
	{
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(new Vec2(x,y));
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


