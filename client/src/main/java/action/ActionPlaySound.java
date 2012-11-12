package action;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

import model.SpriteModel;

public class ActionPlaySound implements GameAction{

    private String soundFile;
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(ActionPlaySound.class);
	
	public ActionPlaySound(String soundFile){
		super();
		this.soundFile = soundFile;
	}

    @Override
    public void doAction(SpriteModel model) {
        URL url = getClass().getClassLoader().getResource(soundFile);
        AudioClip sound = Applet.newAudioClip(url);
        sound.play();
    }
}
