package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActionMoveRight implements JBoxGameAction{
    
    public ActionMoveRight()
    {
              
    }

  @Override
  public void doAction(Body body) {
       
        Vec2 bodyPosition=body.getPosition();
        bodyPosition.x=+1;
        body.setLinearVelocity(bodyPosition);
       }
    
    
    
    
}
