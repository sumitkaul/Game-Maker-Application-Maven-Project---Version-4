package jbox2d;



import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import jbox2d.Actions.ActionMoveLeft;
import model.SpriteModel;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.util.Log;
import utility.SpriteList;

public class PhysicsComponent implements ContactListener{


	private static World world;
	public static LinkedHashMap<String,Body> bodies;
	private CreateWall createWall;
                private ActionMoveLeft left;
                private static PhysicsComponent instance = null;        
                private JBoxObjectList jboxObjectList;
        
	public PhysicsComponent() throws IOException
	{
            
		this.world=new World(new Vec2(0,0.3f),false);
		this.bodies=new LinkedHashMap<String,Body>();
                                this.world.setContactListener(this);
                                this.jboxObjectList=new JBoxObjectList();
                                
	}
        
        public static PhysicsComponent getInstance() throws IOException {
         if(instance == null) {
         instance = new PhysicsComponent();
      }
      return instance;
   }
        
      public void moveLogic() 
      {
          
	world.step(1 / 60f, 8, 3);

      }

      public void initSpriteBodyMapping() throws IOException
      {
          for(SpriteModel sprite:SpriteList.getInstance().getSpriteList())
          {
              JBoxSpriteModel jboxmodel=new JBoxSpriteModel(sprite);
              jboxObjectList.registerSpriteModel(jboxmodel.createJBoxSpriteModel(sprite,"Polygon","Dynamic"));
          }
          
      }
      
      
      public void inputLogic() {
          
		for (Entry<String,Body> body : bodies.entrySet()) {
			if (body.getValue().getType() == BodyType.DYNAMIC) {
                            if(Keyboard.getEventKey()==Keyboard.KEY_A)
                            {
                                
                          /*Vec2 bodyPosition=body.getValue().getLinearVelocity();
                          bodyPosition.x=-5;
                          bodyPosition.y=-2;
                          body.getValue().setLinearVelocity(bodyPosition);
                          */
                                left.doAction(body.getValue());
                                
                                
                                
                            }       
                        
                           
                            
                     }
                        
                }
                
	}
       
      
      
     public void handleContact(Body bodyA,Body bodyB)
     {
          Log.debug("Fresh Contact Handle");
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
        
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
        
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
        
    }
    
    public static World getWorld()
    {
        return world;
    }

}


