/**
 * 
 */
package action;

import utility.Score;
import model.SpriteModel;

/**
 * @author Nikhil
 *
 */
public class ActionIncreaseScore implements GameAction {

    /* (non-Javadoc)
     * @see action.GameAction#doAction(model.SpriteModel)
     */
    int scoreModificationValue = 1;
    
    public ActionIncreaseScore(int scoreModificationValue){
	this.scoreModificationValue = scoreModificationValue;
    }
    
    @Override
    public void doAction(SpriteModel model) {
	Score.getInstance().modifyBy(scoreModificationValue);
    }

}
