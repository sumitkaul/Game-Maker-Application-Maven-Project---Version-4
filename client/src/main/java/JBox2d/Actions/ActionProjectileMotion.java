/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JBox2d.Actions;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Mouse;

public class ActionProjectileMotion implements JBoxGameAction {

    
    private float direction;

    public ActionProjectileMotion(float direction) {
        
        this.direction = direction;
    }

    @Override
    public void doAction(Body body) {
        Vec2 mPosition = new Vec2(Mouse.getX(), Mouse.getY()).mul(0.5f)
                .mul(1 / 10f);
        Vec2 bodyPosition = body.getPosition();
        Vec2 force = mPosition.sub(bodyPosition);
        body.applyForce(force, body.getPosition());
        Vec2 vel = body.getLinearVelocity();
        vel.y = direction * 5;
        vel.x = direction * 2;
        body.setLinearVelocity(vel);
    }
}
