package JBox2d.Actions;


import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class ActionRandomChangeDirection implements JBoxGameAction {
	
	public ActionRandomChangeDirection()
    {
              
    }

  @Override
  public void doAction(Body body) {
       
	  int choice = getRandomNumberFrom(0, 3);
	  
	  if (choice == 0)
	  {
		  //Logic to move up
		  Vec2 bodyPosition=body.getPosition();
	        bodyPosition.y=+1;
	        bodyPosition.x=0;
	        
	        body.applyForce(bodyPosition, body.getWorldCenter());
	  }
	  else if (choice == 1)
	  {
		  //Logic to move right
		  Vec2 bodyPosition=body.getPosition();
	        bodyPosition.x=+1;
	        body.applyForce(bodyPosition, body.getWorldCenter());
	  }
	  else if (choice == 2)
	  {
		  //Logic to move down
		  Vec2 bodyPosition=body.getPosition();
	        bodyPosition.y=-1;
	        body.applyForce(bodyPosition, body.getWorldCenter());
	  }
	  else if(choice == 3)
	  {
		  //Logic to move left
		  Vec2 bodyPosition=body.getPosition();
	        bodyPosition.x=-1;
	        body.applyForce(bodyPosition, body.getWorldCenter());
	  }
       }
  
  public static int getRandomNumberFrom(int min, int max) {
      Random random = new Random();
      int randomNumber = random.nextInt((max + 1) - min) + min;

      return randomNumber;

  }

}
