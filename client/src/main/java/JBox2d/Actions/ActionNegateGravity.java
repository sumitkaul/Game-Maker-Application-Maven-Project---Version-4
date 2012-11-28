/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 *
 * @author ralekar
 */
public class ActionNegateGravity implements JBoxGameAction{
    
    public ActionNegateGravity()
    {
        
    }

    @Override
    public void doAction(Body body) {
        
       body.applyForce(new Vec2(0,-9.8f),body.getWorldCenter());
    }
    
}
