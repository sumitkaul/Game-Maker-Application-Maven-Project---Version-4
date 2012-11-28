
package JBox2d.Events;

import JBox2d.main.JBoxSpriteModel;
import JBox2d.main.PhysicsComponent;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.util.Log;


public class JboxCollisionListener implements ContactListener{

    public JboxCollisionListener()
    {
        PhysicsComponent.getWorld().setContactListener(this);
    }
    
    
    
    public void handleContact(Body bodyA,Body bodyB) throws IOException
     {
          Log.debug("Fresh Contact Handle");
          for(JBoxSpriteModel jboxSprite:PhysicsComponent.getInstance().getJboxObjectList().getListJBoxSpriteModel())
           {
               if(jboxSprite.getBody().getType()==BodyType.DYNAMIC)
               {
                   if (bodyA==jboxSprite.getBody())
                   {
                       
                   }
                   if(bodyB==jboxSprite.getBody())
                   {
                       
                   }
               }
                 
           }
             
         }
     
     
    
    @Override
    public void beginContact(Contact cntct) {
        try {
            handleContact(cntct.getFixtureA().getBody(), cntct.getFixtureB().getBody());
        } catch (IOException ex) {
            Logger.getLogger(JboxCollisionListener.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    
    
}
