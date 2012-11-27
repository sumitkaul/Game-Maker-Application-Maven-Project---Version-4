
package jbox2d;

import java.io.IOException;
import java.util.logging.Level;

public class CreateWall {
    
    private PhysicsComponent physicsComponent;
    
    public CreateWall(PhysicsComponent physicsComponent)
    {
      this.physicsComponent=physicsComponent; 
      this.createLeftWall();
      this.createRightWall();
      this.createTopWall();
      this.createBottomWall();
    }
    
    public void createLeftWall()
    {
      try {
            physicsComponent.bodies.put("Left Wall",physicsComponent.createBody
                        ("Left Wall","polygon","Static",0,600,0,800,0.0f));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GameEngineController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void createRightWall()
    {
        try {
            physicsComponent.bodies.put("Right Wall",physicsComponent.createBody
                        ("Right Wall","polygon","Static",0,600,0,800,0.0f));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GameEngineController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void createTopWall()
    {
         try {
            physicsComponent.bodies.put("Top Wall",physicsComponent.createBody
                        ("Top Wall","polygon","Static",500,0,800,0,0.0f));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GameEngineController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public void createBottomWall()
    {
        try {
            physicsComponent.bodies.put("Bottom Wall",physicsComponent.createBody
                        ("Bottom Wall","polygon","Static",500,580,800,0,0.0f));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GameEngineController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
}
