package utility;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import jbox2d.CreateWall;

import model.SpriteModel;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.util.Log;

public final class JBox2DUtility {
	public static World world;
	public static LinkedHashMap<String,Body> bodies;
	private CreateWall createWall;
	
	private static JBox2DUtility instance = new JBox2DUtility();
	
	private JBox2DUtility()
	{
		this.world=new World(new Vec2(0,9.8f),false);
		this.bodies=new LinkedHashMap<String,Body>();
                             
	}
	
	public static JBox2DUtility getInstanceOf()
	{
		return instance;
	}
	

	public Body getBody(SpriteModel model) throws IOException
	{
		String id = model.getId();
		Body body=null;
		float x = (float)model.getPosX();
		float y = (float)model.getPosY();
		float width = (float)model.getWidth();
		float height =(float) model.getHeight();
		
		body=world.createBody(createBodyDefDynamic(x,y));
		if(body!=null)
		{
			FixtureDef bodyFixture=createFixtureDefinition(0.3f, 0.7f);
			bodyFixture.shape=createShapePolygon(width,height);          
			body.createFixture(bodyFixture);
            bodies.put(id, body);
		}
              return body;
	}
	public BodyDef createBodyDefDynamic(float x,float y)
	{
		BodyDef bodyDef=new BodyDef();
		bodyDef.position.set(new Vec2(x/30,y/30));
		bodyDef.type=BodyType.DYNAMIC;
                                bodyDef.bullet=true;
                
		return bodyDef;
	}

	
	public PolygonShape createShapePolygon(float x,float y)
	{
		PolygonShape polygonShape=new PolygonShape();
		polygonShape.setAsBox(x/58,y/58);
                
		return polygonShape;
	}


	public FixtureDef createFixtureDefinition(float friction,float restitution)
	{
		FixtureDef fixture=new FixtureDef();
		fixture.restitution=restitution;
		fixture.friction=friction;
                
                return fixture;
	}

     
     

        
        
 
	public float pixelToCentimeters(double pixels)
	{
		return (float) (pixels * Constants.PIXEL_TO_CENTIMETER_PARAM);
	}
	
	public double centimetersToPixels(float centimeters)
	{
		return centimeters * Constants.CENTIMETER_TO_PIXEL_PARAM;
	}


}
