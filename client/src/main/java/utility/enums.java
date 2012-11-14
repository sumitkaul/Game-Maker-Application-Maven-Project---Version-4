package utility;

public class enums {

	public enum Layouts {
	    FLOW, BOX
    }
	public enum Sounds {
		PaddleHit, BrickBlow, Win
	}
	
	public enum GameState{
		INITIAL,RUNNING,PAUSED,STOPPED,REPLAY,UNDOING
	}
	
	public enum Direction {
		LEFT, RIGHT, TOP, BOTTOM
	}
	
	public enum ImageSource{
		PRIMARY,SECONDARY
	}
	public enum playerModes{
		SINGLE,MULTI
	}
}

