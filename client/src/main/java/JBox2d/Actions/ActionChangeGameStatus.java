package JBox2d.Actions;

import java.io.Serializable;
import javax.swing.JOptionPane;

import org.jbox2d.dynamics.Body;

import facade.Facade;

import utility.Helper;
import view.GameMakerView;

public class ActionChangeGameStatus implements JBoxGameAction,Serializable {

	private static final long serialVersionUID = 1L;
	private final boolean YES = true;
	private final boolean NO = false;

	private boolean isGameStopCommandIssued = NO;

	// This is added to suppress the message for unit testing.
	private boolean showMessage = true;


	String message = "";

	public ActionChangeGameStatus(boolean isWin) {
		if (isWin)
			message = "You Win";
		else
			message = "You Lose";
	}

	@Override
	public void doAction(Body body) {

		if (!isGameStopCommandIssued  ) {
			if(showMessage == true)		    	
				JOptionPane.showMessageDialog(null, message);

			Facade facade = Helper.getsharedHelper().getFacade();
			facade.stopGame();
			isGameStopCommandIssued = YES;

		}

	}

	public boolean isShowMessage() {
		return showMessage;
	}

	public void setShowMessage(boolean showMessage) {
		this.showMessage = showMessage;
	}


}
