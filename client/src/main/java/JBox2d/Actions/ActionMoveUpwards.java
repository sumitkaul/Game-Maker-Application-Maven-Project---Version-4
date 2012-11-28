package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActionMoveUpwards implements JBoxGameAction{
    
    public ActionMoveUpwards()
    {
              
    }

  @Override
  public void doAction(Body body) {
       
        Vec2 bodyPosition=body.getPosition();
        bodyPosition.y=-1;
        body.setLinearVelocity(bodyPosition);
       }
    
    
    
    
}
