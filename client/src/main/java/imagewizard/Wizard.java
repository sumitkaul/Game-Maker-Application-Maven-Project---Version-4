package imagewizard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.text.html.ImageView;

import jwizardcomponent.Utilities;
import jwizardcomponent.JWizardPanel;
import jwizardcomponent.JWizardComponents;
import jwizardcomponent.frame.JWizardFrame;

import net.miginfocom.swing.MigLayout;

public class Wizard extends JWizardFrame {

    public static final int PANEL_CHOOSER = 0;
    public static final int PANEL_TAG = 1;
    public static final int PANEL_LAST = 2;
    public final int WWIDTH = 500;
    public final int WHEIGTH = 300;
    //keep reference to chooserWizardPanel in order to obtain an image/sound
    private ChooserWizardPanel chooserWizardPanel;
    //keep reference to lastWizardPanel in order to obtain tags
    private TagWizardPanel tagWizardPanel;
    private LastWizardPanel lastWizardPanel;
    private File file;
    private String tags;

    public Wizard() {
        super();
        init();
    }

    private void init() {
        //setSize(500,300);
        setSize(WWIDTH, WHEIGTH);
        this.setTitle("Image/Sound Import Wizard");
        //setChooserWizardPanel(new ChooserWizardPanel(getWizardComponents()));
        chooserWizardPanel = new ChooserWizardPanel(getWizardComponents());
        getWizardComponents().addWizardPanel(PANEL_CHOOSER, getChooserWizardPanel());

        tagWizardPanel = new TagWizardPanel(getWizardComponents());
        lastWizardPanel = new LastWizardPanel(getWizardComponents());
        
        tagWizardPanel.setNextStep(lastWizardPanel);
        tagWizardPanel.setPreviousStep(chooserWizardPanel);

        setTagWizardPanel(tagWizardPanel);
        getWizardComponents().addWizardPanel(PANEL_TAG, getTagWizardPanel());
        setLastWizardPanel(lastWizardPanel);
        //setLastWizardPanel(new LastWizardPanel(getWizardComponents()));
        getWizardComponents().addWizardPanel(PANEL_LAST, getLastWizardPanel());
        Utilities.centerComponentOnScreen(this);
        
    }
    
    public ImageData getImageData(){
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	File file = chooserWizardPanel.getFile();
    	String tags = tagWizardPanel.getTags();
    	ImageData imageData = new ImageData();
    	imageData.setFile(file);
    	imageData.setTags(tags);
    	return imageData;
    }
    
    
    

//    public static void main(String[] args) {
//        Wizard wizard = new Wizard();
//        wizard.setVisible(true);
//    }

    public JWizardPanel getChooserWizardPanel() {
        return chooserWizardPanel;
    }

    public void setChooserWizardPanel(ChooserWizardPanel chooserWizardPanel) {
        this.chooserWizardPanel = chooserWizardPanel;
    }

    public JWizardPanel getTagWizardPanel() {
        return tagWizardPanel;
    }

    public void setTagWizardPanel(TagWizardPanel tagWizardPanel) {
        this.tagWizardPanel = tagWizardPanel;
    }

    public JWizardPanel getLastWizardPanel() {
        return lastWizardPanel;
    }

    public void setLastWizardPanel(LastWizardPanel lastWizardPanel) {
        this.lastWizardPanel = lastWizardPanel;
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
