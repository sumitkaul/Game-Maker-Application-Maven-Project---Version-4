package JBox2d.main;



import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import JBox2d.Actions.ActionMoveLeft;
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

public class PhysicsComponent {


	private static World world;
	public static LinkedHashMap<String,Body> bodies;
	
                private ActionMoveLeft left;
                private static PhysicsComponent instance = null;        
                private JBoxObjectList jboxObjectList;
                private CreateWall createWall;
	public PhysicsComponent() throws IOException
	{
            
		this.world=new World(new Vec2(0,0.3f),false);
		this.bodies=new LinkedHashMap<String,Body>();
                                this.jboxObjectList=new JBoxObjectList();
                               //Subject to Change
                                this.createWall=new CreateWall();
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
          
          for(SpriteModel sprite:createWall.getWallList())
          {
              JBoxSpriteModel jboxmodel=new JBoxSpriteModel(sprite);
              jboxObjectList.registerSpriteModel(jboxmodel.createJBoxSpriteModel(sprite,"Polygon","Static"));
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

   
      
    public JBoxObjectList getJboxObjectList() {
        return jboxObjectList;
    }

    public void setJboxObjectList(JBoxObjectList jboxObjectList) {
        this.jboxObjectList = jboxObjectList;
    }
       
      
      
        
        
        
       
    public static World getWorld()
    {
        return world;
    }

}

