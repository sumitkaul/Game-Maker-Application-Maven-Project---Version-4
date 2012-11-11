package team3.a9.imagewizard;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTextField;

import jwizardcomponent.JWizardComponents;

class LastWizardPanel extends LabelWizardPanel {

    private JLabel fileInfo = new JLabel();
    private JLabel tagsInfo = new JLabel();
//	public LastWizardPanel(JWizardComponents wizardComponents, File file, String tags) {
//		super(wizardComponents, "Please add some tags for the image/sound:");
//		fileInfo.setText("File info");
//		tagsInfo.setText("Tag info");
//		this.add(fileInfo);
//		this.add(tagsInfo);
//		
//		
////		
////		
////		this.add(jTextField, 
////				new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
////				, GridBagConstraints.CENTER, GridBagConstraints.BOTH
////				, new Insets(0, 0, 0, 0), 0, 0));
////		setPanelTitle("Assign Tags to the Image");
//	}

    public LastWizardPanel(JWizardComponents wizardComponents) {
        super(wizardComponents, "Info:");
        fileInfo.setText("File info ");
        tagsInfo.setText("Tag info");
        this.add(fileInfo);
        this.add(tagsInfo);


//	
//	
//	this.add(jTextField, 
//			new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
//			, GridBagConstraints.CENTER, GridBagConstraints.BOTH
//			, new Insets(0, 0, 0, 0), 0, 0));
//	setPanelTitle("Assign Tags to the Image");
    }

    public void update() {
        setNextButtonEnabled(false);
        setFinishButtonEnabled(true);
        setBackButtonEnabled(true);
    }

    public void updateImagePreview(File file) {
        fileInfo.setText(file.getName());
    }

    public void next() {
    }
    @Override
    public void back() {
        switchPanel(Wizard.PANEL_TAG);
    }

}