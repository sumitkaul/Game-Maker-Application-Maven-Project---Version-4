
package JBox2d.Actions;

import java.io.Serializable;

import org.jbox2d.dynamics.Body;

import utility.Score;



@SuppressWarnings("serial")
public class ActionIncreaseScore implements JBoxGameAction,Serializable {

    int scoreModificationValue = 1;
    
    public ActionIncreaseScore(int scoreModificationValue){
	this.scoreModificationValue = scoreModificationValue;
    }
    
   	@Override
	public void doAction(Body body) {
		Score.getInstance().modifyBy(scoreModificationValue);
	}

}
