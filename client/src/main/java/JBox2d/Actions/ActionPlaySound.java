package JBox2d.Actions;

import game.engine.slick2d.player.SoundRepo;
import java.io.Serializable;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Sound;

@SuppressWarnings("serial")
public class ActionPlaySound implements JBoxGameAction, Serializable {

	private String soundFile;
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(ActionPlaySound.class);

	public ActionPlaySound(String soundFile) {
		super();
		this.soundFile = soundFile;
	}

	@Override
	public void doAction(Body body) {
		Sound sound = SoundRepo.getSounds().get(soundFile);
		sound.play();

	}

	public String getSoundFile() {
		return soundFile;
	}


}
