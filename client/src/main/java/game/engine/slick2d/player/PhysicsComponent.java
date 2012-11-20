package game.engine.slick2d.player;



import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.util.Log;

public class PhysicsComponent implements ContactListener{


	public static World world;
	public static LinkedHashMap<String,Body> bodies;
	private CreateWall createWall;
                private static boolean  contactListenerFlag;
       
        
        
	public PhysicsComponent() throws IOException
	{
		this.world=new World(new Vec2(0,9.8f),false);
		this.bodies=new LinkedHashMap<String,Body>();
                                this.world.setContactListener(this);
                                this.contactListenerFlag=false;
                                this.createWall=new CreateWall(this);
                
	}
      public void moveLogic() 
      {
          
	world.step(1 / 60f, 8, 3);

      }

      public void inputLogic() {
          
		for (Entry<String,Body> body : bodies.entrySet()) {
			if (body.getValue().getType() == BodyType.DYNAMIC) {
                            if(Keyboard.getEventKey()==Keyboard.KEY_A)
                            {
                          Vec2 bodyPosition=body.getValue().getLinearVelocity();
                          bodyPosition.x=-5;
                          bodyPosition.y=-2;
                          body.getValue().setLinearVelocity(bodyPosition);
                          
                            }       
                        
                           
                            
                     }
                }
                
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
                                bodyFixture.isSensor=true;
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
                bodyDef.bullet=true;
                
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
		polygonShape.setAsBox(x/58,y/58);
                
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

     public void handleContact(Body bodyA,Body bodyB)
     {
         Log.debug("@@@@@@@@@@@@@");
         if(bodyA.getType()==BodyType.DYNAMIC && bodyB.getType()==BodyType.DYNAMIC)
         {
             for(Entry<String,Body>body:bodies.entrySet())
             {
                 if(body.getValue()==bodyA )
                 {
                     Log.info(body.getKey());
                 }
                 if(body.getValue()==bodyB )
                 {
                     Log.info(body.getKey());
                 }
                 
                 
             }
             
         }
     }
        
        
        
    @Override
    public void beginContact(Contact con) {
         
         handleContact(con.m_fixtureA.getBody(), con.m_fixtureB.getBody());
         
        
    }

    @Override
    public void endContact(Contact cntct) {
        contactListenerFlag=false;
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        
    }

}


